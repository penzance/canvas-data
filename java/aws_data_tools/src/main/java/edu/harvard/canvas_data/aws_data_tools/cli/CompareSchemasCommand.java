package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;

import edu.harvard.canvas_data.aws_data_tools.DataConfiguration;
import edu.harvard.data.client.DataClient;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.CanvasApiClient;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.SchemaDifference;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public class CompareSchemasCommand implements Command {

  @Argument(index = 0, usage = "First schema version to compare.", metaVar = "1.0.0", required = true)
  private String oldVersion;

  @Argument(index = 1, usage = "Second schema version to compare.", metaVar = "1.0.1", required = true)
  private String newVersion;

  @Override
  public ReturnStatus execute(final DataConfiguration config) throws IOException, DataConfigurationException, UnexpectedApiResponseException {
    final CanvasApiClient api = new DataClient().getCanvasApiClient(config.getCanvasDataHost(),
        config.getCanvasApiKey(), config.getCanvasApiSecret());
    final CanvasDataSchema schema1 = api.getSchema(oldVersion);
    final CanvasDataSchema schema2 = api.getSchema(newVersion);
    final List<SchemaDifference> differences = new ArrayList<SchemaDifference>();
    schema1.calculateDifferences(schema2, differences);
    if (differences.size() == 0) {
      System.out.println("Schemas are identical");
    }
    for (final SchemaDifference difference : differences) {
      if (difference.getField() == null || !difference.getField().equals("description")) {
        System.out.println(difference);
      }
    }
    return ReturnStatus.OK;
  }

  @Override
  public String getDescription() {
    return "Compare two versions of the Canvas Data schema.";
  }

}
