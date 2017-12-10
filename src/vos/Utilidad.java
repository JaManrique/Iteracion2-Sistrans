/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

import oracle.net.aso.d;

/**
 * Clase que representa una arreglo de Restaurantes
 * @author Juan
 */
public class Utilidad {
	
	
	@JsonProperty(value="nombre")
	private String nombre;
	/**
	 * List con los videos
	 */
	@JsonProperty(value="detalleProductos")
	private List<String> detalleProductos;
	
	@JsonProperty(value="utilidad")
	private Double utilidad;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public Utilidad( @JsonProperty(value="nombre")String pnom,@JsonProperty(value="detalleProductos")List<String> videos,
			@JsonProperty(value="utilidad")Double util){
		detalleProductos = videos;
		nombre=pnom;
		utilidad=util;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<String> getDetalleProductos() {
		return detalleProductos;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setProductos(List<String> videos) {
		detalleProductos = videos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(Double utilidad) {
		this.utilidad = utilidad;
	}
	
}
