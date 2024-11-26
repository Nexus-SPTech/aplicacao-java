package school.sptech.service;

import school.sptech.notification.Slack;
import school.sptech.provider.S3Provider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

public class S3Service {
    S3Client s3Client = new S3Provider().getS3Client();
    private Slack slackLogs;

    public void getConnectionS3(){
        String bucketName = "nexus-group-bucket";

<<<<<<< HEAD
        // *   Listando todos os buckets       *
        try {
            List<Bucket> buckets = s3Client.listBuckets().buckets();
            System.out.println("Lista de buckets da Nexus:");
            for (Bucket bucket : buckets) {
                System.out.println("- " + bucket.name());
=======
    public InputStream processS3Objects() {
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(BUCKET_NAME)
                    .build();

            ListObjectsV2Response listResponse;

            listResponse = s3Client.listObjectsV2(listRequest);

            System.out.println("---------------------------");
            System.out.println("Conexão com o S3 Feita com sucesso!\n");

            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                List<Tag> tags = getObjectTags(key);

                Boolean isBase = tags.stream()
                        .anyMatch(tag -> tag.key().equals("Tipo")
                                && tag.value().equals("EXCEL"));

                Boolean isRead = tags.stream()
                        .anyMatch(tag -> tag.key().equals("Status")
                                && tag.value().equals("LIDO"));

                if (isBase && !isRead) {
                    System.out.println("Objeto filtrado para leitura: " + key);
                    System.out.println("---------------------------");

                    keyObject = key;
                    return getObjectInputStream(key);
                }
>>>>>>> 207c5012b2ec8c7656d2a0bd403e3c44a6b4c3fb
            }
        } catch (S3Exception e) {
            String mensagem = "Erro ao listar buckets: " + e.getMessage();
            System.err.println();
            slackLogs.setMensagem(mensagem);
            slackLogs.sendNotification();
            System.out.println(mensagem);
        }

        // *   Listando objetos do bucket      *
        try {
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjects).contents();
            System.out.println("Objetos no bucket " + bucketName + ":");
            for (S3Object object : objects) {
                System.out.println("- " + object.key());
            }
        } catch (S3Exception e) {
            String mensagem = "Erro ao listar objetos no bucket: " + e.getMessage();
            slackLogs.setMensagem(mensagem);
            slackLogs.sendNotification();
            System.out.println(mensagem);
        }
    }

    public InputStream getExcelFileFromS3(String bucketName, String key) {
        S3Provider s3Provider = new S3Provider();
        S3Client s3Client = s3Provider.getS3Client();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key) // O caminho do arquivo no bucket
                .build();

        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
        return response; // Retorna o InputStream do arquivo
    }

}
