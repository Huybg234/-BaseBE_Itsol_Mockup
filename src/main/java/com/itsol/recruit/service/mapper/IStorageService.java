package com.itsol.recruit.service.mapper;

import com.itsol.recruit.core.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public String storeFile(MultipartFile file);

    byte[] readFileContent(String fileName);

}
