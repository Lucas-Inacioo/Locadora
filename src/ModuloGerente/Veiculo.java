package ModuloGerente;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String anoFabricação;
    private String nomeGrupo;
    private String status;

    public Veiculo(String placa, String marca, String modelo, String cor, String anoFabricação, String nomeGrupo, String status) {
        this.placa = placa;        
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.anoFabricação = anoFabricação;
        this.nomeGrupo = nomeGrupo;
        this.status = status;
    }

    // Getters
    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public String getAnoFabricação() {
        return anoFabricação;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setAnoFabricação(String anoFabricação) {
        this.anoFabricação = anoFabricação;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void saveVeiculo() {
        String data =   placa + "\t" + marca + "\t" + modelo + "\t" + cor + "\t" + anoFabricação + 
                        "\t" + nomeGrupo + "\t" + status + "\n";

        String relativePath = "database/veiculos.tsv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(relativePath, true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
