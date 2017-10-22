package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Usuario;
import vosContainers.RegistroCliente;
import vos.Ingrediente;
import vos.Producto;

public class DAOClientesRegistrados {

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
	public DAOClientesRegistrados() {
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

	private static final String COLUMNAS = "(USUARIO, CONTRASEÑA, ROL, CORREO)";

	/**
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param prod - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void registrarUsuario(Usuario prod) throws SQLException, Exception {

		if(prod.getRol().equals("cliente"))
			throw new Exception("Debe registrar un cliente desde la URL clientes");

		String sql = "INSERT INTO USUARIO " + " VALUES ('";
		sql += prod.getUsuario() + "','";
		sql += prod.getContraseña() + "','";
		sql += prod.getRol() + "','";
		sql += prod.getCorreo()+ "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void registrarCliente(RegistroCliente cliente) throws SQLException, Exception {

		String correctPass = "";

		String sqlVerif = "SELECT * FROM USUARIO U WHERE U.USUARIO = '" + cliente.getAdminUser() + "'";

		PreparedStatement prepVerifStmt = conn.prepareStatement(sqlVerif);
		recursos.add(prepVerifStmt);
		ResultSet rsverif = prepVerifStmt.executeQuery();

		if(rsverif.next()) 
		{
			correctPass = rsverif.getString("CONTRASEÑA");
		}
		else
			throw new Exception("Usuario inválido");

		if(correctPass.equals(cliente.getAdminPass()))
		{

			String sql = "INSERT INTO USUARIO " + " VALUES ('";
			sql += cliente.getUsuario() + "','";
			sql += cliente.getContraseña() + "','";
			sql += cliente.getRol() + "','";
			sql += cliente.getCorreo()+ "')";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			//Insertar en otra tabla
			sql = "INSERT INTO CLIENTESREGISTRADOS VALUES ('" + cliente.getUsuario() +"')";
			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		else
		{
			throw new Exception("Contraseña incorrecta");
		}


	}
}
