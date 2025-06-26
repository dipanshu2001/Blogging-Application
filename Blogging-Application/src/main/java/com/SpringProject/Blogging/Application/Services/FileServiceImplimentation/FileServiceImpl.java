package com.SpringProject.Blogging.Application.Services.FileServiceImplimentation;

import com.SpringProject.Blogging.Application.Services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // File name
        String name=file.getOriginalFilename();
        // abc.png
        String randomId= UUID.randomUUID().toString();
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));
        //Full path
        String filePath=path+ File.separator+fileName1;
        // Create folder if not created
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        //db logic to return inputstream
        return new FileInputStream(fullPath);
    }
}
