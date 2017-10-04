package gagan.practice.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.ResourceAdapter;

/**
 * Message-Driven Bean implementation class for: ConsumerMessageDrivenBean
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "FOO.BAR") })
@ResourceAdapter("activemq-rar-5.4.3.rar")
@TransactionManagement(TransactionManagementType.BEAN)
public class ConsumerMessageDrivenBean extends ConsumerEnterpriseJavaBean
		implements MessageListener {

	private static final long serialVersionUID = 7726434878298886792L;
	private transient MessageDrivenContext mdc = null;

	/**
	 * Constructor, which is public and takes no arguments.
	 */
	public ConsumerMessageDrivenBean() {
		System.out
				.println("In ConsumerMessageDrivenBean.ConsumerMessageDrivenBean()");
	}

	/**
	 * setMessageDrivenContext method, declared as public (but not final or
	 * static), with a return type of void, and with one argument of type
	 * javax.ejb.MessageDrivenContext.
	 * 
	 * @param mdc
	 *            the context to set
	 */
	public void setMessageDrivenContext(MessageDrivenContext mdc) {
		System.out
				.println("In ConsumerMessageDrivenBean.setMessageDrivenContext()");
		this.mdc = mdc;
	}

	/**
	 * ejbCreate method, declared as public (but not final or static), with a
	 * return type of void, and with no arguments.
	 */
	public void ejbCreate() {
		System.out.println("In ConsumerMessageDrivenBean.ejbCreate()");
	}

	/**
	 * onMessage method, declared as public (but not final or static), with a
	 * return type of void, and with one argument of type javax.jms.Message.
	 * 
	 * Casts the incoming Message to a TextMessage and displays the text.
	 * 
	 * @param inMessage
	 *            the incoming message
	 */
	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message inMessage) {
		System.out
				.println("In ConsumerMessageDrivenBean.onMessage(Message inMessage)");
		TextMessage msg = null;
		try {
			if (inMessage instanceof TextMessage) {
				msg = (TextMessage) inMessage;
				System.out.println("Message received: " + msg.getText());
			} else {
				System.out.println("Message of non-text type: "
						+ inMessage.getClass().getName());
			}
		} catch (JMSException e) {
			System.err.println("ConsumerMessageDrivenBean.onMessage: "
					+ "JMSException: " + e.toString());
			mdc.setRollbackOnly();
		} catch (Throwable te) {
			System.err.println("ConsumerMessageDrivenBean.onMessage: "
					+ "Exception: " + te.toString());
		}
	}

	/**
	 * ejbRemove method, declared as public (but not final or static), with a
	 * return type of void, and with no arguments.
	 */
	public void ejbRemove() {
		System.out.println("In ConsumerMessageDrivenBean.remove()");
	}

}
