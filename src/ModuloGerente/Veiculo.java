package ModuloGerente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Classe que representa um veículo no sistema de gerenciamento de veículos.
 * Possui atributos como placa, marca, modelo, cor, ano de fabricação, grupo e status.
 */
public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String anoFabricação;
    private String nomeGrupo;
    private String status;

    // Construtor da classe Veiculo
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

    /**
     * Salva os dados do veículo no arquivo de persistência.
     */
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

    /**
     * Exclui um veículo do arquivo de persistência, atualizando seu status para indisponível.
     * @param placa A placa do veículo a ser excluído.
     * @param reason O motivo pelo qual o veículo está sendo excluído.
     */
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

    /**
     * Verifica se um veículo com a placa especificada já existe no arquivo de persistência.
     * @param placa A placa do veículo a ser verificada.
     * @return true se o veículo já existir, false caso contrário.
     */
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

    /**
     * Valida se a placa fornecida segue o padrão brasileiro.
     * @param placa A placa a ser validada.
     * @return true se a placa for válida, false caso contrário.
     */
    public static Boolean isValidPlaca(String placa) {
        String regex = "^[A-Za-z]{3}\\d[A-Za-z]\\d{2}$";
        return placa.matches(regex);
    }

    /**
     * Gera uma lista de veículos disponíveis para reserva ou locação.
     * @return Uma lista observável de veículos disponíveis.
     */
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

    /**
     * Obtém um veículo pelo número da placa.
     * @param placa A placa do veículo a ser obtido.
     * @return O veículo correspondente à placa fornecida, ou null se não for encontrado.
     */
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

    /**
     * Verifica se um veículo está disponível para reserva ou locação.
     * @param placa A placa do veículo a ser verificado.
     * @return true se o veículo estiver disponível, false caso contrário.
     */
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

    /**
     * Verifica se um veículo está atualmente locado ou reservado.
     * @param placa A placa do veículo a ser verificado.
     * @return true se o veículo estiver locado ou reservado, false caso contrário.
     */
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
