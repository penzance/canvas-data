package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.Argument;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.canvas_data.aws_data_tools.DataConfiguration;
import edu.harvard.canvas_data.aws_data_tools.DumpInformation;
import edu.harvard.data.client.AwsUtils;
import edu.harvard.data.client.DataClient;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.CanvasApiClient;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.SchemaDifference;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public class CompareSchemasCommand implements Command {

  private static final Logger log = LogManager.getLogger();

  @Argument(index = 0, usage = "S3 bucket holding the dump.", metaVar = "s3_bucket_name", required = true)
  private String bucket;

  @Argument(index = 1, usage = "S3 key for the dump directory.", metaVar = "/path/to/dump", required = true)
  private String key;

  @Argument(index = 2, usage = "Expected schema version.", metaVar = "1.0.0", required = true)
  private String expectedVersion;

  @Override
  public ReturnStatus execute(final DataConfiguration config) throws IOException, DataConfigurationException, UnexpectedApiResponseException {
    final CanvasApiClient api = new DataClient().getCanvasApiClient(config.getCanvasDataHost(),
        config.getCanvasApiKey(), config.getCanvasApiSecret());
    final AwsUtils aws = new AwsUtils();
    final S3ObjectId dumpObj = AwsUtils.key(bucket, key);
    final DumpInformation dumpInfo = DumpInformation.read(aws, AwsUtils.key(dumpObj, "dump_info.json"));

    if (expectedVersion.equals(dumpInfo.getDump().getSchemaVersion())) {
      log.info("Schema version numbers match.");
      return ReturnStatus.OK;
    }
    final CanvasDataSchema schema1 = api.getSchema(expectedVersion);
    final CanvasDataSchema schema2 = api.getSchema(dumpInfo.getDump().getSchemaVersion());
    final List<SchemaDifference> differences = schema1.calculateDifferences(schema2);
    if (differences.isEmpty()) {
      log.info("Schemas are identical.");
      return ReturnStatus.OK;
    }

    for (final SchemaDifference difference : differences) {
      if (difference.getField() == null || !difference.getField().equals("description")) {
        log.warn("Schema difference: " + difference);
      }
    }
    return ReturnStatus.VERIFICATION_FAILURE;
  }

  @Override
  public String getDescription() {
    return "Compare two versions of the Canvas Data schema.";
  }

}
