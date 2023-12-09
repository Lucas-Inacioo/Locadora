package ModuloFuncionario.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import ModuloFuncionario.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Classe que define a aba de clientes no sistema.
 */
public class ClienteTab {
    
    /**
     * Cria e retorna uma aba para gerenciamento de clientes.
     * @return Uma aba de interface gráfica para gerenciamento de clientes.
     */
    public static Tab createClientesTab() {
        Tab customerTab = new Tab("Clientes");
        GridPane customerGrid = new GridPane();
        tabClientes(customerGrid);
        customerTab.setContent(customerGrid);

        return customerTab;
    }

    /**
     * Configura o layout e funcionalidades da aba de clientes.
     * @param customerGrid O GridPane que será utilizado para estruturar os elementos da aba.
     */
    private static void tabClientes(GridPane customerGrid) {
        // Configuração do espaçamento entre elementos do GridPane
        customerGrid.setVgap(10);
        customerGrid.setHgap(10);

        // Adiciona campos de texto e seus rótulos ao GridPane
        Label CPFLabel = new Label("CPF:");
        TextField CPFField = new TextField();
        customerGrid.add(CPFLabel, 0, 0);
        customerGrid.add(CPFField, 1, 0);

        Label nomeClienteLabel = new Label("Nome do Cliente:");
        TextField nomeClienteField = new TextField();
        customerGrid.add(nomeClienteLabel, 0, 1);
        customerGrid.add(nomeClienteField, 1, 1);

        Label dataNascimentoLabel = new Label("Data de Nascimento:");
        Label dataNascimentoTip = new Label("dd/mm/aaaa");
        TextField dataNascimentoField = new TextField();
        customerGrid.add(dataNascimentoLabel, 0, 2);
        customerGrid.add(dataNascimentoField, 1, 2);
        customerGrid.add(dataNascimentoTip, 2, 2);

        Label emailLabel = new Label("E-Mail:");
        TextField emailField = new TextField();
        customerGrid.add(emailLabel, 0, 3);
        customerGrid.add(emailField, 1, 3);

        Label numeroCelularLabel = new Label("Celular:");
        TextField numeroCelularField = new TextField();
        customerGrid.add(numeroCelularLabel, 0, 4);
        customerGrid.add(numeroCelularField, 1, 4);

        // Criação e configuração do botão de registro
        Button submitButton = new Button("Registrar Cliente");
        submitButton.setOnAction(e -> {
            // Verificação de campos vazios
            Boolean CPFEmpty = false, nomeEmpty = false, dataNascEmpty = false, emailEmpty = false, celularEmpty = false;
            String emptyFields = "Os seguintes campos precisam ser preenchidos: ";

            // Lógica para verificação e adição de mensagens de alerta
            if (CPFField.getText().trim().isEmpty()) {
                CPFEmpty = true;
                emptyFields += "| CPF |";
            }
            if (nomeClienteField.getText().trim().isEmpty()) {
                nomeEmpty = true;
                emptyFields += "| NOME |";
            }
            if (dataNascimentoField.getText().trim().isEmpty()) {
                dataNascEmpty = true;
                emptyFields += "| DATA DE NASCIMENTO |";
            }   
            if (emailField.getText().trim().isEmpty()) {
                emailEmpty = true;
                emptyFields += "| EMAIL |";
            }
            if (numeroCelularField.getText().trim().isEmpty()) {
                celularEmpty = true;
                emptyFields += "| TELEFONE |";
            }

            if (CPFEmpty || nomeEmpty || dataNascEmpty || emailEmpty || celularEmpty) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Campos Faltando");
                alert.setHeaderText(null); 
                alert.setContentText(emptyFields);

                alert.showAndWait();
                return;
            }

            // Verificação de campos inválidos
            String CPF = CPFField.getText().replaceAll(" ", "")
                                           .replaceAll("\\.", "")
                                           .replaceAll("-", "");
            String nome = nomeClienteField.getText().replaceAll(" ", "");

            // Transforma a data de nascimento para o formato correto
            String dataNasc = dataNascimentoField.getText().trim();
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dataNasc, inputFormatter);
                dataNasc = date.format(outputFormatter);
            } catch (DateTimeParseException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Data de nascimento inválida");
                alert.setHeaderText("Data de nascimento inválida"); 
                alert.setContentText("A data de nascimento inserida é inválida!");
                alert.showAndWait();
                return;
            }
            String email = emailField.getText().replaceAll(" ", "");
            String celular = numeroCelularField.getText().replaceAll(" ", "")
                                                         .replaceAll("\\(", "")
                                                         .replaceAll("\\)", "")
                                                         .replaceAll("-", "");

            if (Cliente.isDuplicatedCliente(CPF)) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("CPF duplicado");
                alert.setHeaderText("CPF duplicado"); 
                alert.setContentText("Cliente de CPF " + CPF + " já cadastrado, favor verificar!");

                alert.showAndWait();
                return;
            }

            if (!Cliente.isValidCPF(CPF)) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("CPF inválido");
                alert.setHeaderText("CPF inválido"); 
                alert.setContentText("O CPF inserido não é um CPF válido!");

                alert.showAndWait();
                return;
            }

            if (!Cliente.isValidDataNascimento(dataNasc)) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Data de nascimento inválida");
                alert.setHeaderText("Data de nascimento inválida"); 
                alert.setContentText("A data de nascimento inserida é inválida!");

                alert.showAndWait();
                return;
            }

            if (!Cliente.isValidEmail(email)) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Email inválido");
                alert.setHeaderText("Email inválido"); 
                alert.setContentText("O email inserido é inválido!");

                alert.showAndWait();
                return;
            }

            if (!celular.matches("^\\d{10}(\\d{1})?$")) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Celular inválido");
                alert.setHeaderText("Celular inválido"); 
                alert.setContentText("O celular inserido não é válido!");

                alert.showAndWait();
                return;
            }

            // Confirmação de registro do cliente
            if (confirmation(CPF, nome, dataNasc, email, celular)) {
                Cliente cliente = new Cliente(
                    CPF.toUpperCase(),
                    nome.toUpperCase(),
                    dataNasc.toUpperCase(),
                    email.toUpperCase(),
                    celular.toUpperCase()
                );
                cliente.saveCliente();
            }
        });
        customerGrid.add(submitButton, 1, 5);
    }

    /**
     * Exibe uma caixa de diálogo para confirmação de dados do cliente.
     * @param CPF CPF do cliente.
     * @param nome Nome do cliente.
     * @param dataNasc Data de nascimento do cliente.
     * @param email Email do cliente.
     * @param celular Celular do cliente.
     * @return Retorna verdadeiro se o usuário confirmar, caso contrário, falso.
     */
    private static Boolean confirmation(String CPF, String nome, String dataNasc, String email, String celular) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Adicionar cliente?");
        alert.setHeaderText("Você está prestes a adicionar o seguinte cliente: "); 
        alert.setContentText(
                    "CPF: " + CPF + "\n" +
                    "Nome: " + nome + "\n" +
                    "Data de Nascimento: " + dataNasc + "\n" +
                    "E-Mail: " + email + "\n" +
                    "Celular: " + celular
                    );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
