package edu.columbia.cc.elPonePeli.app;

import java.util.ArrayList;
import java.util.Collection;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClient;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.Parameter;

public class LiveStreamingHelper {

	private AmazonCloudFormation stackbuilder = null;
	private final String streamingTemplateURL = "https://s3.amazonaws.com/cfwowza/live-http-streaming-wowza-media-server-3-6-using-cloudfront.txt";
	private final String streamingStackName = "elPonePelisStreaming";
	
	public LiveStreamingHelper(AWSCredentials credentials) {
		stackbuilder = new AmazonCloudFormationClient(credentials);
		Region region = Region.getRegion(Regions.US_EAST_1);
		stackbuilder.setRegion(region);

        System.out.println("===========================================");
        System.out.println("Starting Live Streaming Formation");
        System.out.println("===========================================\n");
        
        String logicalResourceName = "SampleNotificationTopic";
        createFormation(streamingStackName, logicalResourceName);
	}
	
	public void createFormation(String stackName, String logicalResourceName)  {
    	
		try {
			
            // Create a stack
			Collection<Parameter> params = new ArrayList<Parameter>();
			Parameter pInstanceType = new Parameter().withParameterKey("InstanceType").withParameterValue("m1.small");
			params.add(pInstanceType);
			Parameter pKeyPair = new Parameter().withParameterKey("KeyPair").withParameterValue("fbtest");
			params.add(pKeyPair);
			Parameter pLicKey = new Parameter().withParameterKey("WowzaLicenseKey").withParameterValue("SVRT3-HEKkZ-zRp6C-xPTA7-b4a8P-VwR9R-7U6W7f6TXXxt");
			params.add(pLicKey);
			
			
            CreateStackRequest createRequest = new CreateStackRequest()
            									.withTemplateURL(streamingTemplateURL)
            									.withStackName(streamingStackName)
            									.withParameters(params);
            System.out.println("Creating a stack called " + createRequest.getStackName() + ".");
            stackbuilder.createStack(createRequest);

     
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS CloudFormation, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS CloudFormation, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
	}

}
