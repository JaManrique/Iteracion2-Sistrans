package rest;

import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.ClientesRegistrados;
import vos.Ingrediente;
import vos.Producto;
import vos.Restaurante;

@Path("rotonda")
public class RotondAndesServices {

	@Context
	private ServletContext context;
	
	private String getPath()
	{
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e)
	{
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getRestaurantes()
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> restaurantes;
		
		try
		{
			restaurantes = tm.darRestaurantes();
		}
		catch(Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}
	
	/**
	 * Mètodo rest para el requerimiento de registro 1
	 * @param producto
	 * @param ingredientes
	 * @param restaurante
	 * @return
	 */
	@POST
	@Path("productos/{restaurante}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response postProducto(Producto producto, List<Ingrediente> ingredientes, @QueryParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(restaurante == null || restaurante.length() == 0)
				throw new Exception("Nombre del restaurante inválido");
			if(producto == null || producto.getNombre() == null || producto.getNombre().length() == 0)
				throw new Exception("Nombre del producto inválido");
			tm.registrarProducto(producto, ingredientes, restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
	@POST
	@Path("clientes")
	public Response postCliente(ClientesRegistrados cliente)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(cliente.getUsuario() == null || cliente.getUsuario().length() < 5)
				throw new Exception("Usuario inválido");
			tm.registrarCliente(cliente);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
	@POST
	@Path("restaurantes")
	public Response postRestaurante(Restaurante restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(restaurante.getNombre() == null || restaurante.getNombre().length() < 5)
				throw new Exception("Usuario inválido");
			tm.registrarRestaurante(restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
}
