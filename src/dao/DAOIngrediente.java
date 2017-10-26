package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;
import vos.Restaurante;

public class DAOIngrediente {
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
	public DAOIngrediente() {
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
	
	public void registrarIngrediente(Ingrediente prod, String rest) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTE VALUES ('";
		sql += prod.getNombre() + "','";
		sql += prod.getDescEsp() + "','";
		sql += prod.getDescING() + "','";
		sql += prod.getTipo()+ "';";
		sql +="INSERT INTO RESTAURANTE_INGREDIENTE VALUES ('";
		sql+=rest+"','";
		sql+=prod.getNombre() + "';";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void registrarEquivalenciaDeIngrediente(Ingrediente ing1, Ingrediente ing2, Restaurante restaurante, String usuario, String contr)throws SQLException, Exception 
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
