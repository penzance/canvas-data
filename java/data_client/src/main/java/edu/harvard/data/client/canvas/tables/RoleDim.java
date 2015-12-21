// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class RoleDim implements DataTable {
  private Long id;
  private Long canvasId;
  private Long rootAccountId;
  private Long accountId;
  private String name;
  private String baseRoleType;
  private String workflowState;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp deletedAt;

  public RoleDim(final TableFormat format, final CSVRecord record) {
    String $id = record.get(0);
    if ($id != null && $id.length() > 0) {
      this.id = Long.valueOf($id);
    }
    String $canvasId = record.get(1);
    if ($canvasId != null && $canvasId.length() > 0) {
      this.canvasId = Long.valueOf($canvasId);
    }
    String $rootAccountId = record.get(2);
    if ($rootAccountId != null && $rootAccountId.length() > 0) {
      this.rootAccountId = Long.valueOf($rootAccountId);
    }
    String $accountId = record.get(3);
    if ($accountId != null && $accountId.length() > 0) {
      this.accountId = Long.valueOf($accountId);
    }
    this.name = record.get(4);
    this.baseRoleType = record.get(5);
    this.workflowState = record.get(6);
    String $createdAt = record.get(7);
    if ($createdAt != null && $createdAt.length() > 0) {
      this.createdAt = Timestamp.valueOf($createdAt);
    }
    String $updatedAt = record.get(8);
    if ($updatedAt != null && $updatedAt.length() > 0) {
      this.updatedAt = Timestamp.valueOf($updatedAt);
    }
    String $deletedAt = record.get(9);
    if ($deletedAt != null && $deletedAt.length() > 0) {
      this.deletedAt = Timestamp.valueOf($deletedAt);
    }
  }

  /**
   * Unique surrogate id for the role. 
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Primary key for this record in the Canvas roles table 
   */
  public Long getCanvasId() {
    return this.canvasId;
  }

  public Long getRootAccountId() {
    return this.rootAccountId;
  }

  /**
   * The foreign key to the account that is in the role 
   */
  public Long getAccountId() {
    return this.accountId;
  }

  /**
   * The name of role, previously was "role_name" on the enrollments_dim 
   */
  public String getName() {
    return this.name;
  }

  public String getBaseRoleType() {
    return this.baseRoleType;
  }

  public String getWorkflowState() {
    return this.workflowState;
  }

  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  public Timestamp getDeletedAt() {
    return this.deletedAt;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(canvasId);
    fields.add(rootAccountId);
    fields.add(accountId);
    fields.add(name);
    fields.add(baseRoleType);
    fields.add(workflowState);
    fields.add(formatter.formatTimestamp(createdAt));
    fields.add(formatter.formatTimestamp(updatedAt));
    fields.add(formatter.formatTimestamp(deletedAt));
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("id");
      fields.add("canvas_id");
      fields.add("root_account_id");
      fields.add("account_id");
      fields.add("name");
      fields.add("base_role_type");
      fields.add("workflow_state");
      fields.add("created_at");
      fields.add("updated_at");
      fields.add("deleted_at");
    return fields;
  }
}
