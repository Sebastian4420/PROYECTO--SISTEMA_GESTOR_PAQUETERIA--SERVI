package com.paqueteria.controller;

import com.paqueteria.model.entities.Paquete;
import com.paqueteria.model.service.PaqueteService;
import com.paqueteria.model.entities.Usuario;
import com.paqueteria.model.service.UsuarioService;
import com.paqueteria.controller.RastreoPaqueteController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtCodigoSeguimiento;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRastrear;

    private final UsuarioService usuarioService = new UsuarioService();
    private final PaqueteService paqueteService = new PaqueteService();

    @FXML
    private void ingresar() {

        String usuario = txtUsuario.getText().trim();
        String password = txtContrasena.getText();

        if (usuario.isEmpty() || password.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar el usuario y la contraseña.");
            alert.showAndWait();
            return;
        }

        Usuario u = usuarioService.iniciarSesion(usuario, password);

        if (u == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
            return;
        }

        if (!u.isEstado()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario inactivo");
            alert.setHeaderText(null);
            alert.setContentText("El usuario se encuentra inactivo.");
            alert.showAndWait();
            return;
        }

        abrirDashboard(u);

    }
    @FXML
    private void rastrearPaquete() {

        String codigo = txtCodigoSeguimiento.getText().trim();

        if (codigo.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Código vacío");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Ingrese el código de seguimiento."
            );
            alert.showAndWait();

            return;
        }

        Paquete paquete =
                paqueteService.buscarPorCodigo(codigo);

        if (paquete == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Paquete no encontrado");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No existe un paquete con el código: " + codigo
            );
            alert.showAndWait();

            return;
        }

        abrirVentanaRastreo(paquete);
    }
    private void abrirVentanaRastreo(Paquete paquete) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/views/RastreoPaquete.fxml"
                    )
            );

            Parent root = loader.load();

            RastreoPaqueteController controller =
                    loader.getController();

            controller.setPaquete(paquete);

            Stage stage = new Stage();

            stage.setTitle(
                    "Rastreo - " + paquete.getCodigoSeguimiento()
            );

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No se pudo abrir la ventana de rastreo."
            );
            alert.showAndWait();
        }
    }



    private void abrirDashboard(Usuario usuario) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/Dashboard.fxml"));

            Scene scene = new Scene(loader.load());

            DashboardController controller = loader.getController();

            controller.setUsuario(usuario);

            Stage stage = new Stage();

            stage.setTitle("Sistema de Gestión de Paquetería");

            stage.setScene(scene);

            stage.setResizable(true);

            stage.show();

            Stage login = (Stage) btnIngresar.getScene().getWindow();
            login.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}