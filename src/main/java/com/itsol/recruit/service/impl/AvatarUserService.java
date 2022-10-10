package com.itsol.recruit.service.impl;

import com.itsol.recruit.service.mapper.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class AvatarUserService implements IStorageService {

    private final Path storageFolder= Paths.get("uploads");

//dở

    public AvatarUserService(){
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception){
            throw new RuntimeException("Cannot initialize storage",exception);
        }
    }

    private boolean isImageFile(MultipartFile file){
        String fileExtension= FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png","jpg","jpeg","bmp"}).contains(fileExtension.trim().toLowerCase());
    }



    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("abc");
            if (file.isEmpty()){
                throw new RuntimeException("Failed to store file");
            }

            //check file is image?
            if (!isImageFile(file)){
                throw new RuntimeException("Failed to store empty file");
            }
            //file must be <=5mb

            float fileSizeInMegabytes=file.getSize()/1_000_000.0f;
            if (fileSizeInMegabytes>5.0f){
                throw new RuntimeException("File must be <=5mb");
            }

            //file must be rename

            String fileExtension=FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName= UUID.randomUUID().toString().replace("-","");
            generatedFileName=generatedFileName+"."+fileExtension;
            Path destinationFilePath=this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();

            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException("Cannot store file outside current directory");
            }
            try(InputStream inputStream=file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return generatedFileName;

        }catch (IOException e){
            throw new RuntimeException("Failed to store file",e);
        }
    }

//    @Override
//    public byte[] readFileContent(String fileName){
//        try {
//            Path file=storageFolder.resolve(fileName);
//            Resource resource=new UrlResource(file.toUri());
//        }catch ()
//    }




    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }else {
                throw new RuntimeException(
                        "Could not read file:" +fileName
                );
            }

        } catch (java.io.IOException e) {
            throw new RuntimeException(
                    "Could not read file:" +fileName
            );
        }
    }


}
