package edu.columbia.cc.elPonePeli.app;

public enum AwsCredentialConstants
{
//	SECRET (System.getProperty("AWS_SECRET_KEY")),
//	ACCESS (System.getProperty("AWS_ACCESS_KEY_ID"));
	SECRET ("MqPxJF3QOZHR/W6THDkDChv/TGyAGMQn+d7Jyx1k"),
	ACCESS ("AKIAIZ4KF3A5NHIUFMUQ");
	
	private final String key;
	
	private AwsCredentialConstants(String s)
	{
		this.key = s;
	}
	
	public String getValue()
	{
		return this.key;
	}
}
