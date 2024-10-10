package school.sptech.service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelService {

    // Método para ler múltiplos arquivos Excel de um diretório
    public void lerVariosArquivos(String caminhoDiretorio) {
        File diretorio = new File(caminhoDiretorio);
        if (diretorio.isDirectory()) {
            // Percorre todos os arquivos do diretório
            for (File arquivo : diretorio.listFiles()) {
                // Verifica se o arquivo tem a extensão .xls ou .xlsx
                if (arquivo.isFile() && (arquivo.getName().endsWith(".xls") || arquivo.getName().endsWith(".xlsx"))) {
                    lerExcel(arquivo.getAbsolutePath());
                }
            }
        } else {
            System.out.println("O caminho fornecido não é um diretório.");
        }
    }

    // Método para ler um arquivo Excel específico
    public void lerExcel(String caminhoArquivo) {
        try (FileInputStream arquivoExcel = new FileInputStream(caminhoArquivo)) {
            Workbook workbook = new HSSFWorkbook(arquivoExcel); // Para arquivos .xls, use HSSFWorkbook; para .xlsx, use XSSFWorkbook
            Sheet sheet = workbook.getSheetAt(0); // Considerando que a primeira aba é a desejada

            System.out.println("Lendo arquivo: " + caminhoArquivo);

            // Identificar as colunas de interesse
            Map<String, Integer> colunasParaIndices = identificarColunas(sheet, new String[]{"CD_ALUNO", "porc_ACERT_LI", "SERIE_ANO"});

            // Verificar se as colunas foram encontradas
            if (colunasParaIndices.size() < 3) {
                System.out.println("Erro: Uma ou mais colunas não foram encontradas no cabeçalho do arquivo.");
                return;
            }

            // Ler as linhas do Excel, mas apenas as colunas de interesse
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Ignorar o cabeçalho
                Row row = sheet.getRow(i);
                if (row != null) {
                    try {
                        Integer idAluno = tratarParaInteiro(getCellValueAsString(row.getCell(colunasParaIndices.get("CD_ALUNO"))));
                        String acertos = getCellValueAsString(row.getCell(colunasParaIndices.get("porc_ACERT_LI")));
                        String ano = getCellValueAsString(row.getCell(colunasParaIndices.get("SERIE_ANO")));

                        // Imprimir ou processar os dados conforme a regra de negócio
                        System.out.println("ID Aluno: " + idAluno + ", Acertos: " + acertos + ", Ano: " + ano);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter o valor para Integer na linha " + (i + 1) + ": " + e.getMessage());
                    }
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para identificar os índices das colunas desejadas no cabeçalho da planilha
    private Map<String, Integer> identificarColunas(Sheet sheet, String[] colunasDesejadas) {
        Map<String, Integer> colunaParaIndice = new HashMap<>();
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            System.out.println("Erro: Cabeçalho não encontrado na planilha.");
            return colunaParaIndice;
        }

        for (Cell cell : headerRow) {
            String nomeColuna = cell.getStringCellValue();
            for (String colunaDesejada : colunasDesejadas) {
                if (nomeColuna.equalsIgnoreCase(colunaDesejada)) {
                    colunaParaIndice.put(colunaDesejada, cell.getColumnIndex());
                }
            }
        }

        return colunaParaIndice;
    }

    // Método auxiliar para obter o valor da célula como String
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private Integer tratarParaInteiro(String valor) throws NumberFormatException {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        // Remover qualquer parte decimal, se presente, antes de converter
        if (valor.endsWith(".0")) {
            valor = valor.substring(0, valor.length() - 2);
        }
        return Integer.parseInt(valor);
    }
}
