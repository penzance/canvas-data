package edu.harvard.data.client.generators.canvas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.harvard.data.client.FormatLibrary;
import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaColumn;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaTable;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaType;

public class JavaBindingGenerator {

  private static final Logger log = LogManager.getLogger();

  static final String CLIENT_PACKAGE = "edu.harvard.data.client";
  private static final String ORIGINAL_SCHEMA_PACKAGE = CLIENT_PACKAGE + ".canvas.original";
  private static final String EXTENDED_SCHEMA_PACKAGE = CLIENT_PACKAGE + ".canvas.extended";
  private static final String MERGED_SCHEMA_PACKAGE = CLIENT_PACKAGE + ".canvas.merged";
  private static final String EXTENDED_ADDITIONS_JSON = "java_bindings/extended_schema_additions.json";
  private static final String MERGED_ADDITIONS_JSON = "java_bindings/merged_schema_additions.json";
  private static final String POM_XML_TEMPLATE = "java_bindings/pom.xml.template";

  private final File dir;
  private final Map<String, CanvasDataSchemaTable> tables;
  private final String version;
  private final ObjectMapper jsonMapper;

  public JavaBindingGenerator(final File dir, CanvasDataSchema schema) {
    this.dir = dir;
    schema = new CanvasDataSchema(schema); // we're going to change the table
    // structure; make a copy first.
    this.version = schema.getVersion();
    this.tables = schema.getSchema();
    jsonMapper = new ObjectMapper();
    jsonMapper.setDateFormat(FormatLibrary.JSON_DATE_FORMAT);
  }

  // Generates a new Maven project in the directory passed to the constructor.
  // The project has a pom.xml file and three sets of bindings (one for each
  // stage of data processing):
  //
  // Original bindings are generated from the JSON schema provided by
  // Instructure (passed to the class constructor).
  //
  // Extended bindings are produced by the first EMR job which supplements the
  // existing data set with new calculated data. The new tables and fields are
  // specified in EXTENDED_ADDITION_JSON.
  //
  // Merged bindings are produced by the second EMR job, and result from the
  // merging of multiple data sets. The new tables and fields are specified in
  // MERGED_ADDITIONS_JSON.
  //
  public void generate() throws IOException {
    log.info("Generating Java bindings in " + dir);
    // Create the project directory
    if (!dir.exists()) {
      dir.mkdirs();
    }
    // Create the pom.xml file from a template in src/main/resources, with the
    // appropriate version number.
    copyPomXml();

    final File srcBase = new File(dir, "src/main/java");

    // Specify the three versions of the table bindings
    final TableVersion original = new TableVersion(ORIGINAL_SCHEMA_PACKAGE, "");
    final TableVersion extended = new TableVersion(EXTENDED_SCHEMA_PACKAGE, "Extended");
    final TableVersion merged = new TableVersion(MERGED_SCHEMA_PACKAGE, "Merged");
    original
    .setSourceDir(new File(srcBase, ORIGINAL_SCHEMA_PACKAGE.replaceAll("\\.", File.separator)));
    extended
    .setSourceDir(new File(srcBase, EXTENDED_SCHEMA_PACKAGE.replaceAll("\\.", File.separator)));
    merged.setSourceDir(new File(srcBase, MERGED_SCHEMA_PACKAGE.replaceAll("\\.", File.separator)));

    // Generate bindings for each step in the processing pipeline, updating the
    // schema with new tables and fields in between.
    generateTableSet(original, null);
    extendTableSchema(EXTENDED_ADDITIONS_JSON);
    generateTableSet(extended, original);
    extendTableSchema(MERGED_ADDITIONS_JSON);
    generateTableSet(merged, extended);
  }

  // Generate the bindings for one step in the processing pipeline. There are
  // three generators used:
  //
  // CanvasTableGenerator creates the CanvasTable enum type with a constant for
  // each table.
  //
  // TableFactoryGenerator creates the TableFactory subtype that lets us create
  // readers and writers dynamically.
  //
  // TableGenerator is run once per table, and creates the individual table
  // class.
  //
  private void generateTableSet(final TableVersion tableVersion, final TableVersion previousVersion)
      throws IOException {
    final File srcDir = tableVersion.getSourceDir();
    final String classPrefix = tableVersion.getClassPrefix();

    // Create the base directory where all of the classes will be generated
    log.info("Generating tables in " + srcDir);
    if (srcDir.exists()) {
      log.info("Deleting: " + srcDir);
      FileUtils.deleteDirectory(srcDir);
    }
    srcDir.mkdirs();
    final List<String> tableNames = generateTableNames();

    // Generate the CanvasTable enum.
    final File tableEnumFile = new File(srcDir, classPrefix + "CanvasTable.java");
    try (final PrintStream out = new PrintStream(new FileOutputStream(tableEnumFile))) {
      new CanvasTableGenerator(version, tableNames, tableVersion).generate(out);
    }

    // Generate the CanvasTableFactory class.
    final File tableFactoryFile = new File(srcDir, classPrefix + "CanvasTableFactory.java");
    try (final PrintStream out = new PrintStream(new FileOutputStream(tableFactoryFile))) {
      new TableFactoryGenerator(version, tableNames, tableVersion).generate(out);
    }

    // Generate a model class for each table.
    for (final String name : tables.keySet()) {
      final String className = javaClass(tables.get(name).getTableName(), classPrefix);
      final File classFile = new File(srcDir, className + ".java");
      try (final PrintStream out = new PrintStream(new FileOutputStream(classFile))) {
        new ModelClassGenerator(version, tableVersion, previousVersion, tables.get(name)).generate(out);
      }
    }
  }

  // Read the JSON file and add any new tables or fields to the schema. If a
  // table in the JSON file does not exist in the schema, it is created. If a
  // table does exist, any fields specified in the JSON are appended to the
  // field list for that table.
  private void extendTableSchema(final String jsonResource) throws IOException {
    final ClassLoader classLoader = this.getClass().getClassLoader();
    Map<String, CanvasDataSchemaTable> updates;
    try (final InputStream in = classLoader.getResourceAsStream(jsonResource)) {
      updates = jsonMapper.readValue(in, CanvasDataSchema.class).getSchema();
    }
    if (updates != null) {
      // Track tables and columns that were added in this update
      setNewGeneratedFlags(updates.values(), true);
      for (final String tableName : updates.keySet()) {
        final CanvasDataSchemaTable newTable = updates.get(tableName);
        if (!tables.containsKey(tableName)) {
          tables.put(tableName, newTable);
        } else {
          final CanvasDataSchemaTable originalTable = tables.get(tableName);
          originalTable.getColumns().addAll(newTable.getColumns());
        }
      }
    }
  }

  // Bulk-set the newGenerated flags on a set of tables.
  private void setNewGeneratedFlags(final Collection<CanvasDataSchemaTable> tableSet,
      final boolean flag) {
    for (final CanvasDataSchemaTable table : tableSet) {
      table.setNewGenerated(flag);
      for (final CanvasDataSchemaColumn column : table.getColumns()) {
        column.setNewGenerated(flag);
      }
    }
  }

  // Generate the pom.xml file for the Maven project, based off a template in
  // the src/main/resources directory. Arguably this method should use an XML
  // parser, but for the sake of replacing one variable this approach is
  // somewhat more lightweight.
  private void copyPomXml() throws IOException {
    final File pomFile = new File(dir, "pom.xml");
    final File pomTmp = new File(dir, "pom.xml.tmp");
    log.info("Creating pom.xml file at " + pomFile);
    try (
        InputStream inStream = this.getClass().getClassLoader()
        .getResourceAsStream(POM_XML_TEMPLATE);
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
        BufferedWriter out = new BufferedWriter(new FileWriter(pomTmp))) {
      String line = in.readLine();
      while (line != null) {
        // Replace the $schema_version_number variable in the template.
        out.write(line.replaceAll("\\$schema_version_number", version) + "\n");
        line = in.readLine();
      }
    }
    pomFile.delete();
    pomTmp.renameTo(pomFile);
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

  // Write a standard file header to warn future developers against editing the
  // generated files.
  static void writeFileHeader(final PrintStream out, final String version) {
    writeComment("This file was generated on "
        + new SimpleDateFormat("M-dd-yyyy hh:mm:ss").format(new Date()) + ". Do not manually edit.",
        0, out, false);
    writeComment("This class is based on Version " + version + " of the Canvas Data schema", 0, out,
        false);
    out.println();
  }

  // Output a comment string, propery formatted. Uses the double-slash format
  // unless 'javadoc' is set, in which case it will use the /**...*/ format.
  static void writeComment(final String text, final int indent, final PrintStream out,
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

  // Helper to indent comments properly
  static void writeIndent(final int indent, final PrintStream out) {
    for (int i = 0; i < indent; i++) {
      out.print(" ");
    }
  }

  static int startNewCommentLine(final int indent, final PrintStream out, final boolean javadoc) {
    writeIndent(indent, out);
    if (javadoc) {
      out.print(" * ");
      return 2;
    } else {
      out.print("// ");
      return 3;
    }
  }

  // Format a String into the CorrectJavaClassName format.
  static String javaClass(final String str, final String classPrefix) {
    String className = classPrefix;
    for (final String part : str.split("_")) {
      if (part.length() > 0) {
        className += part.substring(0, 1).toUpperCase()
            + (part.length() > 1 ? part.substring(1) : "");
      }
    }
    return className;
  }

  // Format a String into the correctJavaVariableName format.
  static String javaVariable(final String name) {
    final String[] parts = name.split("_");
    String variableName = parts[0].substring(0, 1).toLowerCase() + parts[0].substring(1);
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

  // Convert the types specified in the schema.json format into Java types.
  static String javaType(final CanvasDataSchemaType dataType) {
    switch (dataType) {
    case BigInt:
      return Long.class.getSimpleName();
    case Boolean:
      return Boolean.class.getSimpleName();
    case Date:
      return Date.class.getSimpleName();
    case DateTime:
    case Timestamp:
      return Timestamp.class.getSimpleName();
    case DoublePrecision:
      return Double.class.getSimpleName();
    case Int:
    case Integer:
      return Integer.class.getSimpleName();
    case Text:
    case VarChar:
      return String.class.getSimpleName();
    default:
      throw new RuntimeException("Unknown data type: " + dataType);
    }
  }

}
