package com.jonas.myp_sb.web.myp501;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/myp501")
@RestController
public class Myp501Resource {

    @Autowired
    Myp501Service myp501Service;

    @PostMapping("/uploadFileCheck")
    public Map<String,Object> uploadFileCheck(@RequestPart("form") Map<String,Object> form,
                             @RequestPart(name = "uploadFileData", required = false) MultipartFile uploadFile) {
        return myp501Service.uploadFileCheck(form,uploadFile);
    }

    @PostMapping("/uploadFile")
    public Map<String,Object> uploadFile(@RequestBody Map<String,Object> form) {
        return myp501Service.uploadFile(form);
    }

}
