package rest;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
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
import vos.Usuario;
import vos.Ingrediente;
import vos.Menu;
import vos.Producto;
import vos.Restaurante;
import vos.Restaurante_Producto;
import vos.Zona;
import vosContainers.MenuProductos;
import vosContainers.PedidoMenu;
import vosContainers.ProductoIngredientes;
import vosContainers.ProductosServidos;
import vosContainers.RegistroCliente;
import vosContainers.TuplaEQIngrediente;
import vosContainers.TuplaEQProducto;

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
	 * Método REST para el requerimiento de registro 1
	 * @param usuario a registrar en la BD. Si es de rol cliente, se pide que se use el otro endpoint
	 * @return usuario registrado/mensaje de error
	 */
	@POST
	@Path("usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postUsuario(Usuario usuario)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(usuario.getUsuario() == null)
				throw new Exception("Usuario inválido");
			tm.registrarUsuario(usuario);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
	/**
	 * Método REST para el requerimiento de registro 2
	 * @param cliente objeto que contiene la información de verificacion y del usuario a registrar
	 * @return	cliente registrado/mensaje de error
	 */
	@POST
	@Path("clientes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCliente(RegistroCliente cliente)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(cliente.getUsuario() == null )
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
	 * Método REST para el requerimiento de registro 3
	 * @param restaurante
	 * @return
	 */
	@POST
	@Path("restaurantes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRestaurante(Restaurante restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(restaurante.getNombre() == null)
				throw new Exception("Restaurante inválido");
			tm.registrarRestaurante(restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
	
	/**
	 * Mètodo REST para el requerimiento de registro 4
	 * @param objetos lista de objetos contenedores de información
	 * @param restaurante nombre del restaurante que entra por parámetro
	 * @return producto registrado
	 */
	@POST
	@Path("productos/{restaurante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postProducto(List<ProductoIngredientes> objetos, @PathParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(restaurante == null || restaurante.length() == 0)
				throw new Exception("Nombre del restaurante inválido");
			if(objetos == null)
				throw new Exception("Información inválida");
			
			tm.registrarProducto(objetos, restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(objetos).build();
	}
		
	
	/**
	 * Método REST para el requerimiento de registro 5
	 * @param ingrediente objeto ingrediente a registrar
	 * @param restaurante restaurante a registrar el ingrediente, 
	 * @return ingrediente regitrado
	 */
	@POST
	@Path("ingredientes/{restaurante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postIngrediente(Ingrediente ingrediente, @PathParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(restaurante == null)
				throw new Exception("Nombre de restaurante inválido");
			if(ingrediente.getNombre() == null)
				throw new Exception("Nombre de ingrediente inválido");
			
			tm.registrarIngrediente(ingrediente, restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingrediente).build();
	}
	
	
	/**
	 * Método REST para el requerimiento de registro 6
	 * @param objetos Lista de objetos con información sobre menus y productos
	 * @return Información consignada
	 */
	@POST
	@Path("menus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMenu(List<MenuProductos> objetos)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			tm.registrarMenu(objetos);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(objetos).build();
	}
	
	
	/**
	 * Método REST para el requerimiento de registro 7
	 * @param zona a agregar
	 * @return zona agregada
	 */
	@POST
	@Path("zonas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postZonas(Zona zona)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			if(zona.getNombre() == null)
				throw new Exception("Nombre de zona inválido");
			tm.registrarZona(zona);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
	/**
	 * Método REST para el requerimiento de registro 9 (Registrar un pedido)
	 * @param checkout
	 * @param restaurProduc
	 * @return
	 */
	@POST
	@Path("checkout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
		return Response.status(200).entity(checkout).build();
	}
	
	@POST
	@Path("productosServidos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getProductosServidos(ProductosServidos infoBusqueda)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		
		List<Producto> productos= new ArrayList<>();
		
		try
		{
			productos = tm.consultarProductos(infoBusqueda);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
		
	}
	
	//___________________________________________________________________________________________//
	//-------------------------------------------------------------------------------------------//
	//----------------------------------------ITERACIÓN 3----------------------------------------//
	//-------------------------------------------------------------------------------------------//
	//___________________________________________________________________________________________//
	
	//RF 11 - registrar equivalencia productos
	@POST
	@Path("equivalenciasProductos/{restaurante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response registrarEquivalenciasProducto(TuplaEQProducto ingredientes, @PathParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			tm.registrarEquivalenciaProducto(restaurante, ingredientes);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingredientes).build();
		
	}

	//RF 12 - registrar equivalencia ingredientes
	@POST
	@Path("equivalenciasIngredientes/{restaurante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response registrarEquivalenciasIngrediente(TuplaEQIngrediente ingredientes, @PathParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			tm.registrarEquivalenciaIngrediente(restaurante, ingredientes);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingredientes).build();
		
	}
	
	//RF 13 - Surtir restaurante
	@POST
	@Path("pedidos/{restaurante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response registarPedidoMenu(PedidoMenu pedido, @PathParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			tm.registrarPedidoMenu(pedido, restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
		
	}
	
	
	//RF 14 - Registrar pedido de un producto
	@POST
	@Path("pedidos/{restaurante}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response surtirRestaurante(@PathParam("restaurante") String restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try
		{
			tm.surtirRestaurante(restaurante);
		}
		catch (Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
		
	}
	
}
