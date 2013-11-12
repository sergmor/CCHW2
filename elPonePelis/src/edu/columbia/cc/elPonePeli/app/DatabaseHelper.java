package edu.columbia.cc.elPonePeli.app;

import java.util.ArrayList;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

public class DatabaseHelper
{
	private AmazonDynamoDBClient amazonDynamoDBClient = null;
	public static final String tableName = "VIDEOS_ALL";
	
	
	public DatabaseHelper withCredentials(AWSCredentials awsCredentials)
	{
		this.amazonDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);
		return this;
	}
	
	public void initialize()
	{
		System.out.println("Trying to create table: " + DatabaseHelper.tableName);
		try {
			ArrayList<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("BucketName").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("VideoName").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("VideoLink").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("ETag").withAttributeType("S"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("AvgRating").withAttributeType("N"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("NumRatings").withAttributeType("N"));
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("VideoFormat").withAttributeType("S"));
			
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
				if (status.equals(TableStatus.ACTIVE))
				{
					break;
				}
				else
				{
					System.out.println("Still " + tableDescription.getTableStatus() + ". Sleeping for 10s ...");
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
}
