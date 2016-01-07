package edu.harvard.canvas_data.aws_data_tools;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.harvard.data.client.DataSetReader;
import edu.harvard.data.client.DataSetWriter;
import edu.harvard.data.client.FileDataSetReader;
import edu.harvard.data.client.FileDataSetWriter;
import edu.harvard.data.client.FormatLibrary;
import edu.harvard.data.client.FormatLibrary.Format;
import edu.harvard.data.client.RecordParsingException;
import edu.harvard.data.client.TableFactory;
import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.canvas.tables.CanvasTableFactory;

public class CanvasDumpVerifier {

  private static final Logger log = LogManager.getLogger();

  private final FormatLibrary formats;
  private final Path scratchDirectory;
  private final Path dumpDirectory;
  private final TableFormat inFormat;
  private final TableFormat outFormat;

  public CanvasDumpVerifier(final Path dumpDirectory, final Path scratchDirectory) {
    this.dumpDirectory = dumpDirectory;
    this.scratchDirectory = scratchDirectory;
    formats = new FormatLibrary();
    inFormat = formats.getFormat(Format.CanvasDataFlatFiles);
    outFormat = formats.getFormat(Format.DecompressedCanvasDataFlatFiles);
  }

  public void compareDumps() {

  }

  private void parseAndOutput() throws IOException, VerificationException {
    final TableFactory factory = new CanvasTableFactory();
    try (final DataSetReader in = new FileDataSetReader(dumpDirectory, inFormat, factory);
        final DataSetWriter out = new FileDataSetWriter(scratchDirectory, outFormat, factory);) {
      out.pipe(in);
    } catch (final RecordParsingException e) {
      //      errors.write("Error parsing " + e.getRecord().toString() + ": " + e.getMessage());
      throw new VerificationException(e);
    }
  }

  private void compareFiles() {
    final List<Path> dump1 = Collections.singletonList(dumpDirectory);
    final List<Path> dump2 = Collections.singletonList(scratchDirectory);

    //    VerificationUtils.textualCompareDumps(dump1, dump2, errors);

  }
}
