package com.springboot.planning_poker.model.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static File transferToFile(MultipartFile file) throws IOException {
        File f = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        fos.close();
        return f;
    }
}
