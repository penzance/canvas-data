package edu.harvard.data.client.canvas.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CanvasDataSchema {
  private final String version;
  private final Map<String, CanvasDataSchemaTable> schema;

  @JsonCreator
  public CanvasDataSchema(@JsonProperty("version") final String version,
      @JsonProperty("schema") final Map<String, CanvasDataSchemaTable> schema) {
    this.version = version;
    this.schema = schema;
  }

  public String getVersion() {
    return version;
  }

  public Map<String, CanvasDataSchemaTable> getSchema() {
    return schema;
  }

  public void calculateDifferences(final CanvasDataSchema schema2,
      final List<SchemaDifference> differences) {
    final List<String> tableKeys = new ArrayList<String>(schema.keySet());
    Collections.sort(tableKeys);
    for (final String tableKey : tableKeys) {
      final CanvasDataSchemaTable table = schema.get(tableKey);
      if (schema2.schema.containsKey(tableKey)) {
        table.calculateDifferences(schema2.schema.get(tableKey), differences);
      } else {
        differences.add(new SchemaDifference("Dropped table " + table.getTableName()));
      }
    }

    final List<String> tableKeys2 = new ArrayList<String>(schema2.schema.keySet());
    Collections.sort(tableKeys2);
    for (final String key : tableKeys2) {
      if (!tableKeys.contains(key)) {
        differences
        .add(new SchemaDifference("Added table " +  schema2.schema.get(key).getTableName()));
      }
    }
  }

}