package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

import jms.RotondAndesRestaurantesMDB;
import jms.RotondAndesUtilidadMDB;
import jms.NonReplyException;
import tm.RotondAndesTM;
import vos.IntervaloFecha;
import vos.ListaProductos;
import vos.ListaRestaurantes;
import vos.ListaUtilidad;
import vos.Utilidad;



public class RotondAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotondAndesDistributed instance;
	
	private RotondAndesTM tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private RotondAndesRestaurantesMDB allRestaurantesMQ;
	
	private RotondAndesUtilidadMDB allUtilidadMQ;
	
	private static String path;


	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allRestaurantesMQ = new RotondAndesRestaurantesMDB(factory, ctx);
		allUtilidadMQ=new RotondAndesUtilidadMDB(factory, ctx);
		allRestaurantesMQ.start();
		allUtilidadMQ.start();
	}
	
	public void stop() throws JMSException
	{
		allRestaurantesMQ.close();
		allUtilidadMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTM tm)
	{
	   this.tm = tm;
	}
	
	private static RotondAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesTM tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTM tm = new RotondAndesTM(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTM tm = new RotondAndesTM(path);
		return getInstance(tm);
	}
	
	public ListaProductos getLocalProductos() throws Exception
	{
		return tm.getLocalProductos();
	}

	public ListaProductos getRemoteProductos() throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, JMSException, IOException, NonReplyException, InterruptedException 
	{
		// TODO Auto-generated method stub
		return allRestaurantesMQ.getRemoteProductos();
	}

	public ListaUtilidad getRemoteUtilidad(String fechaIni, String fechaFin, String restau) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, JMSException, IOException, NonReplyException, InterruptedException 
	{
		// TODO Auto-generated method stub
		return allUtilidadMQ.getRemoteUtilidad(fechaIni,fechaFin,restau);
	}

	public ListaUtilidad getLocalUtilidad(IntervaloFecha inter, String restau) throws Exception {
		// TODO Auto-generated method stub
		return tm.getRentabilidadRestauranteLocal(inter, restau);
	} 
	
}
