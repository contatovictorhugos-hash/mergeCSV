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
            System.out.print(
                    "Digite o caminho da pasta contendo os arquivos CSV (ou pressione Enter para usar a pasta atual): ");
            folderPath = scanner.nextLine();
        }

        if (folderPath == null || folderPath.trim().isEmpty()) {
            folderPath = System.getProperty("user.dir");
        }

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("\n[ERRO] O caminho especificado não existe ou não é um diretório.");
            return;
        }

        File[] csvFiles = folder
                .listFiles((dir, name) -> name.toLowerCase().endsWith(".csv") && !name.equalsIgnoreCase("merged.csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            System.out.println("\n[AVISO] Nenhum arquivo CSV para processar na pasta: " + folder.getAbsolutePath());
            return;
        }

        // Ordenar os arquivos por nome alfabeticamente
        Arrays.sort(csvFiles);

        File outputFile = new File(folder, "merged.csv");

        System.out.println("\n=======================================================");
        System.out.println("INICIANDO PROCESSAMENTO DE ARQUIVOS CSV");
        System.out.println("=======================================================");
        System.out.printf("Diretorio: %s%n", folder.getAbsolutePath());
        System.out.printf("Arquivos Encontrados: %d%n", csvFiles.length);
        System.out.println("=======================================================\n");

        int filesProcessed = 0;
        int totalContentLines = 0;
        int totalHeadersFound = 0;
        int outputLinesCount = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String headerRow = null;
            boolean isFirstFile = true;

            for (File file : csvFiles) {
                int linesInFile = 0;
                int headersInFile = 0;

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();

                    if (line != null) {
                        if (isFirstFile) {
                            // Arquivo inicial: armazena a primeira linha como cabeçalho de referência
                            headerRow = line;
                            writer.write(line);
                            writer.newLine();

                            outputLinesCount++;
                            headersInFile++;
                            totalHeadersFound++;
                            isFirstFile = false;
                        } else {
                            // Arquivos subsequentes:
                            if (line.equals(headerRow)) {
                                headersInFile++;
                                totalHeadersFound++;
                            } else {
                                writer.write(line);
                                writer.newLine();

                                outputLinesCount++;
                                linesInFile++;
                                totalContentLines++;
                            }
                        }

                        // Lê e grava o conteúdo restante do CSV, linha a linha.
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();

                            outputLinesCount++;
                            linesInFile++;
                            totalContentLines++;
                        }
                    }
                } catch (IOException e) {
                    System.out.printf("[ERRO] Falha ao processar o arquivo '%s': %s%n", file.getName(), e.getMessage());
                    continue; // Pula para o próximo arquivo em caso de erro individual
                }

                filesProcessed++;
                System.out.printf("Processado: %-30s | Linhas de conteúdo: %-5d | Cabecalhos: %d%n",
                        file.getName(), linesInFile, headersInFile);
            }

            System.out.println("\n=======================================================");
            System.out.println("PROCESSAMENTO CONCLUIDO COM SUCESSO");
            System.out.println("=======================================================");
            System.out.printf("Arquivos Processados : %d de %d%n", filesProcessed, csvFiles.length);

            // Subtrai-se 1 pois o primeiro foi mantido no arquivo final
            int cabecalhosIgnorados = totalHeadersFound > 0 ? (totalHeadersFound - 1) : 0;
            System.out.printf("Cabecalhos Ignorados : %d (1 mantido como titulo)%n", cabecalhosIgnorados);
            System.out.printf("Linhas de conteúdo   : %d%n", totalContentLines);
            System.out.println("-------------------------------------------------------");
            System.out.printf("Arquivo Produzido    : %s%n", outputFile.getName());
            System.out.printf("Total de Linhas Final: %d%n", outputLinesCount);
            System.out.println("=======================================================\n");

        } catch (IOException e) {
            System.out.println("\n[ERRO FATAL] Erro ao criar o arquivo de saida: " + e.getMessage());
        }
    }
}