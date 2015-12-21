// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class DiscussionEntryDim implements DataTable {
  private Long id;
  private Long canvasId;
  private String message;
  private String workflowState;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp deletedAt;
  private Integer depth;

  public DiscussionEntryDim(final TableFormat format, final CSVRecord record) {
    String $id = record.get(0);
    if ($id != null && $id.length() > 0) {
      this.id = Long.valueOf($id);
    }
    String $canvasId = record.get(1);
    if ($canvasId != null && $canvasId.length() > 0) {
      this.canvasId = Long.valueOf($canvasId);
    }
    this.message = record.get(2);
    this.workflowState = record.get(3);
    String $createdAt = record.get(4);
    if ($createdAt != null && $createdAt.length() > 0) {
      this.createdAt = Timestamp.valueOf($createdAt);
    }
    String $updatedAt = record.get(5);
    if ($updatedAt != null && $updatedAt.length() > 0) {
      this.updatedAt = Timestamp.valueOf($updatedAt);
    }
    String $deletedAt = record.get(6);
    if ($deletedAt != null && $deletedAt.length() > 0) {
      this.deletedAt = Timestamp.valueOf($deletedAt);
    }
    String $depth = record.get(7);
    if ($depth != null && $depth.length() > 0) {
      this.depth = Integer.valueOf($depth);
    }
  }

  /**
   * Unique surrogate id for the discussion entry. 
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Primary key for this record in the Canvas discussion_entries table 
   */
  public Long getCanvasId() {
    return this.canvasId;
  }

  /**
   * Full text of the entry's message 
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Workflow state for discussion message (values: deleted, active) 
   */
  public String getWorkflowState() {
    return this.workflowState;
  }

  /**
   * Timestamp when the discussion entry was created. 
   */
  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Timestamp when the discussion entry was updated. 
   */
  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * Timestamp when the discussion entry was deleted. 
   */
  public Timestamp getDeletedAt() {
    return this.deletedAt;
  }

  /**
   * Reply depth for this entry 
   */
  public Integer getDepth() {
    return this.depth;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(canvasId);
    fields.add(message);
    fields.add(workflowState);
    fields.add(formatter.formatTimestamp(createdAt));
    fields.add(formatter.formatTimestamp(updatedAt));
    fields.add(formatter.formatTimestamp(deletedAt));
    fields.add(depth);
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("id");
      fields.add("canvas_id");
      fields.add("message");
      fields.add("workflow_state");
      fields.add("created_at");
      fields.add("updated_at");
      fields.add("deleted_at");
      fields.add("depth");
    return fields;
  }
}
