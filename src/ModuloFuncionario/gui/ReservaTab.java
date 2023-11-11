package ModuloFuncionario.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ModuloFuncionario.Reserva;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ReservaTab {
    
    public static Tab createReservaTab() {
        Tab reservationTab = new Tab("Reservas");
        GridPane reservationGrid = new GridPane();
        tabReservas(reservationGrid);
        reservationTab.setContent(reservationGrid);

        return reservationTab;
    }

    private static void tabReservas(GridPane reservationGrid) {
        reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);

        Label identificadorLabel = new Label("Identificador:");
        TextField identificadorField = new TextField();
        reservationGrid.add(identificadorLabel, 0, 0);
        reservationGrid.add(identificadorField, 1, 0);

        Label CPFClienteLabel = new Label("CPF do Cliente:");
        TextField CPFClienteField = new TextField();
        reservationGrid.add(CPFClienteLabel, 0, 1);
        reservationGrid.add(CPFClienteField, 1, 1);

        Label placaLabel = new Label("Placa do Veículo:");
        TextField placaField = new TextField();
        reservationGrid.add(placaLabel, 0, 2);
        reservationGrid.add(placaField, 1, 2);

        Label dataRetiradaLabel = new Label("Data de Retirada do Veículo:");
        TextField dataRetiradaField = new TextField();
        reservationGrid.add(dataRetiradaLabel, 0, 3);
        reservationGrid.add(dataRetiradaField, 1, 3);

        Label dataDevolucaoLabel = new Label("Data de Devolução do Veículo:");
        TextField dataDevolucaoField = new TextField();
        reservationGrid.add(dataDevolucaoLabel, 0, 4);
        reservationGrid.add(dataDevolucaoField, 1, 4);

        Label valorLocacaoLabel = new Label("Valor da Locação:");
        TextField valorLocacaoField = new TextField();
        reservationGrid.add(valorLocacaoLabel, 0, 5);
        reservationGrid.add(valorLocacaoField, 1, 5);

        Label statusLabel = new Label("Status:");
        TextField statusField = new TextField();
        reservationGrid.add(statusLabel, 0, 6);
        reservationGrid.add(statusField, 1, 6);

        Button submitButton = new Button("Registrar Reserva");
        submitButton.setOnAction(e -> {
            Reserva reserva = new Reserva(
                identificadorField.getText(),
                CPFClienteField.getText(),
                placaField.getText(),
                dataRetiradaField.getText(),
                dataDevolucaoField.getText(),
                valorLocacaoField.getText(),
                statusField.getText()
            );
            reserva.saveReserva();
        });
        reservationGrid.add(submitButton, 1, 7);
    }

}
