package school.sptech.service;

public class Teste {
    public static void main(String[] args) {
        ExcelService excelService = new ExcelService();

        excelService.lerExcelDoS3("nexus-group-bucket", "MICRODADOS SARESP 2017 - DADOS ABERTO.xlsx");


    }
}
