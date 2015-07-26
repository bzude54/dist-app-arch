package com.nozama.custemailer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;

import com.nozama.messaging.JMSInfo;

public class CustomerEmailer {

	@Resource(mappedName= "jms/HoldResponseFactory")
	public static ConnectionFactory holdresponsefactory;

	@Resource(mappedName= "jms/CheckoutTopic")
	public static Topic checkouttopic;

	private JMSContext ctx;
	private JMSConsumer checkout;
    
    public CustomerEmailer() {
		ctx = holdresponsefactory.createContext();
		checkout = ctx.createConsumer(checkouttopic);

    }
    
    public String next() {
    	return checkout.receiveBody(String.class);    //TODO: get the next message in a blocking fashion
    }

	public static void main(String[] args) {
		CustomerEmailer emailer = new CustomerEmailer();

		while (true) {
			System.out.println("Order checked out: " + emailer.next());
		}
	}

}
