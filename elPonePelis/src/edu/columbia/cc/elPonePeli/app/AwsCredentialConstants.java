package edu.columbia.cc.elPonePeli.app;

public enum AwsCredentialConstants
{
//	SECRET (System.getProperty("AWS_SECRET_KEY")),
//	ACCESS (System.getProperty("AWS_ACCESS_KEY_ID"));
	SECRET ("Aah1YgW/zDrjNGvK/z7c1wHcllTlP9+sAsBBzNj7"),
	ACCESS ("AKIAJXGC6OSQRB644AMA");
	
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
