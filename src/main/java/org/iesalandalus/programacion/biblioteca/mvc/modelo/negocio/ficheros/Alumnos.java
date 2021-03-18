package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;

/**
 * @author: Jonathan Simón Sánchez
 * @version: 3
 **/
public class Alumnos implements IAlumnos {
	// Creo el arrayList que contendrá lista de alumnos
	private ArrayList<Alumno> coleccionAlumnos;

	private static final String NOMBRE_FICHERO_ALUMNOS = "datos/alumnos.dat";

	// M.Constructor
	public Alumnos() {
		coleccionAlumnos = new ArrayList<Alumno>();
	}

	// Métodos
	@Override
	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroAlumnos = new File(NOMBRE_FICHERO_ALUMNOS);
		// FileInputStream lee el flujo de byte del fichero
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAlumnos))) {
			Alumno alumno = null;
			do {
				alumno = (Alumno) entrada.readObject();
				insertar(alumno);
			} while (alumno != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar el alumno que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fichero de alumnos.");
		} catch (EOFException e) {
			System.out.println("Fichero alumnos leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void terminar() {
		escribir();
	}

	private void escribir() {
		File ficheroAlumnos = new File(NOMBRE_FICHERO_ALUMNOS);
		// FileOutputStream recibe el objeto que representa el flujo de datos para el
		// dispositivo de salida
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAlumnos))) {
			for (Alumno alumno : coleccionAlumnos)
				salida.writeObject(alumno);
			System.out.println("Fichero alumnos escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de alumnos.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}

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
			int indice = coleccionAlumnos.indexOf(alumno); // Variable con el indice del alumno pasado
			return new Alumno(coleccionAlumnos.get(indice)); // Devolvemos al alumno en esa posición
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

	/*
	 * EJEMPLO LIBRO import java.io.IOException ; import FileNotFoundException ;
	 * 
	 * // ...
	 * 
	 * public static void main(String[] args) { try { // Se ejecuta algo que puede
	 * producir una excepci�n } catch (FileNotFoundException e) { // manejo de una
	 * excepci�n por no encontrar un archivo } catch (IOException e) { // manejo de
	 * una excepci�n de entrada/salida } catch (Exception e) { // manejo de una
	 * excepci�n cualquiera } finally { // c�digo a ejecutar haya o no excepci�n } }
	 */

}
