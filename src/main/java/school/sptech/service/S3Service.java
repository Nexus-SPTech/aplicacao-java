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
    private static final String BUCKET_NAME = "nexus-group-bucket";
    private final S3Client s3Client = new S3Provider().getS3Client();
    private String keyObject;

    public S3Service() {
    }

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
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao processar objetos do bucket: " + e.getMessage());
        }
        return null;
    }

    private List<Tag> getObjectTags(String key) {
        GetObjectTaggingRequest getTaggingRequest = GetObjectTaggingRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();

        GetObjectTaggingResponse getTaggingResponse = s3Client.getObjectTagging(getTaggingRequest);
        return getTaggingResponse.tagSet();
    }

    public void setTagReadExcel() {
        List<Tag> tags = getObjectTags(keyObject);

        System.out.println("Tags antes da atualização: " + tags);
        tags.removeIf(tag -> tag.key().equals("Status"));
        tags.add(Tag.builder().key("Status").value("LIDO").build());
        System.out.println("Tags após a atualização: " + tags);

        PutObjectTaggingRequest putTaggingRequest = PutObjectTaggingRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyObject)
                .tagging(Tagging.builder().tagSet(tags).build())
                .build();

        s3Client.putObjectTagging(putTaggingRequest);

        s3Client.putObjectTagging(putTaggingRequest);
        System.out.println("Atualizando tag do Excel para \"LIDO\" no Bucket");
        System.out.println("---------------------------");
    }

    private InputStream getObjectInputStream(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
        return response; // Retorna o InputStream do Excel
    }
}