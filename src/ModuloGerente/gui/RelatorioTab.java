package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ModuloGerente.Relatorio;

public class RelatorioTab {
    public static Tab createRelatorioTab(Stage primaryStage) {
        Tab relatorioTab = new Tab("Relatórios");
        GridPane relatorioGrid = new GridPane();
        tabRelatorio(relatorioGrid, primaryStage);
        relatorioTab.setContent(relatorioGrid);

        return relatorioTab;
    }

    private static void tabRelatorio(GridPane relatorioGrid, Stage primaryStage) {
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);

        Button btnTotalReservationsReport = new Button("Reservas Totais");
        btnTotalReservationsReport.setOnAction(e -> reportArea.setText(Relatorio.generateTotalReservationsReport()));

        Button btnTotalRevenueReport = new Button("Lucro Total");
        btnTotalRevenueReport.setOnAction(e -> reportArea.setText(Relatorio.generateTotalRevenueReport()));

        Button btnVehicleUsageReport = new Button("Utilização Veículos");
        btnVehicleUsageReport.setOnAction(e -> reportArea.setText(Relatorio.generateVehicleUsageReport()));

        Button btnReservationStatus = new Button("Status Reservas");
        btnReservationStatus.setOnAction(e -> reportArea.setText(Relatorio.generateReservationStatusReport()));

        Button btnVehicleStatusReport = new Button("Status Veículos");
        btnVehicleStatusReport.setOnAction(e -> reportArea.setText(Relatorio.generateVehicleStatusReport()));

        Button btnClientAgeGroupReport = new Button("Faixa Etária Clientes");
        btnClientAgeGroupReport.setOnAction(e -> reportArea.setText(Relatorio.generateClientAgeGroupReport()));


        relatorioGrid.add(btnTotalReservationsReport, 0, 0);
        relatorioGrid.add(btnTotalRevenueReport, 0, 1);
        relatorioGrid.add(btnVehicleUsageReport, 0, 2);
        relatorioGrid.add(btnReservationStatus, 0, 3);
        relatorioGrid.add(btnClientAgeGroupReport, 0, 4);
        relatorioGrid.add(reportArea, 0, 5);
    }
}
