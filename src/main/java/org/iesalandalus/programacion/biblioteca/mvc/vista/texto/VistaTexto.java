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
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		Consola.mostrarCabecera("Gestión Biblioteca");
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
	public void insertarAlumno() {
		Consola.mostrarCabecera("Insertar alumno");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno insertado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlumno() {
		Consola.mostrarCabecera("Buscar alumno");
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe ese alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlumno() {
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlumnos() {
		Consola.mostrarCabecera("Listar alumnos");

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
	public void insertarLibro() {
		Consola.mostrarCabecera("Insertar libro");

		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("Libro insertado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarLibro() {
		Consola.mostrarCabecera("Buscar libro");

		Libro libro;
		try {
			libro = controlador.buscar(Consola.leerLibroFicticio());
			String mensaje = (libro != null) ? libro.toString() : "No existe ese libro.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarLibro() {
		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("Libro borrado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarLibros() {
		Consola.mostrarCabecera("Listar libros");

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

	public void prestarLibro() {
		Consola.mostrarCabecera("Prestar libro");

		try {
			controlador.prestar(Consola.leerPrestamo());
			System.out.println("Libro Prestado.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}


	public void devolverLibro() {
		Consola.mostrarCabecera("Devolver libro");

		try {
			controlador.devolver(Consola.leerPrestamo(), LocalDate.now());
			System.out.println("Libro devuelto.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// Métodos Prestamo


	public void buscarPrestamo() {
		Consola.mostrarCabecera("Buscar préstamo");

		Prestamo prestamo;
		try {
			prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo != null) ? prestamo.toString() : "No existe ese prestamo.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}


	public void borrarPrestamo() {
		Consola.mostrarCabecera("Borrar préstamo");

		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Préstamo borrado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}


	public void listarPrestamos() {
		Consola.mostrarCabecera("Listar préstamos");

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


	public void listarPrestamosAlumno() {
		Consola.mostrarCabecera("Listar préstamos por alumno");

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

	public void listarPrestamosLibro() {
		Consola.mostrarCabecera("Listar préstamos por libro");

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

	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("Listar préstamos por fecha");

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

	public void mostrarEstadisticaMensualPorCurso() {
		Consola.mostrarCabecera("Mostrar estadística mensual por curso");

		Prestamo prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
		LocalDate fecha = prestamo.getFechaPrestamo();
		String cadena = "";
		fecha = LocalDate.parse(cadena, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		controlador.getEstadisticaMensualPorCurso(Consola.leerFecha(cadena));
	}

}
