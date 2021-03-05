/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;

/**
 * @author Jonathan Simón
 * @version v2
 *
 */
public enum FactoriaFuenteDatos {

	FICHEROS {
		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	};
	
	FactoriaFuenteDatos() {
		
	}
	
	public abstract IFuenteDatos crear();
	
}
