package edu.columbia.cc.elPonePeli.app;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

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
}
