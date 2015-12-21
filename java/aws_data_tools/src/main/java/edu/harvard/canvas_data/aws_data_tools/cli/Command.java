package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;

import edu.harvard.canvas_data.aws_data_tools.Configuration;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public interface Command {

  String getDescription();

  ReturnStatus execute(Configuration config) throws IOException, UnexpectedApiResponseException, DataConfigurationException;

}
