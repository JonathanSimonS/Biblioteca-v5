package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;


/**
 * @author Jonathan Simón
 * @version v2
 *
 */
public enum Opcion {

	INSERTAR_ALUMNO(" Insertar alumno") {
		@Override
		public void ejecutar() {
			vista.insertarAlumno();
		}
	},
	BUSCAR_ALUMNO(" Buscar alumno") {
		@Override
		public void ejecutar() {
			vista.buscarAlumno();
		}
	},
	BORRAR_ALUMNO(" Borrar alumno") {
		@Override
		public void ejecutar() {
			vista.borrarAlumno();
		}
	},
	LISTAR_ALUMNOS(" Listar alumnos") {
		@Override
		public void ejecutar() {
			vista.listarAlumnos();
		}
	},
	INSERTAR_LIBRO(" Insertar libro") {
		@Override
		public void ejecutar() {
			vista.insertarLibro();
		}
	},
	BUSCAR_LIBRO(" Buscar libro") {
		@Override
		public void ejecutar() {
			vista.buscarLibro();
		}
	},
	BORRAR_LIBRO(" Borrar libro") {
		@Override
		public void ejecutar() {
 			vista.borrarLibro();
		}
	},
	LISTAR_LIBROS(" Listar libros") {
		@Override
		public void ejecutar() {
 			vista.listarLibros();
		}
	},
	PRESTAR_LIBRO(" Prestar libro") {
		@Override
		public void ejecutar() {
 			vista.prestarLibro();
		}
	},
	DEVOLVER_LIBRO(" Devolver libro") {
		@Override
		public void ejecutar() {
 			vista.devolverLibro();

		}
	},
	BUSCAR_PRESTAMO("Buscar préstamo") {
		@Override
		public void ejecutar() {
 			vista.buscarPrestamo();
		}
	},
	BORRAR_PRESTAMO("Borrar préstamo") {
		@Override
		public void ejecutar() {
 			vista.borrarPrestamo();
		}
	},
	LISTAR_PRESTAMOS("Listar préstamo") {
		@Override
		public void ejecutar() {
 			vista.listarPrestamos();
		}
	},
	LISTAR_PRESTAMOS_ALUMNO("Listar préstamo por alumno") {
		@Override
		public void ejecutar() {
 			vista.listarPrestamosAlumno();
		}
	},
	LISTAR_PRESTAMOS_LIBRO("Listar préstamo por libro") {
		@Override
		public void ejecutar() {
 			vista.listarPrestamosLibro();
		}
	},
	LISTAR_PRESTAMOS_FECHA("Listar préstamo por fecha") {
		@Override
		public void ejecutar() {
 			vista.listarPrestamosFecha();
		}
	},
	MOSTRAR_ESTADISTICA_MENSUAL_POR_CURSO("Mostrar estadística mensual por curso") {
		@Override
		public void ejecutar() {
 			vista.mostrarEstadisticaMensualPorCurso();
		}
	},
	SALIR("Salir del menú") {
		@Override
		public void ejecutar() {
 			vista.terminar();
		}
	};

	private static VistaTexto vista;
	private String mensaje;

	// Métodos
	private Opcion(String mensaje) {
		this.mensaje = mensaje;
	}

	public abstract void ejecutar();

	protected static void setVista(VistaTexto vista) {
		Opcion.vista = vista;
	}

	/**
	 * Devuelve la opción representada por la posición pasada
	 * 
	 * @return: Opcion correspondiente al número ordinal
	 * @param: número ordinal
	 **/
	public static Opcion getOpcionSegunOrdinal(int numero) {
		if (esOrdinalValido(numero)) {
			return values()[numero]; // El método values() devuelve todos los valores dentro del enum
		} else {
			throw new IllegalArgumentException("El ordinal no es válido.");
		}
	}

	/**
	 * Nos dirá si un número pasado por parámetro es válido para representar una
	 * opcion
	 * 
	 * @return: true, si el número pasado es un ordinal válido
	 * @param: número
	 **/
	public static boolean esOrdinalValido(int numero) {
		return (numero >= 0 && numero <= values().length - 1);
	}

	@Override
	public String toString() {
		return ordinal() + ".-" + mensaje; // Muestro el número con el método ordinal()
	}

}
