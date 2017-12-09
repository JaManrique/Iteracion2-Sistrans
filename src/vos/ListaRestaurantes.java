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

/**
 * Clase que representa una arreglo de Restaurantes
 * @author Juan
 */
public class ListaRestaurantes {
	
	/**
	 * List con los videos
	 */
	@JsonProperty(value="restaurantes")
	private List<Restaurante> restaurantes;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaRestaurantes( @JsonProperty(value="restaurantes")List<Restaurante> videos){
		this.restaurantes = videos;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setRestaurantes(List<Restaurante> videos) {
		this.restaurantes = videos;
	}
	
}
