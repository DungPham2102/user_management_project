package com.springsecuritywithjwt.springsecuritywithjwt.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ImportExportService {

    void generateExcel(HttpServletResponse response) throws IOException;

}
