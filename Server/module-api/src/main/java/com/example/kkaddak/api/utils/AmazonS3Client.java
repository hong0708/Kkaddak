package com.example.kkaddak.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AmazonS3Client {

    private final S3Client s3Client;
    private final String bucketName;
    private final Region region;


    public AmazonS3Client(@Value("${aws.s3.region}") String region,
                          @Value("${aws.s3.access-key-id}") String accessKeyId,
                          @Value("${aws.s3.secret-access-key}") String secretAccessKey,
                          @Value("${aws.s3.bucket-name}") String bucketName) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        this.region = Region.of(region);

        this.s3Client = S3Client.builder()
                .region(this.region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        this.bucketName = bucketName;
    }

    public String uploadFile(String prefix, String fileName, InputStream inputStream) {
        String key = prefix + "/" + generateTimestampedFileName(fileName);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        RequestBody requestBody = RequestBody.fromInputStream(inputStream, -1);
        PutObjectResponse response = s3Client.putObject(putObjectRequest, requestBody);

        return "https://" + bucketName + ".s3." + this.region + ".amazonaws.com/" + key;
    }

    private String generateTimestampedFileName(String originalFilename) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);

        Path path = Paths.get(originalFilename);
        String extension = path.getFileName().toString();
        return timestamp + "_" + extension;
    }
}
