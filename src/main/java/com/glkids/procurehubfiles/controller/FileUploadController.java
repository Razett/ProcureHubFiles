package com.glkids.procurehubfiles.controller;

import com.glkids.procurehubfiles.entity.QuotationFile;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final HttpServletResponse httpServletResponse;
    @Value("${main.server.url}")
    private String mainServerUrl;

    private final String savePath = "C:/upload/";
    private final RestTemplate restTemplate;

    @PostMapping("/uploadFile")
    public ResponseEntity<QuotationFile> uploadFile(@RequestParam("file") MultipartFile[] uploadFiles) {

        String originalFileName = "";
        String uuid = "";
        String saveFileName = "";

        for (MultipartFile file : uploadFiles) {
            try {
                originalFileName = file.getOriginalFilename();
                uuid = UUID.randomUUID().toString();
                saveFileName = uuid + "_" + originalFileName;

                // 파일 저장 경로
                String saveFullPath = savePath + saveFileName;
                File saveFile = new File(saveFullPath);
                file.transferTo(saveFile);

                // 파일 저장 정보 생성
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("name", originalFileName);
                System.out.println(originalFileName);
                fileInfo.put("url", saveFullPath);
                System.out.println(saveFullPath);
                fileInfo.put("uuid", uuid);
                System.out.println(uuid);
                fileInfo.put("regdate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                fileInfo.put("moddate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

//                // 메인 서버에 파일 정보 전송
//                ResponseEntity<String> response = restTemplate.postForEntity(
//                        mainServerUrl + "/contractor/quoregister",
//                        fileInfo,
//                        String.class
//                );

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        }
        System.out.println("멈춘시간속 잠든 너를 찾아가 아무링 ㅐ써 도 결국 너으 ㅣ곁이걸");
        return ResponseEntity.ok(QuotationFile.builder().name(originalFileName).url(savePath).uuid(uuid).build());
    }
}
