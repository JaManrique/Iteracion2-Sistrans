package vos;

public class Producto_CheckOut 
{
	int id;
	String nombreProd;
	int cantProd;
	public Producto_CheckOut(int pid,String producto, int cant) {
		// TODO Auto-generated constructor stub
		id=pid;
		nombreProd=producto;
		cantProd=cant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreProd() {
		return nombreProd;
	}
	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}
	public int getCantProd() {
		return cantProd;
	}
	public void setCantProd(int cantProd) {
		this.cantProd = cantProd;
	}

}
