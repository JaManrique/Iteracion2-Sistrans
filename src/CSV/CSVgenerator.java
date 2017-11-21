package CSV;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CSVgenerator {
	
	private File generatedCSV;
	private File clientsCSV;
	private File productsCSV;
	private File restaurantsCSV;
	private File checkoutCSV;
	private File producto_checkoutCSV;
	
	public CSVgenerator()
	{
		Date date = new Date();
		String ds = new SimpleDateFormat("h-mm-ss a").format(date);
		generatedCSV = new File("CSV/" + ds + "-users.csv");
		clientsCSV = new File("CSV/" + ds + "-clientes.csv");
		productsCSV = new File("CSV/" + ds + "-products.csv");
		restaurantsCSV = new File("CSV/" + ds + "-restaurants.csv");
		checkoutCSV = new File("CSV/" + ds + "-checkouts.csv");
		producto_checkoutCSV =  new File("CSV/" + ds + "-producto_checkout.csv");
		
		if(!generatedCSV.exists())
		{
			try {
				//Users CSV
				generatedCSV.getParentFile().mkdirs();
				generatedCSV.createNewFile();
				writeHeader("usuario,contrasena,rol,correo\n", generatedCSV);
				//Products CSV
				productsCSV.getParentFile().mkdirs();
				productsCSV.createNewFile();
				writeHeader("nombre,categoria,precioventa,costoproduccion,tipocomidaprod,tiempopreparacion\n", productsCSV);
				//Checkout CSV
				checkoutCSV.getParentFile().mkdirs();
				checkoutCSV.createNewFile();
				writeHeader("id,entregado,tiempor,zonanombre,cliregs_usuario_cliente\n", checkoutCSV);
				//Restaurantes
				restaurantsCSV.getParentFile().mkdirs();
				restaurantsCSV.createNewFile();
				writeHeader("nombre,descripcion,timpodomidarest,paginaweb\n", restaurantsCSV);
				
			
				
				
				
				generateData();
				System.out.println("Archivos creados en: " + generatedCSV.getAbsolutePath());
			} catch (IOException e) 
			{
				System.out.println("SE PUTIO HIJUEPUTA: " + e.getMessage());
				e.printStackTrace();
			}	
		}
		else
		{
			try
			{
				System.out.println("wtf");
				generateData();
			}
			catch (IOException e)
			{
				System.out.println("SE PUTIO HIJUEPUTA: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		System.out.println("Acabo bien :)");
	}
	
	private void writeHeader(String string, File generatedCSV2) throws IOException
	{
		PrintWriter pw = new PrintWriter(generatedCSV2);
		pw.write(string);
		pw.close();
	}

	private void generateData() throws IOException{
		
		long tiempoInicio = System.currentTimeMillis();
		
		String[] restaurantes = new String[10];
		
		restaurantes[0] = "Andrés Carne de Res";
		restaurantes[1] = "Tostao";
		restaurantes[2] = "Telepizza";
		restaurantes[3] = "Chick & Chips";
		restaurantes[4] = "Jenos Pizza";
		restaurantes[5] = "El Corral";
		restaurantes[6] = "Mateo Parrilla";
		restaurantes[7] = "Tao Calentao";
		restaurantes[8] = "One Burrito";
		restaurantes[9] = "Buffalo Wings";
		
		String[] productos = new String[15];
		
		productos[0] = "Carne asada";
		productos[1] = "Coca cola";
		productos[2] = "Sprite";
		productos[3] = "Club Colombia dorada";
		productos[4] = "Papas fritas en cascos";
		productos[5] = "Papas fritas a la francesa";
		productos[6] = "Anillos de cebolla";
		productos[7] = "Ensalada";
		productos[8] = "Hamburguesa Estándar";
		productos[9] = "Arroz Blanco";
		productos[10] = "Limonada Natural";
		productos[11] = "Nachos con Guacamole";
		productos[12] = "Burrito de una carne";
		productos[13] = "Pizza Estándar";
		productos[14] = "Malteada de Vainilla";
		
		String[] usuarios = new String[15];
		
		usuarios[0] = "Juan";
		usuarios[1] = "Gamer";
		usuarios[2] = "Osito";
		usuarios[3] = "Andres";
		usuarios[4] = "Perrito";
		usuarios[5] = "Valeria";
		usuarios[6] = "Laura";
		usuarios[7] = "Diego";
		usuarios[8] = "69";
		usuarios[9] = "99";
		usuarios[10] = "1969";
		usuarios[11] = "Hitler";
		usuarios[12] = "Choripan";
		usuarios[13] = "Seneca";
		usuarios[14] = "Matiz";
		
		String[] rol = new String[5];
		
		rol[0] = "cliente";
		rol[1] = "admin restaurante";
		rol[2] = "admin DB";
		rol[3] = "gerente";
		
		String[] correo = new String[5];
		
		correo[0] = "@uniandes.edu.co";
		correo[1] = "@hotmail.com";
		correo[2] = "@mit.edu.co";
		correo[3] = "@gmail.com";
		correo[4] = "@outlook.com";
		
		String[] tiposComida = new String[10];
		
		tiposComida[0]= "Fitness";
		tiposComida[9]= "Dietética";
		tiposComida[8]= "Grasosa";
		tiposComida[7]= "Chatarra";
		tiposComida[6]= "Gourmet";
		tiposComida[5]= "Baja en sodio";
		tiposComida[4]= "Sin gluten";
		tiposComida[3]= "Italiana";
		tiposComida[2]= "Zhongguo cai";
		tiposComida[1]= "Casera";
		
		String[] zonas = new String[5];
		
		zonas[0] = "Safari";
		zonas[1] = "Bosque encantado";
		zonas[2] = "Jungla oscura";
		zonas[3] = "Desierto picante";
		zonas[4] = "Nevados dulces";
		
		StringBuilder sb = new StringBuilder();
		
		StringBuilder sbClientes = new StringBuilder();
		sbClientes.append("cliente\n");
		
		FileWriter pw = new FileWriter(generatedCSV, true);
		PrintWriter pwClientes = new PrintWriter(clientsCSV);
		
		//mucha MUCHA MUCHA info acá
		ArrayList<String> clientes = new ArrayList<>();
		
		//Generar tabla usuarios-----------------
		
		try
		{
			int id = 0;
			
			for(id = 0; id < 1000000/*un millón*/; id++)
			{
				if(sb.length() > 3500)
				{
					//Imprimir lo que lleva
					pw.write(sb.toString());
					//Vaciar el String builder
					sb.setLength(0);
				}
				
				int r1 = (int) (Math.random()*5);
				int r3 = (int) (Math.random()*15);
				int r2 = (int) (Math.random()*15);
				
				String usr = usuarios[r3] + usuarios[r2];
				
				//Usuario
				sb.append(usr + id + ",");
				//Contraseña
				sb.append("Pssw" + usr + id + ",");
				
				//Rol
				if(r2 > 14)
				{
					sb.append(rol[3] + ",");
				}
				else if(r2 > 13)
				{
					sb.append(rol[2] + ",");
				}
				else if (r2 > 11)
				{
					sb.append(rol[1] + ",");
				}
				else
				{
					sb.append(rol[0] + ",");
					sbClientes.append(usr + id + "\n");
					clientes.add(usr+id);
					if(sb.length() > 500)
					{
						pwClientes.write(sbClientes.toString());
						sbClientes.setLength(0);
					}
				}
				
				//Correo
				
				sb.append(usr + correo[r1] + "\n");				
			}
			
			if(sb.length() > 0)
			{
				//Imprimir lo que lleva
				pw.write(sb.toString());
				//Vaciar el String builder
				sb.setLength(0);
			}
			
			if(sbClientes.length() > 0)
			{
				pwClientes.print(sbClientes.toString());
				sbClientes.setLength(0);
			}
		}
		catch(IOException e)
		{
			pw.close();
			pwClientes.close();
			throw e;
		}		
		pw.close();
		pwClientes.close();
		
		System.out.println("--------------------------TERMINO USER CSV------------------------");
		System.out.println("t: " + (System.currentTimeMillis() - tiempoInicio) + "ms");

		//Generar tabla producto----------------------
		tiempoInicio = System.currentTimeMillis();
		//Reiniciar String Buffer
		sb.setLength(0);
		pw = new FileWriter(productsCSV, true);
		
		for(String prod : productos)
		{
			int r1 = (int) (Math.random()*150);
			int r2 = 30 + (int) (Math.random()*250);
			int r3 = (int) (Math.random()*10);
			
			sb.append(prod + ",");
			sb.append(r1 + ",");
			sb.append((r1*r2) + ",");
			sb.append(r1*(r2-25) + ",");
			sb.append(tiposComida[r3] + ",");
			sb.append(((r1+r2+r3)/45) + ",\n");			
		}
		//Escribir y reiniciar SB
		pw.write(sb.toString());
		sb.setLength(0);
		pw.close();
		System.out.println("--------------------------TERMINO PRODUCTOS CSV------------------------");
		System.out.println("t: " + (System.currentTimeMillis() - tiempoInicio) + "ms");
		
		//Generar CSV restaurantes ---------------------
		
		sb.setLength(0);
		pw = new FileWriter(restaurantsCSV, true);
		
		for(String rest:restaurantes)
		{
			int r1 = (int) (Math.random()*10);
			String tipoComida = tiposComida[r1];			
			sb.append(rest + ",");
			sb.append(rest + " es un rico restaurante de comida " + tipoComida + ".,");
			sb.append(tipoComida + ",");
			sb.append("http://" + rest + ".com\n");			
		}
		//Escribir y reiniciar SB
		pw.write(sb.toString());
		pw.close();
		sb.setLength(0);
		
		System.out.println("--------------------------TERMINO REST CSV------------------------");
		System.out.println("t: " + (System.currentTimeMillis() - tiempoInicio) + "ms");
		
		//Generar CSV checkout ---------------------
		
		pw = new FileWriter(checkoutCSV, true);
		
		for(int id = 0; id < 1000000 /*un millòn*/ ; id++)
		{
			if(sb.length() > 5000)
			{
				//Imprimir lo que lleva
				pw.write(sb.toString());
				//Vaciar el String builder
				sb.setLength(0);
			}
			
			int r1 = (int) (Math.random()*2);
			int r2 = (int) (Math.random()*5);
			int r3 = (int) (Math.random()*clientes.size());
			
			sb.append(id + ",");
			sb.append(zonas[r2] + ",");
			sb.append(clientes.get(r3) + "\n");			
		}
		
		if(sb.length()>0)
		{
			pw.write(sb.toString());
		}
		sb.setLength(0);		
		pw.close();
		
		System.out.println("--------------------------TERMINO CHECKOUT CSV------------------------");
		System.out.println("t: " + (System.currentTimeMillis() - tiempoInicio) + "ms");		
	}
	
	public static void main(String[] args) {
		CSVgenerator csv = new CSVgenerator();		
	}

}
