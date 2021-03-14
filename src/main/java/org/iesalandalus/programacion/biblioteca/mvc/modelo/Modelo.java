package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;


/**
 * En esta clase, cada método hará una llamada al método homólogo del objeto
 * adecuado.
 * 
 * @author Jonathan Simón
 * @version v2
 *
 */
public class Modelo implements IModelo {

	// Atributos y constantes
	private IAlumnos alumnos;
	private ILibros libros;
	private IPrestamos prestamos;

	// M.Constructor
	public Modelo(IFuenteDatos iFuenteDatos) {
		alumnos=iFuenteDatos.crearAlumnos();
		libros=iFuenteDatos.crearLibros();
		prestamos=iFuenteDatos.crearPrestamos();
	}

	// Métodos
	public void comenzar()  {
		alumnos.comenzar();
		libros.comenzar();
		prestamos.comenzar();
	}
	public void terminar()  {
		alumnos.terminar();
		libros.terminar();
		prestamos.terminar();
	}
	
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.insertar(alumno);
	}

	@Override
	public void insertar(Libro libro) throws OperationNotSupportedException {
		libros.insertar(libro);
	}

	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		if (alumnos.buscar(prestamo.getAlumno()) == null) {
			throw new OperationNotSupportedException("ERROR: No existe el alumno del préstamo.");
		} 
		if (libros.buscar(prestamo.getLibro()) == null) {
			throw new OperationNotSupportedException("ERROR: No existe el libro del préstamo.");
		} else {
		prestamos.prestar(prestamo);
		}
	}

	@Override
	public void devolver(Prestamo prestamo, LocalDate fecha) throws OperationNotSupportedException {

		if(prestamos.buscar(prestamo)==null) {
			throw new OperationNotSupportedException("ERROR: No se puede devolver un préstamo no prestado.");
		}
		
		prestamos.devolver(new Prestamo(prestamo), fecha);
	}

	@Override
	public Alumno buscar(Alumno alumno) {
		return alumnos.buscar(alumno);
	}

	@Override
	public Libro buscar(Libro libro) {
		return libros.buscar(libro);

	}

	@Override
	public Prestamo buscar(Prestamo prestamo) {
		return prestamos.buscar(prestamo);
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.buscar(alumno);
		List<Prestamo> alumnosParaBorrar = prestamos.get(alumno);
		for (Prestamo prestamo : alumnosParaBorrar) {
			prestamos.borrar(prestamo);
		}
		alumnos.borrar(alumno);
	}

	@Override
	public void borrar(Libro libro) throws OperationNotSupportedException {
		libros.buscar(libro);
		List<Prestamo> librosParaBorrar = prestamos.get(libro);
		for (Prestamo prestamo : librosParaBorrar) {
			prestamos.borrar(prestamo);
		}
		libros.borrar(libro);
	}

	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		prestamos.borrar(prestamo);
	}

	@Override
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}

	@Override
	public List<Libro> getLibros() {
		return libros.get();
	}

	@Override
	public List<Prestamo> getPrestamos() {
		return prestamos.get();
	}

	@Override
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return prestamos.get(alumno);
	}

	@Override
	public List<Prestamo> getPrestamos(Libro libro) {
		return prestamos.get(libro);
	}

	@Override
	public List<Prestamo> getPrestamos(LocalDate fecha) {
		return prestamos.get(fecha);
	}
	
	@Override
	public Map<Curso,Integer> getEstadisticaMensualPorCurso(LocalDate fecha){
		return prestamos.getEstadisticaMensualPorCurso(fecha);
	}

}
