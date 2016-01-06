package edu.harvard.canvas_data.aws_data_tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.canvas_data.aws_data_tools.cli.ReturnStatus;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.CanvasApiClient;
import edu.harvard.data.client.canvas.api.CanvasDataArtifact;
import edu.harvard.data.client.canvas.api.CanvasDataDump;
import edu.harvard.data.client.canvas.api.CanvasDataFile;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public class DumpManager {

  private static final Logger log = Logger.getLogger(DumpManager.class);

  private final DataConfiguration config;
  private final AwsUtils aws;

  public DumpManager(final DataConfiguration config, final AwsUtils aws) {
    this.config = config;
    this.aws = aws;
  }

  public boolean needToSaveDump(final CanvasDataDump dump) throws IOException {
    final String dirName = String.format("%05d", dump.getSequence());
    final S3ObjectId archiveObj = AwsUtils.key(config.getCanvasDataArchiveKey(), dirName);
    final S3ObjectId dumpInfoObj = DumpInformation.getKey(archiveObj);
    if (!aws.isFile(AwsUtils.key(dumpInfoObj))) {
      log.info("Dump needs to be saved; dump info file " + dumpInfoObj + " does not exist.");
      return true;
    }
    final DumpInformation info = DumpInformation.read(aws, dumpInfoObj);
    if (info.getDownloadComplete() == null) {
      log.info("Dump needs to be saved; previous download to " + archiveObj.getKey()
      + " did not complete.");
      return true;
    }
    final Date downloadStart = info.getDownloadStart();
    // Re-download any dump that was updated less than an hour before it was
    // downloaded before.
    final Date conservativeStart = new Date(downloadStart.getTime() - (60 * 60 * 1000));
    if (conservativeStart.before(dump.getUpdatedAt())) {
      log.info("Dump needs to be saved; downloaded to " + archiveObj
          + " less than an hour after it was last updated.");
      return true;
    }
    log.info("Dump does not need to be saved; already exists at " + archiveObj + ".");
    return false;
  }

  public DumpInformation saveDump(final CanvasApiClient api, final CanvasDataDump dump)
      throws IOException, UnexpectedApiResponseException, DataConfigurationException {
    final Date downloadStart = new Date();
    final File directory = getScratchDumpDir(dump);
    final boolean created = directory.mkdirs();
    log.debug("Creating directory " + directory + ": " + created);
    final Map<String, CanvasDataArtifact> artifactsByTable = dump.getArtifactsByTable();
    final List<String> tables = new ArrayList<String>(artifactsByTable.keySet());
    for (final String table : tables) {
      int fileIndex = 0;
      final File tableDir = new File(directory, table);
      final CanvasDataArtifact artifact = artifactsByTable.get(table);
      log.info("Dumping " + table + " to " + tableDir);
      final List<CanvasDataFile> files = artifact.getFiles();
      for (int i = 0; i < files.size(); i++) {
        final CanvasDataFile file = files.get(i);
        final CanvasDataDump refreshedDump = api.getDump(dump.getDumpId());
        final CanvasDataArtifact refreshedArtifact = refreshedDump.getArtifactsByTable().get(table);
        final CanvasDataFile refreshedFile = refreshedArtifact.getFiles().get(i);
        if (!refreshedFile.getFilename().equals(file.getFilename())) {
          throw new FatalError(ReturnStatus.API_ERROR,
              "Mismatch in file name for refreshed dump. Expected" + refreshedFile.getFilename()
              + ", got " + file.getFilename());
        }
        final String filename = artifact.getTableName() + "-" + String.format("%05d", fileIndex++)
        + ".gz";
        final File downloadFile = new File(tableDir, filename);
        refreshedFile.download(downloadFile);
        archiveFile(dump, table, downloadFile);
      }
    }
    final Date downloadEnd = new Date();
    return new DumpInformation(dump, downloadStart, downloadEnd);
  }

  public void archiveFile(final CanvasDataDump dump, final String table, final File downloadFile) {
    final S3ObjectId archiveObj = getArchiveDumpObj(dump);
    final S3ObjectId infoObj = AwsUtils.key(archiveObj, table, downloadFile.getName());
    aws.getClient().putObject(infoObj.getBucket(), infoObj.getKey(), downloadFile);
    log.info("Uploaded " + downloadFile + " to " + infoObj);
    downloadFile.delete();
  }

  public void finalizeDump(final CanvasDataDump dump, final DumpInformation dumpInfo,
      final CanvasDataSchema schema) throws IOException {
    final S3ObjectId archiveObj = getArchiveDumpObj(dump);
    aws.writeJson(AwsUtils.key(archiveObj, "schema.json"), schema);
    aws.writeJson(DumpInformation.getKey(archiveObj), dumpInfo);
  }

  public void deleteTemporaryDump(final CanvasDataDump dump) throws IOException {
    final File directory = getScratchDumpDir(dump);
    FileUtils.deleteDirectory(directory);
  }

  private File getScratchDumpDir(final CanvasDataDump dump) {
    final String dirName = String.format("%05d", dump.getSequence());
    return new File(config.getScratchDir(), dirName);
  }

  public S3ObjectId getArchiveDumpObj(final CanvasDataDump dump) {
    final String dirName = String.format("%05d", dump.getSequence());
    return AwsUtils.key(config.getCanvasDataArchiveKey(), dirName);
  }

}
