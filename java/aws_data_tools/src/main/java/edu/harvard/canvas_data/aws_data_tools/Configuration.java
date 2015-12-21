package edu.harvard.canvas_data.aws_data_tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.data.client.DataConfigurationException;

public class Configuration {

  private String canvasApiKey;
  private String canvasApiSecret;
  private String canvasDataHost;
  private File scratchDir;
  private S3ObjectId canvasDataArchiveKey;

  public static Configuration getConfiguration(final String propertiesFileName)
      throws IOException, DataConfigurationException {
    final ClassLoader cl = Configuration.class.getClassLoader();
    Properties properties;
    try (final InputStream in = cl.getResourceAsStream(propertiesFileName)) {
      if (in == null) {
        throw new FileNotFoundException(propertiesFileName);
      }
      properties = new Properties();
      properties.load(in);
    }
    final Configuration config = new Configuration();
    config.canvasApiKey = getConfigParameter(properties, "canvas_data_api_key");
    config.canvasApiSecret = getConfigParameter(properties, "canvas_data_api_secret");
    config.canvasDataHost = getConfigParameter(properties, "canvas_data_host");
    config.scratchDir = new File(getConfigParameter(properties, "scratch_dir"));
    final String dataBucket = getConfigParameter(properties, "canvas_data_bucket");
    config.canvasDataArchiveKey = new S3ObjectId(dataBucket,
        getConfigParameter(properties, "canvas_data_archive_key"));
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

  private Configuration() {
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

}
