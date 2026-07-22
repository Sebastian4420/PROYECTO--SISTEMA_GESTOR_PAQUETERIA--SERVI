package com.paqueteria.controller;

import com.paqueteria.model.entities.Usuario;
import com.paqueteria.model.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {

    //=========================
    // TEXTFIELDS
    //=========================

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    //=========================
    // COMBOBOX
    //=========================

    @FXML
    private ComboBox<String> cbRol;

    @FXML
    private ComboBox<String> cbEstado;

    //=========================
    // BOTONES
    //=========================

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    //=========================
    // TABLA
    //=========================

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colId;

    @FXML
    private TableColumn<Usuario, String> colUsuario;

    @FXML
    private TableColumn<Usuario, Integer> colRol;

    @FXML
    private TableColumn<Usuario, Boolean> colEstado;

    //=========================
    // VARIABLES
    //=========================

    private final UsuarioService usuarioService = new UsuarioService();

    private final ObservableList<Usuario> listaUsuarios =
            FXCollections.observableArrayList();

    private Usuario usuarioSeleccionado;

    //=========================
    // INITIALIZE
    //=========================

    @FXML
    public void initialize() {

        cbRol.getItems().addAll(
                "Administrador",
                "Operador",
                "Repartidor"
        );

        cbEstado.getItems().addAll(
                "Activo",
                "Inactivo"
        );

        configurarTabla();

        cargarUsuarios();

        seleccionarUsuario();

    }
    //=========================
    // CONFIGURAR TABLA
    //=========================

    private void configurarTabla() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idUsuario"));

        colUsuario.setCellValueFactory(
                new PropertyValueFactory<>("username"));

        colRol.setCellValueFactory(
                new PropertyValueFactory<>("idRol"));

        colRol.setCellFactory(col -> new TableCell<Usuario, Integer>() {

            @Override
            protected void updateItem(Integer item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {

                    switch (item) {

                        case 1:
                            setText("Administrador");
                            break;

                        case 2:
                            setText("Operador");
                            break;

                        case 3:
                            setText("Repartidor");
                            break;

                        case 4:
                            setText("Cliente");
                            break;

                        default:
                            setText("Desconocido");
                    }

                }

            }

        });

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado"));

    }

    //=========================
    // CARGAR TABLA
    //=========================

    private void cargarUsuarios() {

        listaUsuarios.clear();

        listaUsuarios.addAll(
                usuarioService.listarUsuarios()
        );
        System.out.println("Usuarios encontrados: " + listaUsuarios.size());

        tablaUsuarios.setItems(listaUsuarios);

    }

    //=========================
    // SELECCIONAR USUARIO
    //=========================

    private void seleccionarUsuario() {

        tablaUsuarios.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, anterior, actual) -> {

                    if (actual != null) {

                        usuarioSeleccionado = actual;

                        txtUsuario.setText(actual.getUsername());

                        txtPassword.setText(actual.getPassword());

                        switch (actual.getIdRol()) {

                            case 1 -> cbRol.setValue("Administrador");

                            case 2 -> cbRol.setValue("Operador");

                            case 3 -> cbRol.setValue("Repartidor");

                            default -> cbRol.setValue(null);

                        }

                        cbEstado.setValue(
                                actual.isEstado()
                                        ? "Activo"
                                        : "Inactivo"
                        );

                    }

                });

    }
    //=========================
    // GUARDAR
    //=========================

    @FXML
    private void guardarUsuario() {

        Usuario usuario = new Usuario();

        usuario.setUsername(txtUsuario.getText());

        usuario.setPassword(txtPassword.getText());

        switch (cbRol.getValue()) {

            case "Administrador" -> usuario.setIdRol(1);

            case "Operador" -> usuario.setIdRol(2);

            case "Repartidor" -> usuario.setIdRol(3);

        }

        usuario.setEstado(
                cbEstado.getValue().equals("Activo"));

        if (usuarioService.guardarUsuario(usuario)) {

            mostrarInformacion("Usuario guardado correctamente.");

            cargarUsuarios();

            limpiarCampos();

        } else {

            mostrarError("No fue posible guardar el usuario.");

        }

    }

    //=========================
    // ACTUALIZAR
    //=========================

    @FXML
    private void actualizarUsuario() {

        if (usuarioSeleccionado == null) {

            mostrarError("Seleccione un usuario.");

            return;

        }

        usuarioSeleccionado.setUsername(txtUsuario.getText());

        usuarioSeleccionado.setPassword(txtPassword.getText());

        switch (cbRol.getValue()) {

            case "Administrador" -> usuarioSeleccionado.setIdRol(1);

            case "Operador" -> usuarioSeleccionado.setIdRol(2);

            case "Repartidor" -> usuarioSeleccionado.setIdRol(3);

        }

        usuarioSeleccionado.setEstado(
                cbEstado.getValue().equals("Activo"));

        if (usuarioService.actualizarUsuario(usuarioSeleccionado)) {

            mostrarInformacion("Usuario actualizado correctamente.");

            cargarUsuarios();

            limpiarCampos();

        } else {

            mostrarError("No fue posible actualizar el usuario.");

        }

    }

    //=========================
    // ELIMINAR
    //=========================

    @FXML
    private void eliminarUsuario() {

        if (usuarioSeleccionado == null) {

            mostrarError("Seleccione un usuario.");

            return;

        }

        if (usuarioService.eliminarUsuario(
                usuarioSeleccionado.getIdUsuario())) {

            mostrarInformacion("Usuario eliminado correctamente.");

            cargarUsuarios();

            limpiarCampos();

        } else {

            mostrarError("No fue posible eliminar el usuario.");

        }

    }

    //=========================
    // LIMPIAR
    //=========================

    @FXML
    private void limpiarCampos() {

        txtUsuario.clear();

        txtPassword.clear();

        cbRol.getSelectionModel().clearSelection();

        cbEstado.getSelectionModel().clearSelection();

        tablaUsuarios.getSelectionModel().clearSelection();

        usuarioSeleccionado = null;

    }

    //=========================
    // ALERTAS
    //=========================

    private void mostrarInformacion(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Información");

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}



