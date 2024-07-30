package com.glkids.procurehubfiles.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

@RestController
public class FileUploadController {

    @Value("${main.server.url}")
    private String mainServerUrl;

    private final RestTemplate restTemplate;

    public FileUploadController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/uploadAjax")
    public ResponseEntity<String> uploadFile(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        for (MultipartFile file : uploadFiles) {
            try {
                String originalFileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveFileName = uuid + "_" + originalFileName;

                // 파일 저장 경로
                String savePath = "C:/upload" + saveFileName;
                File saveFile = new File(savePath);
                file.transferTo(saveFile);

                // 파일 저장 정보 생성
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("name", originalFileName);
                System.out.println(originalFileName);
                fileInfo.put("url", savePath);
                System.out.println(savePath);
                fileInfo.put("uuid", uuid);
                System.out.println(uuid);
                fileInfo.put("regdate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                fileInfo.put("moddate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                fileInfo.put("quotation_qtno", "여기에_견적_번호를_넣으세요"); // 필요 시 설정

                // 메인 서버에 파일 정보 전송
                ResponseEntity<String> response = restTemplate.postForEntity(
                        mainServerUrl + "/contractor/quoregister",
                        fileInfo,
                        String.class
                );
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("File info sent to main server successfully.");
                } else {
                    System.out.println("Failed to send file info to main server.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("File upload failed for: " + file.getOriginalFilename());
            }
        }
        return ResponseEntity.ok("File uploaded and info sent to main server.");
    }
}
