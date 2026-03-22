# MergeCSV

## Resumo da Aplicacao
O MergeCSV e um utilitario Java de linha de comando criado para mesclar multiplos arquivos CSV localizados dentro de um mesmo diretorio.

Ele funciona iterando sobre todos os arquivos `.csv` do diretorio informado, processando-os sequencialmente em ordem alfabetica. O comportamento de mesclagem obedece as seguintes regras:
- A primeira linha (cabecalho) do primeiro arquivo arquivo lido e armazenada e impressa no arquivo de saida.
- Para os arquivos subsequentes lidos, a primeira linha e comparada com o cabecalho. Se for identica, a linha e ignorada, impedindo a repeticao indesejada de cabecalhos no meio dos dados. Caso contrario, a linha e inserida normalmente.
- Todo o restante dos dados e anexado sequencialmente em um unico arquivo resultante chamado `merged.csv`.

## Como Rodar

### Pre-requisitos
- Java Development Kit (JDK) 21 ou superior instalado.

### Via Terminal (Linha de Comando)
1. Abra a sua ferramenta de terminal de preferencia e navegue ate a pasta raiz deste projeto.
2. Compile o codigo fonte utilizando o compilador do Java:
   ```bash
   javac src/MergeCSV.java
   ```
3. Apos compilar, execute a classe gerenciando o classpath para a pasta `src`. Insira o caminho do diretorio onde os arquivos CSV se encontram como argumento final (no exemplo abaixo, o ponto `.` instrui a execucao na pasta atual):
   ```bash
   java -cp src MergeCSV .
   ```
   *Caso voce nao passe o argumento final do diretorio, o programa perguntara o caminho dinamicamente no terminal para voce digitar.*

### Via IntelliJ IDEA
1. Abra a pasta do projeto utilizando a IDE IntelliJ IDEA.
2. No painel de projeto ("Project"), navegue ate o arquivo `src/MergeCSV.java`.
3. Clique com o botao direito do mouse sobre o arquivo ou sobre o editor e selecione a opcao "Run 'MergeCSV.main()'" (ou utilize o atalho Ctrl+Shift+F10).
