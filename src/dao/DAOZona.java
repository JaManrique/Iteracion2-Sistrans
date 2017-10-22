package dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.CheckOut;
import vos.Usuario;
import vos.Producto;
import vos.Zona;
import vosContainers.ZonaCompra;

public class DAOZona {
	//constantes
	public static final String FILTRAR_POR_NOMBRE_PRODUCTO = "PRODUCTO_NOMBRE";
	public static final String FILTRAR_POR_CANTIDAD_PRODUCTO = "CANTIDAD_PRODUCTO";
	public static final String FILTRAR_POR_ID_CHECKOUT = "CHECKOUT_ID";
	public static final String FILTRAR_POR_TIEMPOR = "TIEMPOR";
	public static final String FILTRAR_POR_ENTREGADO = "ENTREGADO";
	static String[] filtros = new String[] {
			FILTRAR_POR_NOMBRE_PRODUCTO,
			FILTRAR_POR_CANTIDAD_PRODUCTO,
			FILTRAR_POR_ID_CHECKOUT,
			FILTRAR_POR_TIEMPOR,
			FILTRAR_POR_ENTREGADO
	};

	public static final String ASCENDENTE = "ASC";
	public static final String DESCENDENTE = "DESC";

	public static final String ORDENAR_POR_NOMBRE_PRODUCTO = "PRODUCTO_NOMBRE";
	public static final String ORDENAR_POR_CANTIDAD_PRODUCTO = "CANTIDAD_PRODUCTO";
	public static final String ORDENAR_POR_ID_CHECKOUT = "CHECKOUT_ID";
	public static final String ORDENAR_POR_TIEMPOR = "TIEMPOR";
	public static final String ORDENAR_POR_ENTREGADO = "ENTREGADO";
	static String[] criteriosOrdenamiento = new String[] {
			ORDENAR_POR_NOMBRE_PRODUCTO,
			ORDENAR_POR_CANTIDAD_PRODUCTO,
			ORDENAR_POR_ID_CHECKOUT,
			ORDENAR_POR_TIEMPOR,
			ORDENAR_POR_ENTREGADO
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
	//atributos
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
	public DAOZona() {
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
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param prod - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void registrarZona(Zona prod) throws SQLException, Exception {

		String sql = "INSERT INTO ZONA VALUES ('";
		sql += prod.getNombre() + "',";
		sql += prod.getCapacidad() + ");";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	public ZonaCompra consultarZonaProductos(String Nzona,String filtro, String columnafiltro,String compfiltro, String agruparPor, String orden) throws SQLException, Exception 
	{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		ArrayList<CheckOut> ck = new ArrayList<CheckOut>();
		
		String sql = "SELECT * FROM (CHECKOUT C INNER JOIN PRODUCTO_CHECKOUT PC ON C.ID=PC.CHECKOUT_ID) WHERE ZONA_NOMBRE= '"+ Nzona+"'";
		if(filtrosContiene(columnafiltro)&&compContiene(compfiltro)) {
			if(esCompString(filtro,compfiltro)) 
			{
				sql+="AND "+columnafiltro+" LIKE "+ compString(filtro,compfiltro);
			}
			else
			{
				sql+="AND "+columnafiltro+" "+compfiltro+" "+filtro;

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
		ResultSet rs1 = prepStmt.executeQuery();
		ArrayList<String> pnombres=new ArrayList();
		while(rs1.next())
		{
			String pnombre=rs1.getString("PRODUCTO_NOMBRE");
			pnombres.add(pnombre);
		}
		for (int i = 0; i < pnombres.size(); i++) {
			String sqlp="SELECT * FROM PRODUCTO P WHERE P.NOMBRE LIKE"+pnombres.get(i);
			PreparedStatement prepStmtf = conn.prepareStatement(sqlp);
			recursos.add(prepStmtf);
			ResultSet rs = prepStmtf.executeQuery();
			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				Integer categoria = rs.getInt("CATEGORIA");
				Integer precioVenta = rs.getInt("PRECIOVENTA");
				Integer costosProduccion = rs.getInt("COSTOPRODUCCION");
				String tipoComidaProd = rs.getString("TIPOCOMIDAPROD");
				Integer tiempoDePreparacion = rs.getInt("TIEMPOPREPARACION");
				productos.add(new Producto(nombre, categoria, precioVenta, costosProduccion, tipoComidaProd,tiempoDePreparacion));
			}
		}		
		
		
		String sql2="SELECT * FROM ZONA WHERE ZONA_NOMBRE= '"+ Nzona+"'";
		if(filtrosContiene(columnafiltro)&&compContiene(compfiltro)) {
			if(esCompString(filtro,compfiltro)) 
			{
				sql2+="AND "+columnafiltro+" LIKE "+ compString(filtro,compfiltro);
			}
			else
			{
				sql2+="AND "+columnafiltro+" "+compfiltro+" "+filtro;

			}
			if(agruparPor!=null&& ordenContiene(agruparPor))
			{
				sql2+=" ORDER BY "+agruparPor;
				if(orden!=null&&(orden.equals(ASCENDENTE)||orden.equals(DESCENDENTE)))
				{
					sql2+=" "+orden;
				}
			}
		}
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt.executeQuery();
		String nom=rs2.getString("NOMBRE");
		Integer cap=rs2.getInt("CAPACIDAD");
		ZonaCompra consulta=new ZonaCompra(nom,cap);
		consulta.setChecksO(productos);
		
		return consulta;
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
	
}
