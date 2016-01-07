package edu.harvard.data.client;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.amazonaws.services.s3.model.S3ObjectId;

public class S3TableReader<T extends DataTable> implements TableReader<T> {

  DelimitedTableReader<T> reader;
  private final String tableName;
  private final S3ObjectId obj;
  private final TableFormat format;
  private final Class<T> tableType;
  private final File tempFile;
  private final AwsUtils aws;

  public S3TableReader(final AwsUtils aws, final Class<T> tableType, final TableFormat format, final S3ObjectId obj,
      final String tableName, final File tempDir) {
    this.aws = aws;
    this.tableType = tableType;
    this.format = format;
    this.obj = obj;
    this.tableName = tableName;
    this.tempFile = new File(tempDir + File.separator + obj.getKey().replaceAll("/", File.separator));
  }

  private DelimitedTableReader<T> getReader() throws IOException {
    if (reader == null) {
      if (tempFile.exists()) {
        tempFile.delete();
      }
      tempFile.getParentFile().mkdirs();
      aws.getFile(obj, tempFile);
      reader = new DelimitedTableReader<T>(tableType, format, tempFile, tableName);
    }
    return reader;
  }

  @Override
  public Iterator<T> iterator() {
    try {
      return getReader().iterator();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
      tempFile.delete();
    }
    reader = null;
  }

  @Override
  public void reset() throws IOException {
    getReader().reset();
  }

  @Override
  public Class<T> getTableType() {
    return tableType;
  }

  @Override
  public long size() throws IOException {
    return getReader().size();
  }

  @Override
  public DataSetInfoTable generateTableInfo() throws IOException {
    return getReader().generateTableInfo();
  }

}
