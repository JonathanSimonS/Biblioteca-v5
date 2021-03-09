package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;

/**
 * @author: Jonathan Simón Sánchez
 * @version: 3
 **/
public class Libros implements ILibros{

	// Atributos
	private ArrayList<Libro> coleccionLibros;
	
	private static final String NOMBRE_FICHERO_LIBROS = "datos/libros.dat";

	// M.Constructor
	public Libros() {
		coleccionLibros = new ArrayList<Libro>();
	}

	// Métodos
	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroLibros = new File(NOMBRE_FICHERO_LIBROS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroLibros))) {
			Libro libro = null;
			do {
				libro = (Libro) entrada.readObject();
				insertar(libro);
			} while (libro != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fichero de libros.");
		} catch (EOFException e) {
			System.out.println("Fichero libros leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void terminar() {
		escribir();
	}

	private void escribir() {
		File ficheroLibros = new File(NOMBRE_FICHERO_LIBROS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroLibros))) {
			for (Libro libro : coleccionLibros)
				salida.writeObject(libro);
			System.out.println("Fichero libros escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de libros.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}	
	
	
	public List<Libro> get() {
		List<Libro> librosOrdenados = copiaProfundaLibros();
		librosOrdenados.sort(Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor));
		return librosOrdenados;
	}

	private List<Libro> copiaProfundaLibros() {
		
		ArrayList<Libro> copiaLibros = new ArrayList<>();
		for (int i = 0; i < getTamano(); i++) {
			Libro libro = coleccionLibros.get(i); // Con el método .get guardo al objeto en esa posicion
			if(libro instanceof LibroEscrito) {
				copiaLibros.add(new LibroEscrito((LibroEscrito)libro));
			} else {
				copiaLibros.add(new AudioLibro((AudioLibro)libro));
			}
		}
		return copiaLibros;
	}

	public int getTamano() {
		return coleccionLibros.size();
	}

	public void insertar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");
		}
		if (coleccionLibros.contains(libro)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese título y autor.");
		} else {
			if(libro instanceof LibroEscrito) {
				coleccionLibros.add(new LibroEscrito((LibroEscrito)libro));
			} else {
				coleccionLibros.add(new AudioLibro((AudioLibro)libro));
			}
		}
	}

	public Libro buscar(Libro libro) {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");
		}
		if (!coleccionLibros.contains(libro)) {
			return null;
		} else {
			if(libro instanceof LibroEscrito) {
				int indice = coleccionLibros.indexOf(libro);
				return new LibroEscrito((LibroEscrito) coleccionLibros.get(indice));
			} else {
				int indice = coleccionLibros.indexOf(libro);
				return new AudioLibro((AudioLibro) coleccionLibros.get(indice));			}
		}
	}

	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");
		}
		if (coleccionLibros.contains(libro)) {
			coleccionLibros.remove(libro);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún libro con ese título y autor.");
		}
	}

}
