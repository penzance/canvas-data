package edu.harvard.data.client.generators;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import edu.harvard.data.client.canvas.api.CanvasDataSchema;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaColumn;
import edu.harvard.data.client.canvas.api.CanvasDataSchemaTable;

public class SchemaGenerator {

  private final CanvasDataSchema schema;

  public SchemaGenerator(final CanvasDataSchema schema) {
    this.schema = schema;
  }

  public void generateRedshiftSchema(final PrintStream out) throws IOException {
    for (final CanvasDataSchemaTable table : schema.getSchema().values()) {
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

  public void generateHiveSchema(final PrintStream out) {
    for (final CanvasDataSchemaTable table : schema.getSchema().values()) {
      out.println("CREATE EXTERNAL TABLE IF NOT EXISTS " + table.getTableName() + "(");
      final List<CanvasDataSchemaColumn> columns = table.getColumns();
      for (int i=0; i<columns.size(); i++) {
        final CanvasDataSchemaColumn column = columns.get(i);
        out.print("    " + column.getName() + " " + column.getHiveType());
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
