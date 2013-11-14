package edu.columbia.cc.elPonePeli.app;

import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference.Metadata;

import edu.columbia.cc.elPonePelis.model.Video;

public class VideoStore
{
	private AmazonS3Client s3 = null;
	private String bucketName = "";
	
	public VideoStore withCredentials(AWSCredentials awsCredentials)
	{
		s3  = new AmazonS3Client(awsCredentials);
		return this;
	}
	
	public VideoStore withBucketName(String bucketName)
	{
		this.bucketName = bucketName;
		return this;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public void initialize()
	{
		System.out.println("Trying to create S3 bucket (video store) ...");
		try
		{
			if (this.bucketName.equals(""))
			{
				throw new AmazonServiceException("Bucket name can't be empty.");
			}
			
			if (this.s3 == null)
			{
				throw new AmazonServiceException("AmazonS3Client object can't be null.");
			}
			
    		s3.createBucket(this.bucketName);
    		System.out.println("Done.");
            
	    }
		catch (AmazonServiceException ase)
	    {
	        System.out.println("Caught Exception: " + ase.getMessage());
	        System.out.println("Reponse Status Code: " + ase.getStatusCode());
	        System.out.println("Error Code: " + ase.getErrorCode());
	        System.out.println("Request ID: " + ase.getRequestId());
	   	}
	}
	
	public Video storeIntoBucket(String key, InputStream is)
	{
		System.out.println("Trying to store object into S3 bucket ...");
		Video video = null;
		try
		{
			if (this.bucketName.equals(""))
			{
				throw new AmazonServiceException("Bucket name can't be empty.");
			}
			
			if (this.s3 == null)
			{
				throw new AmazonServiceException("AmazonS3Client object can't be null.");
			}
			
			AccessControlList acl = new AccessControlList();
			acl.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
    		
			s3.putObject(new PutObjectRequest(this.bucketName, key, is, null).withAccessControlList(acl));
//			s3.putObject(this.bucketName, key, is, null);
			System.out.println("Upload to S3 done.");
			    		
    		System.out.println("Collecting  details ...");
    		S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, key));
    		String bucketName = s3Object.getBucketName();
    		String videoName = s3Object.getKey();
    		String eTag = s3Object.getObjectMetadata().getETag();
    		String videoLink = s3Object.getObjectContent().getHttpRequest().getURI().toString();
    		String videoFormat = "default";
    		video = new Video()
    						.withBucketName(bucketName)
    						.withVideoName(videoName)
    						.withETag(eTag)
    						.withVideoLink(videoLink)
    						.withVideoFormat(videoFormat)
    						.withDefaultRating();
    		System.out.println("Details collected: " + video.toString());
	    }
		catch (Exception e)
	    {
	        e.printStackTrace();
	   	}
		return video;
	}
}
