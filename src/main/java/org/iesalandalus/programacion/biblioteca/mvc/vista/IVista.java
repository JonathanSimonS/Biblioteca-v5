package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
/**
 * @author: Jonathan Simón Sánchez
 * @version v2
 **/
public interface IVista {

	void setControlador(IControlador controlador);

	void comenzar();

	void terminar();

}