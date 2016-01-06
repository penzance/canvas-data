package edu.harvard.data.client;

import java.io.IOException;
import java.util.Map;

public class S3DataSetReader implements DataSetReader {

  @Override
  public Map<String, TableReader<? extends DataTable>> getTables() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T extends DataTable> TableReader<T> getTable(final String tableName, final Class<T> tableClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TableFormat getFormat() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DataSetInfo generateDataSetInfo() throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T extends DataTable> void replaceTable(final String tableName, final TableReader<T> reader)
      throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }
}
