package edu.harvard.data.client.generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.data.client.AwsUtils;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFactory;
import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaColumn;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaTable;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaType;
import edu.harvard.data.client.io.FileTableReader;
import edu.harvard.data.client.io.FileTableWriter;
import edu.harvard.data.client.io.S3TableReader;
import edu.harvard.data.client.io.TableReader;
import edu.harvard.data.client.io.TableWriter;

public class JavaBindingGenerator {

  private static final String TABLE_PACKAGE = "edu.harvard.data.client.canvas.tables";
  private static final String CLIENT_PACKAGE = "edu.harvard.data.client";
  private static final Logger log = Logger.getLogger("Canvas Data");

  private final File dir;
  private final File srcDir;
  private final Map<String, CanvasDataSchemaTable> tables;
  private final String version;

  public JavaBindingGenerator(final File dir, final CanvasDataSchema schema) {
    this.dir = dir;
    this.srcDir = new File(dir,
        new String("src.main.java." + TABLE_PACKAGE).replaceAll("\\.", File.separator));
    this.version = schema.getVersion();
    this.tables = schema.getSchema();
  }

  public void generate() throws IOException {
    log.info("Generating Java bindings in " + dir);
    log.info("Source directory: " + srcDir);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (srcDir.exists()) {
      log.info("Deleting: " + srcDir);
      FileUtils.deleteDirectory(srcDir);
    }
    srcDir.mkdirs();

    copyPomXml();

    final List<String> tableNames = generateTableNames();

    generateModels();
    generateCanvasTableEnum(tableNames);
    generateCanvasTableFactory(tableNames);
  }

  private void copyPomXml() throws IOException {
    final File pomFile = new File(dir, "pom.xml");
    final File pomTmp = new File(dir, "pom.xml.tmp");
    log.info("Creating pom.xml file at " + pomFile);
    try (
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("java_bindings/pom.xml.template");
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
        BufferedWriter out = new BufferedWriter(new FileWriter(pomTmp))) {
      String line = in.readLine();
      while (line != null) {
        out.write(line.replaceAll("\\$schema_version_number", version) + "\n");
        line = in.readLine();
      }
    }
    pomFile.delete();
    pomTmp.renameTo(pomFile);
  }

  private void copyTemplateFile(final String resource, final File outputFile) throws IOException {
    log.info("Copying " + resource + " to " + outputFile);
    try (InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile))) {
      String line = in.readLine();
      while (line != null) {
        out.write(line + "\n");
        line = in.readLine();
      }
    }
  }

  // Generate one class per schema table
  private void generateModels() throws FileNotFoundException {
    for (final String name : tables.keySet()) {
      final String className = javaClass(tables.get(name).getTableName());
      final File classFile = new File(srcDir, className + ".java");
      try (final PrintStream out = new PrintStream(new FileOutputStream(classFile))) {
        generateTableClass(className, tables.get(name), out);
      }
    }
  }

  // Generate a sorted list of table names for the switch tables in the enum and
  // factory classes
  private List<String> generateTableNames() {
    final List<String> tableNames = new ArrayList<String>();
    for (final String name : tables.keySet()) {
      tableNames.add(tables.get(name).getTableName());
    }
    Collections.sort(tableNames);
    return tableNames;
  }

  // Create the CanvasTable Enum type
  private void generateCanvasTableEnum(final List<String> tableNames) throws FileNotFoundException {
    final File tableEnumFile = new File(srcDir, "CanvasTable.java");
    try (final PrintStream out = new PrintStream(new FileOutputStream(tableEnumFile))) {
      generateTableEnum(out, tableNames);
    }
  }

  // Create the CanvasTableFactory class
  private void generateCanvasTableFactory(final List<String> tableNames)
      throws FileNotFoundException {
    final File tableFactoryFile = new File(srcDir, "CanvasTableFactory.java");
    try (final PrintStream out = new PrintStream(new FileOutputStream(tableFactoryFile))) {
      generateCanvasTableFactory(out, tableNames);
    }
  }

  private void generateCanvasTableFactory(final PrintStream out, final List<String> tableNames) {
    log.info("Generating CanvasTableFactory");
    writeFileHeader(out);
    out.println("package " + TABLE_PACKAGE + ";");
    out.println();
    out.println("import " + File.class.getName() + ";");
    out.println("import " + IOException.class.getName() + ";");
    out.println();
    out.println("import " + S3ObjectId.class.getName() + ";");
    out.println();
    out.println("import " + AwsUtils.class.getName() + ";");
    out.println("import " + DataTable.class.getName() + ";");
    out.println("import " + FileTableReader.class.getName() + ";");
    out.println("import " + FileTableWriter.class.getName() + ";");
    out.println("import " + S3TableReader.class.getName() + ";");
    //    out.println("import " + S3TableWriter.class.getName() + ";");
    out.println("import " + TableFactory.class.getName() + ";");
    out.println("import " + TableFormat.class.getName() + ";");
    out.println("import " + TableReader.class.getName() + ";");
    out.println("import " + TableWriter.class.getName() + ";");
    out.println();
    out.println("public class CanvasTableFactory implements TableFactory {");
    out.println();
    out.println("  @Override");
    out.println(
        "  public TableReader<? extends DataTable> getTableReader(String table, TableFormat format, File file) throws IOException {");
    out.println("    switch(table) {");
    for (final String name : tableNames) {
      final String className = javaClass(name);
      out.println("    case \"" + name + "\":");
      out.println("      return new FileTableReader<" + className + ">(" + className
          + ".class, format, file, \"" + name + "\");");
    }
    out.println("    }");
    out.println("    return null;");
    out.println("  }");
    out.println();
    out.println("  @Override");
    out.println(
        "  public TableReader<? extends DataTable> getTableReader(final String table, final TableFormat format, final AwsUtils aws, final S3ObjectId obj, final File tempDir) throws IOException {");
    out.println("    switch(table) {");
    for (final String name : tableNames) {
      final String className = javaClass(name);
      out.println("    case \"" + name + "\":");
      out.println("      return new S3TableReader<" + className + ">(aws, " + className
          + ".class, format, obj, \"" + name + "\", tempDir);");
    }
    out.println("    }");
    out.println("    return null;");
    out.println("  }");
    out.println();
    out.println("  @Override");
    out.println(
        "  public TableWriter<? extends DataTable> getTableWriter(String table, TableFormat format, File file) throws IOException {");
    out.println("    switch(table) {");
    for (final String name : tableNames) {
      final String className = javaClass(name);
      out.println("    case \"" + name + "\":");
      out.println("      return new FileTableWriter<" + className + ">(" + className
          + ".class, format, \"" + name + "\", file);");
    }
    out.println("    }");
    out.println("    return null;");
    out.println("  }");
    out.println("}");
  }

  private void generateTableEnum(final PrintStream out, final List<String> tableNames) {
    log.info("Generating CanvasTable Enum");
    writeFileHeader(out);
    out.println("package " + TABLE_PACKAGE + ";");
    out.println();
    out.println("  import " + CLIENT_PACKAGE + ".DataTable;");
    out.println();
    out.println("public enum CanvasTable {");
    for (int i = 0; i < tableNames.size(); i++) {
      final String name = tableNames.get(i);
      final String className = javaClass(name);
      out.print("  " + className + "(\"" + name + "\", " + className + ".class)");
      out.println(i == (tableNames.size() - 1) ? ";" : ",");
    }
    out.println();
    out.println("  private final String sourceName;");
    out.println("  private final Class<? extends DataTable> tableClass;");
    out.println();
    out.println(
        "  private CanvasTable(final String sourceName, Class<? extends DataTable> tableClass) {");
    out.println("    this.sourceName = sourceName;");
    out.println("    this.tableClass = tableClass;");
    out.println("  }");
    out.println();
    out.println("  public String getSourceName() {");
    out.println("    return sourceName;");
    out.println("  }");
    out.println();
    out.println("  public Class<? extends DataTable> getTableClass() {");
    out.println("    return tableClass;");
    out.println("  }");
    out.println();
    out.println("  public static CanvasTable fromSourceName(String sourceName) {");
    out.println("    switch(sourceName) {");
    for (final String name : tableNames) {
      final String className = javaClass(name);
      out.println("    case \"" + name + "\": return " + className + ";");
    }
    out.println("    default: throw new RuntimeException(\"Unknown table name \" + sourceName);");
    out.println("    }");
    out.println("  }");
    out.println("}");
  }

  private void generateTableClass(final String className, final CanvasDataSchemaTable table,
      final PrintStream out) {
    log.info("Generating table " + className);
    writeFileHeader(out);
    out.println("package " + TABLE_PACKAGE + ";");
    out.println();
    if (hasTimestampColumn(table)) {
      out.println("import java.sql.Timestamp;");
    }
    if (hasDateColumn(table)) {
      out.println("import java.util.Date;");
      out.println("import java.text.ParseException;");
    }
    out.println("import java.util.ArrayList;");
    out.println("import java.util.List;");
    out.println();
    out.println("import org.apache.commons.csv.CSVRecord;");
    out.println("import " + CLIENT_PACKAGE + ".DataTable;");
    out.println("import " + CLIENT_PACKAGE + ".TableFormat;");
    out.println();

    out.println("public class " + className + " implements DataTable {");
    for (final CanvasDataSchemaColumn column : table.getColumns()) {
      final String typeName = javaType(column.getType());
      final String variableName = javaVariable(column.getName());
      out.println("  private " + typeName + " " + variableName + ";");
    }
    out.println();
    if (hasDateColumn(table)) {
      out.println("  public " + className
          + "(final TableFormat format, final CSVRecord record) throws ParseException {");
    } else {
      out.println("  public " + className + "(final TableFormat format, final CSVRecord record) {");
    }
    int columnIdx = 0;
    for (final CanvasDataSchemaColumn column : table.getColumns()) {
      generateParseFromCsv(out, column, columnIdx++);
    }
    out.println("  }");
    out.println();

    for (final CanvasDataSchemaColumn column : table.getColumns()) {
      final String typeName = javaType(column.getType());
      final String methodName = "get" + javaClass(column.getName());
      final String variableName = javaVariable(column.getName());
      writeComment(column.getDescription(), 2, out, true);
      out.println("  public " + typeName + " " + methodName + "() {");
      out.println("    return this." + variableName + ";");
      out.println("  }");
      out.println();
    }

    out.println("  @Override");
    out.println("  public List<Object> getFieldsAsList(final TableFormat formatter) {");

    out.println("    final List<Object> fields = new ArrayList<Object>();");
    for (final CanvasDataSchemaColumn column : table.getColumns()) {
      final String variableName = javaVariable(column.getName());
      if (isTimestamp(column) || isDate(column)) {
        out.println("    fields.add(formatter.formatTimestamp(" + variableName + "));");
      } else {
        out.println("    fields.add(" + variableName + ");");
      }
    }
    out.println("    return fields;");
    out.println("  }");

    out.println();
    out.println("  public static List<String> getFieldNames() {");
    out.println("    final List<String> fields = new ArrayList<String>();");
    for (final CanvasDataSchemaColumn column : table.getColumns()) {
      out.println("      fields.add(\"" + column.getName() + "\");");
    }
    out.println("    return fields;");
    out.println("  }");

    out.println("}");

  }

  private boolean isTimestamp(final CanvasDataSchemaColumn c) {
    return (c.getType() == CanvasDataSchemaType.Timestamp
        || c.getType() == CanvasDataSchemaType.DateTime);
  }

  private boolean isDate(final CanvasDataSchemaColumn c) {
    return c.getType() == CanvasDataSchemaType.Date;
  }

  private boolean hasDateColumn(final CanvasDataSchemaTable table) {
    for (final CanvasDataSchemaColumn c : table.getColumns()) {
      if (isDate(c)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasTimestampColumn(final CanvasDataSchemaTable table) {
    for (final CanvasDataSchemaColumn c : table.getColumns()) {
      if (isTimestamp(c)) {
        return true;
      }
    }
    return false;
  }

  private void generateParseFromCsv(final PrintStream out, final CanvasDataSchemaColumn column,
      final int idx) {
    String parseMethod = null;
    final String extraParams = "";
    switch (column.getType()) {

    case BigInt:
      parseMethod = "Long.valueOf";
      break;
    case Boolean:
      parseMethod = "Boolean.valueOf";
      break;
    case DateTime:
    case Timestamp:
      parseMethod = "Timestamp.valueOf";
      break;
    case Date:
      parseMethod = "format.getDateFormat().parse";
      break;
    case DoublePrecision:
      parseMethod = "Double.valueOf";
      break;
    case Int:
    case Integer:
      parseMethod = "Integer.valueOf";
      break;
    case Text:
    case VarChar:
      break;
    default:
      throw new RuntimeException("Unknown data type: " + column.getType());
    }
    final String getRecord = "record.get(" + idx + ")";
    final String varName = javaVariable(column.getName());
    if (parseMethod == null) {
      out.println("    this." + varName + " = " + getRecord + ";");
    } else {
      final String tmpName = "$" + varName;
      out.println("    String " + tmpName + " = " + getRecord + ";");
      out.println("    if (" + tmpName + " != null && " + tmpName + ".length() > 0) {");
      out.println(
          "      this." + varName + " = " + parseMethod + "(" + tmpName + extraParams + ");");
      out.println("    }");
    }
  }

  private void writeFileHeader(final PrintStream out) {
    writeComment("This file was generated on "
        + new SimpleDateFormat("M-dd-yyyy hh:mm:ss").format(new Date()) + ". Do not manually edit.",
        0, out, false);
    writeComment("This class is based on Version " + version + " of the Canvas Data schema", 0, out,
        false);
    out.println();
  }

  private void writeComment(final String text, final int indent, final PrintStream out,
      final boolean javadoc) {
    if (text == null) {
      return;
    }
    if (javadoc) {
      writeIndent(indent, out);
      out.println("/**");
    }
    final int maxLine = 80;
    startNewCommentLine(indent, out, javadoc);
    int currentLine = indent + 3;
    for (final String word : text.split(" ")) {
      currentLine += word.length() + 1;
      if (currentLine > maxLine) {
        out.println();
        startNewCommentLine(indent, out, javadoc);
        currentLine = indent + 3 + word.length();
      }
      out.print(word + " ");
    }
    if (javadoc) {
      out.println();
      writeIndent(indent, out);
      out.print(" */");
    }
    out.println();
  }

  private void writeIndent(final int indent, final PrintStream out) {
    for (int i = 0; i < indent; i++) {
      out.print(" ");
    }
  }

  private int startNewCommentLine(final int indent, final PrintStream out, final boolean javadoc) {
    writeIndent(indent, out);
    if (javadoc) {
      out.print(" * ");
      return 2;
    } else {
      out.print("// ");
      return 3;
    }
  }

  private String javaClass(final String str) {
    String className = "";
    for (final String part : str.split("_")) {
      className += part.substring(0, 1).toUpperCase() + part.substring(1);
    }
    return className;
  }

  private String javaType(final CanvasDataSchemaType dataType) {
    switch (dataType) {
    case BigInt:
      return "Long";
    case Boolean:
      return "Boolean";
    case Date:
      return "Date";
    case DateTime:
    case Timestamp:
      return "Timestamp";
    case DoublePrecision:
      return "Double";
    case Int:
    case Integer:
      return "Integer";
    case Text:
    case VarChar:
      return "String";
    default:
      throw new RuntimeException("Unknown data type: " + dataType);
    }
  }

  private String javaVariable(final String name) {
    final String[] parts = name.split("_");
    String variableName = parts[0];
    for (int i = 1; i < parts.length; i++) {
      final String part = parts[i];
      variableName += part.substring(0, 1).toUpperCase() + part.substring(1);
    }
    if (variableName.equals("public")) {
      variableName = "_public";
    }
    if (variableName.equals("default")) {
      variableName = "_default";
    }
    return variableName;
  }

}
