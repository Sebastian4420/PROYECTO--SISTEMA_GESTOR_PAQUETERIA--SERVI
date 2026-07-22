package com.paqueteria.controller;

import com.paqueteria.model.dao.ClienteDAO;
import com.paqueteria.model.dao.EstadoPaqueteDAO;
import com.paqueteria.model.dao.RepartidorDAO;
import com.paqueteria.model.dao.TipoEnvioDAO;
import com.paqueteria.model.entities.Cliente;
import com.paqueteria.model.entities.EstadoPaquete;
import com.paqueteria.model.entities.Paquete;
import com.paqueteria.model.entities.Repartidor;
import com.paqueteria.model.entities.TipoEnvio;
import com.paqueteria.model.service.PaqueteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class PaqueteController {

    //=========================
    // TEXTFIELDS
    //=========================

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDestinatario;

    @FXML
    private TextField txtTelefonoDestinatario;

    @FXML
    private TextField txtOrigen;

    @FXML
    private TextField txtDestino;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtPrecio;

    //=========================
    // COMBOBOX
    //=========================

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private ComboBox<Repartidor> cbRepartidor;

    @FXML
    private ComboBox<TipoEnvio> cbTipoEnvio;

    @FXML
    private ComboBox<EstadoPaquete> cbEstado;

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
    private TableView<Paquete> tablaPaquetes;

    @FXML
    private TableColumn<Paquete, String> colCodigo;

    @FXML
    private TableColumn<Paquete, Cliente> colCliente;

    @FXML
    private TableColumn<Paquete, Repartidor> colRepartidor;

    @FXML
    private TableColumn<Paquete, String> colDestino;

    @FXML
    private TableColumn<Paquete, EstadoPaquete> colEstado;

    @FXML
    private TableColumn<Paquete, BigDecimal> colPrecio;

    //=========================
    // VARIABLES
    //=========================

    private final PaqueteService paqueteService = new PaqueteService();

    private final ClienteDAO clienteDAO = new ClienteDAO();

    private final RepartidorDAO repartidorDAO = new RepartidorDAO();

    private final TipoEnvioDAO tipoEnvioDAO = new TipoEnvioDAO();

    private final EstadoPaqueteDAO estadoDAO = new EstadoPaqueteDAO();

    private final ObservableList<Paquete> listaPaquetes =
            FXCollections.observableArrayList();

    private Paquete paqueteSeleccionado;

    //=========================
    // INITIALIZE
    //=========================

    @FXML
    public void initialize() {

        configurarTabla();

        cargarCombos();

        cargarPaquetes();

        seleccionarPaquete();

        generarCodigo();

    }

    //=========================
    // CONFIGURAR TABLA
    //=========================

    private void configurarTabla() {

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigoSeguimiento"));

        colCliente.setCellValueFactory(
                new PropertyValueFactory<>("cliente"));

        colRepartidor.setCellValueFactory(
                new PropertyValueFactory<>("repartidor"));

        colDestino.setCellValueFactory(
                new PropertyValueFactory<>("destino"));

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado"));

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio"));

    }

    //=========================
    // CARGAR COMBOS
    //=========================

    private void cargarCombos() {

        cbCliente.setItems(
                FXCollections.observableArrayList(clienteDAO.listar()));

        cbRepartidor.setItems(
                FXCollections.observableArrayList(repartidorDAO.listar()));

        cbTipoEnvio.setItems(
                FXCollections.observableArrayList(tipoEnvioDAO.listar()));

        cbEstado.setItems(
                FXCollections.observableArrayList(estadoDAO.listar()));

    }

    //=========================
    // GENERAR CÓDIGO
    //=========================

    private void generarCodigo() {

        txtCodigo.setText(
                "PK-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0,8)
                                .toUpperCase()
        );

    }
    //=========================
    // CARGAR TABLA
    //=========================

    private void cargarPaquetes() {

        listaPaquetes.clear();

        listaPaquetes.addAll(paqueteService.listarPaquetes());

        tablaPaquetes.setItems(listaPaquetes);

    }

    //=========================
    // SELECCIONAR PAQUETE
    //=========================

    private void seleccionarPaquete() {

        tablaPaquetes.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, actual) -> {

                    if (actual != null) {

                        paqueteSeleccionado = actual;

                        txtCodigo.setText(actual.getCodigoSeguimiento());

                        cbCliente.setValue(actual.getCliente());

                        cbRepartidor.setValue(actual.getRepartidor());

                        cbTipoEnvio.setValue(actual.getTipoEnvio());

                        cbEstado.setValue(actual.getEstado());

                        txtDestinatario.setText(actual.getNombreDestinatario());

                        txtTelefonoDestinatario.setText(actual.getTelefonoDestinatario());

                        txtOrigen.setText(actual.getOrigen());

                        txtDestino.setText(actual.getDestino());

                        txtPeso.setText(actual.getPeso().toString());

                        txtPrecio.setText(actual.getPrecio().toString());

                    }

                });

    }

    //=========================
    // GUARDAR
    //=========================

    @FXML
    private void guardarPaquete() {

        try {

            Paquete paquete = new Paquete();

            paquete.setCodigoSeguimiento(txtCodigo.getText());

            paquete.setCliente(cbCliente.getValue());

            paquete.setRepartidor(cbRepartidor.getValue());

            paquete.setTipoEnvio(cbTipoEnvio.getValue());

            paquete.setEstado(cbEstado.getValue());

            paquete.setNombreDestinatario(txtDestinatario.getText());

            paquete.setTelefonoDestinatario(txtTelefonoDestinatario.getText());

            paquete.setOrigen(txtOrigen.getText());

            paquete.setDestino(txtDestino.getText());

            paquete.setPeso(new BigDecimal(txtPeso.getText()));

            paquete.setPrecio(new BigDecimal(txtPrecio.getText()));

            // Usuario temporal
            paquete.setUsuario(new com.paqueteria.model.entities.Usuario());
            paquete.getUsuario().setIdUsuario(1);

            if (paqueteService.guardarPaquete(paquete)) {

                mostrarInformacion("Paquete registrado correctamente.");

                cargarPaquetes();

                limpiarCampos();

            } else {

                mostrarError("No fue posible registrar el paquete.");

            }

        } catch (Exception e) {

            mostrarError("Verifique los datos ingresados.");

        }

    }

    //=========================
    // ACTUALIZAR
    //=========================

    @FXML
    private void actualizarPaquete() {

        if (paqueteSeleccionado == null) {

            mostrarError("Seleccione un paquete.");

            return;

        }

        try {

            paqueteSeleccionado.setCodigoSeguimiento(txtCodigo.getText());

            paqueteSeleccionado.setCliente(cbCliente.getValue());

            paqueteSeleccionado.setRepartidor(cbRepartidor.getValue());

            paqueteSeleccionado.setTipoEnvio(cbTipoEnvio.getValue());

            paqueteSeleccionado.setEstado(cbEstado.getValue());

            paqueteSeleccionado.setNombreDestinatario(txtDestinatario.getText());

            paqueteSeleccionado.setTelefonoDestinatario(txtTelefonoDestinatario.getText());

            paqueteSeleccionado.setOrigen(txtOrigen.getText());

            paqueteSeleccionado.setDestino(txtDestino.getText());

            paqueteSeleccionado.setPeso(new BigDecimal(txtPeso.getText()));

            paqueteSeleccionado.setPrecio(new BigDecimal(txtPrecio.getText()));

            if (paqueteService.actualizarPaquete(paqueteSeleccionado)) {

                mostrarInformacion("Paquete actualizado correctamente.");

                cargarPaquetes();

                limpiarCampos();

            } else {

                mostrarError("No fue posible actualizar el paquete.");

            }

        } catch (Exception e) {

            mostrarError("Verifique los datos ingresados.");

        }

    }

    //=========================
    // ELIMINAR
    //=========================

    @FXML
    private void eliminarPaquete() {

        if (paqueteSeleccionado == null) {

            mostrarError("Seleccione un paquete.");

            return;

        }

        if (paqueteService.eliminarPaquete(
                paqueteSeleccionado.getIdPaquete())) {

            mostrarInformacion("Paquete eliminado correctamente.");

            cargarPaquetes();

            limpiarCampos();

        } else {

            mostrarError("No fue posible eliminar el paquete.");

        }

    }

    //=========================
    // LIMPIAR
    //=========================

    @FXML
    private void limpiarCampos() {

        txtCodigo.clear();

        txtDestinatario.clear();

        txtTelefonoDestinatario.clear();

        txtOrigen.clear();

        txtDestino.clear();

        txtPeso.clear();

        txtPrecio.clear();

        cbCliente.getSelectionModel().clearSelection();

        cbRepartidor.getSelectionModel().clearSelection();

        cbTipoEnvio.getSelectionModel().clearSelection();

        cbEstado.getSelectionModel().clearSelection();

        tablaPaquetes.getSelectionModel().clearSelection();

        paqueteSeleccionado = null;

        generarCodigo();

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

