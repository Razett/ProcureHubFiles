package com.glkids.procurehubfiles.controller;

import com.glkids.procurehubfiles.entity.QuotationFile;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final HttpServletResponse httpServletResponse;

    private final String savePath = "/home/mit/upload/";
    private final RestTemplate restTemplate;

    @PostMapping("/{folder}/uploadFile")
    public ResponseEntity<QuotationFile> uploadFile(@RequestParam("file") MultipartFile[] uploadFiles, @PathVariable String folder) {

        String originalFileName = "";
        String uuid = "";
        String saveFileName = "";

        for (MultipartFile file : uploadFiles) {
            try {
                originalFileName = file.getOriginalFilename();
                uuid = UUID.randomUUID().toString();
                saveFileName = uuid + "_" + originalFileName;

                // 파일 저장 경로
                String saveFullPath = savePath + folder + "/" + saveFileName;
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


            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null);
            }
        }
        return ResponseEntity.ok(QuotationFile.builder().name(originalFileName).url(savePath).uuid(uuid).build());
    }
    @GetMapping("/{folder}/display")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("file") String fileName, @PathVariable String folder) {
        ResponseEntity<byte[]> result = null;
        try {
            // 파일 이름을 UTF-8로 디코딩
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            // 파일 경로 생성
            File file = new File(savePath + folder + File.separator + srcFileName);

            // HTTP 헤더 생성
            HttpHeaders header = new HttpHeaders();

            // 파일의 MIME 타입 설정
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            // 파일 내용을 바이트 배열로 변환하여 HTTP 응답 본문으로 설정
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            // 오류가 발생한 경우 500 상태 코드 반환
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
