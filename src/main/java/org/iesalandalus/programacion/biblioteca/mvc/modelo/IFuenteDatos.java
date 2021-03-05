/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

/**
 * @author Jonathan Simón
 *
 */
public interface IFuenteDatos {
	 public IAlumnos crearAlumnos();
	 public ILibros crearLibros(); 
	 public IPrestamos crearPrestamos(); 
}
