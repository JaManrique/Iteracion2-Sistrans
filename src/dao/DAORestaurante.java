package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Producto;
import vos.ProductosBodega;
import vos.Usuario;
import vos.Restaurante;

public class DAORestaurante {
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
	public DAORestaurante() {
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
	public void registrarRestaurante(Restaurante prod) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTE VALUES ('";
		sql += prod.getNombre() + "','";
		sql += prod.getDescripcion() + "','";
		sql += prod.getTipoComidaRest() + "','";
		sql += prod.getPaginaWeb()+ "')";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		sql = "INSERT INTO INVENTARIO VALUES ('" + prod.getNombre() + "')";
		prepStmt = conn.prepareStatement(sql);
		prepStmt.executeQuery();
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

	public List<Restaurante> darRestaurantes()throws SQLException, Exception
	{
		// TODO Auto-generated method stub
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();
		String sql = "SELECT * FROM RESTAURANTE R";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			String nombreRest = rs.getString("NOMBRE");
			String desc = rs.getString("DESCRIPCION");
			String tipo = rs.getString("TIPOCOMIDAREST");
			String pag = rs.getString("PAGINAWEB");
			restaurantes.add(new Restaurante(nombreRest, desc, tipo, pag));
		}
		return restaurantes;
	}
}
