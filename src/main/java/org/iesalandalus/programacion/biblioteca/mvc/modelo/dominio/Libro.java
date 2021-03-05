package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * @author: Jonathan Simón Sánchez
 * 
 **/
public abstract class Libro {

	// Atributos y constantes
	protected String titulo;
	protected String autor;

	// Constructores

	public Libro(String titulo, String autor) {
		setTitulo(titulo);
		setAutor(autor);
	}

	public Libro(Libro e) {
		if (e == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		setTitulo(e.getTitulo());
		setAutor(e.getAutor());
	}

	// Métodos

	/**
	 * Va a devolver un libro correcto y se usará para búsquedas y borrados
	 * 
	 * @param: título y autor return: un libro ficticio correcto de la clase Libro
	 **/
	public static Libro getLibroFicticio(String titulo, String autor) {
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (autor.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
 		return new LibroEscrito("Cien años de soledad", "Gabriel García Márquez", 25);
	}

	public String getTitulo() {
		return titulo;
	}

	protected void setTitulo(String titulo) {
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	protected void setAutor(String autor) {
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (autor.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		this.autor = autor;
	}

	public abstract float getPuntos();

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
		return String.format("título=%s, autor=%s ", titulo, autor);
	}

}
