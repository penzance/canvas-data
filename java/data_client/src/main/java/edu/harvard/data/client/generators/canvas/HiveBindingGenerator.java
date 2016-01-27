package edu.harvard.data.client.generators.canvas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaColumn;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaTable;
import edu.harvard.data.client.generators.SchemaTransformer;

public class HiveBindingGenerator {

  private static final Logger log = LogManager.getLogger();

  private final CanvasDataSchema schema;
  private final File dir;

  public HiveBindingGenerator(final File dir, final CanvasDataSchema schema) {
    this.dir = dir;
    this.schema = schema;
  }

  public void generate() throws IOException {
    log.info("Generating Hive bindings in " + dir);
    // Create the project directory
    if (!dir.exists()) {
      dir.mkdirs();
    }

    final File createTableFile = new File(dir, "create_tables_" + schema.getVersion() + ".q");
    final File copyTablesFile = new File(dir, "copy_tables_" + schema.getVersion() + ".q");

    final SchemaTransformer transformer = new SchemaTransformer(3);
    transformer.setPrefixes("", "in_", "out_");
    transformer.setSchemas(schema, JavaBindingGenerator.PHASE_ONE_ADDITIONS_JSON,
        JavaBindingGenerator.PHASE_TWO_ADDITIONS_JSON);

    try (final PrintStream out = new PrintStream(new FileOutputStream(createTableFile))) {
      generateCreateTablesFile(out, transformer);
    }

    try (final PrintStream out = new PrintStream(new FileOutputStream(copyTablesFile))) {
      generateCopyTablesFile(out, transformer);
    }

  }

  private void generateCopyTablesFile(final PrintStream out, final SchemaTransformer transformer) {
    final TableVersion phase1 = transformer.getPhase(1);
    final TableVersion phase2 = transformer.getPhase(2);

    for (final String tableKey : phase1.getSchema().getSchema().keySet()) {
      if (phase2.getSchema().getSchema().get(tableKey).hasNewlyGeneratedElements()) {
        out.print("-- ");
      }
      final String tableName = phase1.getSchema().getSchema().get(tableKey).getTableName();
      final String table1 = phase1.getPrefix() + tableName;
      final String table2 = phase2.getPrefix() + tableName;
      out.println("INSERT OVERWRITE TABLE " + table2 + " SELECT * FROM " + table1 + ";");
    }
  }

  private void generateCreateTablesFile(final PrintStream out,
      final SchemaTransformer transformer) {
    final TableVersion phase1 = transformer.getPhase(1);
    final TableVersion phase2 = transformer.getPhase(2);
    final Map<String, CanvasDataSchemaTable> tables1 = phase1.getSchema().getSchema();
    final Map<String, CanvasDataSchemaTable> tables2 = phase2.getSchema().getSchema();
    final List<String> tableKeys1 = new ArrayList<String>(tables1.keySet());
    final List<String> tableKeys2 = new ArrayList<String>(tables2.keySet());
    Collections.sort(tableKeys1);
    Collections.sort(tableKeys2);

    for (final String tableKey : tableKeys1) {
      final CanvasDataSchemaTable table = tables1.get(tableKey);
      dropTable(out, "staging_" + phase1.getPrefix() + table.getTableName());
      createStagingTable(out, phase1.getPrefix(), table);
      out.println();
      dropTable(out, phase1.getPrefix() + table.getTableName());
      createTable(out, phase1.getPrefix(), table);
      out.println();
    }

    for (final String tableKey : tableKeys2) {
      final CanvasDataSchemaTable table = tables2.get(tableKey);
      dropTable(out, phase2.getPrefix() + table.getTableName());
      createOutputTable(out, phase2.getPrefix(), table);
      out.println();
    }
  }

  private void dropTable(final PrintStream out, final String tableName) {
    out.println("DROP TABLE IF EXISTS " + tableName + " PURGE;");
  }

  private void createStagingTable(final PrintStream out, final String prefix,
      final CanvasDataSchemaTable table) {
    final String tableName = prefix + table.getTableName();
    out.println("CREATE EXTERNAL TABLE staging_" + tableName + " (");
    listFields(out, table);
    out.println(")");
    out.println("  ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\t' LINES TERMINATED BY '\\n'");
    out.println("  LOCATION '${INPUT}/" + table.getTableName() + "/';");
  }

  private void createTable(final PrintStream out, final String prefix,
      final CanvasDataSchemaTable table) {
    final String tableName = prefix + table.getTableName();
    out.println("CREATE TABLE " + tableName + " (");
    listFields(out, table);
    out.println(")");
    out.println("  STORED AS SEQUENCEFILE;");
    out.println(
        "INSERT OVERWRITE TABLE " + tableName + " SELECT * FROM staging_" + tableName + ";");
  }

  private void createOutputTable(final PrintStream out, final String prefix,
      final CanvasDataSchemaTable table) {
    final String tableName = prefix + table.getTableName();
    out.println("CREATE EXTERNAL TABLE " + tableName + " (");
    listFields(out, table);
    out.println(")");
    out.println("  ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\t' LINES TERMINATED By '\\n'");
    out.println("  STORED AS TEXTFILE");
    out.println("  LOCATION '${OUTPUT}/" + table.getTableName() + "/';");
  }

  private void listFields(final PrintStream out, final CanvasDataSchemaTable table) {
    final List<CanvasDataSchemaColumn> columns = table.getColumns();
    for (int i = 0; i < columns.size(); i++) {
      final CanvasDataSchemaColumn column = columns.get(i);
      out.print("    " + column.getName() + " " + column.getHiveType());
      if (i < columns.size() - 1) {
        out.println(",");
      } else {
        out.println();
      }
    }
  }
}
