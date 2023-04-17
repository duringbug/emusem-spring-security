package tjf.emuseum.emuseum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tjf.emuseum.emuseum.service.Interface.FileStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/17 15:19
 * @description:
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Qualifier("fileStorageImpl1")
    @Autowired
    private FileStorage fileStorage;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody MultipartFile file){
         Map<String, String> responseData = new HashMap<>();
         responseData.put("path",fileStorage.uploadFile(file));
         return ResponseEntity.ok(responseData);
    }
    @GetMapping("/download")
    public byte[] download(String path) throws IOException {
        return fileStorage.downLoadFile(path);
    }
}
