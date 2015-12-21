package edu.harvard.canvas_data.aws_data_tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectId;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class AwsUtils {

  private static final Logger log = Logger.getLogger(AwsUtils.class);

  private final AmazonS3Client client;
  private final ObjectMapper jsonMapper;

  public AwsUtils() {
    this.client = new AmazonS3Client(new ProfileCredentialsProvider());
    this.jsonMapper = new ObjectMapper();
    this.jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
    this.jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public AmazonS3 getClient() {
    return client;
  }

  public boolean isFile(final S3ObjectId obj) {
    final ObjectListing objects = client.listObjects(obj.getBucket(), obj.getKey());
    final List<S3ObjectSummary> summaries = objects.getObjectSummaries();
    return summaries.size() == 1;
  }

  public List<S3ObjectSummary> listKeys(final S3ObjectId obj) {
    log.debug("Listing keys for " + obj);
    ObjectListing objects = client.listObjects(obj.getBucket(), obj.getKey());
    final List<S3ObjectSummary> summaries = new ArrayList<S3ObjectSummary>();
    while (objects.isTruncated()) {
      for (final S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
        summaries.add(objectSummary);
        log.debug("  key: " + objectSummary.getKey());
      }
      objects = client.listNextBatchOfObjects(objects);
    }
    return summaries;
  }

  public static S3ObjectId key(final String bucket, final String... keys) {
    String key = "";
    for (final String k : keys) {
      key += "/" + k;
    }
    return new S3ObjectId(bucket, key.substring(1));
  }

  public static S3ObjectId key(final S3ObjectId obj, final String... keys) {
    String key = obj.getKey();
    for (final String k : keys) {
      key += "/" + k;
    }
    return new S3ObjectId(obj.getBucket(), key);
  }

  public <T> T readJson(final S3ObjectId obj, final Class<T> cls) throws IOException {
    log.debug("Reading JSON at " + obj);
    S3Object object = null;
    try {
      object = client.getObject(obj.getBucket(), obj.getKey());
      return jsonMapper.readValue(object.getObjectContent(), cls);
    } finally {
      if (object != null) {
        object.close();
      }
    }
  }

  public void writeJson(final S3ObjectId obj, final Object jsonObj) throws IOException {
    log.debug("Writing JSON to " + obj);
    final String json = jsonMapper.writeValueAsString(jsonObj);
    final byte[] bytes = json.getBytes();
    final ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(bytes.length);

    client.putObject(obj.getBucket(), obj.getKey(), new ByteArrayInputStream(bytes), metadata);
  }

}