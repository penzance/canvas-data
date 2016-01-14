package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.Argument;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.canvas_data.aws_data_tools.DataConfiguration;
import edu.harvard.canvas_data.aws_data_tools.DumpInformation;
import edu.harvard.canvas_data.aws_data_tools.Verifier;
import edu.harvard.data.client.AwsUtils;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.FormatLibrary;
import edu.harvard.data.client.TableFactory;
import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;
import edu.harvard.data.client.canvas.tables.CanvasTableFactory;

public class VerifyDumpCommand implements Command {

  private static final Logger log = LogManager.getLogger();

  @Argument(index = 0, usage = "S3 bucket holding the dump.", metaVar = "s3_bucket_name", required = true)
  private String bucket;

  @Argument(index = 1, usage = "S3 key for the dump directory.", metaVar = "/path/to/dump", required = true)
  private String key;

  @Override
  public ReturnStatus execute(final DataConfiguration config)
      throws IOException, DataConfigurationException, UnexpectedApiResponseException {
    final AwsUtils aws = new AwsUtils();
    final TableFactory factory = new CanvasTableFactory();
    final FormatLibrary formats = new FormatLibrary();
    final TableFormat format = formats.getFormat(FormatLibrary.Format.CanvasDataFlatFiles);
    final S3ObjectId dumpObj = AwsUtils.key(bucket, key);
    final S3ObjectId dumpInfoObj = AwsUtils.key(bucket, key, "dump_info.json");

    final DumpInformation dumpInfo = aws.readJson(dumpInfoObj, DumpInformation.class);
    final Verifier verifier = new Verifier(aws, factory, format);
    final long errors = verifier.verifyDump(dumpObj);
    if (errors == 0) {
      dumpInfo.setVerified(true);
      dumpInfo.write(aws, dumpInfoObj);
      return ReturnStatus.OK;
    } else {
      log.error("Encountered " + errors + " errors when verifying dump at " + dumpObj);
      return ReturnStatus.VERIFICATION_FAILURE;
    }
  }

  @Override
  public String getDescription() {
    return "Verify the file structure of a Canvas data dump.";
  }

}
