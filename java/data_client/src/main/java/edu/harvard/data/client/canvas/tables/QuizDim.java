// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class QuizDim implements DataTable {
  private Long id;
  private Long canvasId;
  private Long rootAccountId;
  private String name;
  private Double pointsPossible;
  private String description;
  private String quizType;
  private Long courseId;
  private Long assignmentId;
  private String workflowState;
  private String scoringPolicy;
  private String anonymousSubmissions;
  private String displayQuestions;
  private String answerDisplayOrder;
  private String goBackToPreviousQuestion;
  private String couldBeLocked;
  private String browserLockdown;
  private String browserLockdownForDisplayingResults;
  private String browserLockdownMonitor;
  private String ipFilter;
  private String showResults;
  private String showCorrectAnswers;
  private Timestamp showCorrectAnswersAt;
  private Timestamp hideCorrectAnswersAt;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp publishedAt;
  private Timestamp unlockAt;
  private Timestamp lockAt;
  private Timestamp dueAt;
  private Timestamp deletedAt;

  public QuizDim(final TableFormat format, final CSVRecord record) {
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
    this.name = record.get(3);
    String $pointsPossible = record.get(4);
    if ($pointsPossible != null && $pointsPossible.length() > 0) {
      this.pointsPossible = Double.valueOf($pointsPossible);
    }
    this.description = record.get(5);
    this.quizType = record.get(6);
    String $courseId = record.get(7);
    if ($courseId != null && $courseId.length() > 0) {
      this.courseId = Long.valueOf($courseId);
    }
    String $assignmentId = record.get(8);
    if ($assignmentId != null && $assignmentId.length() > 0) {
      this.assignmentId = Long.valueOf($assignmentId);
    }
    this.workflowState = record.get(9);
    this.scoringPolicy = record.get(10);
    this.anonymousSubmissions = record.get(11);
    this.displayQuestions = record.get(12);
    this.answerDisplayOrder = record.get(13);
    this.goBackToPreviousQuestion = record.get(14);
    this.couldBeLocked = record.get(15);
    this.browserLockdown = record.get(16);
    this.browserLockdownForDisplayingResults = record.get(17);
    this.browserLockdownMonitor = record.get(18);
    this.ipFilter = record.get(19);
    this.showResults = record.get(20);
    this.showCorrectAnswers = record.get(21);
    String $showCorrectAnswersAt = record.get(22);
    if ($showCorrectAnswersAt != null && $showCorrectAnswersAt.length() > 0) {
      this.showCorrectAnswersAt = Timestamp.valueOf($showCorrectAnswersAt);
    }
    String $hideCorrectAnswersAt = record.get(23);
    if ($hideCorrectAnswersAt != null && $hideCorrectAnswersAt.length() > 0) {
      this.hideCorrectAnswersAt = Timestamp.valueOf($hideCorrectAnswersAt);
    }
    String $createdAt = record.get(24);
    if ($createdAt != null && $createdAt.length() > 0) {
      this.createdAt = Timestamp.valueOf($createdAt);
    }
    String $updatedAt = record.get(25);
    if ($updatedAt != null && $updatedAt.length() > 0) {
      this.updatedAt = Timestamp.valueOf($updatedAt);
    }
    String $publishedAt = record.get(26);
    if ($publishedAt != null && $publishedAt.length() > 0) {
      this.publishedAt = Timestamp.valueOf($publishedAt);
    }
    String $unlockAt = record.get(27);
    if ($unlockAt != null && $unlockAt.length() > 0) {
      this.unlockAt = Timestamp.valueOf($unlockAt);
    }
    String $lockAt = record.get(28);
    if ($lockAt != null && $lockAt.length() > 0) {
      this.lockAt = Timestamp.valueOf($lockAt);
    }
    String $dueAt = record.get(29);
    if ($dueAt != null && $dueAt.length() > 0) {
      this.dueAt = Timestamp.valueOf($dueAt);
    }
    String $deletedAt = record.get(30);
    if ($deletedAt != null && $deletedAt.length() > 0) {
      this.deletedAt = Timestamp.valueOf($deletedAt);
    }
  }

  /**
   * Unique surrogate ID for the quiz. 
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Primary key for this quiz in the quizzes table. 
   */
  public Long getCanvasId() {
    return this.canvasId;
  }

  /**
   * Root account ID associated with this quiz. 
   */
  public Long getRootAccountId() {
    return this.rootAccountId;
  }

  /**
   * Name of the quiz. Equivalent Canvas API field -> 'title'. 
   */
  public String getName() {
    return this.name;
  }

  /**
   * Total point value given to the quiz. 
   */
  public Double getPointsPossible() {
    return this.pointsPossible;
  }

  /**
   * Description of the quiz. 
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Type of quiz. Possible values are 'practice_quiz', 'assignment', 
   * 'graded_survey' and 'survey'. Defaults to 'NULL'. 
   */
  public String getQuizType() {
    return this.quizType;
  }

  /**
   * Foreign key to the course the quiz belongs to. 
   */
  public Long getCourseId() {
    return this.courseId;
  }

  /**
   * Foreign key to the assignment the quiz belongs to. 
   */
  public Long getAssignmentId() {
    return this.assignmentId;
  }

  /**
   * Denotes where the quiz is in the workflow. Possible values are 
   * 'unpublished', 'published' and 'deleted'. Defaults to 'unpublished'. 
   */
  public String getWorkflowState() {
    return this.workflowState;
  }

  /**
   * Scoring policy for a quiz that students can take multiple times. Is 
   * required and only valid if allowed_attempts > 1. Possible values are 
   * 'keep_highest', 'keep_latest' and 'keep_average'. Defaults to 
   * 'keep_highest'. 
   */
  public String getScoringPolicy() {
    return this.scoringPolicy;
  }

  /**
   * Dictates whether students are allowed to submit the quiz anonymously. 
   * Possible values are 'allow_anonymous_submissions' and 
   * 'disallow_anonymous_submissions'. Defaults to 
   * 'disallow_anonymous_submissions'. 
   */
  public String getAnonymousSubmissions() {
    return this.anonymousSubmissions;
  }

  /**
   * Policy for displaying the questions in the quiz. Possible values are 
   * 'multiple_at_a_time' and 'one_at_a_time'. Defaults to 'multiple_at_a_time'. 
   * Equivalent Canvas API field -> 'one_question_at_a_time'. 
   */
  public String getDisplayQuestions() {
    return this.displayQuestions;
  }

  /**
   * Policy for displaying the answers for each question in the quiz. Possible 
   * values are 'in_order' and 'shuffled'. Defaults to 'in_order'. Equivalent 
   * Canvas API field -> 'shuffle_answers'. 
   */
  public String getAnswerDisplayOrder() {
    return this.answerDisplayOrder;
  }

  /**
   * Policy on going back to the previous question. Is valid only if 
   * 'display_questions' is set to 'one_at_a_time'. Possible values are 
   * 'allow_going_back' and 'disallow_going_back'. Defaults to 
   * 'allow_going_back'. Equivalent Canvas API field -> 'cant_go_back'. 
   */
  public String getGoBackToPreviousQuestion() {
    return this.goBackToPreviousQuestion;
  }

  /**
   * Dictates if the quiz can be locked or not. Possible values are 
   * 'allow_locking' and 'disallow_locking'. Defaults to 'disallow_locking'. 
   */
  public String getCouldBeLocked() {
    return this.couldBeLocked;
  }

  /**
   * Dictates whether the browser has locked-down when the quiz is being taken. 
   * Possible values are 'required' and 'not_required'. Defaults to 
   * 'not_required'. 
   */
  public String getBrowserLockdown() {
    return this.browserLockdown;
  }

  /**
   * Dictates whether the browser has to be locked-down to display the results. 
   * Is valid only if 'hide_results' is set to 'never' or 
   * 'until_after_last_attempt' (for the results to be displayed after the last 
   * attempt). Possible values are 'required' and 'not_required'. Defaults to 
   * 'not_required'. 
   */
  public String getBrowserLockdownForDisplayingResults() {
    return this.browserLockdownForDisplayingResults;
  }

  /**
   * Dictates whether a browser lockdown monitor is required. Possible values 
   * are 'required' and 'not_required'. Defaults to 'not_required'. 
   */
  public String getBrowserLockdownMonitor() {
    return this.browserLockdownMonitor;
  }

  /**
   * Restricts access to the quiz to computers in a specified IP range. Filters 
   * can be a comma-separated list of addresses, or an address followed by a 
   * mask. 
   */
  public String getIpFilter() {
    return this.ipFilter;
  }

  /**
   * Dictates whether or not quiz results are shown to students. If set to 
   * 'always', students can see their results after any attempt and if set to 
   * 'never', students can never see their results. If 
   * 'dw_quiz_fact.allowed_attempts > 1' then when set to 
   * 'always_after_last_attempt', students can only see their results always, 
   * but only after their last attempt. Similarly, if set to 
   * 'only_once_after_last_attempt', then students can see their results only 
   * after their last attempt, that too only once. Possible values are 'always', 
   * 'never', 'always_after_last_attempt' and 'only_once_after_last_attempt'. 
   * Defaults to 'always'. Equivalent Canvas API field -> 'hide_results' 
   * combined with 'one_time_results'. 
   */
  public String getShowResults() {
    return this.showResults;
  }

  /**
   * Dictates whether correct answers are shown when are results are viewed. 
   * It's valid only if 'show_results' is set to 'always'. Possible values are 
   * 'always', 'never', 'only_once_after_last_attempt' and 
   * 'always_after_last_attempt' (Last two are only valid if 
   * 'dw_quiz_fact.allowed_attempts > 1') which have a behavior similar to 
   * 'show_results'. Defaults to 'always'. Equivalent Canvas API field -> 
   * 'show_correct_answers' combined with 'show_correct_answers_last_attempt'. 
   */
  public String getShowCorrectAnswers() {
    return this.showCorrectAnswers;
  }

  /**
   * Day/Time when the correct answers would be shown. 
   */
  public Timestamp getShowCorrectAnswersAt() {
    return this.showCorrectAnswersAt;
  }

  /**
   * Day/Time when the correct answers are to be hidden. 
   */
  public Timestamp getHideCorrectAnswersAt() {
    return this.hideCorrectAnswersAt;
  }

  /**
   * Time when the quiz was created. 
   */
  public Timestamp getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Time when the quiz was last updated. 
   */
  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * Time when the quiz was published. 
   */
  public Timestamp getPublishedAt() {
    return this.publishedAt;
  }

  /**
   * Day/Time when the quiz is to be unlocked for students. 
   */
  public Timestamp getUnlockAt() {
    return this.unlockAt;
  }

  /**
   * Day/Time when the quiz is to be locked for students. 
   */
  public Timestamp getLockAt() {
    return this.lockAt;
  }

  /**
   * Day/Time when the quiz is due. 
   */
  public Timestamp getDueAt() {
    return this.dueAt;
  }

  /**
   * Time when the quiz was deleted. 
   */
  public Timestamp getDeletedAt() {
    return this.deletedAt;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(canvasId);
    fields.add(rootAccountId);
    fields.add(name);
    fields.add(pointsPossible);
    fields.add(description);
    fields.add(quizType);
    fields.add(courseId);
    fields.add(assignmentId);
    fields.add(workflowState);
    fields.add(scoringPolicy);
    fields.add(anonymousSubmissions);
    fields.add(displayQuestions);
    fields.add(answerDisplayOrder);
    fields.add(goBackToPreviousQuestion);
    fields.add(couldBeLocked);
    fields.add(browserLockdown);
    fields.add(browserLockdownForDisplayingResults);
    fields.add(browserLockdownMonitor);
    fields.add(ipFilter);
    fields.add(showResults);
    fields.add(showCorrectAnswers);
    fields.add(formatter.formatTimestamp(showCorrectAnswersAt));
    fields.add(formatter.formatTimestamp(hideCorrectAnswersAt));
    fields.add(formatter.formatTimestamp(createdAt));
    fields.add(formatter.formatTimestamp(updatedAt));
    fields.add(formatter.formatTimestamp(publishedAt));
    fields.add(formatter.formatTimestamp(unlockAt));
    fields.add(formatter.formatTimestamp(lockAt));
    fields.add(formatter.formatTimestamp(dueAt));
    fields.add(formatter.formatTimestamp(deletedAt));
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("id");
      fields.add("canvas_id");
      fields.add("root_account_id");
      fields.add("name");
      fields.add("points_possible");
      fields.add("description");
      fields.add("quiz_type");
      fields.add("course_id");
      fields.add("assignment_id");
      fields.add("workflow_state");
      fields.add("scoring_policy");
      fields.add("anonymous_submissions");
      fields.add("display_questions");
      fields.add("answer_display_order");
      fields.add("go_back_to_previous_question");
      fields.add("could_be_locked");
      fields.add("browser_lockdown");
      fields.add("browser_lockdown_for_displaying_results");
      fields.add("browser_lockdown_monitor");
      fields.add("ip_filter");
      fields.add("show_results");
      fields.add("show_correct_answers");
      fields.add("show_correct_answers_at");
      fields.add("hide_correct_answers_at");
      fields.add("created_at");
      fields.add("updated_at");
      fields.add("published_at");
      fields.add("unlock_at");
      fields.add("lock_at");
      fields.add("due_at");
      fields.add("deleted_at");
    return fields;
  }
}
