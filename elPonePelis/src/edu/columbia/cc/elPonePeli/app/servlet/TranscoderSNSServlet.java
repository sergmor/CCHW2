package edu.columbia.cc.elPonePeli.app.servlet;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.columbia.cc.elPonePelis.model.SNSMessage;

/**
 * Servlet implementation class TranscoderSNSServlet
 */
public class TranscoderSNSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TranscoderSNSServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SecurityException{
		//Get the message type header.
		String messagetype = request.getHeader("x-amz-sns-message-type");
		//If message doesn't have the message type header, don't process it.
		if (messagetype == null)
			return;

    // Parse the JSON message in the message body
    // and hydrate a Message object with its contents 
    // so that we have easy access to the name/value pairs 
    // from the JSON message.
    Scanner scan = new Scanner(request.getInputStream());
    StringBuilder builder = new StringBuilder();
    while (scan.hasNextLine()) {
      builder.append(scan.nextLine());
    }
		
    	SNSMessage msg = readMessageFromJson(builder.toString());
        
    // The signature is based on SignatureVersion 1. 
    // If the sig version is something other than 1, 
    // throw an exception.
    if (msg.getSignatureVersion().equals("1")) {
		  // Check the signature and throw an exception if the signature verification fails.
		  if (isMessageSignatureValid(msg))
				System.out.println(">>Signature verification succeeded");
		  else {
				System.out.println(">>Signature verification failed");
				throw new SecurityException("Signature verification failed.");
		  }
    }
    else {
			System.out.println(">>Unexpected signature version. Unable to verify signature.");
      throw new SecurityException("Unexpected signature version. Unable to verify signature.");
    }

    // Process the message based on type.
		if (messagetype.equals("Notification")) {
			//TODO: Do something with the Message and Subject.
			//Just log the subject (if it exists) and the message.
			String logMsgAndSubject = ">>Notification received from topic " + msg.getTopicArn();
			if (msg.getSubject() != null)
				logMsgAndSubject += " Subject: " + msg.getSubject();
			logMsgAndSubject += " Message: " + msg.getMessage();
			System.out.println(logMsgAndSubject);
		}
    else if (messagetype.equals("SubscriptionConfirmation"))
		{
       //TODO: You should make sure that this subscription is from the topic you expect. Compare topicARN to your list of topics 
       //that you want to enable to add this endpoint as a subscription.
        	
       //Confirm the subscription by going to the subscribeURL location 
       //and capture the return value (XML message body as a string)
       Scanner sc = new Scanner(new URL(msg.getSubscribeURL()).openStream());
       StringBuilder sb = new StringBuilder();
       while (sc.hasNextLine()) {
         sb.append(sc.nextLine());
       }
       System.out.println(">>Subscription confirmation (" + msg.getSubscribeURL() +") Return value: " + sb.toString());
       //TODO: Process the return value to ensure the endpoint is subscribed.
		}
    else if (messagetype.equals("UnsubscribeConfirmation")) {
      //TODO: Handle UnsubscribeConfirmation message. 
      //For example, take action if unsubscribing should not have occurred.
      //You can read the SubscribeURL from this message and 
      //re-subscribe the endpoint.
      System.out.println(">>Unsubscribe confirmation: " + msg.getMessage());
		}
    else {
      //TODO: Handle unknown message type.
      System.out.println(">>Unknown message type.");
    }
		System.out.println(">>Done processing message: " + msg.getMessageId());
	}

}
