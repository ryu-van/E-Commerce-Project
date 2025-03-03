package org.example.ecommerceproject.service;


import jakarta.servlet.ServletContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class UploadService {
    private final ServletContext context;

    public UploadService(ServletContext context) {
        this.context = context;
    }

    public String handleUploadFile(MultipartFile uploadFile, String targetFolder) {
        String rootPath = System.getProperty("user.dir") + "/src/main/resources/static/images";
        String finalName = "";

        try {
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            finalName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            File serverFile = new File(dir, finalName);
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(uploadFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalName;
    }

}
