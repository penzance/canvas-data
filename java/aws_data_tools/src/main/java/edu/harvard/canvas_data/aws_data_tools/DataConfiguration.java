package edu.harvard.canvas_data.aws_data_tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.data.client.DataConfigurationException;

public class DataConfiguration implements AWSCredentialsProvider {

  private String canvasApiKey;
  private String canvasApiSecret;
  private String canvasDataHost;
  private File scratchDir;
  private S3ObjectId canvasDataArchiveKey;
  private String awsKey;
  private String awsSecretKey;

  public static DataConfiguration getConfiguration(final String propertiesFileName)
      throws IOException, DataConfigurationException {
    final ClassLoader cl = DataConfiguration.class.getClassLoader();
    Properties properties;
    try (final InputStream in = cl.getResourceAsStream(propertiesFileName)) {
      if (in == null) {
        throw new FileNotFoundException(propertiesFileName);
      }
      properties = new Properties();
      properties.load(in);
    }
    final DataConfiguration config = new DataConfiguration();
    config.canvasApiKey = getConfigParameter(properties, "canvas_data_api_key");
    config.canvasApiSecret = getConfigParameter(properties, "canvas_data_api_secret");
    config.canvasDataHost = getConfigParameter(properties, "canvas_data_host");
    config.scratchDir = new File(getConfigParameter(properties, "scratch_dir"));
    final String dataBucket = getConfigParameter(properties, "canvas_data_bucket");
    config.canvasDataArchiveKey = new S3ObjectId(dataBucket,
        getConfigParameter(properties, "canvas_data_archive_key"));
    config.awsKey = getConfigParameter(properties, "aws_access_key_id");
    config.awsSecretKey = getConfigParameter(properties, "aws_secret_access_key");
    return config;
  }

  private static String getConfigParameter(final Properties properties, final String key)
      throws DataConfigurationException {
    final String param = (String) properties.get(key);
    if (param == null) {
      throw new DataConfigurationException("Configuration file missing " + key);
    }
    return param;
  }

  private DataConfiguration() {
  }

  public String getCanvasApiKey() {
    return canvasApiKey;
  }

  public String getCanvasApiSecret() {
    return canvasApiSecret;
  }

  public String getCanvasDataHost() {
    return canvasDataHost;
  }

  public File getScratchDir() {
    return scratchDir;
  }

  public S3ObjectId getCanvasDataArchiveKey() {
    return canvasDataArchiveKey;
  }

  @Override
  public AWSCredentials getCredentials() {
    return new BasicAWSCredentials(awsKey, awsSecretKey);
  }

  @Override
  public void refresh() {
  }
}