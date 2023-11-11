package ModuloGerente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuracoes {
    private String nomeGrupo;
    private String valorDiaria;
    private String valorTanque;
    private String valorLimpezaExt;
    private String valorLimpezaInt;
    private String diariaSeguro;

    public Configuracoes(String nomeGrupo, String valorDiaria, String valorTanque, String valorLimpezaExt,
                         String valorLimpezaInt, String diariaSeguro) {
        this.nomeGrupo = nomeGrupo;        
        this.valorDiaria = valorDiaria;
        this.valorTanque = valorTanque;
        this.valorLimpezaExt = valorLimpezaExt;
        this.valorLimpezaInt = valorLimpezaInt;
        this.diariaSeguro = diariaSeguro;
    }

    // Getters
    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public String getValorDiaria() {
        return valorDiaria;
    }

    public String getValorTanque() {
        return valorTanque;
    }

    public String getValorLimpezaExt() {
        return valorLimpezaExt;
    }

    public String getValorLimpezaInt() {
        return valorLimpezaInt;
    }

    public String getDiariaSeguro() {
        return diariaSeguro;
    }

    // Setters
    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public void setValorDiaria(String valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public void setValorTanque(String valorTanque) {
        this.valorTanque = valorTanque;
    }

    public void setValorLimpezaExt(String valorLimpezaExt) {
        this.valorLimpezaExt = valorLimpezaExt;
    }

    public void setValorLimpezaInt(String valorLimpezaInt) {
        this.valorLimpezaInt = valorLimpezaInt;
    }

    public void setDiariaSeguro(String diariaSeguro) {
        this.diariaSeguro = diariaSeguro;
    }

    public void saveConfiguracoes() {
        String relativePath = "database/configuracoes.tsv";
        File configFile = new File(relativePath);
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(nomeGrupo + "\t")) {
                        line = createDataLine();
                        updated = true;
                    }
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!updated) {
            lines.add(createDataLine());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createDataLine() {
        return nomeGrupo + "\t" + valorDiaria + "\t" + valorTanque + "\t" + valorLimpezaExt + 
               "\t" + valorLimpezaInt + "\t" + diariaSeguro;
    }
}
