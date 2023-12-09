package ModuloFuncionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Representa um cliente no sistema de gerenciamento de aluguel de veículos.
 */
public class Cliente {
    private String CPF;
    private String nomeCliente;
    private String dataNascimento;
    private String email;
    private String numeroCelular;

    /**
     * Construtor para criar um novo objeto Cliente.
     * @param CPF CPF do cliente.
     * @param nomeCliente Nome do cliente.
     * @param dataNascimento Data de nascimento do cliente.
     * @param email Email do cliente.
     * @param numeroCelular Número de celular do cliente.
     */
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

    /**
     * Salva os detalhes do cliente no arquivo de banco de dados.
     */
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

    /**
     * Verifica se um cliente com o mesmo CPF já está registrado no sistema.
     * @param CPF CPF do cliente a ser verificado.
     * @return Retorna verdadeiro se o CPF já estiver registrado, falso caso contrário.
     */
    public static Boolean isDuplicatedCliente(String CPF) {
        String relativePath = "database/clientes.tsv";
        File configFile = new File(relativePath);
        boolean duplicated = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(CPF + "\t")) {
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
     * Valida se um CPF é válido com base nos critérios de validação de CPF brasileiro.
     * @param cpf CPF a ser validado.
     * @return Retorna verdadeiro se o CPF for válido, falso caso contrário.
     */
    public static boolean isValidCPF(String cpf) {
        int cpfLength = 11;
        int maxNumbersToCheck = 9;
        int firstDigit, secondDigit, tempDigit, digitSum1 = 0, digitSum2 = 0, remainder;
        
        if (cpf.length() != cpfLength) {
            return false;
        }
    
        for (int i = 0; i < maxNumbersToCheck; i++) {
            tempDigit = Integer.parseInt(cpf.substring(i, i + 1));
            digitSum1 += (i + 1) * tempDigit;
            digitSum2 += i * tempDigit;
        }
    
        remainder = digitSum1 % cpfLength;
        firstDigit = (remainder == 10) ? 0 : remainder;
        digitSum2 += firstDigit * 9;
    
        remainder = digitSum2 % cpfLength;
        secondDigit = (remainder == 10) ? 0 : remainder;
    
        return cpf.substring(maxNumbersToCheck, cpfLength).equals("" + firstDigit + secondDigit);
    }

    /**
     * Valida se uma data de nascimento é válida e se o indivíduo tem pelo menos 18 anos.
     * @param dataNasc Data de nascimento a ser validada.
     * @return Retorna verdadeiro se a data de nascimento for válida e o indivíduo tiver pelo menos 18 anos.
     */
    public static boolean isValidDataNascimento(String dataNasc) {
        if (dataNasc.length() != 8) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate date = LocalDate.parse(dataNasc, formatter);

            if (date.getYear() != Integer.parseInt(dataNasc.substring(4, 8)) ||
                date.getMonthValue() != Integer.parseInt(dataNasc.substring(2, 4)) ||
                date.getDayOfMonth() != Integer.parseInt(dataNasc.substring(0, 2))) {
                return false;
            }

            return Period.between(date, LocalDate.now()).getYears() >= 18;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Verifica se um email é válido.
     * @param email Email a ser validado.
     * @return Retorna verdadeiro se o email for válido, falso caso contrário.
     */
    public static boolean isValidEmail(String email) {
        if (email.length() < 5 || email.length() > 254) {
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }

        if (email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1) {
            return false;
        }

        if (email.indexOf(".") == 0 || email.indexOf(".") == email.length() - 1) {
            return false;
        }

        return true;
    }

    /**
     * Verifica se um cliente cadastrado com um dado CPF é menor de idade (menor que 18 anos ou entre 18 e 21 anos).
     * @param CPF CPF do cliente a ser verificado.
     * @return Retorna verdadeiro se o cliente for menor de idade, falso caso contrário.
     */
    public static boolean isUnderAge(String CPF) {
        String relativePath = "database/clientes.tsv";
        File configFile = new File(relativePath);
        boolean underAge = false;

        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(CPF + "\t")) {
                        String[] parts = line.split("\t");
                        String dataNasc = parts[2].trim();

                        if (!isValidDataNascimento(dataNasc)) {
                            return false;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                        LocalDate date = LocalDate.parse(dataNasc, formatter);

                        if (Period.between(date, LocalDate.now()).getYears() > 17 && Period.between(date, LocalDate.now()).getYears() < 21) {
                            underAge = true;
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (underAge) {
            return true;
        }
        return false;
    }
}
