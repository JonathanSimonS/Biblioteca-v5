package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorEscenarioPrincipal {

	// ObservableList de Alumnos
	private ObservableList<Alumno> obsAlumnos = FXCollections.observableArrayList();
	// ObservableList de Libros
	private ObservableList<Libro> obsLibros = FXCollections.observableArrayList();
	// ObservableList de Prestamos
	private ObservableList<Prestamo> obsPrestamos = FXCollections.observableArrayList();

	// instanciar clase IControlador, para en la vista pasarle el controlador con el
	// set
	private IControlador controladorMVC = null;

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	@FXML   private VBox vbBiblioteca;
	@FXML   private MenuBar mbBiblioteca;
	@FXML	private Menu mBiblioteca;
	@FXML	private MenuItem miGuardar;
	@FXML	private MenuItem miSalir;
	@FXML	private Menu mAyuda;
	@FXML	private MenuItem miAcercaDe;
	@FXML	private TabPane tpBiblioteca;
	@FXML	private Tab tAlumnos;
	@FXML	private ImageView ivAlumnos;
	@FXML	private VBox vbAlumnos;
	@FXML	private Label lbAlumnos;
    @FXML	private ContextMenu cmAlumnos;
	@FXML	private MenuItem miAnadirAlumno;
	@FXML	private MenuItem miBorrarAlumno;
	@FXML	private Tab tLibros;
	@FXML	private ImageView ivLibros;
	@FXML	private VBox vbLibros;
	@FXML	private Label lbLibros;
	@FXML	private ContextMenu cmLibros;
	@FXML	private MenuItem miAnadirLibro;
	@FXML	private MenuItem miBorrarLibro;
	@FXML	private Tab tPrestamos;
	@FXML	private ImageView imPrestamos;
	@FXML	private VBox vbPrestamos;
	@FXML	private Label lbPrestamos;
	@FXML	private ContextMenu cmPrestamos;
	@FXML	private MenuItem miPrestarLibro;
	@FXML	private MenuItem miDevolverLibro;
	@FXML	private MenuItem miListarPrestamo;
	@FXML	private MenuItem miListarPrestamoAlumno;
	@FXML	private MenuItem miListarPrestamoLibro;
	@FXML	private MenuItem miListarPrestamoFecha;
	@FXML	private MenuItem miBorrarPrestamo;
	@FXML	private Button btMostrarEstadistica;

	@FXML	private TableView<Alumno> tvAlumnos;
	@FXML	private TableColumn<Alumno, String> tcTANombre;
	@FXML	private TableColumn<Alumno, String> tcTACorreo;
	@FXML	private TableColumn<Alumno, Curso> tcTACurso;

	@FXML	private TableView<Libro> tvLibros;
	@FXML	private TableColumn<Libro, String> tcTLTitulo;
	@FXML	private TableColumn<Libro, String> tcTLAutor;
	@FXML	private TableColumn<Libro, Integer> tcTLDuracion;
	@FXML	private TableColumn<Libro, Integer> tcTLPaginas;
	
    
    @FXML	private TableView<Prestamo> tvPrestamos;
	@FXML	private TableColumn<Prestamo, String> tcTPAlumno;
	@FXML	private TableColumn<Prestamo, String> tcTPLibro;
	@FXML	private TableColumn<Prestamo, String> tcTPFechaPrestamo;
	@FXML	private TableColumn<Prestamo, String> tcTPFechaDevolucion;



    private Stage anadirAlumno;
    private ControladorAnadirAlumno cAnadirAlumno;
	private Stage anadirLibro;
	private ControladorAnadirLibro cAnadirLibro;
	// Falta la gestión de préstamos

	
	public void initialize() {
		tcTANombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcTACorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
		tcTACurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		tvAlumnos.setItems(obsAlumnos);
		
		tcTLTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		tcTLAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		tcTLDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
		tcTLPaginas.setCellValueFactory(new PropertyValueFactory<>("numPaginas"));
		tvLibros.setItems(obsLibros);

		tcTPAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre()));
		tcTPLibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
		tcTPFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format((prestamo.getValue().getFechaPrestamo()))));
//		tcTPFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format((prestamo.getValue().getFechaDevolucion()))));
		tvPrestamos.setItems(obsPrestamos);
		
	}
// implemento método para diferenciar libroEscrito de audioLibro
//	private Integer getTipoLibro(Libro libro) {
//		Integer num=0;
//		if (libro instanceof LibroEscrito) {
//			num=((LibroEscrito) libro).getNumPaginas();
//		} else {
//			num=((AudioLibro) libro).getDuracion();
//		}
//		return num;
//	}
	
// implemento método para añadir o no la fecha de devolución, ya que no puede ser nula
//	private LocalDate getFechaString(Prestamo prestamo) {
//		String fecha="";
//		if (prestamo.getFechaDevolucion() == null) {
//			return fecha;
//		}
//		return fecha=prestamo.getFechaDevolucion();
//		
//	}


	public void actualizaAlumnos() {
		// usar método clear()
		tvAlumnos.getSelectionModel().clearSelection();
		obsAlumnos.setAll(controladorMVC.getAlumnos());
	}

	public void actualizaLibros() {
		// usar método clear()
		tvLibros.getSelectionModel().clearSelection();
		obsLibros.setAll(controladorMVC.getLibros());
	}

	public void actualizaPrestamos() {
		// usar método clear()
		tvPrestamos.getSelectionModel().clearSelection();
		obsPrestamos.setAll(controladorMVC.getPrestamos());
	}


	@FXML
	void abrirAcercaDe(ActionEvent event) throws IOException {
		VBox contenido = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Biblioteca", contenido);
	}

	@FXML
	void anadirAlumno(ActionEvent event) throws IOException {
		crearAnadirAlumno();
		anadirAlumno.showAndWait();
	}
	private void crearAnadirAlumno() throws IOException {
		if (anadirAlumno == null) {
			anadirAlumno = new Stage();
			FXMLLoader cargadorAnadirAlumno = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/AnadirAlumno.fxml"));
			VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
			cAnadirAlumno = cargadorAnadirAlumno.getController();
			cAnadirAlumno.setControladorMVC(controladorMVC);
			cAnadirAlumno.setAlumnos(obsAlumnos);
			cAnadirAlumno.inicializa();
			
			Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
			anadirAlumno.initModality(Modality.APPLICATION_MODAL); 
			anadirAlumno.setTitle("Añadir Alumno");
			anadirAlumno.setScene(escenaAnadirAlumno);
		} else {
			//cAnadirAlumno.inicializa();
		}
	}

	@FXML
	void anadirLibro(ActionEvent event) throws IOException {
		crearAnadirLibro();
		anadirLibro.showAndWait();
	}
	private void crearAnadirLibro() throws IOException {
		if (anadirLibro==null) {
			anadirLibro= new Stage();
			FXMLLoader cargadorAnadirLibro = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/AnadirLibro.fxml"));
			VBox raizAnadirLibro = cargadorAnadirLibro.load();
			cAnadirLibro = cargadorAnadirLibro.getController();
			cAnadirLibro.setControladorMVC(controladorMVC);
			cAnadirLibro.setLibros(obsLibros);
			cAnadirLibro.inicializa();
			
			Scene escenaAnadirLibro = new Scene(raizAnadirLibro);
			anadirLibro.initModality(Modality.APPLICATION_MODAL); 
			anadirLibro.setTitle("Añadir Libro");
			anadirLibro.setScene(escenaAnadirLibro);
		}
	}
	
	
	@FXML
	void borrar(ActionEvent event) {
		Alumno alumno = null;
		try {
			alumno = tvAlumnos.getSelectionModel().getSelectedItem();
			if (alumno != null && Dialogos.mostrarDialogoConfirmacion("Borrar alumno", "¿Estás seguro de que quieres borrar al alumno?", null)) {
				controladorMVC.borrar(alumno); // borro al alumno
				obsAlumnos.remove(alumno);
				
				// OJO
				// obsPrestamos.clear(); 	//elimino los préstamos que pueda tener
				
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion("Borrar alumno", "Alumno borrado satisfactoriamente");
			}
			
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar alumno", e.getMessage());
		}
		
	}

	@FXML
	void devolverLibro(ActionEvent event) {

	}

	@FXML
	void listarPrestamo(ActionEvent event) {

	}

	@FXML
	void mostrarEstadistica(ActionEvent event) {

	}

	@FXML
	void prestarLibro(ActionEvent event) {

	}

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de querer salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}

}
