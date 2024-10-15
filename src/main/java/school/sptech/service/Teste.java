package school.sptech.service;

public class Teste {
    public static void main(String[] args) {
        ExcelService excelService = new ExcelService();
        S3Service s3Service = new S3Service();

        s3Service.getConnectionS3();

        excelService.readExcel(s3Service.getExcelFileFromS3("nexus-group-bucket", "MICRODADOS SARESP 2018 - DADOS ABERTO.xls"));
    }
}
