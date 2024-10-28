package school.sptech.service;

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

    public void getConnectionS3(){

        String bucketName = "nexus-group-bucket";

        // *************************************
        // *   Listando todos os buckets       *
        // *************************************
        try {
            List<Bucket> buckets = s3Client.listBuckets().buckets();
            System.out.println("Lista de buckets da Nexus:");
            for (Bucket bucket : buckets) {
                System.out.println("- " + bucket.name());
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao listar buckets: " + e.getMessage());
        }

        // *************************************
        // *   Listando objetos do bucket      *
        // *************************************
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
            System.err.println("Erro ao listar objetos no bucket: " + e.getMessage());
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
