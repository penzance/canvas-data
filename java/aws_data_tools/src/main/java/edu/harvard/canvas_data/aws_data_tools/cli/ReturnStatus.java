package edu.harvard.canvas_data.aws_data_tools.cli;

public enum ReturnStatus {
  OK (0, "Application returned correctly"),
  ARGUMENT_ERROR (1, "Invalid command line options passed to application"),
  BAD_DATA_SET (2, "The data set passed to the application is not properly formed"),
  IO_ERROR (3, "I/O error. See logs for details."),
  CONFIG_ERROR (4, "Invalid application configuration. Check the values in secure.properties"),
  VERIFICATION_FAILURE (6, "Data set did not properly validate"),
  API_ERROR (7, "Unexpected response from external API"),

  UNKNOWN_ERROR (255, "Unknown error. See logs for details");

  private int code;
  private String description;

  ReturnStatus(final int code, final String description) {
    this.code = code;
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
