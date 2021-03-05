package org.iesalandalus.programacion.biblioteca;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.Controlador;
import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.IModelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.Modelo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

/**
 * @author Jonathan Simón
 * @version v2
 *
 **/
public class MainApp {

	public static void main(String[] args) {
		IFuenteDatos iFuenteDatos = FactoriaFuenteDatos.FICHEROS.crear();
		IModelo modelo = new Modelo(iFuenteDatos);
		IVista vista = FactoriaVista.TEXTO.crear();
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}
}
