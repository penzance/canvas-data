// This file was generated on 12-21-2015 04:02:40. Do not manually edit.
// This class is based on Version 1.3.0 of the Canvas Data schema

package edu.harvard.data.client.canvas.tables;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.data.client.AwsUtils;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.DelimitedTableReader;
import edu.harvard.data.client.DelimitedTableWriter;
import edu.harvard.data.client.S3TableReader;
import edu.harvard.data.client.TableFactory;
import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.TableReader;
import edu.harvard.data.client.TableWriter;

public class CanvasTableFactory implements TableFactory {

  @Override
  public TableReader<? extends DataTable> getDelimitedTableReader(final String table, final TableFormat format, final File file) throws IOException {
    switch(table) {
    case "account_dim":
      return new DelimitedTableReader<AccountDim>(AccountDim.class, format, file, "account_dim");
    case "assignment_dim":
      return new DelimitedTableReader<AssignmentDim>(AssignmentDim.class, format, file, "assignment_dim");
    case "assignment_fact":
      return new DelimitedTableReader<AssignmentFact>(AssignmentFact.class, format, file, "assignment_fact");
    case "conversation_dim":
      return new DelimitedTableReader<ConversationDim>(ConversationDim.class, format, file, "conversation_dim");
    case "conversation_message_dim":
      return new DelimitedTableReader<ConversationMessageDim>(ConversationMessageDim.class, format, file, "conversation_message_dim");
    case "conversation_message_participant_fact":
      return new DelimitedTableReader<ConversationMessageParticipantFact>(ConversationMessageParticipantFact.class, format, file, "conversation_message_participant_fact");
    case "course_dim":
      return new DelimitedTableReader<CourseDim>(CourseDim.class, format, file, "course_dim");
    case "course_section_dim":
      return new DelimitedTableReader<CourseSectionDim>(CourseSectionDim.class, format, file, "course_section_dim");
    case "course_ui_canvas_navigation_dim":
      return new DelimitedTableReader<CourseUiCanvasNavigationDim>(CourseUiCanvasNavigationDim.class, format, file, "course_ui_canvas_navigation_dim");
    case "course_ui_navigation_item_dim":
      return new DelimitedTableReader<CourseUiNavigationItemDim>(CourseUiNavigationItemDim.class, format, file, "course_ui_navigation_item_dim");
    case "course_ui_navigation_item_fact":
      return new DelimitedTableReader<CourseUiNavigationItemFact>(CourseUiNavigationItemFact.class, format, file, "course_ui_navigation_item_fact");
    case "discussion_entry_dim":
      return new DelimitedTableReader<DiscussionEntryDim>(DiscussionEntryDim.class, format, file, "discussion_entry_dim");
    case "discussion_entry_fact":
      return new DelimitedTableReader<DiscussionEntryFact>(DiscussionEntryFact.class, format, file, "discussion_entry_fact");
    case "discussion_topic_dim":
      return new DelimitedTableReader<DiscussionTopicDim>(DiscussionTopicDim.class, format, file, "discussion_topic_dim");
    case "discussion_topic_fact":
      return new DelimitedTableReader<DiscussionTopicFact>(DiscussionTopicFact.class, format, file, "discussion_topic_fact");
    case "enrollment_dim":
      return new DelimitedTableReader<EnrollmentDim>(EnrollmentDim.class, format, file, "enrollment_dim");
    case "enrollment_fact":
      return new DelimitedTableReader<EnrollmentFact>(EnrollmentFact.class, format, file, "enrollment_fact");
    case "enrollment_rollup_dim":
      return new DelimitedTableReader<EnrollmentRollupDim>(EnrollmentRollupDim.class, format, file, "enrollment_rollup_dim");
    case "enrollment_term_dim":
      return new DelimitedTableReader<EnrollmentTermDim>(EnrollmentTermDim.class, format, file, "enrollment_term_dim");
    case "external_tool_activation_dim":
      return new DelimitedTableReader<ExternalToolActivationDim>(ExternalToolActivationDim.class, format, file, "external_tool_activation_dim");
    case "external_tool_activation_fact":
      return new DelimitedTableReader<ExternalToolActivationFact>(ExternalToolActivationFact.class, format, file, "external_tool_activation_fact");
    case "group_dim":
      return new DelimitedTableReader<GroupDim>(GroupDim.class, format, file, "group_dim");
    case "group_fact":
      return new DelimitedTableReader<GroupFact>(GroupFact.class, format, file, "group_fact");
    case "group_membership_fact":
      return new DelimitedTableReader<GroupMembershipFact>(GroupMembershipFact.class, format, file, "group_membership_fact");
    case "pseudonym_dim":
      return new DelimitedTableReader<PseudonymDim>(PseudonymDim.class, format, file, "pseudonym_dim");
    case "pseudonym_fact":
      return new DelimitedTableReader<PseudonymFact>(PseudonymFact.class, format, file, "pseudonym_fact");
    case "quiz_dim":
      return new DelimitedTableReader<QuizDim>(QuizDim.class, format, file, "quiz_dim");
    case "quiz_fact":
      return new DelimitedTableReader<QuizFact>(QuizFact.class, format, file, "quiz_fact");
    case "quiz_question_answer_dim":
      return new DelimitedTableReader<QuizQuestionAnswerDim>(QuizQuestionAnswerDim.class, format, file, "quiz_question_answer_dim");
    case "quiz_question_answer_fact":
      return new DelimitedTableReader<QuizQuestionAnswerFact>(QuizQuestionAnswerFact.class, format, file, "quiz_question_answer_fact");
    case "quiz_question_dim":
      return new DelimitedTableReader<QuizQuestionDim>(QuizQuestionDim.class, format, file, "quiz_question_dim");
    case "quiz_question_fact":
      return new DelimitedTableReader<QuizQuestionFact>(QuizQuestionFact.class, format, file, "quiz_question_fact");
    case "quiz_question_group_dim":
      return new DelimitedTableReader<QuizQuestionGroupDim>(QuizQuestionGroupDim.class, format, file, "quiz_question_group_dim");
    case "quiz_question_group_fact":
      return new DelimitedTableReader<QuizQuestionGroupFact>(QuizQuestionGroupFact.class, format, file, "quiz_question_group_fact");
    case "quiz_submission_dim":
      return new DelimitedTableReader<QuizSubmissionDim>(QuizSubmissionDim.class, format, file, "quiz_submission_dim");
    case "quiz_submission_fact":
      return new DelimitedTableReader<QuizSubmissionFact>(QuizSubmissionFact.class, format, file, "quiz_submission_fact");
    case "quiz_submission_historical_dim":
      return new DelimitedTableReader<QuizSubmissionHistoricalDim>(QuizSubmissionHistoricalDim.class, format, file, "quiz_submission_historical_dim");
    case "quiz_submission_historical_fact":
      return new DelimitedTableReader<QuizSubmissionHistoricalFact>(QuizSubmissionHistoricalFact.class, format, file, "quiz_submission_historical_fact");
    case "requests":
      return new DelimitedTableReader<Requests>(Requests.class, format, file, "requests");
    case "role_dim":
      return new DelimitedTableReader<RoleDim>(RoleDim.class, format, file, "role_dim");
    case "submission_comment_dim":
      return new DelimitedTableReader<SubmissionCommentDim>(SubmissionCommentDim.class, format, file, "submission_comment_dim");
    case "submission_comment_fact":
      return new DelimitedTableReader<SubmissionCommentFact>(SubmissionCommentFact.class, format, file, "submission_comment_fact");
    case "submission_comment_participant_dim":
      return new DelimitedTableReader<SubmissionCommentParticipantDim>(SubmissionCommentParticipantDim.class, format, file, "submission_comment_participant_dim");
    case "submission_comment_participant_fact":
      return new DelimitedTableReader<SubmissionCommentParticipantFact>(SubmissionCommentParticipantFact.class, format, file, "submission_comment_participant_fact");
    case "submission_dim":
      return new DelimitedTableReader<SubmissionDim>(SubmissionDim.class, format, file, "submission_dim");
    case "submission_fact":
      return new DelimitedTableReader<SubmissionFact>(SubmissionFact.class, format, file, "submission_fact");
    case "user_dim":
      return new DelimitedTableReader<UserDim>(UserDim.class, format, file, "user_dim");
    case "wiki_dim":
      return new DelimitedTableReader<WikiDim>(WikiDim.class, format, file, "wiki_dim");
    case "wiki_fact":
      return new DelimitedTableReader<WikiFact>(WikiFact.class, format, file, "wiki_fact");
    case "wiki_page_dim":
      return new DelimitedTableReader<WikiPageDim>(WikiPageDim.class, format, file, "wiki_page_dim");
    case "wiki_page_fact":
      return new DelimitedTableReader<WikiPageFact>(WikiPageFact.class, format, file, "wiki_page_fact");
    }
    return null;
  }

  @Override
  public TableReader<? extends DataTable> getS3TableReader(final String table, final TableFormat format, final AwsUtils aws, final S3ObjectId obj, final File tempDir) throws IOException {
    switch(table) {
    case "account_dim":
      return new S3TableReader<AccountDim>(aws, AccountDim.class, format, obj, "account_dim", tempDir);
    case "assignment_dim":
      return new S3TableReader<AssignmentDim>(aws, AssignmentDim.class, format, obj, "assignment_dim", tempDir);
    case "assignment_fact":
      return new S3TableReader<AssignmentFact>(aws, AssignmentFact.class, format, obj, "assignment_fact", tempDir);
    case "conversation_dim":
      return new S3TableReader<ConversationDim>(aws, ConversationDim.class, format, obj, "conversation_dim", tempDir);
    case "conversation_message_dim":
      return new S3TableReader<ConversationMessageDim>(aws, ConversationMessageDim.class, format, obj, "conversation_message_dim", tempDir);
    case "conversation_message_participant_fact":
      return new S3TableReader<ConversationMessageParticipantFact>(aws, ConversationMessageParticipantFact.class, format, obj, "conversation_message_participant_fact", tempDir);
    case "course_dim":
      return new S3TableReader<CourseDim>(aws, CourseDim.class, format, obj, "course_dim", tempDir);
    case "course_section_dim":
      return new S3TableReader<CourseSectionDim>(aws, CourseSectionDim.class, format, obj, "course_section_dim", tempDir);
    case "course_ui_canvas_navigation_dim":
      return new S3TableReader<CourseUiCanvasNavigationDim>(aws, CourseUiCanvasNavigationDim.class, format, obj, "course_ui_canvas_navigation_dim", tempDir);
    case "course_ui_navigation_item_dim":
      return new S3TableReader<CourseUiNavigationItemDim>(aws, CourseUiNavigationItemDim.class, format, obj, "course_ui_navigation_item_dim", tempDir);
    case "course_ui_navigation_item_fact":
      return new S3TableReader<CourseUiNavigationItemFact>(aws, CourseUiNavigationItemFact.class, format, obj, "course_ui_navigation_item_fact", tempDir);
    case "discussion_entry_dim":
      return new S3TableReader<DiscussionEntryDim>(aws, DiscussionEntryDim.class, format, obj, "discussion_entry_dim", tempDir);
    case "discussion_entry_fact":
      return new S3TableReader<DiscussionEntryFact>(aws, DiscussionEntryFact.class, format, obj, "discussion_entry_fact", tempDir);
    case "discussion_topic_dim":
      return new S3TableReader<DiscussionTopicDim>(aws, DiscussionTopicDim.class, format, obj, "discussion_topic_dim", tempDir);
    case "discussion_topic_fact":
      return new S3TableReader<DiscussionTopicFact>(aws, DiscussionTopicFact.class, format, obj, "discussion_topic_fact", tempDir);
    case "enrollment_dim":
      return new S3TableReader<EnrollmentDim>(aws, EnrollmentDim.class, format, obj, "enrollment_dim", tempDir);
    case "enrollment_fact":
      return new S3TableReader<EnrollmentFact>(aws, EnrollmentFact.class, format, obj, "enrollment_fact", tempDir);
    case "enrollment_rollup_dim":
      return new S3TableReader<EnrollmentRollupDim>(aws, EnrollmentRollupDim.class, format, obj, "enrollment_rollup_dim", tempDir);
    case "enrollment_term_dim":
      return new S3TableReader<EnrollmentTermDim>(aws, EnrollmentTermDim.class, format, obj, "enrollment_term_dim", tempDir);
    case "external_tool_activation_dim":
      return new S3TableReader<ExternalToolActivationDim>(aws, ExternalToolActivationDim.class, format, obj, "external_tool_activation_dim", tempDir);
    case "external_tool_activation_fact":
      return new S3TableReader<ExternalToolActivationFact>(aws, ExternalToolActivationFact.class, format, obj, "external_tool_activation_fact", tempDir);
    case "group_dim":
      return new S3TableReader<GroupDim>(aws, GroupDim.class, format, obj, "group_dim", tempDir);
    case "group_fact":
      return new S3TableReader<GroupFact>(aws, GroupFact.class, format, obj, "group_fact", tempDir);
    case "group_membership_fact":
      return new S3TableReader<GroupMembershipFact>(aws, GroupMembershipFact.class, format, obj, "group_membership_fact", tempDir);
    case "pseudonym_dim":
      return new S3TableReader<PseudonymDim>(aws, PseudonymDim.class, format, obj, "pseudonym_dim", tempDir);
    case "pseudonym_fact":
      return new S3TableReader<PseudonymFact>(aws, PseudonymFact.class, format, obj, "pseudonym_fact", tempDir);
    case "quiz_dim":
      return new S3TableReader<QuizDim>(aws, QuizDim.class, format, obj, "quiz_dim", tempDir);
    case "quiz_fact":
      return new S3TableReader<QuizFact>(aws, QuizFact.class, format, obj, "quiz_fact", tempDir);
    case "quiz_question_answer_dim":
      return new S3TableReader<QuizQuestionAnswerDim>(aws, QuizQuestionAnswerDim.class, format, obj, "quiz_question_answer_dim", tempDir);
    case "quiz_question_answer_fact":
      return new S3TableReader<QuizQuestionAnswerFact>(aws, QuizQuestionAnswerFact.class, format, obj, "quiz_question_answer_fact", tempDir);
    case "quiz_question_dim":
      return new S3TableReader<QuizQuestionDim>(aws, QuizQuestionDim.class, format, obj, "quiz_question_dim", tempDir);
    case "quiz_question_fact":
      return new S3TableReader<QuizQuestionFact>(aws, QuizQuestionFact.class, format, obj, "quiz_question_fact", tempDir);
    case "quiz_question_group_dim":
      return new S3TableReader<QuizQuestionGroupDim>(aws, QuizQuestionGroupDim.class, format, obj, "quiz_question_group_dim", tempDir);
    case "quiz_question_group_fact":
      return new S3TableReader<QuizQuestionGroupFact>(aws, QuizQuestionGroupFact.class, format, obj, "quiz_question_group_fact", tempDir);
    case "quiz_submission_dim":
      return new S3TableReader<QuizSubmissionDim>(aws, QuizSubmissionDim.class, format, obj, "quiz_submission_dim", tempDir);
    case "quiz_submission_fact":
      return new S3TableReader<QuizSubmissionFact>(aws, QuizSubmissionFact.class, format, obj, "quiz_submission_fact", tempDir);
    case "quiz_submission_historical_dim":
      return new S3TableReader<QuizSubmissionHistoricalDim>(aws, QuizSubmissionHistoricalDim.class, format, obj, "quiz_submission_historical_dim", tempDir);
    case "quiz_submission_historical_fact":
      return new S3TableReader<QuizSubmissionHistoricalFact>(aws, QuizSubmissionHistoricalFact.class, format, obj, "quiz_submission_historical_fact", tempDir);
    case "requests":
      return new S3TableReader<Requests>(aws, Requests.class, format, obj, "requests", tempDir);
    case "role_dim":
      return new S3TableReader<RoleDim>(aws, RoleDim.class, format, obj, "role_dim", tempDir);
    case "submission_comment_dim":
      return new S3TableReader<SubmissionCommentDim>(aws, SubmissionCommentDim.class, format, obj, "submission_comment_dim", tempDir);
    case "submission_comment_fact":
      return new S3TableReader<SubmissionCommentFact>(aws, SubmissionCommentFact.class, format, obj, "submission_comment_fact", tempDir);
    case "submission_comment_participant_dim":
      return new S3TableReader<SubmissionCommentParticipantDim>(aws, SubmissionCommentParticipantDim.class, format, obj, "submission_comment_participant_dim", tempDir);
    case "submission_comment_participant_fact":
      return new S3TableReader<SubmissionCommentParticipantFact>(aws, SubmissionCommentParticipantFact.class, format, obj, "submission_comment_participant_fact", tempDir);
    case "submission_dim":
      return new S3TableReader<SubmissionDim>(aws, SubmissionDim.class, format, obj, "submission_dim", tempDir);
    case "submission_fact":
      return new S3TableReader<SubmissionFact>(aws, SubmissionFact.class, format, obj, "submission_fact", tempDir);
    case "user_dim":
      return new S3TableReader<UserDim>(aws, UserDim.class, format, obj, "user_dim", tempDir);
    case "wiki_dim":
      return new S3TableReader<WikiDim>(aws, WikiDim.class, format, obj, "wiki_dim", tempDir);
    case "wiki_fact":
      return new S3TableReader<WikiFact>(aws, WikiFact.class, format, obj, "wiki_fact", tempDir);
    case "wiki_page_dim":
      return new S3TableReader<WikiPageDim>(aws, WikiPageDim.class, format, obj, "wiki_page_dim", tempDir);
    case "wiki_page_fact":
      return new S3TableReader<WikiPageFact>(aws, WikiPageFact.class, format, obj, "wiki_page_fact", tempDir);
    }
    return null;
  }

  @Override
  public TableWriter<? extends DataTable> getDelimitedTableWriter(final String table, final TableFormat format, final Path directory) throws IOException {
    switch(table) {
    case "account_dim":
      return new DelimitedTableWriter<AccountDim>(AccountDim.class, format, directory, "account_dim");
    case "assignment_dim":
      return new DelimitedTableWriter<AssignmentDim>(AssignmentDim.class, format, directory, "assignment_dim");
    case "assignment_fact":
      return new DelimitedTableWriter<AssignmentFact>(AssignmentFact.class, format, directory, "assignment_fact");
    case "conversation_dim":
      return new DelimitedTableWriter<ConversationDim>(ConversationDim.class, format, directory, "conversation_dim");
    case "conversation_message_dim":
      return new DelimitedTableWriter<ConversationMessageDim>(ConversationMessageDim.class, format, directory, "conversation_message_dim");
    case "conversation_message_participant_fact":
      return new DelimitedTableWriter<ConversationMessageParticipantFact>(ConversationMessageParticipantFact.class, format, directory, "conversation_message_participant_fact");
    case "course_dim":
      return new DelimitedTableWriter<CourseDim>(CourseDim.class, format, directory, "course_dim");
    case "course_section_dim":
      return new DelimitedTableWriter<CourseSectionDim>(CourseSectionDim.class, format, directory, "course_section_dim");
    case "course_ui_canvas_navigation_dim":
      return new DelimitedTableWriter<CourseUiCanvasNavigationDim>(CourseUiCanvasNavigationDim.class, format, directory, "course_ui_canvas_navigation_dim");
    case "course_ui_navigation_item_dim":
      return new DelimitedTableWriter<CourseUiNavigationItemDim>(CourseUiNavigationItemDim.class, format, directory, "course_ui_navigation_item_dim");
    case "course_ui_navigation_item_fact":
      return new DelimitedTableWriter<CourseUiNavigationItemFact>(CourseUiNavigationItemFact.class, format, directory, "course_ui_navigation_item_fact");
    case "discussion_entry_dim":
      return new DelimitedTableWriter<DiscussionEntryDim>(DiscussionEntryDim.class, format, directory, "discussion_entry_dim");
    case "discussion_entry_fact":
      return new DelimitedTableWriter<DiscussionEntryFact>(DiscussionEntryFact.class, format, directory, "discussion_entry_fact");
    case "discussion_topic_dim":
      return new DelimitedTableWriter<DiscussionTopicDim>(DiscussionTopicDim.class, format, directory, "discussion_topic_dim");
    case "discussion_topic_fact":
      return new DelimitedTableWriter<DiscussionTopicFact>(DiscussionTopicFact.class, format, directory, "discussion_topic_fact");
    case "enrollment_dim":
      return new DelimitedTableWriter<EnrollmentDim>(EnrollmentDim.class, format, directory, "enrollment_dim");
    case "enrollment_fact":
      return new DelimitedTableWriter<EnrollmentFact>(EnrollmentFact.class, format, directory, "enrollment_fact");
    case "enrollment_rollup_dim":
      return new DelimitedTableWriter<EnrollmentRollupDim>(EnrollmentRollupDim.class, format, directory, "enrollment_rollup_dim");
    case "enrollment_term_dim":
      return new DelimitedTableWriter<EnrollmentTermDim>(EnrollmentTermDim.class, format, directory, "enrollment_term_dim");
    case "external_tool_activation_dim":
      return new DelimitedTableWriter<ExternalToolActivationDim>(ExternalToolActivationDim.class, format, directory, "external_tool_activation_dim");
    case "external_tool_activation_fact":
      return new DelimitedTableWriter<ExternalToolActivationFact>(ExternalToolActivationFact.class, format, directory, "external_tool_activation_fact");
    case "group_dim":
      return new DelimitedTableWriter<GroupDim>(GroupDim.class, format, directory, "group_dim");
    case "group_fact":
      return new DelimitedTableWriter<GroupFact>(GroupFact.class, format, directory, "group_fact");
    case "group_membership_fact":
      return new DelimitedTableWriter<GroupMembershipFact>(GroupMembershipFact.class, format, directory, "group_membership_fact");
    case "pseudonym_dim":
      return new DelimitedTableWriter<PseudonymDim>(PseudonymDim.class, format, directory, "pseudonym_dim");
    case "pseudonym_fact":
      return new DelimitedTableWriter<PseudonymFact>(PseudonymFact.class, format, directory, "pseudonym_fact");
    case "quiz_dim":
      return new DelimitedTableWriter<QuizDim>(QuizDim.class, format, directory, "quiz_dim");
    case "quiz_fact":
      return new DelimitedTableWriter<QuizFact>(QuizFact.class, format, directory, "quiz_fact");
    case "quiz_question_answer_dim":
      return new DelimitedTableWriter<QuizQuestionAnswerDim>(QuizQuestionAnswerDim.class, format, directory, "quiz_question_answer_dim");
    case "quiz_question_answer_fact":
      return new DelimitedTableWriter<QuizQuestionAnswerFact>(QuizQuestionAnswerFact.class, format, directory, "quiz_question_answer_fact");
    case "quiz_question_dim":
      return new DelimitedTableWriter<QuizQuestionDim>(QuizQuestionDim.class, format, directory, "quiz_question_dim");
    case "quiz_question_fact":
      return new DelimitedTableWriter<QuizQuestionFact>(QuizQuestionFact.class, format, directory, "quiz_question_fact");
    case "quiz_question_group_dim":
      return new DelimitedTableWriter<QuizQuestionGroupDim>(QuizQuestionGroupDim.class, format, directory, "quiz_question_group_dim");
    case "quiz_question_group_fact":
      return new DelimitedTableWriter<QuizQuestionGroupFact>(QuizQuestionGroupFact.class, format, directory, "quiz_question_group_fact");
    case "quiz_submission_dim":
      return new DelimitedTableWriter<QuizSubmissionDim>(QuizSubmissionDim.class, format, directory, "quiz_submission_dim");
    case "quiz_submission_fact":
      return new DelimitedTableWriter<QuizSubmissionFact>(QuizSubmissionFact.class, format, directory, "quiz_submission_fact");
    case "quiz_submission_historical_dim":
      return new DelimitedTableWriter<QuizSubmissionHistoricalDim>(QuizSubmissionHistoricalDim.class, format, directory, "quiz_submission_historical_dim");
    case "quiz_submission_historical_fact":
      return new DelimitedTableWriter<QuizSubmissionHistoricalFact>(QuizSubmissionHistoricalFact.class, format, directory, "quiz_submission_historical_fact");
    case "requests":
      return new DelimitedTableWriter<Requests>(Requests.class, format, directory, "requests");
    case "role_dim":
      return new DelimitedTableWriter<RoleDim>(RoleDim.class, format, directory, "role_dim");
    case "submission_comment_dim":
      return new DelimitedTableWriter<SubmissionCommentDim>(SubmissionCommentDim.class, format, directory, "submission_comment_dim");
    case "submission_comment_fact":
      return new DelimitedTableWriter<SubmissionCommentFact>(SubmissionCommentFact.class, format, directory, "submission_comment_fact");
    case "submission_comment_participant_dim":
      return new DelimitedTableWriter<SubmissionCommentParticipantDim>(SubmissionCommentParticipantDim.class, format, directory, "submission_comment_participant_dim");
    case "submission_comment_participant_fact":
      return new DelimitedTableWriter<SubmissionCommentParticipantFact>(SubmissionCommentParticipantFact.class, format, directory, "submission_comment_participant_fact");
    case "submission_dim":
      return new DelimitedTableWriter<SubmissionDim>(SubmissionDim.class, format, directory, "submission_dim");
    case "submission_fact":
      return new DelimitedTableWriter<SubmissionFact>(SubmissionFact.class, format, directory, "submission_fact");
    case "user_dim":
      return new DelimitedTableWriter<UserDim>(UserDim.class, format, directory, "user_dim");
    case "wiki_dim":
      return new DelimitedTableWriter<WikiDim>(WikiDim.class, format, directory, "wiki_dim");
    case "wiki_fact":
      return new DelimitedTableWriter<WikiFact>(WikiFact.class, format, directory, "wiki_fact");
    case "wiki_page_dim":
      return new DelimitedTableWriter<WikiPageDim>(WikiPageDim.class, format, directory, "wiki_page_dim");
    case "wiki_page_fact":
      return new DelimitedTableWriter<WikiPageFact>(WikiPageFact.class, format, directory, "wiki_page_fact");
    }
    return null;
  }
}
