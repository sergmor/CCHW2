package edu.columbia.cc.elPonePeli.app;

public enum AwsCredentialConstants
{
	SECRET (System.getProperty("AWS_SECRET_KEY")),
	ACCESS (System.getProperty("AWS_ACCESS_KEY_ID"));
	
	private final String key;
	
	private AwsCredentialConstants(String s)
	{
		this.key = s;
	}

}
