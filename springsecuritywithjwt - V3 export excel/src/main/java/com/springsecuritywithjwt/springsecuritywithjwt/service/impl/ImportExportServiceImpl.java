package com.springsecuritywithjwt.springsecuritywithjwt.service.impl;

import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.repository.UserRepository;
import com.springsecuritywithjwt.springsecuritywithjwt.service.ImportExportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ImportExportServiceImpl implements ImportExportService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {

        // đưa tất cả user vào 1 list
        List<User> users = userRepository.findAll();

        // tạo workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // tạo sheet
        HSSFSheet sheet = workbook.createSheet("Users Info");

        // tạo row đầu tiên chứa các đề mục
        HSSFRow row = sheet.createRow(0);

        // thêm các đề mục vào row đầu tiên
        row.createCell(0).setCellValue("User ID");
        row.createCell(1).setCellValue("First Name");
        row.createCell(2).setCellValue("Last Name");
        row.createCell(3).setCellValue("Email");
        row.createCell(4).setCellValue("Role");

        // tạo index để thêm dữ liệu
        int dataRowIndex = 1;

        // thêm dữ liệu vào sheet
        for(User user : users) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex++);
            dataRow.createCell(0).setCellValue(user.getId());
            dataRow.createCell(1).setCellValue(user.getFirstName());
            dataRow.createCell(2).setCellValue(user.getLastName());
            dataRow.createCell(3).setCellValue(user.getEmail());
            dataRow.createCell(4).setCellValue(user.getRole().name());
        }

        // tạo output stream để ghi file
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        // đóng workbook và output stream
        workbook.close();
        ops.close();

    }


}
