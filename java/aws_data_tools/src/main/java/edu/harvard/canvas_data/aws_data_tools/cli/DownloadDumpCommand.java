package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;

import org.apache.log4j.Logger;

import edu.harvard.canvas_data.aws_data_tools.AwsUtils;
import edu.harvard.canvas_data.aws_data_tools.Configuration;
import edu.harvard.canvas_data.aws_data_tools.DumpInformation;
import edu.harvard.canvas_data.aws_data_tools.DumpManager;
import edu.harvard.data.client.DataClient;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.CanvasApiClient;
import edu.harvard.data.client.canvas.api.CanvasDataDump;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public class DownloadDumpCommand implements Command {

  private static final Logger log = Logger.getLogger(DownloadDumpCommand.class);

  @Override
  public ReturnStatus execute(final Configuration config) throws IOException, DataConfigurationException, UnexpectedApiResponseException {
    final AwsUtils aws = new AwsUtils();
    final DumpManager manager = new DumpManager(config, aws);
    final CanvasApiClient api = new DataClient().getCanvasApiClient(config.getCanvasDataHost(),
        config.getCanvasApiKey(), config.getCanvasApiSecret());
    for (final CanvasDataDump dump : api.getDumps()) {
      if (manager.needToSaveDump(dump)) {
        final CanvasDataDump fullDump = api.getDump(dump.getDumpId());
        final CanvasDataSchema schema = api.getSchema(fullDump.getSchemaVersion());
        log.info("Saving " + fullDump.getSequence());
        final DumpInformation dumpInfo = manager.saveDump(api, fullDump);
        manager.archiveDump(fullDump, dumpInfo, schema);
        break;
      }
    }
    return ReturnStatus.OK;
  }

  @Override
  public String getDescription() {
    return "Download and archive all Canvas data dumps that are not already archived.";
  }

}
