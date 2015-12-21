// This file was generated on 12-21-2015 04:02:40. Do not manually edit. 
// This class is based on Version 1.3.0 of the Canvas Data schema 

package edu.harvard.data.client.canvas.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import edu.harvard.data.client.DataTable;
import edu.harvard.data.client.TableFormat;

public class GroupFact implements DataTable {
  private Long groupId;
  private Long parentCourseId;
  private Long parentAccountId;
  private Long parentCourseAccountId;
  private Long enrollmentTermId;
  private Integer maxMembership;
  private Long storageQuota;
  private Long groupCategoryId;
  private Long accountId;
  private Long wikiId;

  public GroupFact(final TableFormat format, final CSVRecord record) {
    String $groupId = record.get(0);
    if ($groupId != null && $groupId.length() > 0) {
      this.groupId = Long.valueOf($groupId);
    }
    String $parentCourseId = record.get(1);
    if ($parentCourseId != null && $parentCourseId.length() > 0) {
      this.parentCourseId = Long.valueOf($parentCourseId);
    }
    String $parentAccountId = record.get(2);
    if ($parentAccountId != null && $parentAccountId.length() > 0) {
      this.parentAccountId = Long.valueOf($parentAccountId);
    }
    String $parentCourseAccountId = record.get(3);
    if ($parentCourseAccountId != null && $parentCourseAccountId.length() > 0) {
      this.parentCourseAccountId = Long.valueOf($parentCourseAccountId);
    }
    String $enrollmentTermId = record.get(4);
    if ($enrollmentTermId != null && $enrollmentTermId.length() > 0) {
      this.enrollmentTermId = Long.valueOf($enrollmentTermId);
    }
    String $maxMembership = record.get(5);
    if ($maxMembership != null && $maxMembership.length() > 0) {
      this.maxMembership = Integer.valueOf($maxMembership);
    }
    String $storageQuota = record.get(6);
    if ($storageQuota != null && $storageQuota.length() > 0) {
      this.storageQuota = Long.valueOf($storageQuota);
    }
    String $groupCategoryId = record.get(7);
    if ($groupCategoryId != null && $groupCategoryId.length() > 0) {
      this.groupCategoryId = Long.valueOf($groupCategoryId);
    }
    String $accountId = record.get(8);
    if ($accountId != null && $accountId.length() > 0) {
      this.accountId = Long.valueOf($accountId);
    }
    String $wikiId = record.get(9);
    if ($wikiId != null && $wikiId.length() > 0) {
      this.wikiId = Long.valueOf($wikiId);
    }
  }

  /**
   * Foreign key to the group dimension for a particular group. 
   */
  public Long getGroupId() {
    return this.groupId;
  }

  /**
   * Foreign key to course dimension. 
   */
  public Long getParentCourseId() {
    return this.parentCourseId;
  }

  /**
   * Foreign key to accounts table. 
   */
  public Long getParentAccountId() {
    return this.parentAccountId;
  }

  /**
   * Foreign key to the account dimension for the account associated with the 
   * course to which the group belongs to. 
   */
  public Long getParentCourseAccountId() {
    return this.parentCourseAccountId;
  }

  /**
   * Foreign key to the enrollment term table for the parent course. 
   */
  public Long getEnrollmentTermId() {
    return this.enrollmentTermId;
  }

  /**
   * Maximum number of users that can be accommodated in a group. 
   */
  public Integer getMaxMembership() {
    return this.maxMembership;
  }

  /**
   * Storage Limit allowed per group. 
   */
  public Long getStorageQuota() {
    return this.storageQuota;
  }

  /**
   * Foreign key to group_category_dim table. 
   */
  public Long getGroupCategoryId() {
    return this.groupCategoryId;
  }

  /**
   * Parent account for this group. 
   */
  public Long getAccountId() {
    return this.accountId;
  }

  /**
   * Foreign key to the wiki_dim table. 
   */
  public Long getWikiId() {
    return this.wikiId;
  }

  @Override
  public List<Object> getFieldsAsList(final TableFormat formatter) {
    final List<Object> fields = new ArrayList<Object>();
    fields.add(groupId);
    fields.add(parentCourseId);
    fields.add(parentAccountId);
    fields.add(parentCourseAccountId);
    fields.add(enrollmentTermId);
    fields.add(maxMembership);
    fields.add(storageQuota);
    fields.add(groupCategoryId);
    fields.add(accountId);
    fields.add(wikiId);
    return fields;
  }

  public static List<String> getFieldNames() {
    final List<String> fields = new ArrayList<String>();
      fields.add("group_id");
      fields.add("parent_course_id");
      fields.add("parent_account_id");
      fields.add("parent_course_account_id");
      fields.add("enrollment_term_id");
      fields.add("max_membership");
      fields.add("storage_quota");
      fields.add("group_category_id");
      fields.add("account_id");
      fields.add("wiki_id");
    return fields;
  }
}
