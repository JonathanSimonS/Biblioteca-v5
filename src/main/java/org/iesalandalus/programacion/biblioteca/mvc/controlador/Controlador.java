package org.iesalandalus.programacion.biblioteca.mvc.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.IModelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.Modelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;
import org.iesalandalus.programacion.biblioteca.mvc.vista.texto.VistaTexto;

/**
 * @author Jonathan Simón
 * @version v2
 *
 */

public class Controlador implements IControlador {

	// Atributos
	IModelo modelo;
	IVista vista;

	// M.Constructor
	public Controlador(IModelo modelo, IVista vista) {
		if (modelo == null) {
			throw new IllegalArgumentException("Ha habido un error, el modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new IllegalArgumentException("Ha habido un error, la vista no puede ser nula.");
		}
		
		IFuenteDatos iFuenteDatos = new FactoriaFuenteDatosFicheros();
		this.modelo = new Modelo(iFuenteDatos );
		this.vista = vista;
		this.vista.setControlador(this);
	}

	// Métodos
	@Override
	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}

	@Override
	public void terminar() {
		modelo.terminar();
		System.out.println("Muchas gracias por su tiempo.");
	}

	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		modelo.insertar(alumno);
	}

	@Override
	public void insertar(Libro libro) throws OperationNotSupportedException {
		modelo.insertar(libro);
	}

	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		modelo.prestar(prestamo);
	}

	@Override
	public void devolver(Prestamo prestamo, LocalDate fecha) throws OperationNotSupportedException {
		modelo.devolver(prestamo, fecha);
	}

	@Override
	public Alumno buscar(Alumno alumno) {
		return modelo.buscar(alumno);
	}

	@Override
	public Libro buscar(Libro libro) {
		return modelo.buscar(libro);
	}

	@Override
	public Prestamo buscar(Prestamo prestamo) {
		return modelo.buscar(prestamo);
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		modelo.borrar(alumno);
	}

	@Override
	public void borrar(Libro libro) throws OperationNotSupportedException {
		modelo.borrar(libro);
	}

	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		modelo.borrar(prestamo);
	}

	@Override
	public List<Alumno> getAlumnos() {
		return modelo.getAlumnos();
	}

	@Override
	public List<Libro> getLibros() {
		return modelo.getLibros();
	}

	@Override
	public List<Prestamo> getPrestamos() {
		return modelo.getPrestamos();
	}

	@Override
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return modelo.getPrestamos(alumno);
	}

	@Override
	public List<Prestamo> getPrestamos(Libro libro) {
		return modelo.getPrestamos(libro);
	}

	@Override
	public List<Prestamo> getPrestamos(LocalDate fecha) {
		return modelo.getPrestamos(fecha);
	}
	@Override
	public Map<Curso,Integer> getEstadisticaMensualPorCurso(LocalDate fecha){
		return modelo.getEstadisticaMensualPorCurso(fecha);
	}
}
