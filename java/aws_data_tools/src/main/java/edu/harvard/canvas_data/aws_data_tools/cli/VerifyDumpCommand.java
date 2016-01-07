package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.canvas_data.aws_data_tools.DataConfiguration;
import edu.harvard.data.client.AwsUtils;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.DataSetReader;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.FormatLibrary;
import edu.harvard.data.client.FormatLibrary.Format;
import edu.harvard.data.client.S3DataSetReader;
import edu.harvard.data.client.TableReader;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public class VerifyDumpCommand implements Command {

  private static final Logger log = LogManager.getLogger();

  @Override
  public ReturnStatus execute(final DataConfiguration config)
      throws IOException, DataConfigurationException, UnexpectedApiResponseException {
    final AwsUtils aws = new AwsUtils();
    final FormatLibrary formats = new FormatLibrary();
    final S3ObjectId dumpObj = AwsUtils.key("tltcanvas-dev-dataprojectstorage-dev",
        "canvas/dumps/00079");
    try (DataSetReader in = new S3DataSetReader(aws, dumpObj, formats.getFormat(Format.CanvasDataFlatFiles), config.getScratchDir())) {
      final Map<String, TableReader<? extends DataTable>> tables = in.getTables();
      for (final String tableName : tables.keySet()) {
        final TableReader<? extends DataTable> table = tables.get(tableName);
        table.iterator();
      }
    }
    return ReturnStatus.OK;
  }

  @Override
  public String getDescription() {
    return "Verify the file structure of a Canvas data dump.";
  }

}
