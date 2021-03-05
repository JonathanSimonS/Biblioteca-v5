/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

/**
 * @author lemon
 *
 */
public interface IPrestamos {
	public List<Prestamo> get();

	public List<Prestamo> get(Alumno alumno);

	/**
	 * Crea un array de tipo Prestamo de libros y devuelve una copia
	 * 
	 * @param: libro
	 * @return: array de libros
	 **/
	public List<Prestamo> get(Libro libro);

	/**
	 * Crea un array de tipo Prestamo de fechas y devuelve una copia
	 * 
	 * @param: fecha
	 * @return: array de fechas
	 **/
	public List<Prestamo> get(LocalDate fechaPrestamo);

	/**
	 * Compara si dos fechas son del mismo mes
	 * 
	 * @param: dos fechas
	 * @return: true si las dos fechas son del mismo mes, false si no lo son
	 **/
	
	public int getTamano();
	
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha);

	/**
	 * Guardará un prestamo efectuado
	 * 
	 * @param: préstamo
	 * @throws OperationNotSupportedException
	 **/
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException;

	/**
	 * Devolverá libro por alumno
	 * 
	 * @param: préstamo y fecha de devolución
	 * @throws OperationNotSupportedException
	 **/
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException;

	public Prestamo buscar(Prestamo prestamo);

	public void borrar(Prestamo prestamo) throws OperationNotSupportedException;
}
