package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas.ControladorEscenarioPrincipal;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaIUGPestanas extends Application implements IVista{

	private static IControlador controladorMVC = null;
	
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
			escenarioPrincipal.setResizable(false);
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
