// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class SubmissionCommentParticipantFact implements DataTable {
  private Long submissionCommentParticipantId;
  private Long submissionCommentId;
  private Long userId;
  private Long submissionId;
  private Long assignmentId;
  private Long courseId;
  private Long enrollmentTermId;
  private Long courseAccountId;
  private Long enrollmentRollupId;

  public SubmissionCommentParticipantFact(final TableFormat format, final CSVRecord record) {
    String $submissionCommentParticipantId = record.get(0);
    if ($submissionCommentParticipantId != null && $submissionCommentParticipantId.length() > 0) {
      this.submissionCommentParticipantId = Long.valueOf($submissionCommentParticipantId);
    }
    String $submissionCommentId = record.get(1);
    if ($submissionCommentId != null && $submissionCommentId.length() > 0) {
      this.submissionCommentId = Long.valueOf($submissionCommentId);
    }
    String $userId = record.get(2);
    if ($userId != null && $userId.length() > 0) {
      this.userId = Long.valueOf($userId);
    }
    String $submissionId = record.get(3);
    if ($submissionId != null && $submissionId.length() > 0) {
      this.submissionId = Long.valueOf($submissionId);
    }
    String $assignmentId = record.get(4);
    if ($assignmentId != null && $assignmentId.length() > 0) {
      this.assignmentId = Long.valueOf($assignmentId);
    }
    String $courseId = record.get(5);
    if ($courseId != null && $courseId.length() > 0) {
      this.courseId = Long.valueOf($courseId);
    }
    String $enrollmentTermId = record.get(6);
    if ($enrollmentTermId != null && $enrollmentTermId.length() > 0) {
      this.enrollmentTermId = Long.valueOf($enrollmentTermId);
    }
    String $courseAccountId = record.get(7);
    if ($courseAccountId != null && $courseAccountId.length() > 0) {
      this.courseAccountId = Long.valueOf($courseAccountId);
    }
    String $enrollmentRollupId = record.get(8);
    if ($enrollmentRollupId != null && $enrollmentRollupId.length() > 0) {
      this.enrollmentRollupId = Long.valueOf($enrollmentRollupId);
    }
  }

  public Long getSubmissionCommentParticipantId() {
    return this.submissionCommentParticipantId;
  }

  public Long getSubmissionCommentId() {
    return this.submissionCommentId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getSubmissionId() {
    return this.submissionId;
  }

  /**
   * Foreign key to assignment dimension 
   */
  public Long getAssignmentId() {
    return this.assignmentId;
  }

  /**
   * Foreign key to course dimension of course associated with the assignment. 
   */
  public Long getCourseId() {
    return this.courseId;
  }

  /**
   * Foreign Key to enrollment term table 
   */
  public Long getEnrollmentTermId() {
    return this.enrollmentTermId;
  }

  /**
   * Foreign key to the account dimension of the account associated with the 
   * course associated with the assignment 
   */
  public Long getCourseAccountId() {
    return this.courseAccountId;
  }

  /**
   * Foreign key to the enrollment roll-up dimension table 
   */
  public Long getEnrollmentRollupId() {
    return this.enrollmentRollupId;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(submissionCommentParticipantId);
    fields.add(submissionCommentId);
    fields.add(userId);
    fields.add(submissionId);
    fields.add(assignmentId);
    fields.add(courseId);
    fields.add(enrollmentTermId);
    fields.add(courseAccountId);
    fields.add(enrollmentRollupId);
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("submission_comment_participant_id");
      fields.add("submission_comment_id");
      fields.add("user_id");
      fields.add("submission_id");
      fields.add("assignment_id");
      fields.add("course_id");
      fields.add("enrollment_term_id");
      fields.add("course_account_id");
      fields.add("enrollment_rollup_id");
    return fields;
  }
}
