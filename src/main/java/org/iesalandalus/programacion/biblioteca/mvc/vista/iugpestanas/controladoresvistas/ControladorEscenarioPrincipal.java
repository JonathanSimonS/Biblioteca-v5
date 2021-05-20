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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorEscenarioPrincipal {

	private ObservableList<Alumno> obsAlumnos = FXCollections.observableArrayList();
	FilteredList<Alumno> filtroAlumnos = new FilteredList<>(obsAlumnos, p -> true);
	private ObservableList<Libro> obsLibros = FXCollections.observableArrayList();
	FilteredList<Libro> filtroLibros = new FilteredList<>(obsLibros, p -> true);
	private ObservableList<Prestamo> obsPrestamos = FXCollections.observableArrayList();

	private IControlador controladorMVC;

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String CSS = "estilos/estilos.css";

	@FXML	private VBox vbBiblioteca;
	@FXML	private MenuBar mbBiblioteca;
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
	@FXML	private MenuItem miBorrarPrestamo;
	@FXML	private Button btMostrarEstadistica;
	@FXML   private MenuItem miBuscarPrestamo;
    @FXML   private TextField tfBuscarLibro;
    @FXML   private TextField tfBuscarAlumno;
    @FXML   private Button btLimpiarAlumno;
    @FXML   private Button btLimpiarLibro;

    @FXML   private Button btBuscarLibro;
    @FXML   private ImageView ivFlechaL;
    @FXML   private Button btBuscarAlumno;
    @FXML   private ImageView ivFlechaA;
		
	@FXML	private TableView<Alumno> tvAlumnos;
	@FXML	private TableColumn<Alumno, String> tcTANombre;
	@FXML	private TableColumn<Alumno, String> tcTACorreo;
	@FXML	private TableColumn<Alumno, Curso> tcTACurso;
	
	@FXML	private TableView<Libro> tvLibros;
	@FXML	private TableColumn<Libro, String> tcTLTipo;
	@FXML	private TableColumn<Libro, String> tcTLTitulo;
	@FXML   private TableColumn<Libro, String> tcTLAutor;
	@FXML	private TableColumn<Libro, String> tcTLPaginas;
	@FXML	private TableColumn<Libro, String> tcTLPuntoss;
	
	@FXML	private TableView<Prestamo> tvPrestamos;
	@FXML	private TableColumn<Prestamo, String> tcTPAlumno;
	@FXML	private TableColumn<Prestamo, String> tcTPLibro;
	@FXML	private TableColumn<Prestamo, String> tcTPFechaPrestamo;
	@FXML	private TableColumn<Prestamo, String> tcTPFechaDevolucion;
	
	private Stage anadirAlumno;
	private ControladorAnadirAlumno cAnadirAlumno;
	private Stage anadirLibro;
	private ControladorAnadirLibro cAnadirLibro;
	private Stage prestarLibro;
	private ControladorPrestarLibro cPrestarLibro;
	private Stage mostrarEstadistica;
	private ControladorEstadisticaMensual cMostrarEstadistica;
	private Stage buscarPrestamo;
	private ControladorBuscarPrestamo cBuscarPrestamo;
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void initialize() {
		
		tfBuscarAlumno.setVisible(false);
		tfBuscarLibro.setVisible(false);
		btLimpiarAlumno.setVisible(false);
		btLimpiarLibro.setVisible(false);
 
		tcTANombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcTACorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
		tcTACurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		// permito que se puedan seleccionar varias filas (falta implementar borrado de varias filas)
		//tvAlumnos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tvAlumnos.setItems(obsAlumnos);
		
		// implemento búsqueda de alumnos
		SortedList<Alumno> alumnos = new SortedList<>(filtroAlumnos);
        alumnos.comparatorProperty().bind(tvAlumnos.comparatorProperty());
        tvAlumnos.setItems(alumnos); 
		tfBuscarAlumno.textProperty().addListener((observable,oldValue,newValue) -> {
            filtroAlumnos.setPredicate(alumno -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String nombre = alumno.getNombre().toLowerCase();
                return nombre.contains(newValue.toLowerCase());
            });
        });

		tcTLTipo.setCellValueFactory(libro -> new SimpleStringProperty(getTipoLibro(libro.getValue())));
		tcTLTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		tcTLAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		tcTLPaginas.setCellValueFactory(libro -> new SimpleStringProperty(getLibroString(libro.getValue())));
		tcTLPuntoss.setCellValueFactory(libro -> new SimpleStringProperty(Float.toString(libro.getValue().getPuntos())));
		// permito que se puedan seleccionar varias filas
		//tvLibros.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tvLibros.setItems(obsLibros);
		
		// implemento búsqueda de libros
		SortedList<Libro> libros = new SortedList<>(filtroLibros);
		libros.comparatorProperty().bind(tvLibros.comparatorProperty());
		tvLibros.setItems(libros); 
		tfBuscarLibro.textProperty().addListener((observable,oldValue,newValue) -> {
		      filtroLibros.setPredicate(libro -> {
		           if (newValue == null || newValue.isEmpty()) {
		                  return true;
		           }
		           String titulo = libro.getTitulo().toLowerCase();
		           return titulo.contains(newValue.toLowerCase());
		     });
		 });
				
		tcTPAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre()));
		tcTPLibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
		tcTPFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcTPFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(getFechaDevo(prestamo.getValue().getFechaDevolucion())));
		// permito que se puedan seleccionar varias filas
		//tvPrestamos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tvPrestamos.setItems(obsPrestamos);
	}

	// devuelvo valor según clase del objeto libro
	private String getLibroString(Libro libro) {
		String durPag ;
		if (libro instanceof AudioLibro) {
			durPag = Integer.toString(((AudioLibro)libro).getDuracion());
			durPag = durPag + " minutos";
		} else {
			durPag = Integer.toString(((LibroEscrito)libro).getNumPaginas());
			durPag = durPag + " páginas";

		}
		return durPag;
	}
	// devuelvo string según clase del objeto libro
	private String getTipoLibro(Libro libro) {
		String tipo; 
		if (libro instanceof AudioLibro) {
			tipo="AudioLibro";
		} else {
			tipo="Escrito";
		}
		return tipo;
	}
	// devuelvo fecha de devolución si tiene, o cadena vacía
	private String getFechaDevo(LocalDate fecha) {
		String tipo; 
		if (fecha == null ) {
			tipo ="";
		} else {
			tipo = FORMATO_FECHA.format(fecha);
		}
		return tipo;
	}

	public void actualizaAlumnos() {
    	tvAlumnos.getSelectionModel().clearSelection();
		obsAlumnos.setAll(controladorMVC.getAlumnos());
	}

	public void actualizaLibros() {
		tvLibros.getSelectionModel().clearSelection();
		obsLibros.setAll(controladorMVC.getLibros());
	}

	public void actualizaPrestamos() {
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
			FXMLLoader cargadorAnadirAlumno = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirAlumno.fxml"));
			VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
			cAnadirAlumno = cargadorAnadirAlumno.getController();
			cAnadirAlumno.setControladorMVC(controladorMVC);
			cAnadirAlumno.setAlumnos(obsAlumnos);
			cAnadirAlumno.inicializa();
			
			Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
			escenaAnadirAlumno.getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
			anadirAlumno.setTitle("Añadir Alumno");
			anadirAlumno.initModality(Modality.APPLICATION_MODAL);
			anadirAlumno.setScene(escenaAnadirAlumno);
		} else {
			cAnadirAlumno.inicializa();
		}
	}

	@FXML
	void anadirLibro(ActionEvent event) throws IOException {
		crearAnadirLibro();
		anadirLibro.showAndWait();
	}

	private void crearAnadirLibro() throws IOException {
		if (anadirLibro == null) {
			anadirLibro = new Stage();
			FXMLLoader cargadorAnadirLibro = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirLibro.fxml"));
			VBox raizAnadirLibro = cargadorAnadirLibro.load();
			cAnadirLibro = cargadorAnadirLibro.getController();
			cAnadirLibro.setControladorMVC(controladorMVC);
			cAnadirLibro.setLibros(obsLibros);
			cAnadirLibro.inicializa();
			Scene escenaAnadirLibro = new Scene(raizAnadirLibro);
			escenaAnadirLibro.getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
			anadirLibro.setTitle("Añadir Libro");
			anadirLibro.initModality(Modality.APPLICATION_MODAL);
			anadirLibro.setScene(escenaAnadirLibro);
		} else {
			cAnadirLibro.inicializa();
		}
	}

	@FXML
	void borrarAlumno(ActionEvent event) {
		Alumno alumno = null;
		try {
			alumno = tvAlumnos.getSelectionModel().getSelectedItem();
			if (alumno != null && Dialogos.mostrarDialogoConfirmacion("Borrar alumno",
					"¿Estás seguro de que quieres borrar al alumno?", null)) {
				controladorMVC.borrar(alumno); // borro al alumno
				obsAlumnos.remove(alumno);
				actualizaAlumnos();
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion("Borrar alumno", "Alumno borrado correctamente");
			}

		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar alumno", e.getMessage());
		}

	}

	@FXML
	void borrarLibro(ActionEvent event) {
		Libro libro = null;
		try {
			libro = tvLibros.getSelectionModel().getSelectedItem();
			if (libro != null && Dialogos.mostrarDialogoConfirmacion("Borrar libro",
					"¿Estás seguro de que quieres borrar el libro?", null)) {
				controladorMVC.borrar(libro); // borro el libro
				obsLibros.remove(libro);
				actualizaLibros();
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion("Borrar libro", "Libro borrado correctamente");
			}

		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar libro", e.getMessage());
		}
	}
	
    @FXML
    void buscarPrestamo(ActionEvent event) throws IOException {
    	crearBuscarPrestamo();
		buscarPrestamo.showAndWait();
    }

	private void crearBuscarPrestamo() throws IOException {
		if (buscarPrestamo == null) {
			buscarPrestamo = new Stage();
			FXMLLoader cargadorBuscarPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/BusquedaPrestamo.fxml"));
			VBox raizBuscarPrestamo = cargadorBuscarPrestamo.load();
			cBuscarPrestamo = cargadorBuscarPrestamo.getController();
			cBuscarPrestamo.setControladorMVC(controladorMVC);
			cBuscarPrestamo.setPrestamos(obsPrestamos);
			cBuscarPrestamo.inicializa();
			Scene escenaBuscarPrestamo = new Scene(raizBuscarPrestamo);
			escenaBuscarPrestamo.getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
			buscarPrestamo.setTitle("Buscar Préstamo");
			buscarPrestamo.initModality(Modality.APPLICATION_MODAL);
			buscarPrestamo.setScene(escenaBuscarPrestamo);
		} else {
			cBuscarPrestamo.inicializa();
		}
	}

	@FXML
	void borrarPrestamo(ActionEvent event) {
		Prestamo prestamo = null;
		try {
			prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion("Borrar préstamo",
					"¿Estás seguro de que quieres borrar el préstamo?", null)) {
				controladorMVC.borrar(prestamo); // borro mostrarDialogoConfirmacion
				obsPrestamos.remove(prestamo);
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion("Borrar préstamo", "Préstamo borrado correctamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar préstamo", e.getMessage());
		}
	}


	@FXML
	void devolverLibro(ActionEvent event) throws IOException {
		Prestamo prestamo = null;
		try {
			if (Dialogos.mostrarDialogoConfirmacion("Devolver préstamo","¿Estás seguro de que quieres devolver el préstamo?", null)) {
				prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
				controladorMVC.devolver(prestamo, LocalDate.now());			
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion("Devolver préstamos", "Préstamo devuelto correctamente");
			}

		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Devolver préstamo", e.getMessage());
		}
	}

	@FXML
	void listarPrestamo(ActionEvent event) {

	}

	@FXML
	void mostrarEstadistica(ActionEvent event) throws IOException {
		crearMostrarEstadistica();
		mostrarEstadistica.showAndWait();
	}
	

	private void crearMostrarEstadistica() throws IOException {
		if (mostrarEstadistica == null) {
			mostrarEstadistica = new Stage();
			FXMLLoader cargadorMostrarEstadistica = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/EstadisticaMensual.fxml"));
			VBox raizMostrarEstadistica = cargadorMostrarEstadistica.load();
			cMostrarEstadistica = cargadorMostrarEstadistica.getController();
			cMostrarEstadistica.setControladorMVC(controladorMVC);;
			cMostrarEstadistica.inicializa();

			Scene escenaMostrarEstadistica = new Scene(raizMostrarEstadistica);
			escenaMostrarEstadistica.getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
			mostrarEstadistica.setTitle("Estadística mensual por curso");
			mostrarEstadistica.initModality(Modality.APPLICATION_MODAL);
			mostrarEstadistica.setScene(escenaMostrarEstadistica);
		} else {
			cMostrarEstadistica.inicializa();
		}
	}

	@FXML
	void prestarLibro(ActionEvent event) throws IOException {
		crearPrestarLibro();
		prestarLibro.showAndWait();

	}

	private void crearPrestarLibro() throws IOException {
		if (prestarLibro == null) {
			prestarLibro = new Stage();
			FXMLLoader cargadorPrestarLibro = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/PrestarLibro.fxml"));
			VBox raizPrestarLibro = cargadorPrestarLibro.load();
			cPrestarLibro = cargadorPrestarLibro.getController();
			cPrestarLibro.setControladorMVC(controladorMVC);
			cPrestarLibro.setPadre(this);
			cPrestarLibro.setLibros(obsLibros);
			cPrestarLibro.setAlumnos(obsAlumnos);
			cPrestarLibro.inicializa();

			Scene escenaPrestarLibro = new Scene(raizPrestarLibro);
			escenaPrestarLibro.getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
			prestarLibro.setTitle("Prestar Libro");
			prestarLibro.initModality(Modality.APPLICATION_MODAL);
			prestarLibro.setScene(escenaPrestarLibro);
		} else {
			cPrestarLibro.inicializa();
		}
	}
	@FXML
	void guardar(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Guardar cambios", "¿Deseas guardar los cambios efectuados?", null)) {
			controladorMVC.terminar();
		} // quitar mensaje de salida
	}
	
	// método para habilitar o deshabilitar la búsqueda     tfBuscarAlumno.setVisible(false);
	@FXML
    void buscarAlumno(ActionEvent event) {
		if(tfBuscarAlumno.isVisible()) {
			tfBuscarAlumno.setVisible(false);
			btLimpiarAlumno.setVisible(false);
			ivFlechaA.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/flechader.png")));
		} else {
			tfBuscarAlumno.setVisible(true);
			btLimpiarAlumno.setVisible(true);
			ivFlechaA.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/flechaizq.png")));
		}		
    }
	@FXML
    void buscarLibro(ActionEvent event) {
		if(tfBuscarLibro.isVisible()) {
			tfBuscarLibro.setVisible(false);
			btLimpiarLibro.setVisible(false);
			ivFlechaL.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/flechader.png")));
		} else {
			tfBuscarLibro.setVisible(true);
			btLimpiarLibro.setVisible(true);
			ivFlechaL.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/flechaizq.png")));

		}
    }

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de querer salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}

	@FXML 
	public void limpiar() {
		tfBuscarAlumno.clear();
		tfBuscarLibro.clear();
	}

}
