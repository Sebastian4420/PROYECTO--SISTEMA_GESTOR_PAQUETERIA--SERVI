package com.paqueteria.controller;

import com.paqueteria.model.entities.Cliente;
import com.paqueteria.model.service.ClienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {

    //=========================
    // TEXTFIELDS
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
    private TextField txtDireccion;

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
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colCedula;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colApellido;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    //=========================
    // VARIABLES
    //=========================

    private final ClienteService clienteService = new ClienteService();

    private final ObservableList<Cliente> listaClientes =
            FXCollections.observableArrayList();

    private Cliente clienteSeleccionado;

    //=========================
    // INITIALIZE
    //=========================

    @FXML
    public void initialize() {

        configurarColumnas();

        cargarClientes();

        seleccionarCliente();
        configurarValidacionesCampos();


    }

    private void configurarValidacionesCampos() {

        // Cédula: solo números y máximo 10 caracteres
        txtCedula.textProperty().addListener((observable, anterior, nuevo) -> {

            if (!nuevo.matches("\\d*")) {
                txtCedula.setText(nuevo.replaceAll("\\D", ""));
            }

            if (txtCedula.getText().length() > 10) {
                txtCedula.setText(
                        txtCedula.getText().substring(0, 10)
                );
            }
        });

        // Teléfono: solo números y máximo 10 caracteres
        txtTelefono.textProperty().addListener((observable, anterior, nuevo) -> {

            if (!nuevo.matches("\\d*")) {
                txtTelefono.setText(nuevo.replaceAll("\\D", ""));
            }

            if (txtTelefono.getText().length() > 10) {
                txtTelefono.setText(
                        txtTelefono.getText().substring(0, 10)
                );
            }
        });

        // Nombres: solo letras y espacios
        txtNombre.textProperty().addListener((observable, anterior, nuevo) -> {

            if (!nuevo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*")) {
                txtNombre.setText(anterior);
            }
        });

        // Apellidos: solo letras y espacios
        txtApellido.textProperty().addListener((observable, anterior, nuevo) -> {

            if (!nuevo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*")) {
                txtApellido.setText(anterior);
            }
        });
    }
    private boolean validarCampos() {

        String cedula = txtCedula.getText().trim();
        String nombres = txtNombre.getText().trim();
        String apellidos = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (cedula.isEmpty()
                || nombres.isEmpty()
                || apellidos.isEmpty()
                || telefono.isEmpty()
                || correo.isEmpty()
                || direccion.isEmpty()) {

            mostrarAdvertencia(
                    "Todos los campos son obligatorios."
            );

            return false;
        }

        if (!cedula.matches("\\d{10}")) {

            mostrarAdvertencia(
                    "La cédula debe contener exactamente 10 números."
            );

            txtCedula.requestFocus();
            return false;
        }

        if (!telefono.matches("\\d{10}")) {

            mostrarAdvertencia(
                    "El teléfono debe contener exactamente 10 números."
            );

            txtTelefono.requestFocus();
            return false;
        }

        if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {

            mostrarAdvertencia(
                    "Los nombres solo pueden contener letras."
            );

            txtNombre.requestFocus();
            return false;
        }

        if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {

            mostrarAdvertencia(
                    "Los apellidos solo pueden contener letras."
            );

            txtApellido.requestFocus();
            return false;
        }

        if (!correo.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )) {

            mostrarAdvertencia(
                    "Ingrese un correo electrónico válido."
            );

            txtCorreo.requestFocus();
            return false;
        }

        return true;
    }

    //=========================
    // CONFIGURAR TABLA
    //=========================

    private void configurarColumnas() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idCliente"));

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

        colDireccion.setCellValueFactory(
                new PropertyValueFactory<>("direccion"));

    }

    //=========================
    // CARGAR TABLA
    //=========================

    private void cargarClientes() {

        listaClientes.clear();

        listaClientes.addAll(clienteService.listarClientes());

        tablaClientes.setItems(listaClientes);

    }

    //=========================
    // SELECCIONAR FILA
    //=========================

    private void seleccionarCliente() {

        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, actual) -> {

                    if (actual != null) {

                        clienteSeleccionado = actual;

                        txtCedula.setText(actual.getCedula());
                        txtNombre.setText(actual.getNombres());
                        txtApellido.setText(actual.getApellidos());
                        txtTelefono.setText(actual.getTelefono());
                        txtCorreo.setText(actual.getCorreo());
                        txtDireccion.setText(actual.getDireccion());

                    }

                });

    }

    //=========================
    // MÉTODOS (PARTE 2)
    //=========================

    //=========================
// GUARDAR
//=========================

    @FXML
    private void guardarCliente() {

        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (clienteService.existeCedula(cedula)) {

            mostrarAdvertencia(
                    "Ya existe un cliente registrado con la cédula "
                            + cedula + "."
            );

            txtCedula.requestFocus();
            return;
        }

        if (clienteService.existeTelefono(telefono)) {

            mostrarAdvertencia(
                    "Ya existe un cliente registrado con el teléfono "
                            + telefono + "."
            );

            txtTelefono.requestFocus();
            return;
        }

        try {

            Cliente cliente = new Cliente();

            cliente.setCedula(cedula);
            cliente.setNombres(txtNombre.getText().trim());
            cliente.setApellidos(txtApellido.getText().trim());
            cliente.setTelefono(telefono);
            cliente.setCorreo(
                    txtCorreo.getText().trim().toLowerCase()
            );
            cliente.setDireccion(txtDireccion.getText().trim());

            if (clienteService.guardarCliente(cliente)) {

                mostrarInformacion(
                        "Cliente guardado correctamente."
                );

                cargarClientes();
                limpiarCampos();

            } else {

                mostrarError(
                        "No fue posible guardar el cliente."
                );
            }

        } catch (Exception e) {

            mostrarError(
                    "Ocurrió un error al guardar el cliente: "
                            + e.getMessage()
            );
        }
    }

//=========================
// ACTUALIZAR
//=========================

    @FXML
    private void actualizarCliente() {

        if (clienteSeleccionado == null) {

            mostrarAdvertencia(
                    "Seleccione un cliente de la tabla."
            );

            return;
        }

        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();

        int idCliente = clienteSeleccionado.getIdCliente();

        if (clienteService.existeCedulaEnOtroCliente(
                cedula,
                idCliente)) {

            mostrarAdvertencia(
                    "La cédula " + cedula
                            + " pertenece a otro cliente."
            );

            txtCedula.requestFocus();
            return;
        }

        if (clienteService.existeTelefonoEnOtroCliente(
                telefono,
                idCliente)) {

            mostrarAdvertencia(
                    "El teléfono " + telefono
                            + " pertenece a otro cliente."
            );

            txtTelefono.requestFocus();
            return;
        }

        try {

            clienteSeleccionado.setCedula(cedula);
            clienteSeleccionado.setNombres(
                    txtNombre.getText().trim()
            );
            clienteSeleccionado.setApellidos(
                    txtApellido.getText().trim()
            );
            clienteSeleccionado.setTelefono(telefono);
            clienteSeleccionado.setCorreo(
                    txtCorreo.getText().trim().toLowerCase()
            );
            clienteSeleccionado.setDireccion(
                    txtDireccion.getText().trim()
            );

            if (clienteService.actualizarCliente(clienteSeleccionado)) {

                mostrarInformacion(
                        "Cliente actualizado correctamente."
                );

                cargarClientes();
                limpiarCampos();

            } else {

                mostrarError(
                        "No fue posible actualizar el cliente."
                );
            }

        } catch (Exception e) {

            mostrarError(
                    "Ocurrió un error al actualizar: "
                            + e.getMessage()
            );
        }
    }

//=========================
// ELIMINAR
//=========================

    @FXML
    private void eliminarCliente() {

        if (clienteSeleccionado == null) {

            mostrarError("Seleccione un cliente.");

            return;

        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Eliminar");

        confirmacion.setHeaderText(null);

        confirmacion.setContentText("¿Desea eliminar este cliente?");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {

            if (clienteService.eliminarCliente(clienteSeleccionado.getIdCliente())) {

                mostrarInformacion("Cliente eliminado.");

                cargarClientes();

                limpiarCampos();

            } else {

                mostrarError("No fue posible eliminar porque tiene paquetes asociados.");

            }

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
        txtDireccion.clear();

        tablaClientes.getSelectionModel().clearSelection();

        clienteSeleccionado = null;

    }
    //=========================
// ALERTA INFORMACIÓN
//=========================

    private void mostrarInformacion(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();

    }
    private void mostrarAdvertencia(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Validación");
        alert.setHeaderText("Revise la información ingresada");
        alert.setContentText(mensaje);

        alert.showAndWait();
    }

//=========================
// ALERTA ERROR
//=========================

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();

    }

}

