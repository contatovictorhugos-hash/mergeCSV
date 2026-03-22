import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MergeCSV {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String folderPath;

        if (args.length > 0) {
            folderPath = args[0];
        } else {
            System.out.print("Digite o caminho da pasta contendo os arquivos CSV (ou pressione Enter para usar a pasta atual): ");
            folderPath = scanner.nextLine();
        }

        if (folderPath == null || folderPath.trim().isEmpty()) {
            folderPath = System.getProperty("user.dir");
        }

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Erro: O caminho especificado não existe ou não é um diretório.");
            return;
        }

        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv") && !name.equalsIgnoreCase("merged.csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            System.out.println("Nenhum arquivo CSV para processar na pasta: " + folder.getAbsolutePath());
            return;
        }

        // Ordenar os arquivos por nome alfabeticamente
        Arrays.sort(csvFiles);

        File outputFile = new File(folder, "merged.csv");
        
        System.out.println("Iniciando o processo de mesclagem de " + csvFiles.length + " arquivos em " + folder.getAbsolutePath());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String headerRow = null;
            boolean isFirstFile = true;

            for (File file : csvFiles) {
                System.out.println("\nProcessando arquivo: " + file.getName());
                
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();

                    if (line != null) {
                        if (isFirstFile) {
                            // Arquivo inicial: armazena a primeira linha como cabeçalho de referência
                            headerRow = line;
                            writer.write(line);
                            writer.newLine();
                            isFirstFile = false;
                        } else {
                            // Arquivos subsequentes:
                            // Se a 1ª linha for igual ao cabeçalho inicial, ignoramos (já inserido).
                            // Se for diferente, gravamos, pois pode ser um CSV sem cabeçalho.
                            if (!line.equals(headerRow)) {
                                writer.write(line);
                                writer.newLine();
                            }
                        }

                        // Lê e grava o conteúdo restante do CSV, linha a linha.
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
                }
            }
            
            System.out.println("\nProcesso concluído com sucesso!");
            System.out.println("Arquivo consolidado salvo em: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo de saída: " + e.getMessage());
        }
    }
}