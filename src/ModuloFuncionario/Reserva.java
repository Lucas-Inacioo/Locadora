package ModuloFuncionario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Reserva {
    private String identificador;   
    private String CPFCliente;
    private String Placa;
    private String dataRetirada;
    private String dataDevolucao;
    private String valorLocacao;
    private String status;

    public Reserva(String identificador, String CPFCliente, String Placa, String dataRetirada, 
                   String dataDevolucao, String valorLocacao, String status) {
        this.identificador = identificador;
        this.CPFCliente = CPFCliente;
        this.Placa = Placa;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorLocacao = valorLocacao;
        this.status = status;
    }

    // Getters
    public String getIdentificador() {
        return identificador;
    }

    public String getCPFCliente() {
        return CPFCliente;
    }

    public String getPlaca() {
        return Placa;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public String getValorLocacao() {
        return valorLocacao;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setCPFCliente(String CPFCliente) {
        this.CPFCliente = CPFCliente;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setValorLocacao(String valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void saveReserva() {
        String data =   identificador + "\t" + CPFCliente + "\t" + Placa + "\t" + dataRetirada + "\t" + 
                        dataDevolucao + "\t" + valorLocacao + "\t" + status + "\n";

        String relativePath = "database/reservas.tsv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
