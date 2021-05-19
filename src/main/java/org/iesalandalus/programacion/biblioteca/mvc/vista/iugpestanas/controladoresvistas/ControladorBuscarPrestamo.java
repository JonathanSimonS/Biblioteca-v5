package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorBuscarPrestamo {
	@FXML    private RadioButton rbLibro;
    @FXML    private ToggleGroup tgBusquedaPrestamo;
    @FXML    private RadioButton rbAlumno;
    @FXML    private RadioButton rbFecha;
    @FXML    private DatePicker dpBusqueda;
    @FXML    private TextField tfBusqueda;
    @FXML    private Label lbBusqueda;
    @FXML    private TableView<Prestamo> tvBusquedaPrestamos;
    @FXML    private TableColumn<Prestamo, String> tcBusquedaLibro;
    @FXML    private TableColumn<Prestamo, String> tcBusquedaAlumno;
    @FXML    private TableColumn<Prestamo, String> tcBusquedaFecha;
    @FXML    private Button btSalir;
    @FXML    private Button btLimpiar;
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private IControlador controladorMVC;
	private ObservableList<Prestamo> obsPrestamos = FXCollections.observableArrayList();
	FilteredList<Prestamo> filtroPrestamos = new FilteredList<>(obsPrestamos, p -> true);	
	
    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	public void setPrestamos(ObservableList<Prestamo> obsPrestamos2) {
		this.obsPrestamos.setAll(obsPrestamos);
	}
	
    public void initialize() {
    	
    	tcBusquedaAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre()));
		tcBusquedaLibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
		tcBusquedaFecha.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tvBusquedaPrestamos.setItems(obsPrestamos);
		
		tgBusquedaPrestamo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> seleccion());
    	
		// búsqueda por defecto
		SortedList<Prestamo> prestamosLibro= new SortedList<>(filtroPrestamos);
		prestamosLibro.comparatorProperty().bind(tvBusquedaPrestamos.comparatorProperty());
		tvBusquedaPrestamos.setItems(prestamosLibro); 
		tfBusqueda.textProperty().addListener((observable,oldValue,newValue) -> {
		      filtroPrestamos.setPredicate(prestamo -> {
		      if (newValue == null || newValue.isEmpty()) {
		              return true;
		      }
		      String libro = prestamo.getLibro().getTitulo().toLowerCase();
		      return libro.contains(newValue.toLowerCase());
		    });
		});	 

    }
    
    /**
     * Este método habilitará y deshabilitará los campos correspondientes al radio button seleccionado.
     * También agrega el tipo de búsqueda dependiendo de la selección
     */
    private void seleccion() {
    	RadioButton seleccionado = (RadioButton)tgBusquedaPrestamo.getSelectedToggle();
    	if (seleccionado == rbLibro) {
    		lbBusqueda.setText("Título:");
    		tfBusqueda.setDisable(false);
    		dpBusqueda.setDisable(true);
    		tfBusqueda.clear();
        	dpBusqueda.setValue(null);	
        	
        	// implemento búsqueda de préstamos por libro
    		SortedList<Prestamo> prestamosLibro= new SortedList<>(filtroPrestamos);
    		prestamosLibro.comparatorProperty().bind(tvBusquedaPrestamos.comparatorProperty());
    		tvBusquedaPrestamos.setItems(prestamosLibro); 
    		tfBusqueda.textProperty().addListener((observable,oldValue,newValue) -> {
    		      filtroPrestamos.setPredicate(prestamo -> {
    		      if (newValue == null || newValue.isEmpty()) {
    		              return true;
    		      }
    		      String libro = prestamo.getLibro().getTitulo().toLowerCase();
    		      return libro.contains(newValue.toLowerCase());
    		    });
    		});	 
    	} else if (seleccionado == rbAlumno) {
    		lbBusqueda.setText("Nombre:");
    		tfBusqueda.setDisable(false);
    		dpBusqueda.setDisable(true);
    		tfBusqueda.clear();
        	dpBusqueda.setValue(null);	
        	
        	// implemento búsqueda de préstamos por alumno
    		SortedList<Prestamo> prestamosAlumno= new SortedList<>(filtroPrestamos);
    		prestamosAlumno.comparatorProperty().bind(tvBusquedaPrestamos.comparatorProperty());
    		tvBusquedaPrestamos.setItems(prestamosAlumno); 
    		tfBusqueda.textProperty().addListener((observable,oldValue,newValue) -> {
    				filtroPrestamos.setPredicate(prestamo -> {
    				if (newValue == null || newValue.isEmpty()) {
    				           return true;
    				}
    				String alumno = prestamo.getAlumno().getNombre().toLowerCase();
    				return alumno.contains(newValue.toLowerCase());
    			});
    		});	 
    	} else  if (seleccionado == rbFecha){
    		lbBusqueda.setText("Fecha:");
    		tfBusqueda.setDisable(true);
    		dpBusqueda.setDisable(false);
    		tfBusqueda.clear();
    		
    		// implemento búsqueda de préstamos por fecha
    		SortedList<Prestamo> prestamosFecha= new SortedList<>(filtroPrestamos);
    		prestamosFecha.comparatorProperty().bind(tvBusquedaPrestamos.comparatorProperty());
    		tvBusquedaPrestamos.setItems(prestamosFecha); 
    		dpBusqueda.valueProperty().addListener((observable,oldValue,newValue) -> {
    		      filtroPrestamos.setPredicate(prestamo -> {
    		      if (newValue == null) {
    		              return true;
    		      }
    		      String fecha = prestamo.getFechaPrestamo().toString().toLowerCase();
    		      return fecha.contains(newValue.toString().toLowerCase());
    		    });
    		});	
    	}
	}

	public void inicializa() {
		tvBusquedaPrestamos.getSelectionModel().clearSelection();
		obsPrestamos.setAll(controladorMVC.getPrestamos());
		
		lbBusqueda.setText("Título:");
		tfBusqueda.setDisable(false);
		dpBusqueda.setDisable(true);
	}
    
    @FXML
    void limpiar(ActionEvent event) {
    	tvBusquedaPrestamos.getSelectionModel().clearSelection();
    	tfBusqueda.clear();
    	dpBusqueda.setValue(null);	
    }
    @FXML
    void salir(ActionEvent event) {
		((Stage) btSalir.getScene().getWindow()).close();
    }
}
