/**
 * 
 */
package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.VistaIUGPestanas;
import org.iesalandalus.programacion.biblioteca.mvc.vista.texto.VistaTexto;

/**
 * @author: Jonathan Simón Sánchez
 * @version v2
 **/
public enum FactoriaVista {

	TEXTO {
	@Override
		public IVista crear() {
			return new VistaTexto();
		}
	},
	IUGPESTANAS {
	@Override
		public IVista crear() {
			return new VistaIUGPestanas();
		}
	};

	FactoriaVista() {

	}

	public abstract IVista crear();

}
	
