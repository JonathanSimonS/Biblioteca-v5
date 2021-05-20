package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas.ControladorEscenarioPrincipal;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaIUGPestanas extends Application implements IVista{

	private static IControlador controladorMVC = null;
	private static final String CSS = "estilos/estilos.css";
	
	@Override 
	public void setControlador(IControlador controlador) {
		controladorMVC = controlador;
	}

	@Override
	public void comenzar() {
		launch(this.getClass());
	}

	@Override
	public void terminar() {
		controladorMVC.terminar();
	}
	 
	
	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			FXMLLoader cargadorEscenarioPrincipal = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/EscenarioPrincipal.fxml"));
			VBox raiz = cargadorEscenarioPrincipal.load();	
			ControladorEscenarioPrincipal cEscenarioPrincipal = cargadorEscenarioPrincipal.getController();
			// establecemos el controlador
			cEscenarioPrincipal.setControladorMVC(controladorMVC);
			// para interactuar 
			cEscenarioPrincipal.actualizaAlumnos();
			cEscenarioPrincipal.actualizaLibros();
			cEscenarioPrincipal.actualizaPrestamos();
				 
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
			escenarioPrincipal.setTitle("Biblioteca");
			escenarioPrincipal.setScene(escena);
			escena.getStylesheets().add(LocalizadorRecursos.class.getResource(CSS).toExternalForm());
			// agrego icono a la aplicación
			escenarioPrincipal.getIcons().add(new Image(LocalizadorRecursos.class.getResource("imagenes/biblioteca.png").toExternalForm()));
			escenarioPrincipal.setResizable(true);
			escenarioPrincipal.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
			controladorMVC.terminar();
			escenarioPrincipal.close();
		}
		else {
			e.consume();
		}
	}

}
