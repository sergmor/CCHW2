package edu.columbia.cc.elPonePeli.app;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

public class Init
{
	AWSCredentials awsCredentials;

	public static void main(String[] args)
	{
		Init init = new Init();
		
		init.doCredentials();
		init.createBucket();
		init.createDistribution();
		init.createDatabase();
		init.createLiveStreaming();
	}
	
	public void doCredentials()
	{
		this.awsCredentials = new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue());
	}
	
	public void createBucket()
	{
		VideoStore store = new VideoStore()
							.withCredentials(awsCredentials);
		store.initialize();
	}
	
	public void createDistribution()
	{
		OnDemandDistributor distributor = new OnDemandDistributor()
							.withAWSCredentials(awsCredentials)
							.withBucketName(VideoStore.bucketName);
		distributor.createWebDistribution();
		distributor.createRtmpDistribution();
	}
	
	public void createDatabase()
	{
		DatabaseHelper helper = new DatabaseHelper()
							.withCredentials(awsCredentials);
		helper.initialize();
		
	}
	
	public void createLiveStreaming() {
		LiveStreamingHelper lsh = new LiveStreamingHelper(awsCredentials);		
	}
	
	

}
