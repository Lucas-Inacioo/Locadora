package ModuloGerente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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

    public static void deleteVeiculo(String placa, String reason) {
        File inputFile = new File("database/veiculos.tsv");
        File tempFile = new File("database/veiculos_temp.tsv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split("\t");
                if (data[0].equals(placa)) {
                    data[6] = "INDISPONIVEL - " + reason.toUpperCase();
                    currentLine = String.join("\t", data);
                }
                writer.write(currentLine + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temporary file.");
            }
        } else {
            System.out.println("Could not delete the original file.");
        }
    }

    public static Boolean isDuplicatedVeiculo(String placa) {
        String relativePath = "database/veiculos.tsv";
        File configFile = new File(relativePath);
        boolean duplicated = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(placa + "\t")) {
                        duplicated = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (duplicated) {
            return true;
        }
        return false;
    }

    public static Boolean isValidPlaca(String placa) {
        String regex = "^[A-Za-z]{3}\\d[A-Za-z]\\d{2}$";
        return placa.matches(regex);
    }

    public static ObservableList<Veiculo> generateVeiculosDisponiveis() {
        String relativePath = "database/veiculos.tsv";
        File configFile = new File(relativePath);

        ObservableList<Veiculo> veiculos = FXCollections.observableArrayList();

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");
                    if (data[6].equals("DISPONIVEL")) {
                        Veiculo veiculo = new Veiculo(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                        veiculos.add(veiculo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return veiculos;
    }

    public static Veiculo getVeiculoByPlaca(String placa) {
        String relativePath = "database/veiculos.tsv";
        File configFile = new File(relativePath);

        Veiculo veiculo = null;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");
                    if (data[0].equals(placa)) {
                        veiculo = new Veiculo(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return veiculo;
    }

    public static Boolean isDisponivel(String placa) {
        String relativePath = "database/veiculos.tsv";
        File configFile = new File(relativePath);
        boolean disponivel = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");
                    if (data[0].equals(placa) && data[6].equals("DISPONIVEL")) {
                        disponivel = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (disponivel) {
            return true;
        }
        return false;
    }

    public static Boolean isLocado(String placa) {
        String relativePath = "database/veiculos.tsv";
        File configFile = new File(relativePath);
        boolean locado = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");
                    if (data[0].equals(placa) && (data[6].equals("RESERVADO") || data[6].equals("LOCADO"))) {
                        locado = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (locado) {
            return true;
        }
        return false;
    }
}
