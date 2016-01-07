package edu.harvard.data.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.s3.model.S3ObjectId;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import edu.harvard.data.client.canvas.tables.CanvasTableFactory;

public class S3DataSetReader implements DataSetReader {

  private final AwsUtils aws;
  private final S3ObjectId dumpObj;
  private final TableFormat format;
  private final File tempDir;
  private final CanvasTableFactory factory;
  private final Map<String, TableReader<? extends DataTable>> tables;

  public S3DataSetReader(final AwsUtils aws, final S3ObjectId dumpObj, final TableFormat format, final File tempDir) {
    this.aws = aws;
    this.dumpObj = dumpObj;
    this.format = format;
    this.tempDir = tempDir;
    this.factory = new CanvasTableFactory();
    tables = new HashMap<String, TableReader<? extends DataTable>>();
  }

  @Override
  public Map<String, TableReader<? extends DataTable>> getTables() throws IOException {
    final Map<String, TableReader<? extends DataTable>> tableMap = new HashMap<String, TableReader<? extends DataTable>>();
    for (final S3ObjectId dir : aws.listDirectories(dumpObj)) {
      final String tableName = dir.getKey().substring(dir.getKey().lastIndexOf("/") + 1);
      final TableReader<? extends DataTable> table = getReaderForTable(dir, tableName);
      if (table != null && !tables.containsKey(tableName)) {
        tableMap.put(tableName, table);
      }
    }
    return tableMap;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataTable> TableReader<T> getTable(final String tableName,
      final Class<T> tableClass) throws IOException {
    if (!tables.containsKey(tableName)) {
      final S3ObjectId dir = AwsUtils.key(dumpObj, tableName);
      tables.put(tableName, getReaderForTable(dir, tableName));
    }
    return (TableReader<T>) tables.get(tableName);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private TableReader<? extends DataTable> getReaderForTable(final S3ObjectId dir,
      final String tableName) throws IOException {
    final List<TableReader<? extends DataTable>> tableReaders = new ArrayList<TableReader<? extends DataTable>>();
    for (final S3ObjectSummary fileObjSummary : aws.listKeys(dir)) {
      final S3ObjectId fileObj = AwsUtils.key(fileObjSummary.getBucketName(), fileObjSummary.getKey());
      tableReaders.add(factory.getS3TableReader(tableName, format, aws, fileObj, tempDir));
    }
    if (tableReaders.size() != 0) {
      if (tableReaders.size() == 1) {
        return tableReaders.get(0);
      } else {
        return new CombinedTableReader(tableReaders, tableReaders.get(0).getTableType(), tableName);
      }
    }
    return null;
  }

  @Override
  public TableFormat getFormat() {
    return format;
  }

  @Override
  public DataSetInfo generateDataSetInfo() throws IOException {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public <T extends DataTable> void replaceTable(final String tableName,
      final TableReader<T> reader) throws IOException {
    tables.put(tableName, reader);
  }

  @Override
  public void close() throws IOException {
    for (final TableReader<? extends DataTable> table : tables.values()) {
      table.close();
    }
  }
}
