package school.sptech.service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    // **** CONSTANTES PARA O NOME DAS COLUNAS QUE SERÃO LIDAS ****
    private static final String COLUNA_ID_ALUNO = "CD_ALUNO";
    private static final String COLUNA_SERIE_ANO = "SERIE_ANO";

    // Localidade do aluno
    private static final String COLUNA_NOME_DEP = "NOMEDEP";
    private static final String COLUNA_NOME_DEP_BOL = "NomeDepBol";
    private static final String COLUNA_REGIAO_METROPOLITANA = "RegiaoMetropolitana";
    private static final String COLUNA_MUN = "MUN";
    private static final String COLUNA_DE = "DE";

    // Propriedades pessoais do aluno
    private static final String COLUNA_SEXO = "SEXO";
    private static final String COLUNA_IDADE = "IDADE";
    private static final String COLUNA_PERIODO = "PERIODO";

    // porcentagem de acertos de cada materia
    private static final String COLUNA_ACERTOS_LP = "porc_ACERT_LP";
    private static final String COLUNA_ACERTOS_BIO = "porc_ACERT_BIO";
    private static final String COLUNA_ACERTOS_FIS = "porc_ACERT_FIS";
    private static final String COLUNA_ACERTOS_QUI = "porc_ACERT_QUI";
    private static final String COLUNA_ACERTOS_MAT = "porc_ACERT_MAT";
    private static final String COLUNA_ACERTOS_GEO = "porc_ACERT_GEO";
    private static final String COLUNA_ACERTOS_HIS = "porc_ACERT_HIS";
    private static final String COLUNA_ACERTOS_FIL = "porc_ACERT_FIL";
    private static final String COLUNA_ACERTOS_SOC = "porc_ACERT_SOC";

    // metodo para ler um arquivo .xls especifico
    // obs: é obrigatorio ser uma arquivo .xls
    public void readExcel(InputStream excelArchive) {
        try {
            // Para arquivos .xls é necessário usar HSSFWorkbooke e para .xlsx XSSFWorkbook
            Workbook workbook = new HSSFWorkbook(excelArchive);
            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("Lendo arquivo: " + excelArchive);

            // Identificar as colunas de interesse
            String[] wishedColumns = {
                    COLUNA_ID_ALUNO, COLUNA_SERIE_ANO, COLUNA_NOME_DEP, COLUNA_NOME_DEP_BOL,
                    COLUNA_REGIAO_METROPOLITANA, COLUNA_DE, COLUNA_MUN, COLUNA_SEXO,
                    COLUNA_IDADE, COLUNA_PERIODO, COLUNA_ACERTOS_LP, COLUNA_ACERTOS_BIO,
                    COLUNA_ACERTOS_FIS, COLUNA_ACERTOS_QUI, COLUNA_ACERTOS_MAT,
                    COLUNA_ACERTOS_GEO, COLUNA_ACERTOS_HIS, COLUNA_ACERTOS_FIL,
                    COLUNA_ACERTOS_SOC
            };

            // Identificar as colunas de interesse, utilizando o metodo identificar colunas
            Map<String, Integer> columnsForIndex = identifyColumns(sheet, wishedColumns);

            // verificação se as colunas foram encontradas, troque o nmr pela qtd de colunas inseridas
            if (columnsForIndex.size() < wishedColumns.length) {
                System.out.println("Erro: Uma ou mais colunas não foram encontradas no cabeçalho do arquivo.");
                return;
            }

            // lendo apenas as colunas de escolha
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Ignorar o cabeçalho
                Row row = sheet.getRow(i);
                if (row != null) {
                    try {
                        // Declarando variaveis com os valores lidos do excel
                        String idAluno = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ID_ALUNO)));

                        // variaveis referentes a porcentagem de acertos de cada materia
                        String acertosLP = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_LP)));
                        String acertosBIO = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_BIO)));
                        String acertosFIS = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_FIS)));
                        String acertosQUI = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_QUI)));
                        String acertosMAT = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_MAT)));
                        String acertosGEO = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_GEO)));
                        String acertosHIS = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_HIS)));
                        String acertosFIL = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_FIL)));
                        String acertosSOC = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_ACERTOS_SOC)));

                        // variaveis referente a localidade
                        String regiaoMetropolitana = getCellValueAsString(row.getCell(columnsForIndex
                                .get(COLUNA_REGIAO_METROPOLITANA)));
                        String regiao = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_DE)));
                        String municipio = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_MUN)));

                        // variaveis referente a propriedades do aluno
                        String ano = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_SERIE_ANO)));
                        String idade = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_IDADE)));
                        String genero = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_SEXO)));
                        String nomeDepartamento =
                                getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_NOME_DEP)));
                        String nomeDepartamentoBol =
                                getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_NOME_DEP_BOL)));

                        // Printando todos os dados lidos
                        System.out.println("Linha " + (i + 1) + ": " +
                                " ID Aluno: " + idAluno +
                                ", Acertos LP: " + acertosLP +
                                ", Acertos BIO: " + acertosBIO +
                                ", Acertos FIS: " + acertosFIS +
                                ", Acertos QUI: " + acertosQUI +
                                ", Acertos MAT: " + acertosMAT +
                                ", Acertos GEO: " + acertosGEO +
                                ", Acertos HIS: " + acertosHIS +
                                ", Acertos FIL: " + acertosFIL +
                                ", Acertos SOC: " + acertosSOC +
                                ", Região Metropolitana: " + regiaoMetropolitana +
                                ", Região: " + regiao +
                                ", Município: " + municipio +
                                ", Ano: " + ano +
                                ", Idade: " + idade +
                                ", Gênero: " + genero);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter o valor para Integer na linha " + (i + 1) + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo para identificar os índices das colunas desejadas no cabeçalho da planilha
    private Map<String, Integer> identifyColumns(Sheet sheet, String[] wishedColumns) {
        Map<String, Integer> columnsForIndex = new HashMap<>();
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            System.out.println("Erro: Cabeçalho não encontrado na planilha.");
            return columnsForIndex;
        }

        for (Cell cell : headerRow) {
            String columnName = cell.getStringCellValue();
            for (String wishedColumn : wishedColumns) {
                if (columnName.equalsIgnoreCase(wishedColumn)) {
                    columnsForIndex.put(wishedColumn, cell.getColumnIndex());
                }
            }
        }

        return columnsForIndex;
    }

    // Metodo auxiliar para obter o valor de todas as celulas como String, facilitando o tratamento dos dados depois
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

    // Metodo para transformar uma string em inteiro
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

    // Metodo para tratar o valor de uma string para double
    private Double tratarValorComoDouble(String valor) throws NumberFormatException {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        // ignora valores menores que zero
        Double valorDouble = Double.parseDouble(valor);
        if (valorDouble <= 0) {
            return null;
        }
        return valorDouble;
    }

}
