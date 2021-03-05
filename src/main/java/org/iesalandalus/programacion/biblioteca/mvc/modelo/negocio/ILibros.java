/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;

/**
 * @author lemon
 *
 */
public interface ILibros {
	
	public List<Libro> get();

	public int getTamano();

	public void insertar(Libro libro) throws OperationNotSupportedException;

	public Libro buscar(Libro libro);

	public void borrar(Libro libro) throws OperationNotSupportedException;
}
