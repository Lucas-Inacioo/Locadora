package ModuloFuncionario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Cliente {
    private String CPF;
    private String nomeCliente;
    private String dataNascimento;
    private String email;
    private String numeroCelular;

    public Cliente(String CPF, String nomeCliente, String dataNascimento, String email, String numeroCelular) {
        this.CPF = CPF;
        this.nomeCliente = nomeCliente;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.numeroCelular = numeroCelular;
    }

    // Getters
    public String getCPF() {
        return CPF;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    // Setters
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public void saveCliente() {
        String data =   CPF + "\t" + nomeCliente + "\t" + dataNascimento + "\t" + email + "\t" + 
                        numeroCelular + "\n";

        String relativePath = "database/clientes.tsv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
