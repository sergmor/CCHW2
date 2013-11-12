package edu.columbia.cc.elPonePelis.model;

public class Video
{
	private String bucketName;
	private String videoName;
	private String videoLink;
	private String eTag;
	
	public Video(){}
	
	public Video withBucketName(String bucketName)
	{
		this.bucketName = bucketName;
		return this;
	}
	
	public Video withVideoName(String videoName)
	{
		this.videoName = videoName;
		return this;
	}
	
	public Video withVideoLink(String videoLink)
	{
		this.videoLink = videoLink;
		return this;
	}
	
	public Video withETag(String eTag)
	{
		this.eTag = eTag;
		return this;
	}
	
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public String geteTag() {
		return eTag;
	}
	public void seteTag(String eTag) {
		this.eTag = eTag;
	}
}
