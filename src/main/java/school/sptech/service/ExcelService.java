package school.sptech.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import school.sptech.models.Institution;
import school.sptech.models.Student;
import school.sptech.models.StudentGrade;
import school.sptech.notification.SlackLogs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelService {
    SlackLogs slackLogs = new SlackLogs();

    // **** CONSTANTES PARA O NOME DAS COLUNAS QUE SERÃO LIDAS ****
    private static final String COLUNA_CD_ALUNO = "CD_ALUNO";
    private static final String COLUNA_CD_INST = "CODESC";

    private static final String COLUNA_DE = "DE";
    private static final String COLUNA_MUN = "MUN";
    private static final String COLUNA_NOME_DEP = "NOMEDEP";
    private static final String COLUNA_NOME_DEP_BOL = "NomeDepBol";
    private static final String COLUNA_REGIAO_METROPOLITANA = "RegiaoMetropolitana";

    // Propriedades do aluno
    private static final String COLUNA_SEXO = "SEXO";
    private static final String COLUNA_IDADE = "IDADE";
    private static final String COLUNA_PERIODO = "PERIODO";
    private static final String COLUNA_SERIE_ANO = "SERIE_ANO";

    // Acertos de cada materia
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
    public Map<String, List<?>> readExcel(InputStream excelArchive) {
        System.out.println("Iniciando leitura do excel...");
        List<Student> students = new ArrayList<>();
        List<Institution> institutions = new ArrayList<>();
        List<StudentGrade> grades = new ArrayList<>();
        Map<String, List<?>> resultReadData = new HashMap<>();

        try {
            // Para arquivos .xls é necessário usar HSSFWorkbooke e para .xlsx XSSFWorkbook
            Workbook workbook = new HSSFWorkbook(excelArchive);
            Sheet sheet = workbook.getSheetAt(0);

            // Identificar as colunas de interesse
            String[] wishedColumns = {
                    COLUNA_CD_ALUNO, COLUNA_CD_INST, COLUNA_SERIE_ANO, COLUNA_NOME_DEP, COLUNA_NOME_DEP_BOL,
                    COLUNA_REGIAO_METROPOLITANA, COLUNA_DE, COLUNA_MUN, COLUNA_SEXO,
                    COLUNA_IDADE, COLUNA_PERIODO, COLUNA_ACERTOS_LP, COLUNA_ACERTOS_BIO,
                    COLUNA_ACERTOS_FIS, COLUNA_ACERTOS_QUI, COLUNA_ACERTOS_MAT,
                    COLUNA_ACERTOS_GEO, COLUNA_ACERTOS_HIS, COLUNA_ACERTOS_FIL,
                    COLUNA_ACERTOS_SOC
            };

            // Identificar as colunas de interesse, utilizando o metodo identificar colunas
            Map<String, Integer> columnsForIndex = identifyColumns(sheet, wishedColumns);

            // verificação se as colunas foram encontradas
            if (columnsForIndex.size() < wishedColumns.length) {
                String mensagem = "Erro: Uma ou mais colunas não foram encontradas no cabeçalho do arquivo";
                slackLogs.sendNotification(mensagem);
                System.out.println(mensagem);
                return null;
            }

            System.out.println("Inicializando tratativa dos dados...");

            // lendo apenas as colunas de escolha
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Ignorar o cabeçalho
                Row row = sheet.getRow(i);
                if (row != null) {
                    try {
                        // Declarando variaveis com os valores lidos do excel

                        String codAluno = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_CD_ALUNO)));
                        String codInstituicao = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_CD_INST)));

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
                        String distritoEstadual = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_DE)));
                        String municipio = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_MUN)));

                        // variaveis referente a propriedades do aluno
                        String ano = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_SERIE_ANO)));
                        String idade = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_IDADE)));
                        String periodo = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_PERIODO)));
                        String genero = getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_SEXO)));
                        String nomeDepartamento =
                                getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_NOME_DEP)));
                        String nomeDepartamentoBol =
                                getCellValueAsString(row.getCell(columnsForIndex.get(COLUNA_NOME_DEP_BOL)));

                        Institution institution = new Institution(transformToInteger(codInstituicao),
                                nomeDepartamento, distritoEstadual, municipio, regiaoMetropolitana);
                        if (!institutions.contains(institution)) {
                            institutions.add(institution);
                        }

                        Student student = new Student(transformToInteger(codAluno), institution, ano, periodo, genero,
                                transformToInteger(idade));
                        students.add(student);

                        StudentGrade grade = new StudentGrade();
                        grade.setStudent(student);
                        grade.addNotasDisciplinas(1, transformToDouble(acertosLP));
                        grade.addNotasDisciplinas(2, transformToDouble(acertosBIO));
                        grade.addNotasDisciplinas(3, transformToDouble(acertosFIS));
                        grade.addNotasDisciplinas(4, transformToDouble(acertosQUI));
                        grade.addNotasDisciplinas(5, transformToDouble(acertosMAT));
                        grade.addNotasDisciplinas(6, transformToDouble(acertosGEO));
                        grade.addNotasDisciplinas(7, transformToDouble(acertosHIS));
                        grade.addNotasDisciplinas(8, transformToDouble(acertosFIL));
                        grade.addNotasDisciplinas(9, transformToDouble(acertosSOC));

                        grades.add(grade);

                    } catch (NumberFormatException e) {
                        String mensagem = "Erro ao converter o valor na linha " + (i + 1) + ": " + e.getMessage();
                        slackLogs.sendNotification(mensagem);
                        System.out.println(mensagem);
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Tratativa de dados finalizada!");

            workbook.close();
        } catch (IOException e) {
            String mensagem = "Erro ao ler arquivo excel " + e.getMessage();
            slackLogs.sendNotification(mensagem);
            System.out.println(mensagem);
            e.printStackTrace();
        }

        System.out.println("---------------------------");

        System.out.println("Leitura do excel finalizada com sucesso!");
        resultReadData.put("alunos", students);
        resultReadData.put("instituicoes", institutions);
        resultReadData.put("notas", grades);
        System.out.println("Retornando HashMap com os dados lidos do excel!");
        return resultReadData;
    }

    // Metodo para identificar os índices das colunas desejadas no cabeçalho da planilha
    private Map<String, Integer> identifyColumns(Sheet sheet, String[] wishedColumns) {
        Map<String, Integer> columnsForIndex = new HashMap<>();
        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            String mensagem = "Erro: Cabeçalho não encontrado na planilha";
            System.out.println();
            slackLogs.sendNotification(mensagem);
            System.out.println(mensagem);
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
    private Integer transformToInteger(String valor) throws NumberFormatException {
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
    private Double transformToDouble(String valor) throws NumberFormatException {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }

        // Substituir a vírgula por ponto
        valor = valor.replace(",", ".");
        Double valorDouble = Double.parseDouble(valor);
        // ignora valores menores que zero
        if (valorDouble <= 0) {
            return null;
        }
        return valorDouble;
    }

}
