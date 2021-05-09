package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
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

public class ControladorAnadirLibro {
	
	private static final String ER_OBLIGATORIO = "[a-zA-ZáéíóúÁÉÍÓÚ ,.'-]+\s{1}[a-zA-ZáéíóúÁÉÍÓÚ ,.'-]+";
	
	private static final String ER_CIFRA = "\\d{1,4}";

	private IControlador controladorMVC;
	private ObservableList<Libro> obsLibros;
	
	@FXML    private VBox vbLibro;
    @FXML    private GridPane gpLibro;
    @FXML    private Label lbTituloLibro;
    @FXML    private Label lbAutorLibro;
    @FXML    private Label lbTipoLibro;
    @FXML    private Label lbPaginasLibro;
    @FXML    private Label lbDuracionLibro;
    @FXML    private TextField tfTituloLibro;
    @FXML    private TextField tfAutorLibro;
    @FXML    private HBox hbRadioLibro;
    @FXML    private RadioButton rbAudioLibro;
    @FXML    private ToggleGroup groupTipoLibro;
    @FXML    private RadioButton rbEscrito;
    @FXML    private TextField tfPaginasLibro;
    @FXML    private TextField tfDuracionLibro;
    @FXML    private Button btAnadirLibro;
    @FXML    private Button btCancelarLibro;
    
    @FXML
    void anadirLibro(ActionEvent event) {
    	Libro libro = null;
    	try {
    		libro = getLibro();
			controladorMVC.insertar(libro);
			obsLibros.setAll(controladorMVC.getLibros());
			Stage propietario = ((Stage) btAnadirLibro.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Libro", "Libro añadido correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Libro", e.getMessage());
		}
    }

    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	public void setLibros(ObservableList<Libro> obsLibros) {
		this.obsLibros = obsLibros;
	}
    
	public void initialize() {
		tfTituloLibro.textProperty().addListener((ob,ov,nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfTituloLibro));
		tfAutorLibro.textProperty().addListener((ob,ov,nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfAutorLibro));
		groupTipoLibro.selectedToggleProperty().addListener((observable, oldValue, newValue) -> getTipoLibro());
		tfPaginasLibro.textProperty().addListener((ob,ov,nv) -> compruebaCampoTexto(ER_CIFRA, tfPaginasLibro));
		tfDuracionLibro.textProperty().addListener((ob,ov,nv) -> compruebaCampoTexto(ER_CIFRA, tfDuracionLibro));
	}
	
	// método para mostrar opciones dependiendo del RadioButton seleccionado
	private void getTipoLibro() {
		RadioButton seleccion = (RadioButton)groupTipoLibro.getSelectedToggle();
	
		if (seleccion == rbAudioLibro) {
			tfPaginasLibro.setVisible(false);
			lbPaginasLibro.setVisible(false);
			tfDuracionLibro.setVisible(true);
			lbDuracionLibro.setVisible(true);
			
		}
		else if (seleccion == rbEscrito){
			tfDuracionLibro.setVisible(false);
			lbDuracionLibro.setVisible(false);
			tfPaginasLibro.setVisible(true);
			lbPaginasLibro.setVisible(true);
		}		
	}
	
	public void inicializa() {
		tfTituloLibro.setText("");
    	compruebaCampoTexto(ER_OBLIGATORIO, tfTituloLibro);
    	tfAutorLibro.setText("");
    	compruebaCampoTexto(ER_OBLIGATORIO, tfAutorLibro);
    	tfPaginasLibro.setText("");
    	rbAudioLibro.setSelected(true);
    	rbEscrito.setSelected(false);
    	compruebaCampoTexto(ER_CIFRA, tfPaginasLibro);
    	tfDuracionLibro.setText("");
    	compruebaCampoTexto(ER_CIFRA, tfDuracionLibro);
    }
	
	// devuelve el libro según su tipo
	private Libro getLibro() {
		Libro libro = null;
		if (libro instanceof AudioLibro) {
			String titulo = tfTituloLibro.getText();
			String autor = tfAutorLibro.getText();
			int duracion = Integer.parseInt(tfDuracionLibro.getText());
			return new AudioLibro(titulo, autor, duracion);
		} else {
			String titulo = tfTituloLibro.getText();
			String autor = tfAutorLibro.getText();
			int paginas = Integer.parseInt(tfPaginasLibro.getText());
			return new LibroEscrito(titulo, autor, paginas);
		}
	}
	
	private void compruebaCampoTexto(String er, TextField campoTexto) {	
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}
	
    @FXML private void cancelar() {
		((Stage) btCancelarLibro.getScene().getWindow()).close();
	}
}
