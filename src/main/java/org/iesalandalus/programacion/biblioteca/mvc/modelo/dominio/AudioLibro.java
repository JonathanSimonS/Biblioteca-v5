/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * @author lemon
 * 
 */
public class AudioLibro extends Libro {

	// Atributos y constantes
	private static final int MINUTOS_PARA_RECOMPENSA = 15;
	private static final float PUNTOS_PREMIO = 0.25f;
	private int duracion;

	// M.Constructor
	public AudioLibro(String titulo, String autor, int duracion) {
		super(titulo, autor);
		if (duracion <= 0) {
			throw new IllegalArgumentException("ERROR: La duración debe ser mayor que cero.");
		}
		setDuracion(duracion);
	}

	public AudioLibro(AudioLibro e) {
		super(e);
		if (e == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		setDuracion(e.getDuracion());
	}

	// Métodos
	/**
	 * @return duracion
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * @param duracion a establecer
	 */
	public void setDuracion(int duracion) {
		if (duracion <= 0) {
			throw new IllegalArgumentException("ERROR: La duración no es correcta.");
		}
		this.duracion = duracion;
	}

	public float getPuntos() {
		float puntos = (float) (Math.ceil(duracion / MINUTOS_PARA_RECOMPENSA + 1) * PUNTOS_PREMIO);
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
		return String.format("título=%s, autor=%s, duración=%s", titulo, autor, duracion);
	}
	

}
