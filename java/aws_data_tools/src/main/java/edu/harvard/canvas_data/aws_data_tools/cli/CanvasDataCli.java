package edu.harvard.canvas_data.aws_data_tools.cli;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

import edu.harvard.canvas_data.aws_data_tools.DataConfiguration;
import edu.harvard.canvas_data.aws_data_tools.DumpInfo;
import edu.harvard.canvas_data.aws_data_tools.FatalError;
import edu.harvard.canvas_data.aws_data_tools.VerificationException;
import edu.harvard.data.client.DataConfigurationException;
import edu.harvard.data.client.canvas.api.UnexpectedApiResponseException;

public class CanvasDataCli {

  private static final Logger log = LogManager.getLogger();

  @Argument(handler = SubCommandHandler.class, usage = "Top-level command.")
  @SubCommands({ @SubCommand(name = "download", impl = DownloadDumpCommand.class),
    @SubCommand(name = "verify", impl = VerifyDumpCommand.class),
    @SubCommand(name = "javasdk", impl = GenerateJavaSdkCommand.class),
    @SubCommand(name = "compareschemas", impl = CompareSchemasCommand.class), })
  public Command cmd;

  public static void main(final String[] args) {
    final CanvasDataCli parser = new CanvasDataCli();
    final CmdLineParser cli = new CmdLineParser(parser);
    try {
      cli.parseArgument(args);
    } catch (final CmdLineException e) {
      System.exit(ReturnStatus.ARGUMENT_ERROR.getCode());
    }
    if (parser.cmd == null) {
      System.exit(ReturnStatus.ARGUMENT_ERROR.getCode());
    } else {
      DataConfiguration config = null; // Config is set or System.exit is
      // called.
      try {
        config = DataConfiguration.getConfiguration("secure.properties");
        DumpInfo.init(config.getDumpInfoDynamoTable());
        log.info("Using table " + config.getDumpInfoDynamoTable() + " for dump info.");
      } catch (final DataConfigurationException e) {
        log.fatal("Invalid configuration. Field", e);
        System.exit(ReturnStatus.CONFIG_ERROR.getCode());
      } catch (final IOException e) {
        log.fatal("IO error: " + e.getMessage(), e);
        System.exit(ReturnStatus.IO_ERROR.getCode());
      }
      try {
        final ReturnStatus status = parser.cmd.execute(config);
        System.exit(status.getCode());
      } catch (final IOException e) {
        log.fatal("IO error: " + e.getMessage(), e);
        System.exit(ReturnStatus.IO_ERROR.getCode());
      } catch (final VerificationException e) {
        log.fatal("Verification error: " + e.getMessage(), e);
        System.exit(ReturnStatus.VERIFICATION_FAILURE.getCode());
      } catch (final IllegalArgumentException e) {
        log.fatal(e.getMessage(), e);
        System.exit(ReturnStatus.ARGUMENT_ERROR.getCode());
      } catch (final UnexpectedApiResponseException e) {
        log.fatal("API error: " + e.getMessage(), e);
        System.exit(ReturnStatus.API_ERROR.getCode());
      } catch (final FatalError e) {
        log.fatal(e);
        System.exit(e.getStatus().getCode());
      } catch (final Throwable t) {
        log.fatal("Unexpected error: " + t.getMessage(), t);
        System.exit(ReturnStatus.UNKNOWN_ERROR.ordinal());
      }
    }
  }
}
