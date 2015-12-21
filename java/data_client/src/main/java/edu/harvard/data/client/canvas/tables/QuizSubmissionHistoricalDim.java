// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class QuizSubmissionHistoricalDim implements DataTable {
  private Long id;
  private Long canvasId;
  private Long quizId;
  private Long submissionId;
  private Long userId;
  private Integer versionNumber;
  private String submissionState;
  private String workflowState;
  private String quizStateDuringSubmission;
  private String submissionScoringPolicy;
  private String submissionSource;
  private String hasSeenResults;
  private String temporaryUserCode;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp startedAt;
  private Timestamp finishedAt;
  private Timestamp dueAt;

  public QuizSubmissionHistoricalDim(final TableFormat format, final CSVRecord record) {
    String $id = record.get(0);
    if ($id != null && $id.length() > 0) {
      this.id = Long.valueOf($id);
    }
    String $canvasId = record.get(1);
    if ($canvasId != null && $canvasId.length() > 0) {
      this.canvasId = Long.valueOf($canvasId);
    }
    String $quizId = record.get(2);
    if ($quizId != null && $quizId.length() > 0) {
      this.quizId = Long.valueOf($quizId);
    }
    String $submissionId = record.get(3);
    if ($submissionId != null && $submissionId.length() > 0) {
      this.submissionId = Long.valueOf($submissionId);
    }
    String $userId = record.get(4);
    if ($userId != null && $userId.length() > 0) {
      this.userId = Long.valueOf($userId);
    }
    String $versionNumber = record.get(5);
    if ($versionNumber != null && $versionNumber.length() > 0) {
      this.versionNumber = Integer.valueOf($versionNumber);
    }
    this.submissionState = record.get(6);
    this.workflowState = record.get(7);
    this.quizStateDuringSubmission = record.get(8);
    this.submissionScoringPolicy = record.get(9);
    this.submissionSource = record.get(10);
    this.hasSeenResults = record.get(11);
    this.temporaryUserCode = record.get(12);
    String $createdAt = record.get(13);
    if ($createdAt != null && $createdAt.length() > 0) {
      this.createdAt = Timestamp.valueOf($createdAt);
    }
    String $updatedAt = record.get(14);
    if ($updatedAt != null && $updatedAt.length() > 0) {
      this.updatedAt = Timestamp.valueOf($updatedAt);
    }
    String $startedAt = record.get(15);
    if ($startedAt != null && $startedAt.length() > 0) {
      this.startedAt = Timestamp.valueOf($startedAt);
    }
    String $finishedAt = record.get(16);
    if ($finishedAt != null && $finishedAt.length() > 0) {
      this.finishedAt = Timestamp.valueOf($finishedAt);
    }
    String $dueAt = record.get(17);
    if ($dueAt != null && $dueAt.length() > 0) {
      this.dueAt = Timestamp.valueOf($dueAt);
    }
  }

  /**
   * Unique surrogate ID for the quiz submission. 
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Primary key for this quiz submission in the 'quiz_submissions' Canvas 
   * table. 
   */
  public Long getCanvasId() {
    return this.canvasId;
  }

  /**
   * ID of the quiz the quiz submission represents. Foreign key to the quiz 
   * dimension table. 
   */
  public Long getQuizId() {
    return this.quizId;
  }

  /**
   * ID to the submission the quiz submission represents. Foreign key to the 
   * quiz submission dimension table. 
   */
  public Long getSubmissionId() {
    return this.submissionId;
  }

  /**
   * ID of the user (who is a student) who made the submission. Foreign key to 
   * the user dimension table. 
   */
  public Long getUserId() {
    return this.userId;
  }

  /**
   * Version number of this quiz submission. 
   */
  public Integer getVersionNumber() {
    return this.versionNumber;
  }

  /**
   * Denotes if the quiz submission is a current or previous submission. 
   * Possible values are 'current_submission' and 'previous_submission'. 
   * Defaults to 'current_submission'. 
   */
  public String getSubmissionState() {
    return this.submissionState;
  }

  /**
   * Denotes the current state of the quiz submission. Possible values are 
   * 'untaken', 'complete', 'pending_review', 'preview' and 'settings_only'. Out 
   * of these, 'settings_only' pertains only to quiz moderation events. It 
   * stores the settings to create and store moderation events before the 
   * student has begun an attempt. Defaults to 'untaken'. 
   */
  public String getWorkflowState() {
    return this.workflowState;
  }

  /**
   * There can be two types of quiz states during submission, 1. Quiz 
   * submission took place after the quiz was manually unlocked after being 
   * locked (but only for a particular student such that (s)he can take the quiz 
   * even if it's locked for everyone else). 2. Quiz submission was on-time 
   * (that is, when the quiz was never locked). So the two possible values are 
   * 'manually_unlocked' and 'never_locked'. Defaults to 'never_locked'. 
   */
  public String getQuizStateDuringSubmission() {
    return this.quizStateDuringSubmission;
  }

  /**
   * Denotes if the score has been manually overridden by a teacher to reflect 
   * the score of a previous attempt (as opposed to a score calculated by the 
   * quiz's scoring policy. Possible values are 'manually_overridden' or the 
   * general quiz scoring policies, i.e. 'keep_highest', 'keep_latest' and 
   * 'keep_average'. Defaults to the scoring policy of the quiz the submission 
   * is associated with. 
   */
  public String getSubmissionScoringPolicy() {
    return this.submissionScoringPolicy;
  }

  /**
   * Denotes where the submission was received from. Possible values are 
   * 'student' and 'test_preview'. Defaults to 'student'. 
   */
  public String getSubmissionSource() {
    return this.submissionSource;
  }

  /**
   * Denotes whether the student has viewed their results to the quiz. 
   */
  public String getHasSeenResults() {
    return this.hasSeenResults;
  }

  /**
   * Construct for previewing a quiz. 
   */
  public String getTemporaryUserCode() {
    return this.temporaryUserCode;
  }

  /**
   * Time when the quiz submission was created. 
   */
  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Time when the quiz submission was last updated. 
   */
  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * Time at which the student started the quiz submission. 
   */
  public Timestamp getStartedAt() {
    return this.startedAt;
  }

  /**
   * Time at which the student submitted the quiz submission. 
   */
  public Timestamp getFinishedAt() {
    return this.finishedAt;
  }

  /**
   * Time at which the quiz submission will be overdue, and will be flagged as 
   * a late submission. 
   */
  public Timestamp getDueAt() {
    return this.dueAt;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(canvasId);
    fields.add(quizId);
    fields.add(submissionId);
    fields.add(userId);
    fields.add(versionNumber);
    fields.add(submissionState);
    fields.add(workflowState);
    fields.add(quizStateDuringSubmission);
    fields.add(submissionScoringPolicy);
    fields.add(submissionSource);
    fields.add(hasSeenResults);
    fields.add(temporaryUserCode);
    fields.add(formatter.formatTimestamp(createdAt));
    fields.add(formatter.formatTimestamp(updatedAt));
    fields.add(formatter.formatTimestamp(startedAt));
    fields.add(formatter.formatTimestamp(finishedAt));
    fields.add(formatter.formatTimestamp(dueAt));
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("id");
      fields.add("canvas_id");
      fields.add("quiz_id");
      fields.add("submission_id");
      fields.add("user_id");
      fields.add("version_number");
      fields.add("submission_state");
      fields.add("workflow_state");
      fields.add("quiz_state_during_submission");
      fields.add("submission_scoring_policy");
      fields.add("submission_source");
      fields.add("has_seen_results");
      fields.add("temporary_user_code");
      fields.add("created_at");
      fields.add("updated_at");
      fields.add("started_at");
      fields.add("finished_at");
      fields.add("due_at");
    return fields;
  }
}
