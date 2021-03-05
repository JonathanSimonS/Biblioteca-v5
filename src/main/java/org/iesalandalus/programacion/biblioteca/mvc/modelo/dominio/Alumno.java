package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * @author: Jonathan Simón Sánchez
 * 
 **/
public class Alumno {

	// Atributos y constantes
	
	private static final String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúñ\\s]*";
	private static final String ER_CORREO = "\\w+(?:\\.\\w+)*@{1}\\w+\\.\\w{2,5}";
	private String nombre;
	private String correo; 
	private Curso curso;

	// M. Constructores
	
	public Alumno(String nombre, String correo, Curso curso) {
		setNombre(nombre);
		setCorreo(correo);
		setCurso(curso);
	}

	public Alumno(Alumno e) {
		if (e == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(e.getNombre());
		setCorreo(e.getCorreo());
		setCurso(e.getCurso());
	}

	// Métodos

	/**
	 * Va a devolver un alumno correcto y se usará para búsquedas y borrados
	 * 
	 * @param: correo return: un alumno ficticio correcto de la clase Alumno
	 **/
	public static Alumno getAlumnoFicticio(String correo) {
		Alumno alumnoFicticio = new Alumno("Paco Calavera", correo, Curso.PRIMERO);
		return alumnoFicticio;
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {

		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre no puede estar vacío.");
		}
		this.nombre = formateaNombre(nombre);
	}

	/**
	 * Va a recibir un nombre por parámetro, y devolverá dicho nombre formateado
	 * 
	 * Solo habrá un espacio entre cada palabra, y la primera letra de cada palabra
	 * será mayúscula
	 * 
	 * @param: nombre return: nombre formateado
	 **/
	private String formateaNombre(String nombre) { 

		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		
		String nombreFormateado = nombre.trim();
		nombreFormateado = nombreFormateado.toLowerCase();

		for (int i = 0; i < nombre.length() - 1; i++) {
			nombreFormateado = nombreFormateado.replace("  ", " ");
		}

		char[] caracteres = nombreFormateado.toCharArray(); // Creación de array de caracteres para la cadena nombre
		caracteres[0] = Character.toUpperCase(caracteres[0]);

		for (int i = 0; i < nombreFormateado.length(); i++)
			if (caracteres[i] == ' ')
				caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]); // Pasar a mayúscula si el caracter
																				// anterior es un espacio
		return new String(caracteres);
	}

	/**
	 * Va a devolver las iniciales en mayúscula de una cadena de caracteres
	 * 
	 * @return: iniciales
	 **/
	public String getIniciales() { 

		String cadena = "";
		
		char[] caracteres = formateaNombre(nombre).toCharArray(); // Creación de array de caracteres para la cadena nombre
		caracteres[0] = Character.toUpperCase(caracteres[0]);
		
		cadena += caracteres[0];
		for (int i = 0; i < nombre.length() -1 ; i++)
			if (caracteres[i] == ' ')
				cadena += caracteres[i + 1] ;
		cadena=cadena.trim();
		return cadena;
	}

	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		if (curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}
		this.curso = curso;
	}

	// Métodos hasCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		if (!(obj instanceof Alumno)) {
			return false;
		}
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;

		return true;
	}

	// Método toString
	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, curso=%s", formateaNombre(nombre), getIniciales(), correo,
				curso);
	}

}
