package com.dungpham.springboot_excel_import.service;


import com.dungpham.springboot_excel_import.domain.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelUploadService {
    public static boolean isValidExcelFile(MultipartFile file){
        // kiểm tra file có phải là file excel không
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<Customer> getCustomersDataFromExcel(InputStream inputStream){
        // đọc dữ liệu từ file excel và trả về list customer
        List<Customer> customers = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            // lấy sheet đầu tiên, có thể đề tên sheet vào đây để lấy sheet theo tên
            XSSFSheet sheet = workbook.getSheetAt(0);

            // lấy số dòng của sheet
            int rowIndex = 0;
            for(Row row : sheet){
                // bỏ qua dòng đầu tiên vì là tiêu đề
                if(rowIndex == 0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellIndex = 0;
                Customer customer = new Customer();
                while(cellIterator.hasNext()){
                    // lấy cell hiện tại
                    Cell cell = cellIterator.next();
                    // set giá trị cho customer với từng hàng là tuoong ứng với từng cell
                    switch (cellIndex){
                        case 0:
                            customer.setCustomerId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            customer.setFirstName(cell.getStringCellValue());
                            break;
                        case 2:
                            customer.setLastName(cell.getStringCellValue());
                            break;
                        case 3:
                            customer.setCountry(cell.getStringCellValue());
                            break;
                        case 4:
                            customer.setTelephone((int) cell.getNumericCellValue());
                            break;
                    }
                    // tăng index của cell
                    cellIndex++;
                }
                // thêm customer vào list
                customers.add(customer);

            }
        } catch (IOException e) {
            e.getStackTrace();
        }



        return customers;
    }

}
