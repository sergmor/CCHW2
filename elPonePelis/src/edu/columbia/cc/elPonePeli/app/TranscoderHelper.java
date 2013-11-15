package edu.columbia.cc.elPonePeli.app;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreateJobResult;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineRequest;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineResult;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.Notifications;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

public class TranscoderHelper {
	
	private AWSCredentials credentials;
	private final String mobilePresetId = "1351620000001-100020";
	private final String webPresetId = "1351620000001-100070";
	
	public TranscoderHelper(AWSCredentials credentials) {
		this.credentials = credentials;
	}
	
	public void helpTranscode(String id, String filename, String inputBucket, String outputBucket) {
		
		AmazonSNSClient amazonSNSClient = new AmazonSNSClient();
		//Create topic to publish result
		CreateTopicRequest createTopicRequest = new CreateTopicRequest()
		.withName("transcoderResult");

		CreateTopicResult createTopicResult = amazonSNSClient.createTopic(createTopicRequest);
		String topicName = createTopicResult.getTopicArn();
		System.out.println("created a topic with arn: " + topicName);
		//Subscribe to topic REST Message
		/*SubscribeRequest subscribeRequest = new SubscribeRequest()
		.withTopicArn(topicName)
		.withProtocol("email")
		.withEndpoint("sdm2162@columbia.edu");
		SubscribeResult subsResult = amazonSNSClient.subscribe(subscribeRequest);
		System.out.println("subscribed to topic with id" + subsResult.getSubscriptionArn());*/
		
		SubscribeRequest subscribeRequest = new SubscribeRequest()
		.withTopicArn(topicName)
		.withProtocol("email")
		.withEndpoint("sdm2162@columbia.edu");
		SubscribeResult subsResult = amazonSNSClient.subscribe(subscribeRequest);
		System.out.println("subscribed to topic with id" + subsResult.getSubscriptionArn());
		
		
		//Create object
		Notifications notif = new Notifications()
		.withCompleted(topicName)
		.withError(topicName)
		.withProgressing(topicName)
		.withWarning(topicName);
		//Create Pipeline
		AmazonElasticTranscoderClient amazonElasticTranscoderClient = new AmazonElasticTranscoderClient(credentials);

		CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest()
		.withName("ponePeli")    													
		.withInputBucket(inputBucket)
		.withOutputBucket(outputBucket)
		.withRole("arn:aws:iam::442983548192:role/transcoderRole")
		.withNotifications(notif);
		System.out.println("creating pipeline");												
		CreatePipelineResult createPipelineResult = amazonElasticTranscoderClient.createPipeline(createPipelineRequest);
		System.out.println("created pipeline with id " + createPipelineResult.getPipeline().getArn());

		JobInput input = new JobInput()
		.withContainer("auto")
		.withKey(filename);

		//Create mobile job
		CreateJobOutput createJobOutput = new CreateJobOutput()
		.withKey(id+"_"+filename+"_mobile")    										
		.withPresetId(mobilePresetId)
		.withThumbnailPattern(id+"_"+"mob-thumb-{count}");

		CreateJobRequest createJobRequest = new CreateJobRequest()
		.withPipelineId(createPipelineResult.getPipeline().getId())
		.withInput(input)
		.withOutput(createJobOutput);
		System.out.println("creating the mobile job");
		CreateJobResult createJobResult = amazonElasticTranscoderClient.createJob(createJobRequest);
		System.out.println("created a job with id " + createJobResult.getJob().getId());
		
		//Create web job
		createJobOutput = new CreateJobOutput()
		.withKey(id+"_"+filename+"_web")    										
		.withPresetId(webPresetId)
		.withThumbnailPattern(id+"_"+"web-thumb-{count}");


		createJobRequest = new CreateJobRequest()
		.withPipelineId(createPipelineResult.getPipeline().getId())
		.withInput(input)
		.withOutput(createJobOutput);
		System.out.println("creating the web job");
		createJobResult = amazonElasticTranscoderClient.createJob(createJobRequest);
		System.out.println("created a job with id " + createJobResult.getJob().getId());
	}
}
