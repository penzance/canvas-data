package edu.harvard.data.client.canvas.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CanvasDataSchemaTable {

  public enum DataWarehouseType {
    dimension, fact, both
  }

  private final DataWarehouseType dwType;
  private final String description;
  private final boolean incremental;
  private final String tableName;
  private final Map<String, String> hints; // "sort_key" : "timestamp" in
  // requests
  private List<CanvasDataSchemaColumn> columns;
  private final String seeAlso;
  private final String databasePath; // non-null in requests
  private final String originalTable; // non-null in requests

  @JsonCreator
  public CanvasDataSchemaTable(@JsonProperty("dw_type") final DataWarehouseType dwType,
      @JsonProperty("description") final String description,
      @JsonProperty("columns") final List<CanvasDataSchemaColumn> columns,
      @JsonProperty("incremental") final boolean incremental,
      @JsonProperty("tableName") final String tableName,
      @JsonProperty("hints") final Map<String, String> hints,
      @JsonProperty("data_base_path") final String databasePath,
      @JsonProperty("original_table") final String originalTable,
      @JsonProperty("see_also") final String seeAlso) {
    this.dwType = dwType;
    this.description = description;
    this.columns = columns;
    this.incremental = incremental;
    this.tableName = tableName;
    this.hints = hints;
    this.databasePath = databasePath;
    this.originalTable = originalTable;
    this.seeAlso = seeAlso;
  }

  public CanvasDataSchemaTable(final CanvasDataSchemaTable original) {
    this.dwType = original.dwType;
    this.description = original.description;
    this.incremental = original.incremental;
    this.tableName = original.tableName;
    this.hints = new HashMap<String, String>(original.hints);
    this.seeAlso = original.seeAlso;
    this.databasePath = original.databasePath;
    this.originalTable = original.originalTable;
    this.columns = new ArrayList<CanvasDataSchemaColumn>();
    for (final CanvasDataSchemaColumn column : original.columns) {
      this.columns.add(new CanvasDataSchemaColumn(column));
    }
  }

  public DataWarehouseType getDwType() {
    return dwType;
  }

  public String getDescription() {
    return description;
  }

  public boolean isIncremental() {
    return incremental;
  }

  public String getTableName() {
    return tableName;
  }

  public Map<String, String> getHints() {
    return hints;
  }

  public List<CanvasDataSchemaColumn> getColumns() {
    return columns;
  }

  public String getSeeAlso() {
    return seeAlso;
  }

  public String getDatabasePath() {
    return databasePath;
  }

  public String getOriginalTable() {
    return originalTable;
  }

  public void calculateDifferences(final CanvasDataSchemaTable table2,
      final List<SchemaDifference> differences) {
    if (dwType != table2.dwType) {
      differences.add(new SchemaDifference("dwType", dwType, table2.dwType, tableName));
    }
    if (!compareStrings(description, table2.description)) {
      differences
      .add(new SchemaDifference("description", description, table2.description, tableName));
    }
    if (!compareStrings(tableName, table2.tableName)) {
      differences.add(new SchemaDifference("tableName", tableName, table2.tableName, tableName));
    }
    if (incremental != table2.incremental) {
      differences
      .add(new SchemaDifference("incremental", incremental, table2.incremental, tableName));
    }
    if (!compareStrings(seeAlso, table2.seeAlso)) {
      differences.add(new SchemaDifference("seeAlso", seeAlso, table2.seeAlso, tableName));
    }
    if (!compareStrings(databasePath, table2.databasePath)) {
      differences
      .add(new SchemaDifference("databasePath", databasePath, table2.databasePath, tableName));
    }
    if (!compareStrings(originalTable, table2.originalTable)) {
      differences.add(
          new SchemaDifference("originalTable", originalTable, table2.originalTable, tableName));
    }
    calculateHintDifferences(table2.hints, differences);
    calculateColumnDifferences(table2.columns, differences);
  }

  private boolean compareStrings(final String s1, final String s2) {
    if (s1 == null || s2 == null) {
      return s1 == s2;
    }
    return s1.equals(s2);
  }

  private void calculateColumnDifferences(final List<CanvasDataSchemaColumn> columns2,
      final List<SchemaDifference> differences) {
    final Map<String, CanvasDataSchemaColumn> map = new HashMap<String, CanvasDataSchemaColumn>();
    for (final CanvasDataSchemaColumn column : columns) {
      map.put(column.getName(), column);
    }

    for (final CanvasDataSchemaColumn column : columns2) {
      final String columnName = column.getName();
      if (map.containsKey(columnName)) {
        map.get(columnName).calculateDifferences(tableName, column, differences);
        map.remove(columnName);
      } else {
        differences.add(new SchemaDifference(tableName + ": Added column " + columnName));
      }
    }

    for (final String columnName : map.keySet()) {
      differences.add(new SchemaDifference(tableName + ": Removed column " + columnName));
    }
  }

  private void calculateHintDifferences(final Map<String, String> hints2,
      final List<SchemaDifference> differences) {
    for (final String hintKey : hints.keySet()) {
      final String hint2 = hints2.get(hintKey);
      if (hint2 == null) {
        differences.add(new SchemaDifference(tableName + ": Removed hint " + hintKey));
      } else {
        if (!hints.get(hintKey).equals(hint2)) {
          differences.add(new SchemaDifference(tableName + ": Changed hint " + hintKey + " from "
              + hints.get(hintKey) + " to " + hint2));
        }
      }
    }
    for (final String hintKey : hints2.keySet()) {
      if (!hints.containsKey(hintKey)) {
        differences.add(new SchemaDifference(tableName + ": Added hint " + hintKey));
      }
    }
  }

  public void setColumns(final ArrayList<CanvasDataSchemaColumn> newColumns) {
    this.columns = newColumns;
  }
}
