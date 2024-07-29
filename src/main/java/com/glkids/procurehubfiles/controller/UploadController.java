package com.glkids.procurehubfiles.controller;

import com.glkids.procurehubfiles.entity.ImportFile;
import com.glkids.procurehubfiles.repository.ImportFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final ImportFileRepository myFileRepository;

    @GetMapping("myupload")
    public void myupload(Model m) {
        //db조회
        List<ImportFile> list = myFileRepository.findAll();
        m.addAttribute("list", list);
    }

    @PostMapping("myupload")
    public String myuploadPro(MultipartFile myfile) {
        String originalName = myfile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

        myFileRepository.save(ImportFile.builder().name(fileName).build());

        Path savePath = Paths.get("c:/upload/" + fileName);
        try {
            myfile.transferTo(savePath);
        } catch (Exception e) {
            System.out.println("파일쓰기 오류");
        }
        return "redirect:/myupload";
    }

    @GetMapping("/uploadEx")
    public void uploadEx() {

    }
}
