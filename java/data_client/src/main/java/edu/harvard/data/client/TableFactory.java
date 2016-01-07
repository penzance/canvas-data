package edu.harvard.data.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.amazonaws.services.s3.model.S3ObjectId;

public interface TableFactory {

  TableReader<? extends DataTable> getDelimitedTableReader(String table, TableFormat format, File file) throws IOException;

  TableWriter<? extends DataTable> getDelimitedTableWriter(String table, TableFormat format, Path resolve) throws IOException;

  TableReader<? extends DataTable> getS3TableReader(String table, TableFormat format, AwsUtils aws,
      S3ObjectId obj, File tempDir) throws IOException;

}
