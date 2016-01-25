package edu.harvard.data.client.generators.canvas;

import java.io.File;

import edu.harvard.data.client.canvas.api.CanvasDataSchema;

public class TableVersion {

  private String packageName;
  private String classPrefix;
  private File sourceDir;
  private CanvasDataSchema schema;

  public void setSourceDir(final File sourceDir) {
    this.sourceDir = sourceDir;
  }

  public void setPackageName(final String packageName) {
    this.packageName = packageName;
  }

  public void setClassPrefix(final String classPrefix) {
    this.classPrefix = classPrefix;
  }

  public void setSchema(final CanvasDataSchema schema) {
    this.schema = schema;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getClassPrefix() {
    return classPrefix;
  }

  public File getSourceDir() {
    return sourceDir;
  }

  public CanvasDataSchema getSchema() {
    return schema;
  }
}
