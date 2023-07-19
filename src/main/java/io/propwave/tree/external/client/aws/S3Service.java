package io.propwave.tree.external.client.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.propwave.tree.exception.model.BadRequestException;
import io.propwave.tree.exception.model.NotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.region}")
    private String region;

    @PostConstruct
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public String uploadImage(MultipartFile multipartFile, String folder) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket+"/"+ folder + "/image", fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket+"/"+ folder + "/image", fileName).toString();
        } catch(IOException e) {
            throw new NotFoundException("이미지 저장에 실패했습니다.");
        }
    }

    public void deleteFile(String imageUrl) {
        String imageKey = imageUrl.substring(53);
        amazonS3.deleteObject(bucket, imageKey);
    }

    // 파일명 (중복 방지)
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new NotFoundException("이미지 저장에 실패했습니다.");
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new BadRequestException("허용되지 않은 파일 확장자입니다.");
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
