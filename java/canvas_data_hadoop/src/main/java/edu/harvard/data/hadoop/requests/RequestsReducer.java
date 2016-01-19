package edu.harvard.data.hadoop.requests;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.csv.CSVPrinter;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import edu.harvard.data.client.FormatLibrary;
import edu.harvard.data.client.FormatLibrary.Format;
import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.canvas.extended.ExtendedRequests;

public class RequestsReducer extends Reducer<LongWritable, ExtendedRequests, LongWritable, Text> {

  private final TableFormat format;
  private MultipleOutputs<LongWritable, Text> outputs;

  public RequestsReducer() {
    this.format = new FormatLibrary().getFormat(Format.CanvasDataFlatFiles);
  }

  @Override
  protected void setup(final Context context) {
    outputs = new MultipleOutputs<LongWritable, Text>(context);
  }

  @Override
  public void cleanup(final Context context) throws IOException, InterruptedException {
    outputs.close();
  }

  @Override
  public void reduce(final LongWritable courseId, final Iterable<ExtendedRequests> values,
      final Context context) throws IOException, InterruptedException {
    for (final ExtendedRequests request : values) {
      final StringWriter writer = new StringWriter();
      try (final CSVPrinter printer = new CSVPrinter(writer, format.getCsvFormat())) {
        printer.printRecord(request.getFieldsAsList(format));
      }
      final LongWritable timestamp = new LongWritable(request.getTimestamp().getTime());
      final Text csvText = new Text(writer.toString().trim());
      context.write(timestamp, csvText);
      if (request.getUserId() != null && request.getUserId() == 134926641248969922L) {
        outputs.write("tlttools", timestamp, csvText);
      }
    }
  }
}