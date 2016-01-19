package edu.harvard.data.hadoop.requests;

import java.io.IOException;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import edu.harvard.data.client.FormatLibrary;
import edu.harvard.data.client.FormatLibrary.Format;
import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.analysis_utils.UserAgentParser;
import edu.harvard.data.client.canvas.extended.ExtendedRequests;
import edu.harvard.data.client.canvas.original.Requests;
import net.sf.uadetector.ReadableUserAgent;

public class RequestsMapper extends Mapper<Object, Text, LongWritable, ExtendedRequests> {

  private final TableFormat format;
  private final UserAgentParser uaParser;

  public RequestsMapper() {
    this.format = new FormatLibrary().getFormat(Format.CanvasDataFlatFiles);
    this.uaParser = new UserAgentParser();
  }

  @Override
  public void map(final Object key, final Text value, final Context context) throws IOException, InterruptedException {
    final CSVParser parser = CSVParser.parse(value.toString(), format.getCsvFormat());
    for (final CSVRecord csvRecord : parser.getRecords()) {
      final Requests request = new Requests(format, csvRecord);
      final ExtendedRequests extended = new ExtendedRequests(request);
      parseUserAgent(extended);
      if (request.getCourseId() == null) {
        context.write(new LongWritable(-1L), extended);
      } else {
        context.write(new LongWritable(request.getCourseId()), extended);
      }
    }
  }

  private void parseUserAgent(final ExtendedRequests request) {
    final String agentString = request.getUserAgent();
    if (agentString != null) {
      final ReadableUserAgent agent = uaParser.parse(agentString);
      request.setBrowser(agent.getName());
      request.setOs(agent.getOperatingSystem().getName());
    }
  }

}
