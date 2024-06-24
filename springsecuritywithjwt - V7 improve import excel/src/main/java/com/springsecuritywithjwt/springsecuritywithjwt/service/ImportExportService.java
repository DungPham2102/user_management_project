package com.springsecuritywithjwt.springsecuritywithjwt.service;

import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public interface ImportExportService {

    void generateExcel(HttpServletResponse response) throws IOException;


    boolean isValidExcelFile(MultipartFile file);

    List<User> getUsersDataFromExcel(InputStream inputStream);

}
