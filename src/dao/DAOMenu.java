package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Usuario;
import vos.Ingrediente;
import vos.Menu;
import vos.Producto;

public class DAOMenu {
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
	public DAOMenu() {
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
	 * @param menu - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void registrarMenu(Menu menu, String nomRest, List<String> productos) throws SQLException, Exception {

		String sql = "INSERT INTO MENU VALUES ('";
		sql += menu.getNombre() + "',";
		sql += menu.getCategoria() + ",";
		sql += menu.getPrecioVenta() + ",";
		sql += menu.getCostosProduccion() + ",'";
		sql += menu.getTipoComidaProd() + "',";
		sql += menu.getTiempoPreparacion() + ",'";
		sql += nomRest +"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
		for (int i = 0; i < productos.size(); i++) {
			sql ="INSERT INTO PRODUCTO_MENU VALUES ('";
			sql +=menu.getNombre()  + "','";
			sql +=productos.get(i) + "')";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.execute();
		}
		
		
	}	
}
