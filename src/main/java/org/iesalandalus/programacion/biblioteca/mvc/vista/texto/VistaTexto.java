package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.biblioteca.mvc.vista.*;
import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.*;

/**
 * @author: Jonathan Simón Sánchez
 * @version v2
 **/
public class VistaTexto implements IVista {

	// Atributos
	private IControlador controlador;

	// M.Constructor
	public VistaTexto() {
		Opcion.setVista(this);
	}

	@Override
	public void setControlador(IControlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		int opcion;
		do {
			Consola.mostrarMenu();
			opcion = Consola.elegirOpcion();
			Opcion opcionElegida = Opcion.getOpcionSegunOrdinal(opcion);
			opcionElegida.ejecutar();
		} while (opcion != Opcion.SALIR.ordinal());
	}

	@Override
	public void terminar() {
		controlador.terminar();
	}

	// Métodos Alumno
	@Override
	public void insertarAlumno() {
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno insertado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void buscarAlumno() {
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe ese alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void borrarAlumno() {
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void listarAlumnos() {
		List<Alumno> alumnos = controlador.getAlumnos();
		if (alumnos != null) {
			for (Alumno alumno : alumnos) {
				if (alumno != null)
					System.out.println(alumno);
			}
		} else {
			System.out.println("No hay alumnos para mostrar.");
		}
	}

	// Métodos Libro
	@Override
	public void insertarLibro() {
		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("Libro insertado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void buscarLibro() {
		Libro libro;
		try {
			libro = controlador.buscar(Consola.leerLibroFicticio());
			String mensaje = (libro != null) ? libro.toString() : "No existe ese libro.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void borrarLibro() {
		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("Libro borrado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void listarLibros() {
		List<Libro> libros = controlador.getLibros();
		if (libros != null) {
			for (Libro libro : libros) {
				if (libro != null)
					System.out.println(libro);
			}
		} else {
			System.out.println("No hay libros para mostrar.");
		}
	}

	@Override
	public void prestarLibro() {
		try {
			controlador.prestar(Consola.leerPrestamo());
			System.out.println("Libro Prestado.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void devolverLibro() {
		try {
			controlador.devolver(Consola.leerPrestamo(), LocalDate.now());
			System.out.println("Libro devuelto.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// Métodos Prestamo

	@Override
	public void buscarPrestamo() {
		Prestamo prestamo;
		try {
			prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo != null) ? prestamo.toString() : "No existe ese prestamo.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void borrarPrestamo() {
		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Préstamo borrado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void listarPrestamos() {
		List<Prestamo> prestamos = controlador.getPrestamos();
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null)
					System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay prestamos para mostrar.");
		}
	}

	@Override
	public void listarPrestamosAlumno() {
		List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerAlumnoFicticio());
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null)
					System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay prestamos para mostrar.");
		}
	}

	@Override
	public void listarPrestamosLibro() {
		List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerLibroFicticio());
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null)
					System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay prestamos para mostrar.");
		}
	}

	@Override
	public void listarPrestamosFecha() {
		List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerPrestamo().getFechaPrestamo());
		if (prestamos != null) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null)
					System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay prestamos para mostrar.");
		}
	}

	@Override
	public void mostrarEstadisticaMensualPorCurso() {
		Prestamo prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
		LocalDate fecha = prestamo.getFechaPrestamo();
		String cadena = "";
		fecha = LocalDate.parse(cadena, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		controlador.getEstadisticaMensualPorCurso(Consola.leerFecha(cadena));
	}

}
