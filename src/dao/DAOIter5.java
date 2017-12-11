package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.glass.ui.Size;

import oracle.net.aso.n;
import oracle.net.aso.p;
import vos.CheckOut;
import vos.EquivalenciasProducto;
import vos.Ingrediente;
import vos.Producto;
import vos.Producto_CheckOut;
import vos.Producto_Menu;
import vos.ProductosBodega;
import vos.Restaurante;
import vos.Restaurante_Producto;
import vos.Utilidad;
import vosContainers.PedidoMenu;
import vosContainers.ReporteProducto;
import vosContainers.ReporteRestaurante;

public class DAOIter5 {

	private static final int SERVIDO = 1;
	private static final int NO_SERVIDO = 0;
	
	
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
	public DAOIter5() {
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

	public boolean esRestaurante(String nombre, String contrasena)throws SQLException, Exception 
	{
		System.out.println(nombre + " // " + contrasena );
		String sql="SELECT * FROM USUARIO U WHERE U.USUARIO LIKE '"+nombre+"' AND U.CONTRASENA LIKE '"+contrasena+"' AND U.ROL LIKE 'admin restaurante'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		return rs.next();
	}
	
	
	public boolean esCliente(String nombre, String contrasena)throws SQLException, Exception
	{
		String sql="SELECT * FROM USUARIO U WHERE U.USUARIO = '"+nombre+"' AND U.CONTRASENA LIKE '"+contrasena+"' AND U.ROL LIKE 'cliente'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		return rs.next();
	}
	public boolean esAcmon(String nombre, String contraseña)throws SQLException, Exception
	{
		String sql="SELECT * FROM USUARIO U WHERE U.USUARIO = '"+nombre+"'"+" AND U.CONTRASEÑA = '"+contraseña+"'"+" AND U.ROL='admin DB'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		return rs.next();
	}
	
	
	// RF 11 - Equivalencias ingrediente
	public void registrarEquivalenciaDeIngrediente(String ing1, String ing2, String restaurante, String usuario, String contr)throws SQLException, Exception 
	{
		if(esRestaurante(usuario, contr))
		{
			int eq1 = -1;
			int eq2 = -1;
			String sql = "";
			
			sql = "SELECT EQUIVALENCIA FROM EQUIVALENCIASINGREDIENTE WHERE INGREDIENTE_NOMBRE LIKE '" + ing1 +"' AND RESTAURANTE_NOMBRE LIKE '" + restaurante + "'";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				eq1 = rs.getInt(1);
			}
			
			sql = "SELECT EQUIVALENCIA FROM EQUIVALENCIASINGREDIENTE WHERE INGREDIENTE_NOMBRE = '" + ing2 +"' AND RESTAURANTE_NOMBRE = '" + restaurante + "'";
			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				eq2 = rs.getInt(1);
			}
			
			if(eq1 >0 && eq2>0)
			{
				throw new Exception("Esos productos ya pertenecen a alguna equivalencia del restaurante");
			}
			
			
			if(eq1 > 0)
			{
				sql = "INSERT INTO EQUIVALENCIASINGREDIENTE VALUES ('" + restaurante + "', '" + ing2 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, eq1);
				recursos.add(prepStmt);
				rs = prepStmt.executeQuery();
			}
			else if (eq2 > 0)
			{
				sql = "INSERT INTO EQUIVALENCIASINGREDIENTE VALUES ('" + restaurante + "', '" + ing1 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, eq2);
				recursos.add(prepStmt);
				prepStmt.execute();
			}
			else
			{
				int max;
				sql = "SELECT MAX(E.EQUIVALENCIA) FROM EQUIVALENCIASINGREDIENTE E WHERE E.RESTAURANTE_NOMBRE = '" + restaurante + "'";
				prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				rs = prepStmt.executeQuery();
				rs.next();
				max = rs.getInt(1);
				
				sql = "INSERT INTO EQUIVALENCIASINGREDIENTE VALUES ('" + restaurante + "', '" + ing1 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, max+1);
				recursos.add(prepStmt);
				prepStmt.execute();
				
				sql = "INSERT INTO EQUIVALENCIASINGREDIENTE VALUES ('" + restaurante + "', '" + ing2 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, max+1);
				recursos.add(prepStmt);
				prepStmt.execute();
			}
			prepStmt.close();
		}
		else
			throw new Exception("Login inválido");
	}

	
	// RF 12 - Equivalencias producto
	public void registrarEquivalenciaDeProducto(String prod1, String prod2, String restaurante, String usuario, String contr)throws SQLException, Exception 
	{
		if(esRestaurante(usuario, contr))
		{
			int eq1 = -1;
			int eq2 = -1;
			String sql = "";
			
			sql = "SELECT E.EQUIVALENCIA FROM EQUIVALENCIASPRODUCTO E WHERE E.PRODUCTO_NOMBRE = '" + prod1 +"'";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				eq1 = rs.getInt(1);
			}
			
			sql = "SELECT E.EQUIVALENCIA FROM EQUIVALENCIASPRODUCTO E WHERE E.PRODUCTO_NOMBRE = '" + prod2 +"'";
			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				eq2 = rs.getInt(1);
			}
			
			if(eq1 >0 && eq2>0)
			{
				throw new Exception("Esos productos ya pertenecen a alguna equivalencia del restaurante");
			}
			
			if(eq1 > 0)
			{
				sql = "INSERT INTO EQUIVALENCIASPRODUCTO VALUES ('" + restaurante + "', '" + prod2 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, eq1);
				recursos.add(prepStmt);
				rs = prepStmt.executeQuery();
			}
			else if (eq2 > 0)
			{
				sql = "INSERT INTO EQUIVALENCIASPRODUCTO VALUES ('" + restaurante + "', '" + prod1 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, eq2);
				recursos.add(prepStmt);
				prepStmt.execute();
			}
			else
			{
				int max;
				sql = "SELECT MAX(E.EQUIVALENCIA) FROM EQUIVALENCIASPRODUCTO E WHERE E.RESTAURANTE_NOMBRE = '" + restaurante + "'";
				prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				rs = prepStmt.executeQuery();
				rs.next();
				max = rs.getInt(1);
				
				sql = "INSERT INTO EQUIVALENCIASPRODUCTO VALUES ('" + restaurante + "', '" + prod1 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, max+1);
				recursos.add(prepStmt);
				prepStmt.execute();
				
				sql = "INSERT INTO EQUIVALENCIASPRODUCTO VALUES ('" + restaurante + "', '" + prod2 + "', ?)";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setInt(1, max+1);
				recursos.add(prepStmt);
				prepStmt.execute();
			}
		}
		else
			throw new Exception("Login inválido");
	}
	
	
	//RF 13 - Surtir restaurante
	public void surtirRestaurante(String rest) throws SQLException, Exception
	{
		ArrayList<ProductosBodega> productos=darProductosRestaurante(rest);
		String sql="";
		for (int i = 0; i < productos.size(); i++)
		{
			sql = "UPDATE PRODUCTOSBODEGA P SET CANTIDADPRODUCTO="+productos.get(i).getMaximo() + " WHERE P.PRODUCTO_NOMBRE LIKE '"+productos.get(i).getNombreProd()+"' AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+productos.get(i).getNombreRest()+"'";			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}
	
	public void retirarproductosbodega(String rest, List<Producto> prods)throws SQLException, Exception
	{
		String sql="";
		for (int i = 0; i < prods.size(); i++)
		{
			int cantActual=cantidadEnBodega(prods.get(i).getNombre(), rest);
			if(cantActual==0)
			{
				throw new Exception("no se puede vender el producto, no hay mas en bodega.");
			}
			else
			{
				cantActual--;
			}
			 sql+= "UPDATE PRODUCTOSBODEGA P SET CANTIDADPRODUCTO="+cantActual;
			 sql+=" WHERE P.PRODUCTO_NOMBRE LIKE '"+prods.get(i).getNombre()+"' AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+rest+"'";			
		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<ProductosBodega> darProductosRestaurante(String rest)throws SQLException, Exception
	{
		ArrayList<ProductosBodega> productos = new ArrayList<ProductosBodega>();
		String sql = "SELECT * FROM PRODUCTOSBODEGA P WHERE P.INVENTARIO_RESTAURANTE_NOMBRE LIKE "+ "'"+rest+"'";
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
	
	
	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public List<Producto> buscarProductosPorNombre(List<String> name) throws SQLException, Exception {
		List<Producto> productos = new ArrayList<Producto>();
		Iterator<String> iter= name.iterator();
		while(iter.hasNext())
		{
			String sql = "SELECT * FROM PRODUCTO WHERE NOMBRE ='" + iter.next() + "'";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) 
			{
				String nombre = rs.getString("NOMBRE");
				Integer categoria = rs.getInt("CATEGORIA");
				Integer precioVenta = rs.getInt("PRECIOVENTA");
				Integer costosProduccion = rs.getInt("COSTOPRODUCCION");
				String tipoComidaProd = rs.getString("TIPOCOMIDAPROD");
				Integer tiempoDePreparacion = rs.getInt("TIEMPOPREPARACION");
				productos.add(new Producto(nombre, categoria, precioVenta, costosProduccion, tipoComidaProd,tiempoDePreparacion));
			}
		}

		return productos;
	}
	
	public void registrarpedidoIter3REQ14(String nombrePM, boolean esMenu, List<String> productos,String usuario, String contr,String restaurante)throws SQLException, Exception 
	{
		String cliente="NULL";
		if(usuario!=null)
		{
			if(!esCliente(usuario, contr))
			{
				throw new Exception("el usuario o la contraseña no existen o son incorrectos.");
			}
			cliente=usuario;
		}
		int max;
		String sql = "SELECT MAX(C.CHECKOUT_ID) FROM PRODUCTO_CHECKOUT C";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		max = rs.getInt(1);
		if(esMenu) 
		{
			ArrayList<CheckOut> cks=new ArrayList<>();
			//lista productos que llegan por parametro en el pedido.
			List<Producto> productosB=buscarProductosPorNombre(productos);
			//lista productos del menú.
			List<Producto> productosA=darProductosMenu(nombrePM);
			
			if(!diferentesCategorias(productosB))
			{
				throw new Exception("No puede haber en un Menú productos de la misma categoría.");
			}
			//equivalencias productos del pedido.
			ArrayList<Integer> equivalenciasB=darEquivalenciasProductos(productos, restaurante);
			//equivalencias productos del menú.
			ArrayList<Integer> equivalenciasA=darEquivalenciasProductosMenú(productosA, restaurante);
			for (int i = 0; i < equivalenciasA.size(); i++) 
			{
				Producto poductoA=productosA.get(i);
				int equivalenciaA=equivalenciasA.get(i);
				for(int j=0;j< equivalenciasB.size();j++)
				{
					Producto poductoB=productosB.get(j);
					int equivalenciaB=equivalenciasB.get(j);
					if(poductoA.getCategoria()==poductoB.getCategoria()&&equivalenciaA!=equivalenciaB)
					{
						throw new Exception("Los algunos de los productos pedidos no son equivalentes a los permitidos en el menu, por ejemplo el producto: "+poductoB.getNombre()+".");
					}
				}
				
			}

			for (int i = 0; i < productosB.size(); i++)
			{
				String time=String.valueOf(System.currentTimeMillis());   

				String sql2= "INSERT INTO CHECKOUT VALUES ("+max+", "+0+", "+time+", NULL, "+cliente+")";
				sql2+="INSERT INTO PRODUCTO_CHECKOUT VALUES ("+max+", "+productosB.get(i).getNombre()+", "+restaurante+","+1+","+nombrePM+")";

				prepStmt = conn.prepareStatement(sql2);
				recursos.add(prepStmt);
				rs = prepStmt.executeQuery();
			}
		}

		if(!esMenu)
		{
			String time=String.valueOf(System.currentTimeMillis());   
			String sql2= "INSERT INTO CHECKOUT VALUES ("+max+", "+0+", "+time+", NULL, "+cliente+")";
			sql2+="INSERT INTO PRODUCTO_CHECKOUT VALUES ("+max+", "+nombrePM+", "+restaurante+","+1+",NULL)";
			prepStmt = conn.prepareStatement(sql2);
			recursos.add(prepStmt);
			prepStmt.execute();
		}
	}
	public void registrarpedidoMesa(List<PedidoMenu> pedidos)throws SQLException, Exception
	{
		
		Iterator<PedidoMenu> iter=pedidos.iterator();
		while(iter.hasNext())
		{
			PedidoMenu temp=iter.next();
			String nombrePM=temp.getNombre();
			boolean esMenu=temp.getEsMenu();
			List<String> productos=temp.getAlternativos();
			String usuario=temp.getUsuario();
			String contr=temp.getPass();
			String restaurante=temp.getRestaurante();
			if(esMenu)
			{
				for(String prod:productos)
				{
					int c=cantidadEnBodega(prod, restaurante);
					if(c==0)
					{
						throw new Exception("No se puede registrar el pedido ya que no esta disponible el producto: "+prod);
					}
				}
			}
			else
			{
				int c=cantidadEnBodega(nombrePM, restaurante);
				if(c==0)
				{
					throw new Exception("No se puede registrar el pedido ya que no esta disponible el producto: "+nombrePM);
				}
			}
				
		}
		while (iter.hasNext()) 
		{
			PedidoMenu temp=iter.next();
			String nombrePM=temp.getNombre();
			boolean esMenu=temp.getEsMenu();
			List<String> productos=temp.getAlternativos();
			String usuario=temp.getUsuario();
			String contr=temp.getPass();
			String restaurante=temp.getRestaurante();
			registrarpedidoIter3REQ14(nombrePM, esMenu, productos, usuario, contr, restaurante);
		}
	}
	public void registrarServicioMesaIter3REQ16(int idCheckOut)throws SQLException, Exception
	{
		String sql = "SELECT * FROM PRODUCTO_CHECKOUT C WHERE C.CHECKOUT_ID="+idCheckOut;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) 
		{
			String prodNom=rs.getString("PRODUCTO_NOMBRE");
			String restaurante=rs.getString("RESTAURANTE_NOMBRE");
			List<String> names=new ArrayList<>();
			names.add(prodNom);
			List<Producto> prods=buscarProductosPorNombre(names);
			retirarproductosbodega(restaurante, prods);
			sql="UPDATE CHECKOUT C SET ENTREGADO=1 WHERE C.ID="+idCheckOut;
			PreparedStatement prepStmt2 = conn.prepareStatement(sql);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();	
		}		

	}
	public void cancelarPedido(int idCheckOut)throws SQLException, Exception
	{
		
	}
	public List<Producto> consumoClienteRegistrado(String usuario, String contraseña, String usuarioBusqueda)throws SQLException, Exception
	{
		List<Producto> respuesta=new ArrayList<>();
			if(esCliente(usuario, contraseña))
			{
				if(usuarioBusqueda!=null)
				{
					throw new Exception("Usted no tiene acceso a la información de otros usuarios.");
				}
				respuesta=buscarProductosPorNombre(darNombresProductosCliente(usuario));
			}
			else if(esAcmon(usuario, contraseña)) 
			{
				respuesta=buscarProductosPorNombre(darNombresProductosCliente(usuarioBusqueda));
			}
			else
			{
				throw new Exception("El usuario solicitado no existe.");
			}
		return respuesta;
	}
	public List<String> darNombresProductosCliente(String usuario) throws SQLException, Exception
	{
		String sql="SELECT * FROM CHECKOUT C WHERE C.CLISREGS_USUARIO_CLIENTE='"+usuario+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		List<Integer>idsck=new ArrayList<>();
		while(rs.next())
		{
			int a=rs.getInt("ID");
			idsck.add(a);
		}
		Iterator<Integer>iter=idsck.iterator();
		List<String>prods=new ArrayList<>();
		while(iter.hasNext())
		{
			String sql2="SELECT * FROM PRODUCTO_CHECKOUT C WHERE C.CHECKOUT_ID="+iter.next();
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			while(rs2.next())
			{
				String b=rs2.getString("PRODUCTO_NOMBRE");
				prods.add(b);
			}
		}
		return prods;
	}
	public List<ReporteRestaurante> darDatosProductosMasVeendidos(String usuario, String password, String restaurante) throws SQLException, Exception
	{
		List<ReporteRestaurante>resp=new ArrayList<>();
		if(esRestaurante(usuario, password))
		{
			if(restaurante==null)
			{
				throw new Exception("Usted no tiene acceso a la información de otros usuarios.");
			}
			ReporteRestaurante rest=new ReporteRestaurante(restaurante, darProductosCheckOutsEntregados(restaurante));
			resp.add(rest);
		}
		else if(esAcmon(usuario, password))
		{
			String sql="SELECT * RESTAURANTE";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				String nombre=rs.getString("NOMBRE");
				ReporteRestaurante rest=new ReporteRestaurante(nombre, darProductosCheckOutsEntregados(nombre));
				resp.add(rest);
			}
		}
		else
		{
			throw new Exception("Usuario o contraseña incorrectos.");
		}
		return resp;
	}
	
	public List<ReporteProducto> darProductosCheckOutsEntregados(String restaurante)throws SQLException, Exception
	{
		List<Integer> prodsrest=new ArrayList<Integer>();
		String sql="SELECT * FROM PRODUCTO_CHECKOUT WHERE RESTAURANTE_NOMBRE='"+restaurante+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			int b=rs.getInt("CHECKOUT_ID");
			String sql2="SELECT * FROM CHECKOUT WHERE ID="+b+" AND ENTREGADO=1";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				prodsrest.add(b);
			}
		}
		List<ReporteProducto> prods=new ArrayList<>();  
		Iterator<Integer> iter=prodsrest.iterator();
		while(iter.hasNext())
		{
			int b=iter.next();
			String sql2="SELECT * FROM PRODUCTO_CHECKOUT WHERE CHECKOUT_ID="+b;
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			while(rs2.next())
			{
				String producto=rs2.getString("PRODUCTO_NOMBRE");
				int cant=rs2.getInt("CANTIDAD_PRODUCTO");
				for(ReporteProducto r:prods)
				{
					if(r.getProducto().equals(producto))
					{
						r.setCantidad(r.getCantidad()+cant);
						r.setGanancia(r.getGanancia()+darganancia(producto, cant));
					}
					else
					{
						ReporteProducto nuevo=new ReporteProducto(producto, darganancia(producto, cant), cant, cant, 0);
						prods.add(nuevo);
					}
				}
			}
		}
		List<Integer> prodsrest2=new ArrayList<Integer>();
		Iterator<Integer> iter2=prodsrest.iterator();
		String sql2="SELECT * FROM PRODUCTO_CHECKOUT WHERE RESTAURANTE_NOMBRE='"+restaurante+"'";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		while(rs2.next())
		{
			int b=rs2.getInt("CHECKOUT_ID");
			String sql3="SELECT * FROM CHECKOUT WHERE ID="+b+" AND ENTREGADO=1 AND CLIREGS_USUARIO_CLIENTE=NULL";
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			if(rs3.next())
			{
				prodsrest2.add(b);
			}
		}
		while(iter2.hasNext())
		{
			String s="SELECT * FROM PRODUCTO_CHECKOUT WHERE CHECKOUT_ID="+iter.next();
			PreparedStatement prepStmt3 = conn.prepareStatement(s);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{
				String nombre=rs.getString("PRODUCTO_NOMBRE");
				for(ReporteProducto r:prods)
				{
					if(r.getProducto().equals(nombre))
					{
						r.setCant_no_registrados(r.getCant_no_registrados()+1);
						r.setCant_registrados(r.getCant_registrados()-1);
					}
				}
			}
		}
		
		return prods;
	}
	
	public double darganancia(String producto, int cantidad) throws SQLException,Exception
	{
		double ganancia=0;
		String sql="SELECT * FROM PRODUCTO WHERE NOMBRE='"+producto+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		double venta=rs.getDouble("PRECIOVENTA");
		double costo=rs.getDouble("COSTOPRODUCCION");
		ganancia=(venta-costo)*cantidad;
		return ganancia;
	}
	public boolean diferentesCategorias(List<Producto> prods)
	{
		boolean hay=false;
		for (int i = 0; i < prods.size()&&!hay; i++) 
		{
			int cat=prods.get(i).getCategoria();
			for (int j = i+1; j < prods.size()&&!hay; j++) 
			{
				if(cat==prods.get(j).getCategoria())
				hay=true;
			}
		}
		return hay;
	}
	public List<Producto> darProductosMenu(String menu)throws SQLException, Exception
	{
		List<String> prods=new ArrayList<>() ;
		String sql = "SELECT * FROM PRODUCTO_MENU WHERE MENU_NOMBRE ='" + menu + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String nombre_producto = rs.getString("PRODUCTO_NOMBRE");
			prods.add(nombre_producto);
		}
		return buscarProductosPorNombre(prods);
	}
	public ArrayList<Integer> darEquivalenciasProductos(List<String> productos, String restaurante)throws SQLException, Exception
	{
		ArrayList<Integer> eqs=new ArrayList<>() ;
		Iterator<String> p=productos.iterator();
		while(p.hasNext())
		{
			int equivalencia=0;
			//sacar equivalencia del producto
			String sql = "SELECT * FROM EQUIVALECIASPRODUCTO WHERE PRODUCTO_NOMBRE ='" + p.next() + "' AND RESTAURANTE_NOMBRE='"+restaurante+"'";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			equivalencia = rs.getInt("EQUIVALENCIA");
			eqs.add(equivalencia);
		}
		return eqs;
	}
	public ArrayList<Integer> darEquivalenciasProductosMenú(List<Producto> productos, String restaurante)throws SQLException, Exception
	{
		ArrayList<Integer> eqs=new ArrayList<>() ;
		Iterator<Producto> p=productos.iterator();
		while(p.hasNext())
		{
			int equivalencia=0;
			//sacar equivalencia del producto
			String sql = "SELECT * FROM EQUIVALECIASPRODUCTO WHERE PRODUCTO_NOMBRE ='" + p.next().getNombre() + "' AND RESTAURANTE_NOMBRE='"+restaurante+"'";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			equivalencia = rs.getInt("EQUIVALENCIA");
			eqs.add(equivalencia);
		}
		return eqs;
	} 	
	public Integer cantidadEnBodega(String prod, String rest)throws SQLException, Exception
	{
		Integer a=0;
		String sql = "SELECT * FROM PRODUCTOSBODEGA P WHERE P.NOMBRE LIKE '"+ prod+"' "
				+ "AND P.INVENTARIO_RESTAURANTE_NOMBRE LIKE '"+rest+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			a = rs.getInt("CANTIDADPRODUCTO");
		}
		return a;
	}
	
	
	
	//---------- RF 17
	
	public void cancelarPedido(Long idCheckout) throws Exception
	{
		int estado = -1;
		
		String sql = "SELECT ENTREGADO FROM CHECKOUT WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setLong(1, idCheckout);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next())
		{
			boolean servido = rs.getInt(1) == SERVIDO;
			
			//si no esta servido lo cancelo
			if(!servido)
			{
				//Eliminar dependencias				
				sql = "DELETE FROM MENU_CHECKOUT WHERE CHECKOUT_ID = ?";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setLong(1, idCheckout);
				recursos.add(prepStmt);
				prepStmt.execute();
				
				sql = "DELETE FROM PRODUCTO_CHECKOUT WHERE CHECKOUT_ID = ?";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setLong(1, idCheckout);
				recursos.add(prepStmt);
				prepStmt.execute();
				
				//Eliminar checkout
				sql = "DELETE FROM CHECKOUT WHERE ID = ?";
				prepStmt = conn.prepareStatement(sql);
				prepStmt.setLong(1, idCheckout);
				recursos.add(prepStmt);
				prepStmt.execute();
			}
			//Si sí, excepción
			else
			{
				throw new Exception("El producto ya fué servido");
			}
		}
		else
		{
			throw new Exception("El checkout a eliminar no existe");
		}
	}
	public List<Utilidad> darRentabilidadRestaurante(String restaurante,String fechaini, String fechafin) throws SQLException, Exception
	{
		List<Utilidad> respuesta=new ArrayList<Utilidad>();
		List<String> detalle=new ArrayList<String>();
		Utilidad temp=new Utilidad(restaurante, detalle, 0.0);
		String sql="SELECT SUM(-PROD.COSTOPRODUCCION +PROD.PRECIOVENTA) as UTILIDAD "
				+ "FROM PRODUCTO PROD INNER JOIN (SELECT * "
				+ "FROM CHECKOUT C INNER JOIN PRODUCTO_CHECKOUT P ON C.ID=P.CHECKOUT_ID" + 
				"                                        WHERE P.RESTAURANTE_NOMBRE='"+restaurante+"' "
						+ "AND C.TIEMPOR BETWEEN '"+fechaini+"' AND '"+fechafin+"')CONS " + 
				"ON PROD.NOMBRE=CONS.PRODUCTO_NOMBRE";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		double resp=0.0;
		while(rs.next())
		{
			resp=rs.getDouble("UTILIDAD");
		}
		temp.setUtilidad(resp);
		String sql2="Select Producto_nombre, count(producto_nombre) AS cantidad_Vendidad, sum(PrecioVenta - Costoproduccion) AS Utilidad from" + 
				"(" + 
				"    select Producto_nombre from " + 
				"    (" + 
				"        (" + 
				"            select Producto_nombre, checkout_id from PRODUCTO_CHECKOUT where restaurante_nombre = '"+restaurante+"'" + 
				"        ) A" + 
				"        INNER JOIN\r\n" + 
				"        (" + 
				"            select * from CHECKOUT where TIEMPOR between '"+fechaini+"' AND '"+fechafin+"'" + 
				"        ) B" + 
				"        ON A.CHECKOUT_ID = B.ID" + 
				"    )" + 
				") A INNER JOIN PRODUCTO B ON A.Producto_nombre = B.nombre" + 
				"GROUP BY PRODUCTO_NOMBRE";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		while(rs2.next())
		{
			String descripcion="PRODUCTO NOMBRE: "+rs2.getString("PRODUCTO_NOMBRE")+" CANTIDAD VENDIDA: "+rs2.getString("CANTIDAD_VENDIDA")+
					" UTILIDAD PRODUCTO: "+rs2.getString("UTILIDAD");
			detalle.add(descripcion);
		}
		temp.setProductos(detalle);
		respuesta.add(temp);
		return respuesta;
	}
	
}
