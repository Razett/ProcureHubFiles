package com.glkids.procurehubfiles.controller;

import com.glkids.procurehubfiles.entity.Import;
import com.glkids.procurehubfiles.entity.ImportFile;
import com.glkids.procurehubfiles.repository.ImportFileRepository;
import com.glkids.procurehubfiles.repository.ImportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final ImportFileRepository myFileRepository;
    private final ImportRepository importRepository;

    @GetMapping("myupload")
    public void myupload(Model m) {
        //db조회
        List<ImportFile> list = myFileRepository.findAll();
        m.addAttribute("list", list);
    }

    @PostMapping("/myuploadpro")
    public String myuploadPro(MultipartFile myfile) {
        String originalName = myfile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

        Path savePath = Paths.get("c:/upload/" + fileName);
        try {
            myfile.transferTo(savePath);
        } catch (Exception e) {
            System.out.println("파일쓰기 오류");
        }

        // 특정 importno를 가진 Import 객체 조회
        Long desiredImportno = 1L; // 원하는 importno 값을 Long 타입으로 지정
        Optional<Import> importOptional = importRepository.findByImportno(desiredImportno);

        Import importEntity;
        if (importOptional.isPresent()) {
            importEntity = importOptional.get();
        } else {
            // 기본 Import 객체 생성 (또는 오류 처리)
            importEntity = new Import();
            importRepository.save(importEntity); // 기본값을 DB에 저장 (필요시)
        }

        // ImportFile 엔티티 저장
        myFileRepository.save(ImportFile.builder()
                .name(fileName)
                .anImport(importEntity) // 조회한 Import 객체 사용
                .url(savePath.toString())
                .uuid(UUID.randomUUID().toString())
                .build());

        return "redirect:/myupload";
    }
}
