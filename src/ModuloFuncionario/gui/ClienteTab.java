package ModuloFuncionario.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ModuloFuncionario.Cliente;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClienteTab {
    
    public static Tab createClientesTab() {
        Tab customerTab = new Tab("Clientes");
        GridPane customerGrid = new GridPane();
        tabClientes(customerGrid);
        customerTab.setContent(customerGrid);

        return customerTab;
    }

    private static void tabClientes(GridPane customerGrid) {
        customerGrid.setVgap(10);
        customerGrid.setHgap(10);

        Label CPFLabel = new Label("CPF:");
        TextField CPFField = new TextField();
        customerGrid.add(CPFLabel, 0, 0);
        customerGrid.add(CPFField, 1, 0);

        Label nomeClienteLabel = new Label("Nome do Cliente:");
        TextField nomeClienteField = new TextField();
        customerGrid.add(nomeClienteLabel, 0, 1);
        customerGrid.add(nomeClienteField, 1, 1);

        Label dataNascimentoLabel = new Label("Data de Nascimento:");
        TextField dataNascimentoField = new TextField();
        customerGrid.add(dataNascimentoLabel, 0, 2);
        customerGrid.add(dataNascimentoField, 1, 2);

        Label emailLabel = new Label("E-Mail:");
        TextField emailField = new TextField();
        customerGrid.add(emailLabel, 0, 3);
        customerGrid.add(emailField, 1, 3);

        Label numeroCelularLabel = new Label("Celular:");
        TextField numeroCelularField = new TextField();
        customerGrid.add(numeroCelularLabel, 0, 4);
        customerGrid.add(numeroCelularField, 1, 4);

        Button submitButton = new Button("Registrar Cliente");
        submitButton.setOnAction(e -> {
            Cliente cliente = new Cliente(
                CPFField.getText(),
                nomeClienteField.getText(),
                dataNascimentoField.getText(),
                emailField.getText(),
                numeroCelularField.getText()
            );
            cliente.saveCliente();
        });
        customerGrid.add(submitButton, 1, 5);
    }

}
