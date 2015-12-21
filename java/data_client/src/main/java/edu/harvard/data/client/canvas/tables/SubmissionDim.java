// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class SubmissionDim implements DataTable {
  private Long id;
  private Long canvasId;
  private String body;
  private String url;
  private String grade;
  private Timestamp submittedAt;
  private String submissionType;
  private String workflowState;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Boolean processed;
  private Integer processAttempts;
  private Boolean gradeMatchesCurrentSubmission;
  private String publishedGrade;
  private Timestamp gradedAt;
  private Boolean hasRubricAssessment;
  private Integer attempt;
  private Boolean hasAdminComment;
  private Long assignmentId;

  public SubmissionDim(final TableFormat format, final CSVRecord record) {
    String $id = record.get(0);
    if ($id != null && $id.length() > 0) {
      this.id = Long.valueOf($id);
    }
    String $canvasId = record.get(1);
    if ($canvasId != null && $canvasId.length() > 0) {
      this.canvasId = Long.valueOf($canvasId);
    }
    this.body = record.get(2);
    this.url = record.get(3);
    this.grade = record.get(4);
    String $submittedAt = record.get(5);
    if ($submittedAt != null && $submittedAt.length() > 0) {
      this.submittedAt = Timestamp.valueOf($submittedAt);
    }
    this.submissionType = record.get(6);
    this.workflowState = record.get(7);
    String $createdAt = record.get(8);
    if ($createdAt != null && $createdAt.length() > 0) {
      this.createdAt = Timestamp.valueOf($createdAt);
    }
    String $updatedAt = record.get(9);
    if ($updatedAt != null && $updatedAt.length() > 0) {
      this.updatedAt = Timestamp.valueOf($updatedAt);
    }
    String $processed = record.get(10);
    if ($processed != null && $processed.length() > 0) {
      this.processed = Boolean.valueOf($processed);
    }
    String $processAttempts = record.get(11);
    if ($processAttempts != null && $processAttempts.length() > 0) {
      this.processAttempts = Integer.valueOf($processAttempts);
    }
    String $gradeMatchesCurrentSubmission = record.get(12);
    if ($gradeMatchesCurrentSubmission != null && $gradeMatchesCurrentSubmission.length() > 0) {
      this.gradeMatchesCurrentSubmission = Boolean.valueOf($gradeMatchesCurrentSubmission);
    }
    this.publishedGrade = record.get(13);
    String $gradedAt = record.get(14);
    if ($gradedAt != null && $gradedAt.length() > 0) {
      this.gradedAt = Timestamp.valueOf($gradedAt);
    }
    String $hasRubricAssessment = record.get(15);
    if ($hasRubricAssessment != null && $hasRubricAssessment.length() > 0) {
      this.hasRubricAssessment = Boolean.valueOf($hasRubricAssessment);
    }
    String $attempt = record.get(16);
    if ($attempt != null && $attempt.length() > 0) {
      this.attempt = Integer.valueOf($attempt);
    }
    String $hasAdminComment = record.get(17);
    if ($hasAdminComment != null && $hasAdminComment.length() > 0) {
      this.hasAdminComment = Boolean.valueOf($hasAdminComment);
    }
    String $assignmentId = record.get(18);
    if ($assignmentId != null && $assignmentId.length() > 0) {
      this.assignmentId = Long.valueOf($assignmentId);
    }
  }

  /**
   * Unique surrogate id for the submission. 
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Primary key of this record in the Canvas submissions table 
   */
  public Long getCanvasId() {
    return this.canvasId;
  }

  /**
   * Text content for the submission. 
   */
  public String getBody() {
    return this.body;
  }

  /**
   * URL content for the submission 
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * Letter grade mapped from the score by the grading scheme 
   */
  public String getGrade() {
    return this.grade;
  }

  /**
   * Timestamp of when the submission was submitted. 
   */
  public Timestamp getSubmittedAt() {
    return this.submittedAt;
  }

  /**
   * Type of submission  (online_url, media_recording, online_upload, 
   * online_quize, external_tool, online_text_entry, online_file_upload, 
   * discussion_topic) 
   */
  public String getSubmissionType() {
    return this.submissionType;
  }

  /**
   * Workflow state for submission lifetime values (unsubmitted, submitted, 
   * graded, pending_review) 
   */
  public String getWorkflowState() {
    return this.workflowState;
  }

  /**
   * Timestamp of when the submission was created 
   */
  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Timestamp of when the submission was last updated 
   */
  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * TBD 
   */
  public Boolean getProcessed() {
    return this.processed;
  }

  /**
   * TBD 
   */
  public Integer getProcessAttempts() {
    return this.processAttempts;
  }

  /**
   * TBD 
   */
  public Boolean getGradeMatchesCurrentSubmission() {
    return this.gradeMatchesCurrentSubmission;
  }

  /**
   * TBD 
   */
  public String getPublishedGrade() {
    return this.publishedGrade;
  }

  /**
   * Timestamp of when the submission was graded 
   */
  public Timestamp getGradedAt() {
    return this.gradedAt;
  }

  /**
   * TBD 
   */
  public Boolean getHasRubricAssessment() {
    return this.hasRubricAssessment;
  }

  /**
   * The number of attempts made including this one. 
   */
  public Integer getAttempt() {
    return this.attempt;
  }

  /**
   * TBD 
   */
  public Boolean getHasAdminComment() {
    return this.hasAdminComment;
  }

  /**
   * Foreign key to assignment dimension 
   */
  public Long getAssignmentId() {
    return this.assignmentId;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(canvasId);
    fields.add(body);
    fields.add(url);
    fields.add(grade);
    fields.add(formatter.formatTimestamp(submittedAt));
    fields.add(submissionType);
    fields.add(workflowState);
    fields.add(formatter.formatTimestamp(createdAt));
    fields.add(formatter.formatTimestamp(updatedAt));
    fields.add(processed);
    fields.add(processAttempts);
    fields.add(gradeMatchesCurrentSubmission);
    fields.add(publishedGrade);
    fields.add(formatter.formatTimestamp(gradedAt));
    fields.add(hasRubricAssessment);
    fields.add(attempt);
    fields.add(hasAdminComment);
    fields.add(assignmentId);
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("id");
      fields.add("canvas_id");
      fields.add("body");
      fields.add("url");
      fields.add("grade");
      fields.add("submitted_at");
      fields.add("submission_type");
      fields.add("workflow_state");
      fields.add("created_at");
      fields.add("updated_at");
      fields.add("processed");
      fields.add("process_attempts");
      fields.add("grade_matches_current_submission");
      fields.add("published_grade");
      fields.add("graded_at");
      fields.add("has_rubric_assessment");
      fields.add("attempt");
      fields.add("has_admin_comment");
      fields.add("assignment_id");
    return fields;
  }
}
