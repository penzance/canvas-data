package edu.harvard.data.client.generators.canvas;

import java.io.File;

import edu.harvard.data.client.canvas.api.CanvasDataSchema;

public class TableVersion {

  private String packageName;
  private String prefix;
  private File sourceLocation;
  private CanvasDataSchema schema;

  public void setSourceDir(final File sourceDir) {
    this.sourceLocation = sourceDir;
  }

  public void setPackageName(final String packageName) {
    this.packageName = packageName;
  }

  public void setPrefix(final String prefix) {
    this.prefix = prefix;
  }

  public void setSchema(final CanvasDataSchema schema) {
    this.schema = schema;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getPrefix() {
    return prefix;
  }

  public File getSourceLocation() {
    return sourceLocation;
  }

  public CanvasDataSchema getSchema() {
    return schema;
  }
}
