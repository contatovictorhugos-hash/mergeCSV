# MergeCSV

## Resumo da Aplicacao
O MergeCSV e um utilitario Java de linha de comando criado para mesclar multiplos arquivos CSV localizados dentro de um mesmo diretorio.

Ele funciona iterando sobre todos os arquivos `.csv` do diretorio informado, processando-os sequencialmente em ordem alfabetica. O comportamento principal obedece a duas regras fundamentais:
- A primeira linha (cabecalho) do primeiro arquivo lido e armazenada e impressa no arquivo de saida.
- Para os arquivos subsequentes lidos, a primeira linha e sempre comparada. Se for identica ao cabecalho original, a linha e pulada, impedindo que cabecalhos sujem as metricas de analise no meio do resultado.
- Todo o restante dos dados utilitarios e anexado em sequencia natural num unico arquivo gerado denominado `merged.csv`.

## Log Estruturado (Metricas no Terminal)
Enquanto e executado, o sistema imprime logs visuais para acompanhamento completo das metricas. A rotina gera no proprio terminal estatisticas finais que medem exatamente o seu esforco, declarando com clareza estrutural em tempo real:
- O andamento do processamento arquivo por arquivo (quantidade de linhas de conteudo puras vs cabecalhos).
- O numero englobado global de arquivos lidos e verificados.
- Contabilidade de quantos cabecalhos unicos foram descartados e preservados.
- A quantidade final documentada do calculo global de linhas efetivamente consolidadas dentro do `merged.csv`.

## Como Rodar

### Pre-requisitos
- Java Development Kit (JDK) 21 ou superior instalado.

### Via Terminal (Linha de Comando)
1. Abra a sua ferramenta de terminal de preferencia e navegue ate a pasta raiz deste projeto.
2. Compile o codigo fonte utilizando o compilador do Java:
   ```bash
   javac src/MergeCSV.java
   ```
3. Apos compilar, execute a classe gerenciando o classpath de modo a apontar a pasta `src`. Insira o caminho do diretorio onde os arquivos CSV se encontram como argumento final (no exemplo abaixo, o ponto `.` instrui a execucao em relacao a pasta atual da linha de comando em uso):
   ```bash
   java -cp src MergeCSV .
   ```
   *Caso voce nao passe diretorio nenhum o programa perguntara via console de input do terminal antes de iniciar.*

### Via IntelliJ IDEA ou VS Code
1. Abra a pasta do projeto utilizando a sua IDE preferida.
2. Navegue ate encontrar o arquivo `src/MergeCSV.java`.
3. Simplesmente mande "Rodar" executando o contexto pelo atalho de "Run File" do ambiente da sua IDE. O Console da sua tela atuara da mesma forma como interface.
