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

    public InputStream processS3Objects() {
        String bucketName = "nexus-group-bucket";

        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listResponse;

            listResponse = s3Client.listObjectsV2(listRequest);
            for (S3Object object : listResponse.contents()) {
                String key = object.key();
                List<Tag> tags = getObjectTags(bucketName, key);

                Boolean isBase = tags.stream()
                        .anyMatch(tag -> tag.key().equals("Tipo")
                                && tag.value().equals("BASE"));

                Boolean isRead = tags.stream()
                        .anyMatch(tag -> tag.key().equals("Status")
                                && tag.value().equals("LIDO"));

                if (isBase && !isRead) {
                    System.out.println("Objeto filtrado para leitura: " + key);

                    return getObjectInputStream(bucketName, key);
                }
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao processar objetos do bucket: " + e.getMessage());
        }
        return null;
    }

    public List<Tag> getObjectTags(String bucketName, String key) {
        GetObjectTaggingRequest getTaggingRequest = GetObjectTaggingRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectTaggingResponse getTaggingResponse = s3Client.getObjectTagging(getTaggingRequest);
        return getTaggingResponse.tagSet();
    }

    public void updateObjectTag(String bucketName, String key, String tagKey, String tagValue) {
        List<Tag> tags = getObjectTags(bucketName, key);

        tags.removeIf(tag -> tag.key().equals(tagKey));
        tags.add(Tag.builder().key(tagKey).value(tagValue).build());

        PutObjectTaggingRequest putTaggingRequest = PutObjectTaggingRequest.builder()
                .bucket(bucketName)
                .key(key)
                .tagging(Tagging.builder().tagSet(tags).build())
                .build();

        s3Client.putObjectTagging(putTaggingRequest);
    }

    public InputStream getObjectInputStream(String bucketName, String key) {
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
