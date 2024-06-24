import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GerarArquivoC {

    public static void main(String[] args) {
        // Caminhos absolutos para os arquivos
        String enderecoFile = "C:/Users/autologon/Desktop/Simulado/Simulado/src/data/Endereco.csv";
        String pessoaFile = "C:/Users/autologon/Desktop/Simulado/Simulado/src/data/Pessoas.csv";
        String outputFile = "C:/Users/autologon/Desktop/Simulado/Simulado/src/data/PessoaComEndereco.csv";

        // Verificar se os arquivos de entrada existem
        if (!Files.exists(Paths.get(enderecoFile)) || !Files.exists(Paths.get(pessoaFile))) {
            System.err.println("Arquivos de entrada não encontrados. Verifique os caminhos especificados.");
            return;
        }

        // Ler endereços do arquivo Endereco.csv
        List<Endereco> enderecos = lerEnderecos(enderecoFile);

        // Ler pessoas do arquivo Pessoas.csv
        List<Pessoa> pessoas = lerPessoas(pessoaFile);

        // Combinar endereços com pessoas e gerar arquivo PessoaComEndereco.csv
        gerarArquivoC(outputFile, enderecos, pessoas);

        System.out.println("Arquivo PessoaComEndereco.csv gerado com sucesso!");
    }

    private static List<Endereco> lerEnderecos(String filename) {
        List<Endereco> enderecos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Ignorar cabeçalho
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String rua = parts[0].trim();
                    String cidade = parts[1].trim();
                    int codigo = Integer.parseInt(parts[2].trim());
                    Endereco endereco = new Endereco(rua, cidade, codigo);
                    enderecos.add(endereco);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return enderecos;
    }

    private static List<Pessoa> lerPessoas(String filename) {
        List<Pessoa> pessoas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Ignorar cabeçalho
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    int codigo = Integer.parseInt(parts[0].trim());
                    String nome = parts[1].trim();
                    Pessoa pessoa = new Pessoa(codigo, nome);
                    pessoas.add(pessoa);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    private static void gerarArquivoC(String filename, List<Endereco> enderecos, List<Pessoa> pessoas) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Escrever cabeçalho
            writer.write("Codigo;Nome;Rua;Cidade\n");

            // Iterar sobre as pessoas e combinar com os endereços
            for (Pessoa pessoa : pessoas) {
                for (Endereco endereco : enderecos) {
                    if (endereco.getCodigo() == pessoa.getCodigo()) {
                        String linha = pessoa.getCodigo() + ";" + pessoa.getNome() + ";" +
                                endereco.getRua() + ";" + endereco.getCidade() + "\n";
                        writer.write(linha);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Endereco {
        private String rua;
        private String cidade;
        private int codigo;

        public Endereco(String rua, String cidade, int codigo) {
            this.rua = rua;
            this.cidade = cidade;
            this.codigo = codigo;
        }

        public String getRua() {
            return rua;
        }

        public String getCidade() {
            return cidade;
        }

        public int getCodigo() {
            return codigo;
        }
    }

    static class Pessoa {
        private int codigo;
        private String nome;

        public Pessoa(int codigo, String nome) {
            this.codigo = codigo;
            this.nome = nome;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getNome() {
            return nome;
        }
    }
}
