package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorPrestarLibro {

	private IControlador controladorMVC;
	private ControladorEscenarioPrincipal padre;
	private ObservableList<Alumno> obsAlumnos = FXCollections.observableArrayList();
	private ObservableList<Libro> obsLibros = FXCollections.observableArrayList();
	private ObservableList<Prestamo> obsPrestamos = FXCollections.observableArrayList();


	@FXML	private VBox vbPrestarLibro;
	@FXML	private Label lbAlumnoPrestarLibro;
	@FXML	private Label lbLibroPrestarLibro;
	@FXML	private Label lbFechaPrestarLibro;
	@FXML	private ListView<Alumno> lvAPrestarLibro;
	@FXML	private ListView<Libro> lvLPrestarLibro;
	@FXML	private DatePicker dpPrestarLibro;
	@FXML	private Button btAnadirPrestarLibro;
	@FXML	private Button btCancelar;

	
	private class CeldaAlumno extends ListCell<Alumno> {
		@Override
		public void updateItem(Alumno alumno, boolean vacia) {
			super.updateItem(alumno, vacia);
			if (vacia) {
				setText("");
			} else {
				setText(alumno.getNombre());
			}
		}
	}
	private class CeldaLibro extends ListCell<Libro> {
		@Override
		public void updateItem(Libro libro, boolean vacia) {
			super.updateItem(libro, vacia);
			if (vacia) {
				setText("");
			} else {
				setText(libro.getTitulo());
			}
		}
	}
	public void setAlumnos(ObservableList<Alumno> obsAlumnos) {
		this.obsAlumnos.setAll(obsAlumnos);
	}

	public void setLibros(ObservableList<Libro> obsLibros) {
		this.obsLibros.setAll(obsLibros);
	}	
	public void initialize() {
		lvAPrestarLibro.setItems(obsAlumnos);	
		lvAPrestarLibro.setCellFactory(l -> new CeldaAlumno());
		
		lvLPrestarLibro.setItems(obsLibros);	
		lvLPrestarLibro.setCellFactory(l -> new CeldaLibro());
	
	}

	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setPadre(ControladorEscenarioPrincipal padre) {
		this.padre = padre;
	}



	@FXML
	void anadirPrestarLibro(ActionEvent event) {
		Alumno alumno = lvAPrestarLibro.getSelectionModel().getSelectedItem();
		Libro libro = lvLPrestarLibro.getSelectionModel().getSelectedItem();
		LocalDate fechaPrestamo = dpPrestarLibro.getValue();

		try {
			Prestamo prestamo = new Prestamo(alumno, libro, fechaPrestamo);
			controladorMVC.prestar(prestamo);
			obsPrestamos.setAll(controladorMVC.getPrestamos());
//			padre.actualizaAlumnos();
//			padre.actualizaLibros();
			padre.actualizaPrestamos();
			Stage propietario = ((Stage) btAnadirPrestarLibro.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Realizar préstamo", "Préstamo realizado correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Realizar préstamo", e.getMessage());
		}

	}

	public void inicializa() {
		lvAPrestarLibro.getSelectionModel().clearSelection();
		lvLPrestarLibro.getSelectionModel().clearSelection();
		dpPrestarLibro.setValue(null);
	}

	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
}
