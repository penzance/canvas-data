package edu.harvard.data.client.generators.canvas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaColumn;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaTable;
import edu.harvard.data.client.generators.SchemaTransformer;

public class RedshiftBindingGenerator {

  private static final Logger log = LogManager.getLogger();

  private final CanvasDataSchema schema;
  private final File dir;

  public RedshiftBindingGenerator(final File dir, final CanvasDataSchema schema) {
    this.dir = dir;
    this.schema = schema;
  }

  public void generate() throws IOException {
    log.info("Generating Redshift bindings in " + dir);
    // Create the project directory
    if (!dir.exists()) {
      dir.mkdirs();
    }

    final File createTableFile = new File(dir, "create_tables_" + schema.getVersion() + ".sql");

    final SchemaTransformer transformer = new SchemaTransformer(3);
    transformer.setSchemas(schema, JavaBindingGenerator.PHASE_ONE_ADDITIONS_JSON,
        JavaBindingGenerator.PHASE_TWO_ADDITIONS_JSON);

    try (final PrintStream out = new PrintStream(new FileOutputStream(createTableFile))) {
      generateCreateTableFile(out, transformer.getPhase(2));
    }
  }

  private void generateCreateTableFile(final PrintStream out, final TableVersion phase) {
    for (final CanvasDataSchemaTable table : phase.getSchema().getSchema().values()) {
      out.println("CREATE TABLE " + table.getTableName() + "(");
      final List<CanvasDataSchemaColumn> columns = table.getColumns();
      for (int i=0; i<columns.size(); i++) {
        final CanvasDataSchemaColumn column = columns.get(i);
        out.print("    " + column.getName() + " " + column.getRedshiftType());
        if (i < columns.size() - 1) {
          out.println(",");
        } else {
          out.println();
        }
      }
      out.println(");");
    }
  }

}
