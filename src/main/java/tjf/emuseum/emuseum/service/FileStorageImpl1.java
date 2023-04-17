package tjf.emuseum.emuseum.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import tjf.emuseum.emuseum.entity.MinioProp;
import tjf.emuseum.emuseum.service.Interface.FileStorage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/17 15:02
 * @description:
 */
@Service
public class FileStorageImpl1 implements FileStorage {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProp minioProp;
    private final static String separator = "/";

    /**
     * @param dirPath
     * @param filename yyyy/mm/dd/file.jpg
     * @return
     */
    public String builderFilePath(String dirPath, String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if (!ObjectUtils.isEmpty(dirPath)) {
            stringBuilder.append(dirPath).append(separator);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(separator);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    /**
     * 上传图片文件
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    @Override
    public String uploadImgFile(String prefix, String filename, InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minioProp.getBucket()).stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minioProp.getReadpath());
            urlPath.append(separator + minioProp.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        } catch (Exception ex) {
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream) {
        return null;
    }

    /**
     * 上传pdf文件
     *
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {

        // 获取文件的名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件的前缀
        String caselsh = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        String filePath = builderFilePath(caselsh, originalFilename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(file.getContentType())
                    .bucket(minioProp.getBucket()).stream(file.getInputStream(), file.getInputStream().available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minioProp.getReadpath());
            urlPath.append(separator + minioProp.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("上传文件失败:" + ex.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param pathUrl 文件全路径
     */
    @Override
    public void delete(String pathUrl) {
        String key = pathUrl.replace(minioProp.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("minio delete file error.  pathUrl:{" + pathUrl + "}");
        }
    }

    /**
     * 下载文件
     *
     * @param pathUrl 文件全路径
     * @return 文件流
     */
    @Override
    public byte[] downLoadFile(String pathUrl) throws IOException {
        String key = pathUrl.replace(minioProp.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProp.getBucket())
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("minio download file error.  pathUrl:{" + pathUrl + "}"+e.getMessage());
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while (true) {
            try {
                if (!((rc = inputStream.read(buff, 0, 100)) > 0))
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public List<Bucket> listBucket(){
        try {
            return minioClient.listBuckets();
        } catch (ErrorResponseException | InvalidKeyException | InsufficientDataException | InternalException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }
}
