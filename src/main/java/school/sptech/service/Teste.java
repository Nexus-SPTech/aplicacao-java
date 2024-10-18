package school.sptech.service;

public class Teste {
    public static void main(String[] args) {
        ExcelService excelService = new ExcelService();
        S3Service s3Service = new S3Service();

        s3Service.getConnectionS3();

        excelService.readExcel(s3Service.getExcelFileFromS3("nexus-group-bucket", "Microdados de Alunos - Ensino Medio PROVAO - 2023.xls"));
    }
}
