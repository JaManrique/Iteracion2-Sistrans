package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.scenario.effect.impl.state.PerspectiveTransformState;

import vos.Ingrediente;
import vos.Producto;

public class DAOProducto {
	//constantes
	public static final String FILTRAR_POR_NOMBRE = "NOMBRE";
	public static final String FILTRAR_POR_CATEGORIA = "CATEGORIA";
	public static final String FILTRAR_POR_PRECIO_DE_VENTA = "PRECIOVENTA";
	public static final String FILTRAR_POR_COSTO_DE_PRODUCCION = "COSTOPRODUCCION";
	public static final String FILTRAR_POR_TIPO_DE_COMIDA = "TIPOCOMIDAPROD";
	public static final String FILTRAR_POR_TIEMPO_DE_PREPARACION = "TIEMPOPREPARACION";
	static String[] filtros = new String[] {
			FILTRAR_POR_NOMBRE,
			FILTRAR_POR_CATEGORIA,
			FILTRAR_POR_PRECIO_DE_VENTA,
			FILTRAR_POR_COSTO_DE_PRODUCCION,
			FILTRAR_POR_TIPO_DE_COMIDA,
			FILTRAR_POR_TIEMPO_DE_PREPARACION
	};

	public static final String ASCENDENTE = "ASC";
	public static final String DESCENDENTE = "DESC";

	public static final String ORDENAR_POR_NOMBRE = "NOMBRE";
	public static final String ORDENAR_POR_CATEGORIA = "CATEGORIA";
	public static final String ORDENAR_POR_PRECIO_DE_VENTA = "PRECIOVENTA";
	public static final String ORDENAR_POR_COSTO_DE_PRODUCCION = "COSTOPRODUCCION";
	public static final String ORDENAR_POR_TIPO_DE_COMIDA = "TIPOCOMIDAPROD";
	public static final String ORDENAR_POR_TIEMPO_DE_PREPARACION = "TIEMPOPREPARACION";
	static String[] criteriosOrdenamiento = new String[] {
			ORDENAR_POR_NOMBRE,
			ORDENAR_POR_CATEGORIA,
			ORDENAR_POR_PRECIO_DE_VENTA,
			ORDENAR_POR_COSTO_DE_PRODUCCION,
			ORDENAR_POR_TIPO_DE_COMIDA,
			ORDENAR_POR_TIEMPO_DE_PREPARACION
	};
	public static final String MENOR_O_IGUAL = "<=";
	public static final String MENOR = "<";
	public static final String MAYOR_O_IGUAL = ">=";
	public static final String MAYOR = ">";
	public static final String IGUAL = "=";
	public static final String COMIENZA_CON = "1";
	public static final String TERMINA_CON = "2";
	public static final String CONTIENE = "3";
	
	public static String[] comparadores = {
			MENOR_O_IGUAL,
			MENOR,
			MAYOR,
			MAYOR_O_IGUAL,
			IGUAL,
			COMIENZA_CON,
			TERMINA_CON,
			CONTIENE			
	};

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOProducto() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductos() throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer categoria = rs.getInt("CATEGORIA");
			Integer precioVenta = rs.getInt("PRECIOVENTA");
			Integer costosProduccion = rs.getInt("COSTOPRODUCCION");
			String tipoComidaProd = rs.getString("TIPOCOMIDAPROD");
			Integer tiempoDePreparacion = rs.getInt("TIEMPOPREPARACION");
			productos.add(new Producto(nombre, categoria, precioVenta, costosProduccion, tipoComidaProd,tiempoDePreparacion));
		}
		return productos;
	}

	public ArrayList<Producto> darProductosCriterio(String filtro, String columnafiltro,String compfiltro, String agruparPor, String orden) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		String sql = "SELECT * FROM PRODUCTO P";
		if(filtrosContiene(columnafiltro)&&compContiene(compfiltro)) {
			if(esCompString(filtro,compfiltro)) 
			{
				sql+="WHERE P."+columnafiltro+" LIKE "+ compString(filtro,compfiltro);
			}
			else
			{
				sql+="WHERE P."+columnafiltro+" "+compfiltro+" "+filtro;

			}
			if(agruparPor!=null&& ordenContiene(agruparPor))
			{
				sql+=" ORDER BY "+agruparPor;
				if(orden!=null&&(orden.equals(ASCENDENTE)||orden.equals(DESCENDENTE)))
				{
					sql+=" "+orden;
				}
			}
		}

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer categoria = rs.getInt("CATEGORIA");
			Integer precioVenta = rs.getInt("PRECIOVENTA");
			Integer costosProduccion = rs.getInt("COSTOPRODUCCION");
			String tipoComidaProd = rs.getString("TIPOCOMIDAPROD");
			Integer tiempoDePreparacion = rs.getInt("TIEMPOPREPARACION");
			productos.add(new Producto(nombre, categoria, precioVenta, costosProduccion, tipoComidaProd,tiempoDePreparacion));
		}
		return productos;
	}

	private String compString(String filtro, String compfiltro) throws Exception {
		// TODO Auto-generated method stub
		String resp;
		if(compfiltro.equals(CONTIENE))
		{
			resp="'%"+filtro+"%'";
		}
		else if(compfiltro.equals(COMIENZA_CON))
		{
		resp="'"+filtro+"%'";

		}
		else if(compfiltro.equals(TERMINA_CON))
		{
			resp="'%"+filtro+"'";

		}
		else
			throw new Exception("ComparadorInvalido");
		
		return resp;
	}

	private boolean esCompString(String filtro, String compfiltro) {
		// TODO Auto-generated method stub
		boolean esCompString=false;
		try 
		{			
			Double.parseDouble(filtro);
		}
		catch(Exception e)
		{
			esCompString=true;			
		}
		if(compfiltro.equals(COMIENZA_CON)||compfiltro.equals(TERMINA_CON)||compfiltro.equals(CONTIENE))
		{
			esCompString=true;
		}
		return esCompString;
	}

	private boolean filtrosContiene(String filtro) {
		// TODO Auto-generated method stub
		for (int i = 0; i < filtros.length; i++) {
			if(filtros[i].equals(filtro))
				return true;
		}
		return false;
	}

	private boolean ordenContiene(String filtro) {
		// TODO Auto-generated method stub
		for (int i = 0; i < criteriosOrdenamiento.length; i++) {
			if(criteriosOrdenamiento[i].equals(filtro))
				return true;
		}
		return false;
	}
	private boolean compContiene(String comp) {
		// TODO Auto-generated method stub
		for (int i = 0; i < comparadores.length; i++) {
			if(comparadores[i].equals(comp))
				return true;
		}
		return false;
	}

	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> buscarProductosPorNombre(String name) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer categoria = rs.getInt("CATEGORIA");
			Integer precioVenta = rs.getInt("PRECIOVENTA");
			Integer costosProduccion = rs.getInt("COSTOPRODUCCION");
			String tipoComidaProd = rs.getString("TIPOCOMIDAPROD");
			Integer tiempoDePreparacion = rs.getInt("TIEMPOPREPARACION");
			productos.add(new Producto(nombre, categoria, precioVenta, costosProduccion, tipoComidaProd,tiempoDePreparacion));
		}

		return productos;
	}


	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param prod - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void registrarProducto(Producto prod, String nomRest, List<Ingrediente> ingr) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTO VALUES ('";
		sql += prod.getNombre() + "',";
		sql += prod.getCategoria() + ",";
		sql += prod.getPrecioVenta() + ",";
		sql += prod.getCostosDeProduccion() + ",'";
		sql += prod.getTipoComidaProd() + "',";
		sql += prod.getTiempoDePreparacion() + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
		sql = "INSERT INTO PRODUCTOSBODEGA VALUES ( ?,'";
		sql += nomRest + "', '";
		sql += prod.getNombre() + "')";
		
		prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, 1);
		prepStmt.execute();
		
		for (int i = 0; i < ingr.size(); i++) {
			sql ="INSERT INTO PRODUCTO_INGREDIENTE VALUES ('";
			sql += ingr.get(i).getNombre() + "','";
			sql += prod.getNombre() + "')";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		
		sql ="INSERT INTO RESTAURANTE_PRODUCTO VALUES ('";
		sql += nomRest + "','";
		sql += prod.getNombre() + "')";
		prepStmt = conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
		sql =  "INSERT INTO EQUIVALENCIASPRODUCTO VALUES ('";
		sql += nomRest + "', '";
		sql += prod.getNombre() + "')";
		prepStmt = conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
		recursos.add(prepStmt);

	}
}
