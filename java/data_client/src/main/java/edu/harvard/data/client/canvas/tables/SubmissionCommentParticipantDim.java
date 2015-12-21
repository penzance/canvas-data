// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class SubmissionCommentParticipantDim implements DataTable {
  private Long id;
  private Long canvasId;
  private String participationType;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  public SubmissionCommentParticipantDim(final TableFormat format, final CSVRecord record) {
    String $id = record.get(0);
    if ($id != null && $id.length() > 0) {
      this.id = Long.valueOf($id);
    }
    String $canvasId = record.get(1);
    if ($canvasId != null && $canvasId.length() > 0) {
      this.canvasId = Long.valueOf($canvasId);
    }
    this.participationType = record.get(2);
    String $createdAt = record.get(3);
    if ($createdAt != null && $createdAt.length() > 0) {
      this.createdAt = Timestamp.valueOf($createdAt);
    }
    String $updatedAt = record.get(4);
    if ($updatedAt != null && $updatedAt.length() > 0) {
      this.updatedAt = Timestamp.valueOf($updatedAt);
    }
  }

  public Long getId() {
    return this.id;
  }

  public Long getCanvasId() {
    return this.canvasId;
  }

  public String getParticipationType() {
    return this.participationType;
  }

  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(canvasId);
    fields.add(participationType);
    fields.add(formatter.formatTimestamp(createdAt));
    fields.add(formatter.formatTimestamp(updatedAt));
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("id");
      fields.add("canvas_id");
      fields.add("participation_type");
      fields.add("created_at");
      fields.add("updated_at");
    return fields;
  }
}
