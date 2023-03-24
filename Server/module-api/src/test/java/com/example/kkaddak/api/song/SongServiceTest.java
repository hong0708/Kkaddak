package com.example.kkaddak.api.song;

import com.example.kkaddak.api.utils.DynamicTimeWarping;
import com.example.kkaddak.api.utils.FeatureExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class SongServiceTest {

    @Autowired
    S3Client s3Client;
    @Test
    public void checkSongSimilarity() {

        // given
            // 미리 저장되어있는 mp3파일을 읽어온다.
        ClassPathResource resource1 = new ClassPathResource("file/Savior - Telecasted (1).mp3");
        ClassPathResource resource2 = new ClassPathResource("file/Savior - Telecasted (1).mp3");

        // when
        try {
            if (resource1.getFile() == null || resource2.getFile() == null) return;

            File mp3File1 = new File(resource1.getFile().getPath());
            File mp3File2 = new File(resource2.getFile().getPath());

            float[][] mfcc1 = FeatureExtractor.extractMFCC(mp3File1);
            float[][] mfcc2 = FeatureExtractor.extractMFCC(mp3File2);

            double similarity = DynamicTimeWarping.calculateDistance(mfcc1, mfcc2);
            // then
            // 동일한 음악이면 similarity가 0임 (0에 가까울수록 유사하다)
            Assertions.assertEquals(0L, similarity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkSongSimilarityWithS3() {

        // given
        String bucketName = "kkaddak";
        String key1 = "songs/Beckoning - Telecasted.mp3";
        String key2 = "songs/Beckoning - Telecasted.mp3";
        // when
        try {
            Path mp3Path1 = Files.createTempFile("Beckoning - Telecasted.mp3", ".mp3");
            Path mp3Path2 = Files.createTempFile("Beckoning - Telecasted.mp3", ".mp3");

            GetObjectRequest getObjectRequest1 = GetObjectRequest.builder().bucket(bucketName).key(key1).build();
            GetObjectRequest getObjectRequest2 = GetObjectRequest.builder().bucket(bucketName).key(key2).build();

            try (ResponseInputStream<GetObjectResponse> inputStream1 = s3Client.getObject(getObjectRequest1)) {
                Files.copy(inputStream1, mp3Path1, StandardCopyOption.REPLACE_EXISTING);
            }

            try (ResponseInputStream<GetObjectResponse> inputStream2 = s3Client.getObject(getObjectRequest2)) {
                Files.copy(inputStream2, mp3Path2, StandardCopyOption.REPLACE_EXISTING);
            }

            float[][] mfcc1 = FeatureExtractor.extractMFCC(mp3Path1.toFile());
            float[][] mfcc2 = FeatureExtractor.extractMFCC(mp3Path2.toFile());

            double similarity = DynamicTimeWarping.calculateDistance(mfcc1, mfcc2);
            // then
            // 동일한 음악이면 similarity가 0임 (0에 가까울수록 유사하다)
            Assertions.assertEquals(0L, similarity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
