package com.paqueteria.controller;

import com.paqueteria.model.entities.Repartidor;
import com.paqueteria.model.service.RepartidorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RepartidorController {

    //=========================
    // CAMPOS
    //=========================

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtVehiculo;

    @FXML
    private TextField txtPlaca;

    @FXML
    private ComboBox<String> cbDisponible;

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
    private TableView<Repartidor> tablaRepartidores;

    @FXML
    private TableColumn<Repartidor, Integer> colId;

    @FXML
    private TableColumn<Repartidor, String> colCedula;

    @FXML
    private TableColumn<Repartidor, String> colNombre;

    @FXML
    private TableColumn<Repartidor, String> colApellido;

    @FXML
    private TableColumn<Repartidor, String> colTelefono;

    @FXML
    private TableColumn<Repartidor, String> colCorreo;

    @FXML
    private TableColumn<Repartidor, String> colVehiculo;

    @FXML
    private TableColumn<Repartidor, String> colPlaca;

    @FXML
    private TableColumn<Repartidor, Boolean> colDisponible;

    //=========================
    // VARIABLES
    //=========================

    private final RepartidorService repartidorService = new RepartidorService();

    private final ObservableList<Repartidor> listaRepartidores =
            FXCollections.observableArrayList();

    private Repartidor repartidorSeleccionado;

    //=========================
    // INITIALIZE
    //=========================

    @FXML
    public void initialize() {

        cbDisponible.getItems().addAll(
                "Disponible",
                "No disponible"
        );

        configurarColumnas();

        cargarRepartidores();

        seleccionarRepartidor();

    }

    //=========================
    // CONFIGURAR TABLA
    //=========================

    private void configurarColumnas() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idRepartidor"));

        colCedula.setCellValueFactory(
                new PropertyValueFactory<>("cedula"));

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombres"));

        colApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidos"));

        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono"));

        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("correo"));

        colVehiculo.setCellValueFactory(
                new PropertyValueFactory<>("vehiculo"));

        colPlaca.setCellValueFactory(
                new PropertyValueFactory<>("placa"));

        colDisponible.setCellValueFactory(
                new PropertyValueFactory<>("disponible"));

    }

    //=========================
    // CARGAR TABLA
    //=========================

    private void cargarRepartidores() {

        listaRepartidores.clear();

        listaRepartidores.addAll(
                repartidorService.listarRepartidores()
        );

        tablaRepartidores.setItems(listaRepartidores);

    }

    //=========================
    // SELECCIONAR FILA
    //=========================

    private void seleccionarRepartidor() {

        tablaRepartidores.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, anterior, actual) -> {

                    if (actual != null) {

                        repartidorSeleccionado = actual;

                        txtCedula.setText(actual.getCedula());
                        txtNombre.setText(actual.getNombres());
                        txtApellido.setText(actual.getApellidos());
                        txtTelefono.setText(actual.getTelefono());
                        txtCorreo.setText(actual.getCorreo());
                        txtVehiculo.setText(actual.getVehiculo());
                        txtPlaca.setText(actual.getPlaca());

                        cbDisponible.setValue(
                                actual.isDisponible()
                                        ? "Disponible"
                                        : "No disponible"
                        );

                    }

                });

    }

    //=========================
    // MÉTODOS CRUD
    //=========================

    //=========================
    // GUARDAR
    //=========================

    @FXML
    private void guardarRepartidor() {

        Repartidor repartidor = new Repartidor();

        repartidor.setCedula(txtCedula.getText());
        repartidor.setNombres(txtNombre.getText());
        repartidor.setApellidos(txtApellido.getText());
        repartidor.setTelefono(txtTelefono.getText());
        repartidor.setCorreo(txtCorreo.getText());
        repartidor.setVehiculo(txtVehiculo.getText());
        repartidor.setPlaca(txtPlaca.getText());

        repartidor.setDisponible(
                cbDisponible.getValue().equals("Disponible"));

        if (repartidorService.guardarRepartidor(repartidor)) {

            mostrarInformacion("Repartidor guardado correctamente.");

            cargarRepartidores();

            limpiarCampos();

        } else {

            mostrarError("No fue posible guardar el repartidor.");

        }

    }

    //=========================
    // ACTUALIZAR
    //=========================

    @FXML
    private void actualizarRepartidor() {

        if (repartidorSeleccionado == null) {

            mostrarError("Seleccione un repartidor.");

            return;

        }

        repartidorSeleccionado.setCedula(txtCedula.getText());
        repartidorSeleccionado.setNombres(txtNombre.getText());
        repartidorSeleccionado.setApellidos(txtApellido.getText());
        repartidorSeleccionado.setTelefono(txtTelefono.getText());
        repartidorSeleccionado.setCorreo(txtCorreo.getText());
        repartidorSeleccionado.setVehiculo(txtVehiculo.getText());
        repartidorSeleccionado.setPlaca(txtPlaca.getText());

        repartidorSeleccionado.setDisponible(
                cbDisponible.getValue().equals("Disponible"));

        if (repartidorService.actualizarRepartidor(repartidorSeleccionado)) {

            mostrarInformacion("Repartidor actualizado.");

            cargarRepartidores();

            limpiarCampos();

        } else {

            mostrarError("No fue posible actualizar.");

        }

    }

    //=========================
    // ELIMINAR
    //=========================

    @FXML
    private void eliminarRepartidor() {

        if (repartidorSeleccionado == null) {

            mostrarError("Seleccione un repartidor.");

            return;

        }

        if (repartidorService.eliminarRepartidor(
                repartidorSeleccionado.getIdRepartidor())) {

            mostrarInformacion("Repartidor eliminado.");

            cargarRepartidores();

            limpiarCampos();

        } else {

            mostrarError("No fue posible eliminar el repartidor.");

        }

    }

    //=========================
    // LIMPIAR
    //=========================

    @FXML
    private void limpiarCampos() {

        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtVehiculo.clear();
        txtPlaca.clear();

        cbDisponible.getSelectionModel().clearSelection();

        tablaRepartidores.getSelectionModel().clearSelection();

        repartidorSeleccionado = null;

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
