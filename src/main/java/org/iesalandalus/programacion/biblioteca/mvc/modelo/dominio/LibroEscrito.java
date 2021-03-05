/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * @author lemon
 *
 */
public class LibroEscrito extends Libro {
	
	//Atributos y constantes
	private static final int PAGINAS_PARA_RECOMPENSA = 25;
	private static final float PUNTOS_PREMIO = 0.5f;
	private int numPaginas;

	// Constructores
	public LibroEscrito(String titulo, String autor, int numPaginas) {
		super (titulo, autor);
		setNumPaginas(numPaginas);
	}

	public LibroEscrito(LibroEscrito e) {
		super (e);
		setNumPaginas(e.getNumPaginas());
	}

	// Métodos

	public int getNumPaginas() {
		return numPaginas;
	}

	private void setNumPaginas(int numPaginas) {
		if (numPaginas <= 0) {
			throw new IllegalArgumentException("ERROR: El número de páginas debe ser mayor que cero.");
		}
		this.numPaginas = numPaginas;
	}

	/**
	 * Va a devolver el número de puntos que se obtienen de un libro
	 * 
	 * Obtendremos 0,5 puntos por cada 25 páginas del libro: de 0-24 obtenemos 0,5
	 * puntos, de 25-49 obtenemos 1 puntos, de 50-74 obtenemos 1,5 puntos, etc.
	 * 
	 * @return: puntos
	 **/
	public float getPuntos() {
		float puntos = (float) (Math.ceil(numPaginas / PAGINAS_PARA_RECOMPENSA + 1) * PUNTOS_PREMIO);
		return puntos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!(obj instanceof Libro)) {
			return false;
		}
		Libro other = (Libro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("título=%s, autor=%s, número de páginas=%s", titulo, autor, numPaginas);
	}
}
