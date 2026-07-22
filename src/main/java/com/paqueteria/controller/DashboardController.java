package com.paqueteria.controller;

import com.paqueteria.model.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.application.Platform;

public class DashboardController {

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private Label lblUsuario;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnRepartidores;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnPaquetes;

    @FXML
    private Button btnHistorial;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private void mostrarInicio() {
        System.out.println("Inicio");
    }

    @FXML
    private void mostrarClientes() {
        cargarVista("Clientes.fxml");
    }

    @FXML
    private void mostrarRepartidores() {
        cargarVista("Repartidores.fxml");
    }

    @FXML
    private void mostrarUsuarios() {
        cargarVista("Usuarios.fxml");
    }

    @FXML
    private void mostrarPaquetes() {
        cargarVista("Paquetes.fxml");
    }

    @FXML
    private void mostrarHistorial() {
        cargarVista("Historial.fxml");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar sesión");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de que desea cerrar sesión?"
        );

        ButtonType respuesta =
                confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        if (respuesta != ButtonType.OK) {
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/Login.fxml")
            );

            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Sistema de Paquetería");
            loginStage.setScene(new Scene(root));
            loginStage.setResizable(false);
            loginStage.show();

            Stage dashboardStage = (Stage)
                    ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            dashboardStage.close();

        } catch (Exception e) {

            e.printStackTrace();

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText(
                    "No se pudo cerrar la sesión."
            );
            error.showAndWait();
        }
    }

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;

        lblUsuario.setText(
                usuario.getUsername()
                        + " | Rol: "
                        + obtenerNombreRol(usuario.getIdRol())
        );

        configurarPermisos(usuario.getIdRol());
    }

    private String obtenerNombreRol(int idRol) {

        return switch (idRol) {
            case 1 -> "Administrador";
            case 2 -> "Operador";
            case 3 -> "Repartidor";
            case 4 -> "Cliente";
            default -> "Desconocido";
        };

    }

    private void configurarPermisos(int idRol) {

        // Primero ocultamos todos los módulos
        ocultarBoton(btnClientes);
        ocultarBoton(btnRepartidores);
        ocultarBoton(btnUsuarios);
        ocultarBoton(btnPaquetes);
        ocultarBoton(btnHistorial);

        switch (idRol) {

            case 1 -> {
                // ADMINISTRADOR: acceso completo
                mostrarBoton(btnClientes);
                mostrarBoton(btnRepartidores);
                mostrarBoton(btnUsuarios);
                mostrarBoton(btnPaquetes);
                mostrarBoton(btnHistorial);
            }

            case 2 -> {
                // OPERADOR
                mostrarBoton(btnClientes);
                mostrarBoton(btnPaquetes);
                mostrarBoton(btnHistorial);
            }

            case 3 -> {
                // REPARTIDOR
                mostrarBoton(btnPaquetes);
                mostrarBoton(btnHistorial);
            }

            case 4 -> {
                // CLIENTE
                // Por ahora no tendrá acceso al dashboard administrativo.
            }

            default -> {
                // Rol desconocido: no se muestra ningún módulo
            }
        }
    }
    private void ocultarBoton(Button boton) {

        boton.setVisible(false);
        boton.setManaged(false);
    }
    private void mostrarBoton(Button boton) {

        boton.setVisible(true);
        boton.setManaged(true);
    }
    private void cargarVista(String vista) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/" + vista)
            );

            AnchorPane panel = loader.load();

            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(panel);

            AnchorPane.setTopAnchor(panel, 0.0);
            AnchorPane.setBottomAnchor(panel, 0.0);
            AnchorPane.setLeftAnchor(panel, 0.0);
            AnchorPane.setRightAnchor(panel, 0.0);

            // Tamaño real del CRUD cargado
            double anchoCrud = panel.getPrefWidth();
            double altoCrud = panel.getPrefHeight();

            // Ajustar el área central al tamaño del CRUD
            panelPrincipal.setPrefWidth(anchoCrud);
            panelPrincipal.setPrefHeight(altoCrud);

            panelPrincipal.setMinWidth(anchoCrud);
            panelPrincipal.setMinHeight(altoCrud);

            // Obtener la ventana principal
            Stage stage = (Stage) panelPrincipal
                    .getScene()
                    .getWindow();

            // Espacio adicional para menú lateral, título y márgenes
            double anchoVentana = anchoCrud + 230;
            double altoVentana = altoCrud + 150;

            stage.setWidth(anchoVentana);
            stage.setHeight(altoVentana);

            // Centrar nuevamente la ventana
            Platform.runLater(stage::centerOnScreen);

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No se pudo cargar la vista: " + vista
            );
            alert.showAndWait();
        }
    }

}
