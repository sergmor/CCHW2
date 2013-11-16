package edu.columbia.cc.elPonePeli.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

import edu.columbia.cc.elPonePelis.model.Video;

public class DatabaseHelper
{
	private AmazonDynamoDBClient amazonDynamoDBClient = null;
	public static final String tableName = "bucketallmytubedani";
	
	public DatabaseHelper withCredentials(AWSCredentials awsCredentials)
	{
		this.amazonDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);
		return this;
	}
	
//	public DatabaseHelper withCredentialsProvider(AWSCredentialsProvider provider)
//	{
//		this.amazonDynamoDBClient = new AmazonDynamoDBClient(provider);
//		return this;
//	}
	
	public void initialize()
	{
		System.out.println("Trying to create table: " + DatabaseHelper.tableName);
		try {
			ArrayList<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("S"));
			
			ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
			ks.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));
			  
			ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
						    .withReadCapacityUnits(10L)
						    .withWriteCapacityUnits(10L);
			        
			CreateTableRequest request = new CreateTableRequest()
						    .withTableName(DatabaseHelper.tableName)
						    .withAttributeDefinitions(attributeDefinitions)
						    .withKeySchema(ks)
						    .withProvisionedThroughput(provisionedThroughput);
			    
			this.amazonDynamoDBClient.createTable(request);
			System.out.println("Request sent.");
			
			while (true)
			{
				System.out.println("Checking state ...");
				TableDescription tableDescription = this.amazonDynamoDBClient.describeTable(new DescribeTableRequest()
																				.withTableName(DatabaseHelper.tableName)).getTable();
				String status = tableDescription.getTableStatus();
				if (status.equals(TableStatus.ACTIVE.toString()))
				{
					System.out.println("State = " + status);
					break;
				}
				else
				{
					System.out.println("State = " + status + ". Sleeping for 10s ...");
					try{Thread.sleep(10 * 1000);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
			System.out.println("Table " + DatabaseHelper.tableName + " created.");
		}
		catch(AmazonServiceException e)
		{
			e.printStackTrace();
		}
		catch (AmazonClientException e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveVideo(Video video)
	{
		System.out.println("Trying to save video details ...");
		try
		{			
			DynamoDBMapper mapper = new DynamoDBMapper(this.amazonDynamoDBClient);
			mapper.save(video);			
			System.out.println("Saved.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Video getVideoById(String id)
	{
		Video video = null;
		System.out.println("Checking if video record exixts with id: '" + id + "' ...");
		try
		{			
			DynamoDBMapper mapper = new DynamoDBMapper(this.amazonDynamoDBClient);
			video = mapper.load(Video.class, id);
			
			if (video == null)
			{
				System.out.println("No such record found.");
			}
			else
			{
				System.out.println("Retrieved.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return video;
	}
	
	public List<Video> getAllVideos()
	{
		System.out.println("Trying to fetch all video records ...");
		List<Video> scannedVideos = new ArrayList<Video>();
		List<Video> videos = new ArrayList<Video>();
		try
		{			
			DynamoDBMapper mapper = new DynamoDBMapper(this.amazonDynamoDBClient);
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
			
			scannedVideos = mapper.scan(Video.class, scanExpression);
			System.out.println("Retrieved " + scannedVideos.size() + " record(s).");
			
			videos.addAll(scannedVideos);
			
			if (videos.size() > 0)
			{
				Collections.sort(videos);
				Collections.reverse(videos);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return videos;
	}
	
	public void deleteVideoById(String id)
	{
		try
		{
			Video video = getVideoById(id);
			if (video == null)
			{
				System.out.println("Nothing to delete.");
			}
			else
			{
				System.out.println("Trying to delete record with id: '" + id + "' ...");
				DynamoDBMapper mapper = new DynamoDBMapper(this.amazonDynamoDBClient);
				mapper.delete(video);
				System.out.println("Deleted.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
