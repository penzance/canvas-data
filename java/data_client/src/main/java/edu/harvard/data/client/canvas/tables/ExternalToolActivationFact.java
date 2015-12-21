// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class ExternalToolActivationFact implements DataTable {
  private Long externalToolActivationId;
  private Long courseId;
  private Long accountId;
  private Long rootAccountId;
  private Long enrollmentTermId;
  private Long courseAccountId;

  public ExternalToolActivationFact(final TableFormat format, final CSVRecord record) {
    String $externalToolActivationId = record.get(0);
    if ($externalToolActivationId != null && $externalToolActivationId.length() > 0) {
      this.externalToolActivationId = Long.valueOf($externalToolActivationId);
    }
    String $courseId = record.get(1);
    if ($courseId != null && $courseId.length() > 0) {
      this.courseId = Long.valueOf($courseId);
    }
    String $accountId = record.get(2);
    if ($accountId != null && $accountId.length() > 0) {
      this.accountId = Long.valueOf($accountId);
    }
    String $rootAccountId = record.get(3);
    if ($rootAccountId != null && $rootAccountId.length() > 0) {
      this.rootAccountId = Long.valueOf($rootAccountId);
    }
    String $enrollmentTermId = record.get(4);
    if ($enrollmentTermId != null && $enrollmentTermId.length() > 0) {
      this.enrollmentTermId = Long.valueOf($enrollmentTermId);
    }
    String $courseAccountId = record.get(5);
    if ($courseAccountId != null && $courseAccountId.length() > 0) {
      this.courseAccountId = Long.valueOf($courseAccountId);
    }
  }

  /**
   * Foreign key to the external_tool_activation_dim dimension with attribute 
   * for this activation 
   */
  public Long getExternalToolActivationId() {
    return this.externalToolActivationId;
  }

  /**
   * Foreign key to the course if this tool was activated in a course 
   */
  public Long getCourseId() {
    return this.courseId;
  }

  /**
   * Foreign key to the account this tool was activated in if it was activated 
   * in an account 
   */
  public Long getAccountId() {
    return this.accountId;
  }

  /**
   * Foreign key to the root account for this data 
   */
  public Long getRootAccountId() {
    return this.rootAccountId;
  }

  /**
   * Foreign key to the course's enrollment term if this tool was activated in 
   * a course 
   */
  public Long getEnrollmentTermId() {
    return this.enrollmentTermId;
  }

  /**
   * Foreign key to the course's account if this tool was activated in a course 
   */
  public Long getCourseAccountId() {
    return this.courseAccountId;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(externalToolActivationId);
    fields.add(courseId);
    fields.add(accountId);
    fields.add(rootAccountId);
    fields.add(enrollmentTermId);
    fields.add(courseAccountId);
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("external_tool_activation_id");
      fields.add("course_id");
      fields.add("account_id");
      fields.add("root_account_id");
      fields.add("enrollment_term_id");
      fields.add("course_account_id");
    return fields;
  }
}
