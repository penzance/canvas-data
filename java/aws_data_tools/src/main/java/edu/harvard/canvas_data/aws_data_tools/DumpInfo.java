package edu.harvard.canvas_data.aws_data_tools;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.s3.model.S3ObjectId;

import edu.harvard.data.client.AwsUtils;

@DynamoDBTable(tableName = "DumpStatus")
public class DumpInfo {

  private static DynamoDBMapper mapper;

  static {
    mapper = new DynamoDBMapper(new AmazonDynamoDBClient());
  }

  @DynamoDBHashKey(attributeName = "id")
  private String id;

  @DynamoDBAttribute(attributeName = "sequence")
  private Long sequence;

  @DynamoDBAttribute(attributeName = "downloaded")
  private boolean downloaded;

  @DynamoDBAttribute(attributeName = "verified")
  private boolean verified;

  @DynamoDBAttribute(attributeName = "s3Bucket")
  private String bucket;

  @DynamoDBAttribute(attributeName = "s3Key")
  private String key;

  @DynamoDBAttribute(attributeName = "schemaVersion")
  private String schemaVersion;

  @DynamoDBAttribute(attributeName = "downloadStart")
  private Date downloadStart;

  @DynamoDBAttribute(attributeName = "downloadEnd")
  private Date downloadEnd;

  public DumpInfo() {
    downloaded = false;
    verified = false;
  }

  public DumpInfo(final String id) {
    this();
    this.id = id;
  }

  public DumpInfo(final String id, final long sequence, final String schemaVersion) {
    this(id);
    this.sequence = sequence;
    this.schemaVersion = schemaVersion;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Long getSequence() {
    return sequence;
  }

  public void setSequence(final Long sequence) {
    this.sequence = sequence;
  }

  public String getSchemaVersion() {
    return schemaVersion;
  }

  public void setSchemaVersion(final String schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  public Boolean getDownloaded() {
    return downloaded;
  }

  public void setDownloaded(final Boolean downloaded) {
    this.downloaded = downloaded;
  }

  public Boolean getVerified() {
    return verified;
  }

  public void setVerified(final Boolean verified) {
    this.verified = verified;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(final String bucket) {
    this.bucket = bucket;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public Date getDownloadStart() {
    return downloadStart;
  }

  public void setDownloadStart(final Date downloadStart) {
    this.downloadStart = downloadStart;
  }

  public Date getDownloadEnd() {
    return downloadEnd;
  }

  public void setDownloadEnd(final Date downloadEnd) {
    this.downloadEnd = downloadEnd;
  }

  public void save() {
    mapper.save(this);
  }

  public static DumpInfo find(final String dumpId) {
    return mapper.load(DumpInfo.class, dumpId);
  }

  @DynamoDBIgnore
  public S3ObjectId getS3Location() {
    return AwsUtils.key(bucket, key);
  }
}
