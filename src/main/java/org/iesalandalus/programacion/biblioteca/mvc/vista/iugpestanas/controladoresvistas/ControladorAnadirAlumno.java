package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorAnadirAlumno {

	private static final String ER_OBLIGATORIO = "[a-zA-ZáéíóúÁÉÍÓÚ ,.'-]+\s{1}[a-zA-ZáéíóúÁÉÍÓÚ ,.'-]+";
	private static final String ER_CORREO = "\\w+(?:\\.\\w+)*@\\w+\\.\\w{2,5}";
	private IControlador controladorMVC;
	private ObservableList<Alumno> obsAlumnos;

	@FXML	private VBox vbAnadirAlumno;
	@FXML	private GridPane gpAnadirAlumno;
	@FXML	private Label lbNombreAnadirAlumno;
	@FXML	private Label lbCorreoAnadirAlumno;
	@FXML	private Label lbCursoAnadirAlumno;
	@FXML	private TextField tfNombreAnadirAlumno;
	@FXML	private TextField tfCorreoAnadirAlumno;
	@FXML	private HBox hbCursoAnadirAlumno;
	@FXML	private ToggleGroup groupCurso;
	@FXML	private RadioButton rbCurso1AnadirAlumno;
	@FXML	private RadioButton rbCurso2AnadirAlumno;
	@FXML	private RadioButton rbCurso3AnadirAlumno;
	@FXML	private RadioButton rbCurso4AnadirAlumno;
	@FXML	private HBox hbBotonesAnadirAlumno;
	@FXML	private Button bAnadirAlumno;
	@FXML	private Button bCancelar;

	public void initialize() {
		tfNombreAnadirAlumno.textProperty()
				.addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfNombreAnadirAlumno));
		tfCorreoAnadirAlumno.textProperty()
				.addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreoAnadirAlumno));
	}

	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setAlumnos(ObservableList<Alumno> obsAlumnos) {
		this.obsAlumnos = obsAlumnos;
	}

	@FXML
	private void anadirAlumno(ActionEvent event) {
		Alumno alumno = null;
		try {
			alumno = getAlumno();
			controladorMVC.insertar(alumno);
			obsAlumnos.setAll(controladorMVC.getAlumnos());
			Stage propietario = ((Stage) bAnadirAlumno.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Alumno", "Alumno añadido correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Alumno", e.getMessage());
		}
	}

	public void inicializa() {
		tfNombreAnadirAlumno.setText("");
		compruebaCampoTexto(ER_OBLIGATORIO, tfNombreAnadirAlumno);
		tfCorreoAnadirAlumno.setText("");
		compruebaCampoTexto(ER_CORREO, tfCorreoAnadirAlumno);
		rbCurso1AnadirAlumno.setSelected(false);
		rbCurso2AnadirAlumno.setSelected(false);
		rbCurso3AnadirAlumno.setSelected(false);
		rbCurso4AnadirAlumno.setSelected(false);
	}

	private Alumno getAlumno() {
		String nombre = tfNombreAnadirAlumno.getText();
		String correo = tfCorreoAnadirAlumno.getText();
		Curso curso = cursoElegido();
		return new Alumno(nombre, correo, curso);
	}
	
	// método para incluir el curso elegido en la creación del alumno
	private Curso cursoElegido() {
		RadioButton cursoSeleccionado = (RadioButton) groupCurso.getSelectedToggle();
		if (cursoSeleccionado == rbCurso1AnadirAlumno) {
			return Curso.PRIMERO;
		} else if (cursoSeleccionado == rbCurso2AnadirAlumno) {
			return Curso.SEGUNDO;
		} else if (cursoSeleccionado == rbCurso3AnadirAlumno) {
			return Curso.TERCERO;
		} else
			return Curso.CUARTO;
	}

	private void compruebaCampoTexto(String er, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}

	@FXML
	private void cancelar() {
		((Stage) bCancelar.getScene().getWindow()).close();
	}
}
