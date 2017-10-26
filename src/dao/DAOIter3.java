package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;
import vos.Producto;
import vos.ProductosBodega;
import vos.Restaurante;

public class DAOIter3 {

	/**
	 * Arraylits de recursos que se usan para la ejecuciÃ³n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexiÃ³n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOIter3() {
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	public boolean esRestaurante(String nombre, String contraseña)throws SQLException, Exception 
	{
		String sql="SELECT * FROM USUARIO U WHERE";
		sql+="U.USUARIO = '"+nombre+"'"+" AND U.CONTRASEÑA = '"+contraseña+"'"+" AND U.ROL='admin restaurante'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		return rs.next();
	}
	public boolean esCliente(String nombre, String contraseña)throws SQLException, Exception
	{
		String sql="SELECT * FROM USUARIO U WHERE";
		sql+="U.USUARIO = '"+nombre+"'"+" AND U.CONTRASEÑA = '"+contraseña+"'"+" AND U.ROL='cliente'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		return rs.next();
	}
	public void registrarEquivalenciaDeProducto(Producto prod1, Producto prod2, Restaurante restaurante, String usuario, String contr)throws SQLException, Exception 
	{
		if(esRestaurante(usuario, contr))
		{
			String sql1 = "INSERT INTO EQUIVALENCIASPRODUCTO VALUES ('";
			sql1+=prod1.getNombre()+"','";
			sql1 += restaurante.getNombre() + "','";
			sql1+= prod2.getNombre() + "';";
			PreparedStatement prepStmt = conn.prepareStatement(sql1);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}
	public void registrarEquivalenciaDeIngrediente(Ingrediente ing1, Ingrediente ing2, Restaurante restaurante, String usuario, String contr)throws SQLException, Exception 
	{
		if(esRestaurante(usuario, contr))
		{
			String sql1 = "INSERT INTO EQUIVALENCIASINGREDIENTE VALUES ('";
			sql1+=ing1.getNombre()+"','";
			sql1 += restaurante.getNombre() + "','";
			sql1+= ing2.getNombre() + "';";
			PreparedStatement prepStmt = conn.prepareStatement(sql1);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}
	public void surtirRestaurante(Restaurante rest) throws SQLException, Exception
	{
		ArrayList<ProductosBodega> productos=darProductosRestaurante(rest);
		String sql="";
		for (int i = 0; i < productos.size(); i++)
		{
			 sql+= "UPDATE PRODUCTOSBODEGA P SET CANTIDADPRODUCTO="+productos.get(i).getMaximo();
			 sql+=" WHERE P.PRODUCTO_NOMBRE LIKE '"+productos.get(i).getNombreProd()+"' AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+productos.get(i).getNombreRest()+"';";			
		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	public ArrayList<ProductosBodega> darProductosRestaurante(Restaurante rest)throws SQLException, Exception
	{
		ArrayList<ProductosBodega> productos = new ArrayList<ProductosBodega>();
		String sql = "SELECT * FROM PRODUCTOSBODEGA P";
		sql+="WHERE P.INVENTARIO_RESTAURANTE_NOMBRE LIKE "+ "'"+rest.getNombre()+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			String nombreRest = rs.getString("INVENTARIO_RESTAURANTE_NOMBRE");
			String nombreProd = rs.getString("PRODUCTO_NOMBRE");
			Integer cantProd = rs.getInt("CANTIDADPRODUCTO");
			Integer max = rs.getInt("MAXIMO");
			productos.add(new ProductosBodega(nombreRest, nombreProd, cantProd, max));
		}
		return productos;
	}
}
