package edu.columbia.cc.elPonePelis.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import edu.columbia.cc.elPonePeli.app.DatabaseHelper;

@DynamoDBTable(tableName=DatabaseHelper.tableName)
public class Video
{
	private Integer id;
	private String bucketName;
	private String videoName;
	private String videoLink;
	private String eTag;
	private float avgRating;
	private int numRatings;
	private String videoFormat;
	
	@DynamoDBHashKey(attributeName="Id")
	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	@DynamoDBAttribute(attributeName="BucketName")
	public String getBucketName(){return bucketName;}
	public void setBucketName(String bucketName) {this.bucketName = bucketName;}
	
	@DynamoDBAttribute(attributeName="VideoName")
	public String getVideoName() {return videoName;}
	public void setVideoName(String videoName) {this.videoName = videoName;}
	
	@DynamoDBAttribute(attributeName="VideoLink")
	public String getVideoLink() {return videoLink;}
	public void setVideoLink(String videoLink) {this.videoLink = videoLink;}
	
	@DynamoDBAttribute(attributeName="ETag")
	public String geteTag() {return eTag;}
	public void seteTag(String eTag) {this.eTag = eTag;}

	@DynamoDBAttribute(attributeName="AvgRating")
	public float getAvgRating() {return avgRating;}
	public void setAvgRating(float avgRating) {this.avgRating = avgRating;}
	
	@DynamoDBAttribute(attributeName="NumRatings")
	public int getNumRatings() {return numRatings;}
	public void setNumRatings(int numRatings) {this.numRatings = numRatings;}

	@DynamoDBAttribute(attributeName="VideoFormat")
	public String getVideoFormat() {return videoFormat;}
	public void setVideoFormat(String videoFormat) {this.videoFormat = videoFormat;}
	
	@DynamoDBIgnore
	public Video withId(int id)
	{
		this.id = Integer.valueOf(id);
		return this;
	}
	
	@DynamoDBIgnore
	public Video withBucketName(String bucketName)
	{
		this.bucketName = bucketName;
		return this;
	}
	
	@DynamoDBIgnore
	public Video withVideoName(String videoName)
	{
		this.videoName = videoName;
		return this;
	}
	
	@DynamoDBIgnore
	public Video withVideoLink(String videoLink)
	{
		this.videoLink = videoLink;
		return this;
	}
	
	@DynamoDBIgnore
	public Video withETag(String eTag)
	{
		this.eTag = eTag;
		return this;
	}
	
	@DynamoDBIgnore
	public Video withVideoFormat(String videoFormat)
	{
		this.videoFormat = videoFormat;
		return this;
	}
	
	@DynamoDBIgnore
	public Video withDefaultRating()
	{
		this.avgRating = 0.0f;
		this.numRatings = 0;
		return this;
	}
	
	@DynamoDBIgnore
	public float updateRating(float newRating)
	{
		float currentTotalRating = this.avgRating * this.numRatings;
		float newAvgRating = (currentTotalRating + newRating) / (this.numRatings + 1);
		
		this.avgRating = newAvgRating;
		this.numRatings++;
				
		return this.avgRating;
	}
}
