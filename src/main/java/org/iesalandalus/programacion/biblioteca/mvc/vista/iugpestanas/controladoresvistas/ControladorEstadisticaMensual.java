package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorEstadisticaMensual {
	
	@FXML    private VBox vbEstadisticas;
    @FXML    private Label lbFecha;
    @FXML    private GridPane gpEstadisticas;
    @FXML    private TextField tfCurso1;
    @FXML    private TextField tfCurso2;
    @FXML    private TextField tfCurso3;
    @FXML    private TextField tfCurso4;
    @FXML    private DatePicker dpFecha;
    @FXML    private Button btSalir;
    @FXML    private Button btLimpiar;
	private IControlador controladorMVC;
	
    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
    
    @FXML
    private void initialize() {
    	lbFecha.setStyle("-fx-font:20 arial;");
    	dpFecha.valueProperty().addListener((ob,oldValue,newValue) -> estadisticas());
    }
  
    private void estadisticas() {
    	LocalDate fecha = dpFecha.getValue();
    	Map<Curso, Integer> estadisticaMensual = controladorMVC.getEstadisticaMensualPorCurso(fecha);
    	lbFecha.setText(fecha.getMonth().toString() + " / "+ fecha.getYear());
    	lbFecha.setStyle("-fx-font:20 arial;");
    	tfCurso1.setText(""+ estadisticaMensual.get(Curso.PRIMERO));
    	tfCurso2.setText(""+ estadisticaMensual.get(Curso.SEGUNDO));
    	tfCurso3.setText(""+ estadisticaMensual.get(Curso.TERCERO));
    	tfCurso4.setText(""+ estadisticaMensual.get(Curso.CUARTO));
	}

	@FXML
    void limpiar(ActionEvent event) {
    	dpFecha.getEditor().clear();
    	tfCurso1.clear();
    	tfCurso2.clear();
    	tfCurso3.clear();
    	tfCurso4.clear();
		lbFecha.setText("Fecha");
    }

    @FXML private void salir() {
		((Stage) btSalir.getScene().getWindow()).close();
	}

	public void inicializa() {
		tfCurso1.clear();
    	tfCurso2.clear();
    	tfCurso3.clear();
    	tfCurso4.clear();
		dpFecha.setValue(null);	
		lbFecha.setText("Fecha");

	}
}
