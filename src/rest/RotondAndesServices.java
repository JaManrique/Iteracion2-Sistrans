package rest;

import java.util.ArrayList;
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
import vos.CheckOut;
import vos.ClientesRegistrados;
import vos.Ingrediente;
import vos.Menu;
import vos.Producto;
import vos.Restaurante;
import vos.Restaurante_Producto;
import vos.Zona;
import vosContainers.ProductoIngredientes;

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
	
	/*
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
	*/
	
	/**
	 * Método rest para el requerimiento de registro 1
	 * @param cliente
	 * @return
	 */
	@POST
	@Path("clientes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
		return Response.status(200).entity(cliente).build();
	}
	
	/**
	 * Método rest para el rquerimiento de registro 3
	 * @param restaurante
	 * @return
	 */
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
	
	
	/**
	 * Mètodo rest para el requerimiento de registro 4
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
		
	
	/**
	 * Método REST para el requerimiento de registro 5
	 * @param ingrediente
	 * @param restaurante
	 * @return
	 */
	@POST
	@Path("ingredientes/{restaurante}")
	public Response postIngrediente(Ingrediente ingrediente, @QueryParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(restaurante == null || restaurante.length() < 3)
				throw new Exception("Nombre de restaurante inválido");
			if(ingrediente.getNombre() == null || ingrediente.getNombre().length() < 4)
				throw new Exception("Nombre de ingrediente inválido");
			
			tm.registrarIngrediente(ingrediente, restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
	/**
	 * Método REST para ek requerimiento de registro 6
	 * @param menu
	 * @param productos
	 * @param restaurante
	 * @return
	 */
	@POST
	@Path("menus/{restaurante}")
	public Response postMenu(Menu menu, List<Producto> productos, @QueryParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(menu.getNombre() == null || menu.getNombre().length() < 5)
				throw new Exception("Nombre de menú inválido");
			tm.registrarMenu(menu, restaurante, productos);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
	
	/**
	 * 
	 * @param zona
	 * @return
	 */
	@POST
	@Path("zonas")
	public Response postZonas(Zona zona)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(zona.getNombre() == null || zona.getNombre().length() < 5)
				throw new Exception("Nombre de zona inválido");
			tm.registrarZona(zona);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
	@POST
	@Path("checkout")
	public Response postCheckoutNoVerificado(CheckOut checkout, List<Restaurante_Producto> restaurProduc)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(checkout.getId() < 0)
				throw new Exception("Id del checkout inválido");
			tm.registrarCheckout(checkout, restaurProduc);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(203).build();
	}
	
	@POST
	@Path("test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response test(List<ProductoIngredientes> objetos)
	{
		List<Ingrediente> prueba = parseProductoIngrediente(objetos);
		
		System.out.println("sieze:" + prueba.size());
		
		return Response.status(200).entity((List<Ingrediente>)prueba).build();
	}
	
	private List<Ingrediente> parseProductoIngrediente(List<ProductoIngredientes> objetos)
	{
		List<Ingrediente> resp = new ArrayList<>();
		
		for (ProductoIngredientes obj: objetos)
		{
			if(obj.getIsIngrediente() != null && obj.getIsIngrediente())
			{
				Ingrediente ing = new Ingrediente(obj.getNombreIng(), obj.getDescEsp(), obj.getDescING(), obj.getTipo());
				resp.add(ing);
			}
		}
		return resp;
	}
}
