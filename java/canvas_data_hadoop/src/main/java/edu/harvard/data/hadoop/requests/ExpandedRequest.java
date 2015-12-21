package edu.harvard.data.hadoop.requests;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.io.Writable;

import edu.harvard.data.client.TableFormat;
import edu.harvard.data.client.canvas.tables.Requests;

public class ExpandedRequest implements Writable {

  private static final String NULL_STRING = "!!NULL!!";

  private String id;
  private Long timestamp;
  private String timestampYear;
  private String timestampMonth;
  private String timestampDay;
  private Long userId;
  private Long courseId;
  private Long rootAccountId;
  private Long courseAccountId;
  private Long quizId;
  private Long discussionId;
  private Long conversationId;
  private Long assignmentId;
  private String url;
  private String userAgent;
  private String httpMethod;
  private String remoteIp;
  private Long interactionMicros;
  private String webApplicationController;
  private String webApplicaitonAction;
  private String webApplicationContextType;
  private String webApplicationContextId;
  private String browser;
  private String os;

  public ExpandedRequest() {
  }

  public ExpandedRequest(final Requests request) {
    this.id = request.getId();
    this.timestamp = request.getTimestamp().getTime();
    this.timestampYear = request.getTimestampYear();
    this.timestampMonth = request.getTimestampMonth();
    this.timestampDay = request.getTimestampDay();
    this.userId = request.getUserId();
    this.courseId = request.getCourseId();
    this.rootAccountId = request.getRootAccountId();
    this.courseAccountId = request.getCourseAccountId();
    this.quizId = request.getQuizId();
    this.discussionId = request.getDiscussionId();
    this.conversationId = request.getConversationId();
    this.assignmentId = request.getAssignmentId();
    this.url = request.getUrl();
    this.userAgent = request.getUserAgent();
    this.httpMethod = request.getHttpMethod();
    this.remoteIp = request.getRemoteIp();
    this.interactionMicros = request.getInteractionMicros();
    this.webApplicationController = request.getWebApplicationController();
    this.webApplicaitonAction = request.getWebApplicaitonAction();
    this.webApplicationContextType = request.getWebApplicationContextType();
    this.webApplicationContextId = request.getWebApplicationContextId();
  }

  @Override
  public void write(final DataOutput out) throws IOException {
    out.writeUTF(id);
    out.writeLong(timestamp);
    out.writeUTF(timestampYear == null ? NULL_STRING : timestampYear);
    out.writeUTF(timestampMonth == null ? NULL_STRING : timestampMonth);
    out.writeUTF(timestampDay == null ? NULL_STRING : timestampDay);
    out.writeLong(userId == null ? Long.MIN_VALUE : userId);
    out.writeLong(courseId == null ? Long.MIN_VALUE : courseId);
    out.writeLong(rootAccountId == null ? Long.MIN_VALUE : rootAccountId);
    out.writeLong(courseAccountId == null ? Long.MIN_VALUE : courseAccountId);
    out.writeLong(quizId == null ? Long.MIN_VALUE : quizId);
    out.writeLong(discussionId == null ? Long.MIN_VALUE : discussionId);
    out.writeLong(conversationId == null ? Long.MIN_VALUE : conversationId);
    out.writeLong(assignmentId == null ? Long.MIN_VALUE : assignmentId);
    out.writeUTF(url == null ? NULL_STRING : url);
    out.writeUTF(userAgent == null ? NULL_STRING : userAgent);
    out.writeUTF(httpMethod == null ? NULL_STRING : httpMethod);
    out.writeUTF(remoteIp == null ? NULL_STRING : remoteIp);
    out.writeLong(interactionMicros == null ? Long.MIN_VALUE : interactionMicros);
    out.writeUTF(webApplicationController == null ? NULL_STRING : webApplicationController);
    out.writeUTF(webApplicaitonAction == null ? NULL_STRING : webApplicaitonAction);
    out.writeUTF(webApplicationContextType == null ? NULL_STRING : webApplicationContextType);
    out.writeUTF(webApplicationContextId == null ? NULL_STRING : webApplicationContextId);
    out.writeUTF(browser == null ? NULL_STRING : browser);
    out.writeUTF(os == null ? NULL_STRING : os);
  }

  @Override
  public void readFields(final DataInput in) throws IOException {
    id = in.readUTF();
    timestamp = in.readLong();
    timestampYear = in.readUTF();
    if (timestampYear.equals(NULL_STRING)) { timestampYear = null; }
    timestampMonth = in.readUTF();
    if (timestampMonth.equals(NULL_STRING)) { timestampMonth = null; }
    timestampDay = in.readUTF();
    if (timestampDay.equals(NULL_STRING)) { timestampDay = null; }
    userId = in.readLong();
    if (userId == Long.MIN_VALUE) { userId = null; }
    courseId = in.readLong();
    if (courseId == Long.MIN_VALUE) { courseId = null; }
    rootAccountId = in.readLong();
    if (rootAccountId == Long.MIN_VALUE) { rootAccountId = null; }
    courseAccountId = in.readLong();
    if (courseAccountId == Long.MIN_VALUE) { courseAccountId = null; }
    quizId = in.readLong();
    if (quizId == Long.MIN_VALUE) { quizId = null; }
    discussionId = in.readLong();
    if (discussionId == Long.MIN_VALUE) { discussionId = null; }
    conversationId = in.readLong();
    if (conversationId == Long.MIN_VALUE) { conversationId = null; }
    assignmentId = in.readLong();
    if (assignmentId == Long.MIN_VALUE) { assignmentId = null; }
    url = in.readUTF();
    if (url.equals(NULL_STRING)) { url = null; }
    userAgent = in.readUTF();
    if (userAgent.equals(NULL_STRING)) { userAgent = null; }
    httpMethod = in.readUTF();
    if (httpMethod.equals(NULL_STRING)) { httpMethod = null; }
    remoteIp = in.readUTF();
    if (remoteIp.equals(NULL_STRING)) { remoteIp = null; }
    interactionMicros = in.readLong();
    if (interactionMicros == Long.MIN_VALUE) { interactionMicros = null; }
    webApplicationController = in.readUTF();
    if (webApplicationController.equals(NULL_STRING)) { webApplicationController = null; }
    webApplicaitonAction = in.readUTF();
    if (webApplicaitonAction.equals(NULL_STRING)) { webApplicaitonAction = null; }
    webApplicationContextType = in.readUTF();
    if (webApplicationContextType.equals(NULL_STRING)) { webApplicationContextType = null; }
    webApplicationContextId = in.readUTF();
    if (webApplicationContextId.equals(NULL_STRING)) { webApplicationContextId = null; }
    browser = in.readUTF();
    if (browser.equals(NULL_STRING)) { browser = null; }
    os = in.readUTF();
    if (os.equals(NULL_STRING)) { os = null; }
  }

  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(id);
    fields.add(formatter.formatTimestamp(new Date(timestamp)));
    fields.add(timestampYear);
    fields.add(timestampMonth);
    fields.add(timestampDay);
    fields.add(userId);
    fields.add(courseId);
    fields.add(rootAccountId);
    fields.add(courseAccountId);
    fields.add(quizId);
    fields.add(discussionId);
    fields.add(conversationId);
    fields.add(assignmentId);
    fields.add(url);
    fields.add(userAgent);
    fields.add(httpMethod);
    fields.add(remoteIp);
    fields.add(interactionMicros);
    fields.add(webApplicationController);
    fields.add(webApplicaitonAction);
    fields.add(webApplicationContextType);
    fields.add(webApplicationContextId);
    fields.add(browser);
    fields.add(os);
    return fields;
  }

  public String getId() {
    return id;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getTimestampYear() {
    return timestampYear;
  }

  public String getTimestampMonth() {
    return timestampMonth;
  }

  public String getTimestampDay() {
    return timestampDay;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public Long getRootAccountId() {
    return rootAccountId;
  }

  public Long getCourseAccountId() {
    return courseAccountId;
  }

  public Long getQuizId() {
    return quizId;
  }

  public Long getDiscussionId() {
    return discussionId;
  }

  public Long getConversationId() {
    return conversationId;
  }

  public Long getAssignmentId() {
    return assignmentId;
  }

  public String getUrl() {
    return url;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public String getRemoteIp() {
    return remoteIp;
  }

  public Long getInteractionMicros() {
    return interactionMicros;
  }

  public String getWebApplicationController() {
    return webApplicationController;
  }

  public String getWebApplicaitonAction() {
    return webApplicaitonAction;
  }

  public String getWebApplicationContextType() {
    return webApplicationContextType;
  }

  public String getWebApplicationContextId() {
    return webApplicationContextId;
  }

  public void setBrowser(final String browser) {
    this.browser = browser;
  }

  public void setOs(final String os) {
    this.os = os;
  }

  public String getBrowser() {
    return browser;
  }

  public String getOs() {
    return os;
  }

}
