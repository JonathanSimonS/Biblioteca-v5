package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;

/**
 * @author: Jonathan Simón Sánchez
 * 
 **/
public class Alumnos implements IAlumnos {
	// Creo el arrayList que contendrá lista de alumnos
	private ArrayList<Alumno> coleccionAlumnos;

	// M.Constructor
	public Alumnos() {
		coleccionAlumnos = new ArrayList<Alumno>();
	}

	// Métodos

	public List<Alumno> get() {
		List<Alumno> alumnosOrdenados = copiaProfundaAlumnos();
		alumnosOrdenados.sort(Comparator.comparing(Alumno::getNombre));
		return alumnosOrdenados;
	}

	private List<Alumno> copiaProfundaAlumnos() {
		ArrayList<Alumno> copiaAlumnos = new ArrayList<>();
		for (int i = 0; i < getTamano(); i++) {
			Alumno alumno = coleccionAlumnos.get(i); // Con el método .get devuelvo al objeto en esa posicion
			copiaAlumnos.add(new Alumno(alumno)); // Voy copiando y almacenando los alumnos
		}
		return copiaAlumnos;
	}

	public int getTamano() {
		return coleccionAlumnos.size();
	}

	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		if (!coleccionAlumnos.contains(alumno)) { // Nos devuelve true si el objeto no está en la colección
			coleccionAlumnos.add(new Alumno(alumno));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese correo.");
		}
	}

	public Alumno buscar(Alumno alumno) {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		if (coleccionAlumnos.contains(alumno)) { // Nos devuelve true si el objeto está en la colección
			int indice = coleccionAlumnos.indexOf(alumno);  //Variable con el indice del alumno pasado
			return new Alumno(coleccionAlumnos.get(indice));  //Devolvemos al alumno en esa posición
		} else {
			return null;
		}
	}

	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		
		if (coleccionAlumnos.contains(alumno)) { // Nos devuelve true si el objeto está en la colección
			coleccionAlumnos.remove(alumno);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese correo.");
		}

	}

}
