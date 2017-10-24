package vos;

public class ProductosBodega 
{
	public String getNombreRest() {
		return nombreRest;
	}

	public void setNombreRest(String nombreRest) {
		this.nombreRest = nombreRest;
	}

	public String getNombreProd() {
		return nombreProd;
	}

	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}

	public int getCantAct() {
		return cantAct;
	}

	public void setCantAct(int cantAct) {
		this.cantAct = cantAct;
	}

	public int getMaximo() {
		return maximo;
	}

	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}

	private String nombreRest;
	
	private String nombreProd;
	
	private int cantAct;
	
	private int maximo;
	
	public ProductosBodega(String prest, String pprod, int pcant, int pmax)
	{
		nombreRest=prest;
		nombreProd=pprod;
		cantAct=pcant;
		maximo=pmax;
	}
	
}
