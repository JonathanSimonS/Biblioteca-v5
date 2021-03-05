package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author: Jonathan Simón Sánchez
 * 
 **/

public class Prestamo {

	// Atributos y constantes
	private static final int MAX_DIAS_PRESTAMO = 20;
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucion;

	private Alumno alumno;
	private Libro libro;

	// CONSTRUCTORES
	public Prestamo(Alumno alumno, Libro libro, LocalDate fecha) {
		setAlumno(alumno);
		setLibro(libro);
		setFechaPrestamo(fecha);
	}

	public Prestamo(Prestamo e) {
		if (e == null) {
			throw new NullPointerException("ERROR: No es posible copiar un préstamo nulo.");
		}
		setAlumno(e.getAlumno());
		setLibro(e.getLibro());
		setFechaPrestamo(e.getFechaPrestamo());
		if (e.getFechaDevolucion() != null) {
			this.fechaDevolucion = e.getFechaDevolucion();
		}
	}

	// Métodos

	/**
	 * @param: alumno y libro, correspondientes al 'correo' (Alumno), 'título y
	 * @autor' (Libro)
	 * 
	 * @return: un préstamo ficticio correcto de la clase Prestamo
	 **/
	public static Prestamo getPrestamoFicticio(Alumno alumno, Libro libro) {
		return new Prestamo(alumno, libro, LocalDate.now());
	}

	/**
	 * @param: fecha devolución CORREGIR fecha de devolución de un libro
	 **/
	public void devolver(LocalDate fecha) {

		setFechaDevolucion(fecha);

	}

	/**
	 * 
	 * Devolverá los puntos conseguidos en un préstamo.
	 * 
	 * Se calcularán de la siguiente manera:
	 * 
	 * Si hay más de 20 dias de diferencia entre la fecha de préstamo y la de
	 * devolución, serán 0 pts Si no: 20(máximo de días) / pts del libro * 20(máximo
	 * de días).
	 * 
	 * @return: entero CORREGIR
	 **/
	public int getPuntos() {

		int puntos = 0;
		if (fechaDevolucion == null) {
			return puntos = 0;
		}
		if (ChronoUnit.DAYS.between(getFechaPrestamo(), getFechaDevolucion()) <= MAX_DIAS_PRESTAMO) {
			puntos = (int) Math.round(libro.getPuntos() / ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion));
		}
		return puntos;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	private void setAlumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}

		alumno = new Alumno(alumno);
		this.alumno = alumno;
	}

	public Libro getLibro() {
		return libro;
	}

	private void setLibro(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		if (libro instanceof LibroEscrito) {
			this.libro = new LibroEscrito((LibroEscrito) libro);
		} else if (libro instanceof AudioLibro) {
			this.libro = new AudioLibro((AudioLibro) libro);
		}
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	private void setFechaPrestamo(LocalDate fechaPrestamo) {
		if (fechaPrestamo == null) {
			throw new NullPointerException("ERROR: La fecha de préstamo no puede ser nula.");
		}
		if (fechaPrestamo.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de préstamo no puede ser futura.");
		}
		this.fechaPrestamo = fechaPrestamo;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (this.fechaDevolucion != null) {
			throw new IllegalArgumentException("ERROR: La devolución ya estaba registrada.");
		}
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if (fechaDevolucion.isBefore(fechaPrestamo) || fechaDevolucion.isEqual(fechaPrestamo)) {
			throw new IllegalArgumentException(
					"ERROR: La fecha de devolución debe ser posterior a la fecha de préstamo.");
		}
		this.fechaDevolucion = fechaDevolucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + ((libro == null) ? 0 : libro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		if (alumno == null) {
			if (other.alumno != null)
				return false;
		} else if (!alumno.equals(other.alumno))
			return false;
		if (libro == null) {
			if (other.libro != null)
				return false;
		} else if (!libro.equals(other.libro))
			return false;
		return true;
	}

	@Override
	public String toString() {

		String cadenaFechaDevolucion = (fechaDevolucion == null) ? ""
				: ", fecha de devolución=" + fechaDevolucion.format(FORMATO_FECHA);

		return String.format("alumno=(%s), libro=(%s), fecha de préstamo=%s%s, puntos=%s", alumno, libro,
				fechaPrestamo.format(FORMATO_FECHA), cadenaFechaDevolucion, getPuntos());

	}

}
