package edu.harvard.data.client.generators.canvas;

import java.io.File;

public class TableVersion {

  private final String packageName;
  private final String classPrefix;
  private File sourceDir;

  public TableVersion(final String packageName, final String classPrefix) {
    this.packageName = packageName;
    this.classPrefix = classPrefix;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getClassPrefix() {
    return classPrefix;
  }

  public void setSourceDir(final File sourceDir) {
    this.sourceDir = sourceDir;
  }

  public File getSourceDir() {
    return sourceDir;
  }
}
