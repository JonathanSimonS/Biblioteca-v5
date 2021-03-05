package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * @author: Jonathan Simón Sánchez
 * 
 **/
public enum Curso {

	PRIMERO("1"), SEGUNDO("2"), TERCERO("3"), CUARTO("4");

	private String cadenaAMostrar;

	private Curso(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}

	@Override
	public String toString() {
		return cadenaAMostrar + "º ESO";
	}
}
