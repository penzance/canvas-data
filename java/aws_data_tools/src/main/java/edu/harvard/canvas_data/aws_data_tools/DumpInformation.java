package edu.harvard.canvas_data.aws_data_tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.services.s3.model.S3ObjectId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.harvard.data.client.canvas.api.CanvasDataDump;

public class DumpInformation {

  private static final ObjectMapper mapper;

  static {
    mapper = new ObjectMapper();
    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  private final CanvasDataDump dump;
  private final Date downloadStart;
  private final Date downloadComplete;

  @JsonCreator
  public DumpInformation(
      @JsonProperty("dump") final CanvasDataDump dump,
      @JsonProperty("downloadStart") final Date downloadStart,
      @JsonProperty("downloadComplete") final Date downloadComplete) {
    this.dump = dump;
    this.downloadStart = downloadStart;
    this.downloadComplete = downloadComplete;
  }

  public CanvasDataDump getDump() {
    return dump;
  }

  public Date getDownloadStart() {
    return downloadStart;
  }

  public Date getDownloadComplete() {
    return downloadComplete;
  }

  public static File getFile(final File dir) {
    return new File(dir, "dump_info.json");
  }

  public static S3ObjectId getKey(final S3ObjectId archiveObj) {
    return AwsUtils.key(archiveObj, "dump_info.json");
  }

  public void write(final AwsUtils aws, final S3ObjectId obj) throws IOException {
    aws.writeJson(obj, this);
  }

  public static DumpInformation read(final AwsUtils aws, final S3ObjectId obj) throws IOException {
    return aws.readJson(obj, DumpInformation.class);
  }
}
