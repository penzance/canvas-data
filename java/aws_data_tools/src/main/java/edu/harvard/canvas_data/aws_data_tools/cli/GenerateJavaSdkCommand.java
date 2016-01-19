package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.File;
import java.io.IOException;

import org.kohsuke.args4j.Argument;

import edu.harvard.canvas_data.aws_data_tools.DataConfiguration;
import edu.harvard.data.client.DataClient;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.CanvasApiClient;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;
import edu.harvard.data.client.generators.canvas.JavaBindingGenerator;

public class GenerateJavaSdkCommand implements Command {

  @Argument(index = 0, usage = "Schema version.", metaVar = "1.0.0", required = true)
  private String version;

  @Argument(index = 1, usage = "Base directory to put the source code (do not include src/main/java).", metaVar = "/path/to/dir", required = true)
  private File output;

  @Override
  public ReturnStatus execute(final DataConfiguration config)
      throws DataConfigurationException, IOException, UnexpectedApiResponseException {
    final CanvasApiClient api = new DataClient().getCanvasApiClient(config.getCanvasDataHost(),
        config.getCanvasApiKey(), config.getCanvasApiSecret());
    final CanvasDataSchema schema = api.getSchema(version);
    new JavaBindingGenerator(output, schema).generate();
    return ReturnStatus.OK;
  }

  @Override
  public String getDescription() {
    return "Generate the SDK table classes to a specified directory.";
  }

}
