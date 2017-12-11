package jms;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rabbitmq.jms.admin.RMQDestination;

import dtm.RotondAndesDistributed;
import vos.ExchangeMsg;
import vos.IntervaloFecha;
import vos.ListaUtilidad;
import vos.Utilidad;

public class RotondAndesUtilidadMDB implements MessageListener, ExceptionListener 
{
	public final static int TIME_OUT = 50;
	private final static String TOPIC_NAME = "java:global/RMQTopicTerrorVideos";
	private final static String GLOBAL_TOPIC_NAME = "java:global/RMQTopicAllTerrorVideos";

	private static final String APP = "app2";
	
	private final static String REQUEST = "REQUEST";
	private final static String REQUEST_ANSWER = "REQUEST_ANSWER";

	private TopicConnection topicConnection;
	private TopicSession topicSession;
	private Topic globalTopic;
	private Topic topic;
	
	public List<Utilidad> answer = new ArrayList<>();

	public RotondAndesUtilidadMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException
	{
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopic = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME);
		TopicSubscriber topicSubscriber =  topicSession.createSubscriber(globalTopic);
		topicSubscriber.setMessageListener(this);
		topic = (RMQDestination) ctx.lookup(TOPIC_NAME);
		topicSubscriber =  topicSession.createSubscriber(topic);
		topicSubscriber.setMessageListener(this);
		topicConnection.setExceptionListener(this);
	}

	public void start() throws JMSException
	{
		topicConnection.start();
	}
	
	public void close() throws JMSException
	{
		topicSession.close();
		topicConnection.close();
	}
	
	public ListaUtilidad getRemoteUtilidad(String restaurante, String fechaI,String fechaF) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		answer.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);
		//id = new String(md.digest(id.getBytes()));
		
		sendMessage(restaurante+","+fechaI+","+fechaF, REQUEST, topic, id);
		System.out.println("Mensaje Enviado");
		boolean waiting = true;

		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(this.answer == null){
				waiting = false;
				throw new NonReplyException("Time Out - No Reply");
			}
		}
		waiting = false;
		if(answer == null)
			throw new NonReplyException("Non Response");
		ListaUtilidad util = new ListaUtilidad(answer);
        return util;
	}
	
	@Override
	public void onException(JMSException exception) 
	{
		System.out.println(exception);		
	}

	@Override
	public void onMessage(Message message) 
	{
		TextMessage txt = (TextMessage) message;
		try 
		{
			String body = txt.getText();
			System.out.println(body);
			ObjectMapper mapper = new ObjectMapper();
			ExchangeMsg ex = mapper.readValue(body, ExchangeMsg.class);
			String id = ex.getMsgId();
			System.out.println(ex.getSender());
			System.out.println(ex.getStatus());
			if(!ex.getSender().equals(APP))
			{
				
				String[] metodo = ex.getPayload().split(",");
				if(ex.getStatus().equals(REQUEST))
				{
					RotondAndesDistributed dtm = RotondAndesDistributed.getInstance();
					IntervaloFecha f=new IntervaloFecha(metodo[1],  metodo[2]);
					ListaUtilidad uti = dtm.getLocalUtilidad(f,metodo[2]);	
					String payload = mapper.writeValueAsString(uti);
					Topic t = new RMQDestination("", "videos.test", ex.getRoutingKey(), "", false);
					sendMessage(payload, REQUEST_ANSWER, t, id);
				}
				else if(ex.getStatus().equals(REQUEST_ANSWER))
				{
					Utilidad v = mapper.readValue(ex.getPayload(), Utilidad.class);
					answer.add(v);
				}
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void sendMessage(String payload, String status, Topic dest, String id) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();		
		System.out.println(id);
		ExchangeMsg msg = new ExchangeMsg("videos.terror.app2", APP, payload, status, id);
		TopicPublisher topicPublisher = topicSession.createPublisher(dest);
		topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage txtMsg = topicSession.createTextMessage();
		txtMsg.setJMSType("TextMessage");
		String envelope = mapper.writeValueAsString(msg);
		System.out.println(envelope);
		txtMsg.setText(envelope);
		topicPublisher.publish(txtMsg);

	}
	
	
	
}