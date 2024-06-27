import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        String arquivoEntrada = "C:\\Users\\autologon\\Desktop\\ProvaA1Java\\ProvaA1\\src\\alunos.csv";
        String arquivoSaida = "C:\\Users\\autologon\\Desktop\\ProvaA1Java\\ProvaA1\\src\\resumo.csv";

        List<Aluno> listaAlunos = new ArrayList<>();

        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    try {
                        
                        int matricula = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        double nota = Double.parseDouble(dados[2].trim().replace(',', '.')); // Tratamento para vírgula decimal
                        
                        listaAlunos.add(new Aluno(matricula, nome, nota));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter dados do aluno: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        
        int quantidadeAlunos = listaAlunos.size();
        int quantidadeAprovados = 0;
        int quantidadeReprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : listaAlunos) {
            double nota = aluno.getNota();
            somaNotas += nota;
            if (nota >= 6.0) {
                quantidadeAprovados++;
            } else {
                quantidadeReprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        
        double mediaGeral = somaNotas / quantidadeAlunos;

        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {
            bw.write("Quantidade de Alunos: " + quantidadeAlunos);
            bw.newLine();
            bw.write("Aprovados (nota >= 6.0): " + quantidadeAprovados);
            bw.newLine();
            bw.write("Reprovados (nota < 6.0): " + quantidadeReprovados);
            bw.newLine();
            bw.write("Menor Nota: " + menorNota);
            bw.newLine();
            bw.write("Maior Nota: " + maiorNota);
            bw.newLine();
            bw.write("Média Geral: " + mediaGeral);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        System.out.println("Processamento concluído. Resultados salvos em " + arquivoSaida);
    }
}

class Aluno {
    private int matricula;
    private String nome;
    private double nota;

    public Aluno(int matricula, String nome, double nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public double getNota() {
        return nota;
    }
}

