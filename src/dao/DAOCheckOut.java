package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.CheckOut;
import vos.Usuario;
import vos.Producto;
import vos.Restaurante;
import vos.Restaurante_Producto;

public class DAOCheckOut {
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
	public DAOCheckOut() {
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
	public void registrarCheckOut(CheckOut prod, List<Restaurante_Producto> lis) throws SQLException, Exception {
		
		String sql="";
		for(int i=0; i<lis.size();i++)
		{
			Integer bool=disponibleProducto(lis.get(i));
			sql+= "INSERT INTO CHECKOUT VALUES ('";
			sql += prod.getId() + "','";
			prod.setEntregado(bool);
			sql += prod.getEntregado() + "','";
			sql += prod.getTiempor()+ "');";
		}
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	public Integer disponibleProducto(Restaurante_Producto prod)throws SQLException, Exception
	{
		Integer resp=0;
		String sql = "SELECT * FROM PRODUCTOSBODEGA P WHERE P.NOMBRE LIKE '"+ prod.getProducto_nombre()+"' "
				+ "AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+prod.getRestaurante_nombre()+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			resp = rs.getInt("CANTIDADPRODUCTO");
		}
		if(resp>0)
		{
			resp=1;
		}
		else 
		{
			resp=0;
		}
		return resp;
	}
	public void registrarServProd(CheckOut prod, Restaurante_Producto resp)throws SQLException, Exception
	{
		Integer cantAct=cantidadEnBodega(resp);
		String sql="";
		if(disponibleProducto(resp)==1)
		{
			sql += "UPDATE PRODUCTOSBODEGA SET CANTIDADPRODUCTO ="+ cantAct-- +" P WHERE P.NOMBRE LIKE '"+ resp.getProducto_nombre()+"' "
					+ "AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+resp.getRestaurante_nombre()+"'";
		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	public Integer cantidadEnBodega(Restaurante_Producto prod)throws SQLException, Exception
	{
		Integer a=0;
		String sql = "SELECT * FROM PRODUCTOSBODEGA P WHERE P.NOMBRE LIKE '"+ prod.getProducto_nombre()+"' "
				+ "AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+prod.getRestaurante_nombre()+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			a = rs.getInt("CANTIDADPRODUCTO");
		}
		return a;
	}
}
