package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOCheckOut;
import dao.DAOClientesRegistrados;
import dao.DAOIngrediente;
import dao.DAOIter3;
import dao.DAOMenu;
import dao.DAOProducto;
import dao.DAORestaurante;
import dao.DAOZona;
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

public class RotondAndesTM {

	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	//-------------------------------------------
	//Transacciones -----------------------------
	//-------------------------------------------
	
	/*
	public List<Restaurante> darRestaurantes() {
		
		List<Restaurante> restaurantes;
		
		DAORestaurante daoRestaurantes = new DAORestaurante();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurantes = daoRestaurantes.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	*/
	
	/**
	 * Mètodo que modela la transacción para el Requqrimiento de registro 1
	 * @param producto
	 * @param ingredientes
	 * @param restaurante
	 * @throws Exception
	 */
	public void registrarProducto(List<ProductoIngredientes> objetos, String restaurante) throws Exception
	{
		DAOProducto daoProducto = new DAOProducto();
		
		List<Ingrediente> ingredientes = parseProductoIngrediente(objetos);
		Producto producto = null;
		int max = -1;
		
		for(ProductoIngredientes obj : objetos)
		{
			if(obj.getIsProducto() != null && obj.getIsProducto())
			{
				producto = new Producto(obj.getNombre(), obj.getCategoria(), obj.getPrecioVenta(), obj.getCostosDeProduccion(), obj.getTipoComidaProd(), obj.getTiempoPreparacion());
			}
			else if (obj.getEsMax() != null && obj.getEsMax())
			{
				max = obj.getMax();
				System.out.println("MAXHP: " + max);
			}
		}
		
		if (producto == null)
			throw new Exception("El producto no es válido");
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProducto.setConn(conn);
			daoProducto.registrarProducto(producto, restaurante, ingredientes, max);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void registrarUsuario(Usuario cliente) throws Exception{
		
		DAOClientesRegistrados daoCllienteRegistrados = new DAOClientesRegistrados();
		try 
		{
			
			//////transaccion
			this.conn = darConexion();
			daoCllienteRegistrados.setConn(conn);
			daoCllienteRegistrados.registrarUsuario(cliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCllienteRegistrados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}

	public void registrarCliente(RegistroCliente cliente) throws Exception {
		
		DAOClientesRegistrados daoCllienteRegistrados = new DAOClientesRegistrados();
		try 
		{
			
			//////transaccion
			this.conn = darConexion();
			daoCllienteRegistrados.setConn(conn);
			daoCllienteRegistrados.registrarCliente(cliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCllienteRegistrados.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}

	public void registrarRestaurante(Restaurante restaurante) throws Exception {
		
		DAORestaurante dao = new DAORestaurante();
		try 
		{
			/////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarRestaurante(restaurante);
			conn.commit();					

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}

	public void registrarIngrediente(Ingrediente ingrediente, String restaurante) throws Exception{
		
		DAOIngrediente dao = new DAOIngrediente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarIngrediente(ingrediente, restaurante);
			conn.commit();			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}

	public void registrarMenu(List<MenuProductos> objetos)  throws Exception{
		
		DAOMenu dao = new DAOMenu();
		
		List<String> nombreProductos = parseMenuProducto(objetos);
		Menu menu = null;
		
		for(MenuProductos obj : objetos)
		{
			if(obj.getIsMenu() != null && obj.getIsMenu())
			{
				menu = new Menu(obj.getNombre(), obj.getCategoria(), obj.getPrecioVenta(), obj.getCostosProduccion(), obj.getTipoComidaProd(), obj.getTiempoPreparacion(), obj.getRestaurante_nombre());
			}
		}
		
		if(menu == null)
			throw new Exception("No se encontró un menù");
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarMenu(menu, menu.getRestaurante_nombre(), nombreProductos);
			conn.commit();			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}

	public void registrarZona(Zona zona) throws Exception{
		
		DAOZona dao = new DAOZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarZona(zona);
			conn.commit();			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}
	
	
	public void registrarCheckout(CheckOut checkout, List<Restaurante_Producto> productos) throws Exception
	{

		DAOCheckOut dao = new DAOCheckOut();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarCheckOut(checkout, productos);
			conn.commit();			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	


	public List<Producto> consultarProductos(ProductosServidos infQuery) throws Exception{
		
		DAOProducto dao = new DAOProducto();
		
		List<Producto> productos = new ArrayList<>();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductosCriterio(infQuery.getFiltro(), infQuery.getColumnaFiltro(), infQuery.getCompFiltro(), infQuery.getAgruparPor(), infQuery.getOrden());		

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		return productos;
		
	}
	
	private List<Ingrediente> parseProductoIngrediente(List<ProductoIngredientes> objetos) throws Exception
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
	
	private List<String> parseMenuProducto(List<MenuProductos> objetos) throws Exception
	{
		List<String> resp = new ArrayList<>();
		
		for (MenuProductos obj: objetos)
		{
			if(obj.getIsProducto() != null && obj.getIsProducto())
			{
				resp.add(obj.getProductoNombre());
			}
		}
		return resp;
	}
	
	//___________________________________________________________________________________________//
	//-------------------------------------------------------------------------------------------//
	//----------------------------------------ITERACIÓN 3----------------------------------------//
	//-------------------------------------------------------------------------------------------//
	//___________________________________________________________________________________________//
	
	
	//RF 11 - registrar equivalencia ingredientes
	public TuplaEQIngrediente registrarEquivalenciaIngrediente(String restaurante, TuplaEQIngrediente productos) throws Exception
	{
		
		DAOIter3 dao = new DAOIter3();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarEquivalenciaDeIngrediente(productos.getIng1(), productos.getIng2(), restaurante, productos.getAdminUser(), productos.getAdminPass());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		return productos;
	}
	
	
	//RF 12 - registrar equivalencia productos
	public TuplaEQProducto registrarEquivalenciaProducto(String restaurante, TuplaEQProducto productos) throws Exception
	{
		
		DAOIter3 dao = new DAOIter3();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.registrarEquivalenciaDeProducto(productos.getProd1(), productos.getProd2(), restaurante, productos.getAdminUser(), productos.getAdminPass());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		return productos;
	}	
	
	
	//RF 13 - Surtir restaurante	
	public String surtirRestaurante(String restaurante) throws Exception
	{
		DAOIter3 dao = new DAOIter3();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			dao.surtirRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		return restaurante;
	}
	
	
	//RF 14 - Registrar pedido de un producto
	public PedidoMenu registrarPedidoMenu(PedidoMenu menu, String restaurante) throws Exception
	{
		DAOIter3 dao = new DAOIter3();
		
		try 
		{
			//////transaccion
			this.conn = darConexion();
			dao.setConn(conn);
			
			//TODO llamar método RF14
			//dao.METODOREEGISTRARPEDIDO()

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		return menu;
	}
	
}
