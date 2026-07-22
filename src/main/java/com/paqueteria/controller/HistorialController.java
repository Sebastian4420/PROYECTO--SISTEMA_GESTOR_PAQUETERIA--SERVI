package com.paqueteria.controller;

import com.paqueteria.model.dao.EstadoPaqueteDAO;
import com.paqueteria.model.dao.PaqueteDAO;
import com.paqueteria.model.entities.EstadoPaquete;
import com.paqueteria.model.entities.HistorialEstado;
import com.paqueteria.model.entities.Paquete;
import com.paqueteria.model.service.HistorialEstadoService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistorialController {

    @FXML
    private ComboBox<Paquete> cbPaquete;

    @FXML
    private ComboBox<EstadoPaquete> cbEstado;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextArea txtObservacion;

    @FXML
    private TableView<HistorialEstado> tablaHistorial;

    @FXML
    private TableColumn<HistorialEstado, Integer> colId;

    @FXML
    private TableColumn<HistorialEstado, String> colPaquete;

    @FXML
    private TableColumn<HistorialEstado, String> colEstado;

    @FXML
    private TableColumn<HistorialEstado, String> colFecha;

    @FXML
    private TableColumn<HistorialEstado, String> colObservacion;

    private final HistorialEstadoService historialService =
            new HistorialEstadoService();

    private final PaqueteDAO paqueteDAO =
            new PaqueteDAO();

    private final EstadoPaqueteDAO estadoDAO =
            new EstadoPaqueteDAO();

    private HistorialEstado historialSeleccionado;

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML
    public void initialize() {

        configurarTabla();
        configurarComboBoxPaquetes();
        configurarComboBoxEstados();

        cargarPaquetes();
        cargarEstados();
        cargarHistorial();

        txtFecha.setText(LocalDateTime.now().format(formatoFecha));

        tablaHistorial.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        mostrarHistorialSeleccionado(seleccionado);
                    }

                });
    }

    private void configurarTabla() {

        colId.setCellValueFactory(datos ->
                new SimpleIntegerProperty(
                        datos.getValue().getIdHistorial()
                ).asObject()
        );

        colPaquete.setCellValueFactory(datos -> {

            Paquete paquete = datos.getValue().getPaquete();

            String codigo = paquete != null
                    ? paquete.getCodigoSeguimiento()
                    : "Sin paquete";

            return new SimpleStringProperty(codigo);
        });

        colEstado.setCellValueFactory(datos -> {

            EstadoPaquete estado = datos.getValue().getEstado();

            String nombre = estado != null
                    ? estado.getNombre()
                    : "Sin estado";

            return new SimpleStringProperty(nombre);
        });

        colFecha.setCellValueFactory(datos -> {

            LocalDateTime fecha = datos.getValue().getFecha();

            String fechaFormateada = fecha != null
                    ? fecha.format(formatoFecha)
                    : "";

            return new SimpleStringProperty(fechaFormateada);
        });

        colObservacion.setCellValueFactory(datos ->
                new SimpleStringProperty(
                        datos.getValue().getObservacion()
                )
        );
    }

    private void configurarComboBoxPaquetes() {

        cbPaquete.setConverter(new StringConverter<>() {

            @Override
            public String toString(Paquete paquete) {

                if (paquete == null) {
                    return "";
                }

                return paquete.getCodigoSeguimiento();
            }

            @Override
            public Paquete fromString(String texto) {
                return null;
            }
        });
    }

    private void configurarComboBoxEstados() {

        cbEstado.setConverter(new StringConverter<>() {

            @Override
            public String toString(EstadoPaquete estado) {

                if (estado == null) {
                    return "";
                }

                return estado.getNombre();
            }

            @Override
            public EstadoPaquete fromString(String texto) {
                return null;
            }
        });
    }

    private void cargarPaquetes() {

        cbPaquete.setItems(
                FXCollections.observableArrayList(
                        paqueteDAO.listar()
                )
        );
    }

    private void cargarEstados() {

        cbEstado.setItems(
                FXCollections.observableArrayList(
                        estadoDAO.listar()
                )
        );
    }

    private void cargarHistorial() {

        tablaHistorial.setItems(
                FXCollections.observableArrayList(
                        historialService.listarHistorial()
                )
        );
    }

    @FXML
    private void guardarHistorial() {

        Paquete paquete = cbPaquete.getValue();
        EstadoPaquete estado = cbEstado.getValue();
        String observacion = txtObservacion.getText().trim();

        if (paquete == null || estado == null || observacion.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Seleccione un paquete, un estado y escriba una observación."
            );

            return;
        }

        HistorialEstado historial = new HistorialEstado();

        historial.setPaquete(paquete);
        historial.setEstado(estado);
        historial.setFecha(LocalDateTime.now());
        historial.setObservacion(observacion);

        boolean guardado =
                historialService.guardarHistorial(historial);

        if (guardado) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Registro guardado",
                    "El historial fue guardado correctamente."
            );

            limpiarCampos();
            cargarHistorial();

        } else {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo guardar el historial."
            );
        }
    }

    @FXML
    private void actualizarHistorial() {

        if (historialSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Sin selección",
                    "Seleccione un registro de la tabla."
            );

            return;
        }

        Paquete paquete = cbPaquete.getValue();
        EstadoPaquete estado = cbEstado.getValue();
        String observacion = txtObservacion.getText().trim();

        if (paquete == null || estado == null || observacion.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Seleccione un paquete, un estado y escriba una observación."
            );

            return;
        }

        historialSeleccionado.setPaquete(paquete);
        historialSeleccionado.setEstado(estado);
        historialSeleccionado.setObservacion(observacion);

        boolean actualizado =
                historialService.actualizarHistorial(historialSeleccionado);

        if (actualizado) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Registro actualizado",
                    "El historial fue actualizado correctamente."
            );

            limpiarCampos();
            cargarHistorial();

        } else {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo actualizar el historial."
            );
        }
    }

    @FXML
    private void eliminarHistorial() {

        if (historialSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Sin selección",
                    "Seleccione un registro de la tabla."
            );

            return;
        }

        Alert confirmacion =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de eliminar este registro del historial?"
        );

        ButtonType resultado =
                confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        if (resultado != ButtonType.OK) {
            return;
        }

        boolean eliminado =
                historialService.eliminarHistorial(
                        historialSeleccionado.getIdHistorial()
                );

        if (eliminado) {

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Registro eliminado",
                    "El historial fue eliminado correctamente."
            );

            limpiarCampos();
            cargarHistorial();

        } else {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo eliminar el historial."
            );
        }
    }

    private void mostrarHistorialSeleccionado(
            HistorialEstado historial) {

        historialSeleccionado = historial;

        cbPaquete.setValue(historial.getPaquete());
        cbEstado.setValue(historial.getEstado());
        txtObservacion.setText(historial.getObservacion());

        if (historial.getFecha() != null) {

            txtFecha.setText(
                    historial.getFecha().format(formatoFecha)
            );

        } else {

            txtFecha.clear();
        }
    }

    @FXML
    private void limpiarCampos() {

        historialSeleccionado = null;

        cbPaquete.getSelectionModel().clearSelection();
        cbEstado.getSelectionModel().clearSelection();

        txtObservacion.clear();

        txtFecha.setText(
                LocalDateTime.now().format(formatoFecha)
        );

        tablaHistorial
                .getSelectionModel()
                .clearSelection();
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alert = new Alert(tipo);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}
