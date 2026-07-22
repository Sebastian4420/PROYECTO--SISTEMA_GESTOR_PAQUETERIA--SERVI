package com.paqueteria.controller;

import com.paqueteria.model.entities.HistorialEstado;
import com.paqueteria.model.entities.Paquete;
import com.paqueteria.model.service.HistorialEstadoService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RastreoPaqueteController {

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblDestinatario;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblOrigen;

    @FXML
    private Label lblDestino;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblTipoEnvio;

    @FXML
    private Label lblRepartidor;

    @FXML
    private TableView<HistorialEstado> tablaHistorial;

    @FXML
    private TableColumn<HistorialEstado, String> colEstado;

    @FXML
    private TableColumn<HistorialEstado, String> colFecha;

    @FXML
    private TableColumn<HistorialEstado, String> colObservacion;

    private final HistorialEstadoService historialService =
            new HistorialEstadoService();

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {

        configurarTabla();
    }

    private void configurarTabla() {

        colEstado.setCellValueFactory(datos -> {

            if (datos.getValue().getEstado() == null) {
                return new SimpleStringProperty("Sin estado");
            }

            return new SimpleStringProperty(
                    datos.getValue().getEstado().getNombre()
            );
        });

        colFecha.setCellValueFactory(datos -> {

            LocalDateTime fecha = datos.getValue().getFecha();

            String fechaTexto = fecha != null
                    ? fecha.format(formatoFecha)
                    : "";

            return new SimpleStringProperty(fechaTexto);
        });

        colObservacion.setCellValueFactory(datos ->
                new SimpleStringProperty(
                        datos.getValue().getObservacion() != null
                                ? datos.getValue().getObservacion()
                                : ""
                )
        );
    }

    public void setPaquete(Paquete paquete) {

        if (paquete == null) {
            return;
        }

        lblCodigo.setText(paquete.getCodigoSeguimiento());
        lblDestinatario.setText(paquete.getNombreDestinatario());
        lblTelefono.setText(paquete.getTelefonoDestinatario());
        lblOrigen.setText(paquete.getOrigen());
        lblDestino.setText(paquete.getDestino());

        if (paquete.getEstado() != null) {
            lblEstado.setText(paquete.getEstado().getNombre());
        } else {
            lblEstado.setText("Sin estado");
        }

        if (paquete.getTipoEnvio() != null) {
            lblTipoEnvio.setText(paquete.getTipoEnvio().getNombre());
        } else {
            lblTipoEnvio.setText("Sin tipo de envío");
        }

        if (paquete.getRepartidor() != null) {


            lblRepartidor.setText(
                    paquete.getRepartidor().getNombres()
            );

        } else {

            lblRepartidor.setText("No asignado");
        }

        cargarHistorial(paquete.getIdPaquete());
    }

    private void cargarHistorial(int idPaquete) {

        List<HistorialEstado> historial =
                historialService.listarPorPaquete(idPaquete);

        tablaHistorial.setItems(
                FXCollections.observableArrayList(historial)
        );
    }

    @FXML
    private void cerrarVentana() {

        Stage stage =
                (Stage) tablaHistorial.getScene().getWindow();

        stage.close();
    }
}
