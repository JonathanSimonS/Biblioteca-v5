package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

/**
 * @author: Jonathan Simón Sánchez
 * @version: 3
 **/
public class Prestamos implements IPrestamos{

	private ArrayList<Prestamo> coleccionPrestamos;
	private static final String NOMBRE_FICHERO_PRESTAMOS = "datos/prestamos.dat";

	// M. Constructores
	public Prestamos() {
		coleccionPrestamos = new ArrayList<Prestamo>();
	}

	// Métodos
	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroLibros = new File(NOMBRE_FICHERO_PRESTAMOS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroLibros))) {
			Prestamo prestamo = null;
			do {
				prestamo = (Prestamo) entrada.readObject();
				prestar(prestamo);
			} while (prestamo != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fichero de prestamos.");
		} catch (EOFException e) {
			System.out.println("Fichero prestamos leído satisfactoriamente.");
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
		File ficheroPrestamos = new File(NOMBRE_FICHERO_PRESTAMOS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroPrestamos))) {
			for (Prestamo prestamoo : coleccionPrestamos)
				salida.writeObject(prestamoo);
			System.out.println("Fichero prestamos escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de prestamos.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}
	
	
	public List<Prestamo> get() {

		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getCorreo);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);

		List<Prestamo> prestamosOrdenados = copiaProfundaPrestamos();
		prestamosOrdenados.sort(comparadorPrestamo);
		return prestamosOrdenados;
	}

	private List<Prestamo> copiaProfundaPrestamos() {

		// Prestamo[] copiaPrestamos = new Prestamo[capacidad];
		ArrayList<Prestamo> copiaPrestamos = new ArrayList<>();

		for (int i = 0; i < getTamano(); i++) {
			Prestamo prestamo = coleccionPrestamos.get(i);
			copiaPrestamos.add(new Prestamo(prestamo));
		}
		return copiaPrestamos;
	}

	public List<Prestamo> get(Alumno alumno) {

		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}

		ArrayList<Prestamo> prestamosPorAlumnos = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos) { // Bucle foreach que recorre la colección de Préstamos
			if (prestamo.getAlumno().equals(alumno)) { // El objeto prestamo será el que asigne si coindiden los laumnos
				prestamosPorAlumnos.add(new Prestamo(prestamo));
			}
		}

		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getLibro, comparadorLibro);

		prestamosPorAlumnos.sort(comparadorPrestamo);
		return prestamosPorAlumnos;
	}

	/**
	 * Crea un array de tipo Prestamo de libros y devuelve una copia
	 * 
	 * @param: libro
	 * @return: array de libros
	 **/
	public List<Prestamo> get(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		ArrayList<Prestamo> prestamosPorLibros = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos) { // Bucle foreach que recorre la colección de Préstamos
			if (prestamo.getLibro().equals(libro)) { // El objeto prestamo será el que asigne si coindiden los libros
				prestamosPorLibros.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getCorreo);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno);

		prestamosPorLibros.sort(comparadorPrestamo);
		return prestamosPorLibros;
	}

	/**
	 * Crea un array de tipo Prestamo de fechas y devuelve una copia
	 * 
	 * @param: fecha
	 * @return: array de fechas
	 **/
	public List<Prestamo> get(LocalDate fechaPrestamo) {
		if (fechaPrestamo == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		
		ArrayList<Prestamo> prestamosPorFecha = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos) {
			if (mismoMes(fechaPrestamo, prestamo.getFechaPrestamo())) {
				prestamosPorFecha.add(prestamo);
			}
		}
		
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getCorreo);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);

		prestamosPorFecha.sort(comparadorPrestamo);
		return prestamosPorFecha;
	}
	

	/**
	 * Compara si dos fechas son del mismo mes
	 * 
	 * @param: dos fechas
	 * @return: true si las dos fechas son del mismo mes, false si no lo son
	 **/
	private boolean mismoMes(LocalDate fecha1, LocalDate fecha2) {
		return (fecha1.getMonth().equals(fecha2.getMonth()) && (fecha1.getYear() == fecha2.getYear()));
	} // Comtemplo la posibilidad de que el año también sea diferente

	public int getTamano() {
		return coleccionPrestamos.size();
	}
	
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha){
		Map<Curso, Integer> estadisticaMensual = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);		
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno=prestamo.getAlumno().getCurso();
			estadisticaMensual.put(cursoAlumno, estadisticaMensual.get(cursoAlumno) + prestamo.getPuntos());
		}
		return estadisticaMensual;
	}
	private Map<Curso, Integer> inicializarEstadisticas() {	
		Map<Curso, Integer> estadisticaMensual = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			estadisticaMensual.put(curso, 0);
			}		
		return estadisticaMensual;
	}
	

	/**
	 * Guardará un prestamo efectuado
	 * 
	 * @param: préstamo
	 * @throws OperationNotSupportedException
	 **/
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		if (coleccionPrestamos.contains(prestamo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");
		} 	
		else {
			coleccionPrestamos.add(new Prestamo(prestamo));
		}

	}

	/**
	 * Devolverá libro por alumno
	 * 
	 * @param: préstamo y fecha de devolución
	 * @throws OperationNotSupportedException
	 **/
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un préstamo nulo.");
		}
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: No se puede devolver en una fecha nula.");
		}

		if (!coleccionPrestamos.contains(prestamo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			int indice = coleccionPrestamos.indexOf(prestamo);
			coleccionPrestamos.get(indice).devolver(fechaDevolucion);
		}
	}

	public Prestamo buscar(Prestamo prestamo) {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: Debe selecionar un préstamo.");
		}
		if (coleccionPrestamos.contains(prestamo)) {
			int indice = coleccionPrestamos.indexOf(prestamo);
			return new Prestamo(coleccionPrestamos.get(indice));
		} else {
			return null;
		}

	}

	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");
		}
		if (coleccionPrestamos.contains(prestamo)) {
			coleccionPrestamos.remove(prestamo);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		}

	}

}
