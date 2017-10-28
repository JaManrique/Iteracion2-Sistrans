package vos;

public class Producto_Menu 
{
	String menu;
	
	String producto;
	
	public Producto_Menu(String pmenu, String pProducto) {
		// TODO Auto-generated constructor stub
		menu=pmenu;
		producto=pProducto;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	
}
