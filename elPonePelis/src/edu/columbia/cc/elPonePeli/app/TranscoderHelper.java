package edu.columbia.cc.elPonePeli.app;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreateJobResult;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineRequest;
import com.amazonaws.services.elastictranscoder.model.CreatePipelineResult;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.Notifications;
import com.amazonaws.services.elastictranscoder.model.Pipeline;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

public enum TranscoderHelper {
	INSTANCE;

	private AWSCredentials credentials=new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue());
	private final String mobilePresetId = "1351620000001-100020";
	private final String webPresetId = "1351620000001-100070";
	//private final String roleToSNS = "arn:aws:iam::442983548192:role/aws-elasticbeanstalk-ec2-role";
	private final String roleToSNS = "arn:aws:iam::442983548192:role/transcoderRole";
	private Pipeline pipeline = null;
	private String topicARN = "";

	public void init() {

		AmazonSNSClient amazonSNSClient = new AmazonSNSClient(credentials);
		//Create topic to publish result
		if( topicARN.equals("") ) {
			CreateTopicRequest createTopicRequest = new CreateTopicRequest()
			.withName("transcoderResult");

			CreateTopicResult createTopicResult = amazonSNSClient.createTopic(createTopicRequest);

			topicARN = createTopicResult.getTopicArn();
			System.out.println("created a topic with arn: " + topicARN);
			//Subscribe to topic REST Message
			/*SubscribeRequest subscribeRequest = new SubscribeRequest()
			.withTopicArn(topicARN)
			.withProtocol("email")
			.withEndpoint("sdm2162@columbia.edu");
			SubscribeResult subsResult = amazonSNSClient.subscribe(subscribeRequest);
			System.out.println("subscribed to topic with id" + subsResult.getSubscriptionArn());*/

			SubscribeRequest subscribeRequest = new SubscribeRequest()
			.withTopicArn(topicARN)
			.withProtocol("http")
			.withEndpoint("http://test-m2nt5e3rrf.elasticbeanstalk.com/TranscoderSNSServlet");
			SubscribeResult subsResult1 = amazonSNSClient.subscribe(subscribeRequest);
			System.out.println("subscribed to topic with id" + subsResult1.getSubscriptionArn());
		}


	}

	public void helpTranscode(String id, String filename, String inputBucket, String outputBucket) {

		AmazonElasticTranscoderClient amazonElasticTranscoderClient = new AmazonElasticTranscoderClient(credentials);

		//Create object
		Notifications notif = new Notifications()
		.withCompleted(topicARN)
		.withError(topicARN)
		.withProgressing(topicARN)
		.withWarning(topicARN);

		//Create Pipeline
		if(pipeline == null) {
			CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest()
			.withName("ponePeli")    													
			.withInputBucket(inputBucket)
			.withOutputBucket(outputBucket)
			.withRole(roleToSNS)
			.withNotifications(notif);
			System.out.println("creating pipeline");												
			CreatePipelineResult createPipelineResult = amazonElasticTranscoderClient.createPipeline(createPipelineRequest);
			pipeline = createPipelineResult.getPipeline();
			System.out.println("created pipeline with id " + createPipelineResult.getPipeline().getArn());
		}

		JobInput input = new JobInput()
		.withContainer("auto")
		.withKey(filename);

		//Create mobile job
		CreateJobOutput createJobOutput = new CreateJobOutput()
		.withKey(id+"_"+"_mob_"+filename)    										
		.withPresetId(mobilePresetId)
		.withThumbnailPattern(id+"_"+"mob-thumb-{count}");

		CreateJobRequest createJobRequest = new CreateJobRequest()
		.withPipelineId(pipeline.getId())
		.withInput(input)
		.withOutput(createJobOutput);
		System.out.println("creating the mobile job");
		CreateJobResult createJobResult = amazonElasticTranscoderClient.createJob(createJobRequest);
		System.out.println("created a job with id " + createJobResult.getJob().getId());

		//Create web job
		createJobOutput = new CreateJobOutput()
		.withKey(id+"_"+"_web_"+filename)    										
		.withPresetId(webPresetId)
		.withThumbnailPattern(id+"_"+"web-thumb-{count}");


		createJobRequest = new CreateJobRequest()
		.withPipelineId(pipeline.getId())
		.withInput(input)
		.withOutput(createJobOutput);
		System.out.println("creating the web job");
		createJobResult = amazonElasticTranscoderClient.createJob(createJobRequest);
		System.out.println("created a job with id " + createJobResult.getJob().getId());
	}
}
