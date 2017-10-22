package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.junit.Test;

public class RotondAndesSQLTest {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";


	private String connectionDataPath;


	private String user;


	private String password;


	private String url;


	private String driver;


	private Connection conn;


	private void inicializar(){
		connectionDataPath = getPath() + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		try
		{
			this.conn = darConexion();
			this.conn.setAutoCommit(false);
		}
		catch (Exception e)
		{
			System.out.println("Conexión fallida: " + e.getMessage());
		}
	}

	@Context
	private ServletContext context;

	private String getPath()
	{
		return "WebContent/WEB-INF/ConnectionData";
	}

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



	@Test
	public void testUnicidadTuplas() {

		inicializar();

		try
		{	
			String sql;
			PreparedStatement prepStat;
			//RESTAURANTE

			sql = "INSERT INTO RESTAURANTE values ('aaaaaa', 'aaaaaaaaaaaaaaa', 'aaaa', 'aaaaaaaaaaaa')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO RESTAURANTE values ('aaaaaa', 'bbbbbbbbbbbbbbb', 'bbbb', 'bbbbbbbbbbbb')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");					
				}
			}

			//REPRESENTANTE

			sql = "INSERT INTO REPRESENTANTE values ('aaa', '1111111111', 'aaaaaa')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO REPRESENTANTE values ('aaa', '1111111111', 'aaaaaa')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}

			//INVENTARIO
			
			sql = "INSERT INTO INVENTARIO values ('aaaaaa')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO INVENTARIO values ('aaaaaa')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}

			//PRODUCTO
			
			sql = "INSERT INTO PRODUCTO values ('pppppp', 1, 50, 50, '1', 5)";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO PRODUCTO values ('pppppp', 1, 50, 50, '1', 5)";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//PRODUCTOS_BODEGA

			sql = "INSERT INTO PRODUCTOSBODEGA values (?, 'aaaaaa', 'pppppp')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.setInt(1, 1);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO PRODUCTOSBODEGA values (?, 'aaaaaa', 'pppppp')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.setInt(1, 1);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//INGREDIENTE
			
			sql = "INSERT INTO INGREDIENTE values ('iiiiii', 'dddddddddd', 'dddddddddd', 'ttt')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO INGREDIENTE values ('iiiiii', 'dddddddddd', 'dddddddddd', 'ttt')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//PRODUCTO_INGREDIENTE
			
			sql = "INSERT INTO PRODUCTO_INGREDIENTE values ('iiiiii', 'pppppp')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO PRODUCTO_INGREDIENTE values ('iiiiii', 'pppppp')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//ZONA
			
			sql = "INSERT INTO ZONA values ('zzzzzz', 20, 'aaaaaa')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO ZONA values ('zzzzzz', 20, 'aaaaaa')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//CHECKOUT
			
			sql = "INSERT INTO CHECKOUT values (222, 1, 1507167427, 'zzzzzz')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO CHECKOUT values (222, 1, 1507167427, 'zzzzzz')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//PRODUCTO_CHECKOUT
			
			sql = "INSERT INTO PRODUCTO_CHECKOUT values (222, 'pppppp', 1)";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO PRODUCTO_CHECKOUT values (222, 'pppppp', 1)";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//USUARIOS
			
			sql = "INSERT INTO USUARIO values ('uuuuuu', 'cccccccccc', 'rrrrrr', 'cccccccccc')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO USUARIO values ('uuuuuu', 'cccccccccc', 'rrrrrr', 'cccccccccc')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//CLIENTES
			
			sql = "INSERT INTO CLIENTESREGISTRADOS values ('uuuuuu')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO CLIENTESREGISTRADOS values ('uuuuuu')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//EQUIVALENCIAS_INGREDIENTES
			
			sql = "INSERT INTO EQUIVALENCIASINGREDIENTE values ('iiiiii', 'aaaaaa')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO EQUIVALENCIASINGREDIENTE values ('iiiiii', 'aaaaaa')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//MENU
			
			sql = "INSERT INTO MENU values ('mmmmmm', 2, 50, 30, 'muyrico', 20, 'aaaaaa')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO MENU values ('mmmmmm', 2, 50, 30, 'muyrico', 20, 'aaaaaa')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//PRODUCTO_MENU
			
			sql = "INSERT INTO PRODUCTO_MENU values ('mmmmmm', 'pppppp')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO PRODUCTO_MENU values ('mmmmmm', 'pppppp')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//RESTAURANTE_PRODUCTO
			
			sql = "INSERT INTO RESTAURANTE_PRODUCTO values ('aaaaaa', 'pppppp')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO RESTAURANTE_PRODUCTO values ('aaaaaa', 'pppppp')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//EQUIVALENCIASPRODUCTO
			
			sql = "INSERT INTO EQUIVALENCIASPRODUCTO values ('aaaaaa', 'pppppp')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO EQUIVALENCIASPRODUCTO values ('aaaaaa', 'pppppp')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//TIPOCOMIDA
			
			sql = "INSERT INTO TIPOCOMIDA values ('tctctc', 'pppppp')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO TIPOCOMIDA values ('tctctc', 'pppppp')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//EVENTO
			
			sql = "INSERT INTO EVENTO values (333, 'mmmmmm', 'uuuuuu', 'zzzzzz')";
			prepStat = this.conn.prepareStatement(sql);
			prepStat.execute();

			try
			{
				sql = "INSERT INTO EVENTO values (333, 'mmmmmm', 'uuuuuu', 'zzzzzz')";
				prepStat = this.conn.prepareStatement(sql);
				prepStat.execute();
				fail("no se está verificando unicidad de PK");
			} catch(SQLException e)
			{
				//Bien!
				if(!e.getMessage().contains("PK"))
				{
					e.printStackTrace();
					fail("Error de SQL (no es de PK)");
				}
			}
			
			//--FIN
			
		}
		catch(Exception e)
		{
			System.out.println("Error en la ejecución de las pruebas: " + e.getMessage());
			e.printStackTrace();
			fail();
		}
		finally {
			//devolver todo
			try {
				this.conn.rollback();
			} catch (SQLException e) {
				System.out.println("Error rollbackeando: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
