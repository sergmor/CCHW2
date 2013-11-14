package edu.columbia.cc.elPonePelis.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import edu.columbia.cc.elPonePeli.app.DatabaseHelper;

@DynamoDBTable(tableName=DatabaseHelper.tableName)
@XmlRootElement
public class Video implements Comparable
{
	private String id;
	private String bucketName;
	private String videoName;
	private String videoLink;
	private String thumbnailLink;
	private String eTag;
	private float avgRating;
	private int numRatings;
	private String videoFormat;
	
	@DynamoDBHashKey(attributeName="Id")
	@DynamoDBAutoGeneratedKey
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	
	@DynamoDBAttribute(attributeName="BucketName")
	public String getBucketName(){return bucketName;}
	public void setBucketName(String bucketName) {this.bucketName = bucketName;}
	
	@DynamoDBAttribute(attributeName="VideoName")
	public String getVideoName() {return videoName;}
	public void setVideoName(String videoName) {this.videoName = videoName;}
	
	@DynamoDBAttribute(attributeName="VideoLink")
	public String getVideoLink() {return videoLink;}
	public void setVideoLink(String videoLink) {this.videoLink = videoLink;}
	
	@DynamoDBAttribute(attributeName="ThumbnailLink")
	public String getThumbnailLink() {return thumbnailLink;}
	public void setThumbnailLink(String thumbnailLink) {this.thumbnailLink = thumbnailLink;}
	
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
	public Video withId(String id)
	{
		this.id = id;
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
	public Video withThumbnailLink(String thumbnailLink)
	{
		this.thumbnailLink = thumbnailLink;
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
	
	@Override
	@DynamoDBIgnore
	public String toString()
	{
		String str = "{id=" + this.id
			+ ", " + "bucketName=" + this.bucketName
			+ ", " + "videoName=" + this.videoName
			+ ", " + "videoLink=" + this.videoLink
			+ ", " + "thumbnailLink=" + this.thumbnailLink
			+ ", " + "eTag=" + this.eTag
			+ ", " + "avgRating=" + this.avgRating
			+ ", " + "numRatings=" + this.numRatings
			+ ", " + "videoFormat=" + this.videoFormat + "}";
		return str;
	}
	
	@Override
	@DynamoDBIgnore
	public int compareTo(Object o)
	{
		if (this.avgRating > ((Video)o).avgRating)
		{
			return 1;
		}
		else if (this.avgRating < ((Video)o).avgRating)
		{
			return -1;
		}
		return 0;
	}
}
