package com.springsecuritywithjwt.springsecuritywithjwt.service.impl;

import com.springsecuritywithjwt.springsecuritywithjwt.entity.Role;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.repository.UserRepository;
import com.springsecuritywithjwt.springsecuritywithjwt.service.ImportExportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImportExportServiceImpl implements ImportExportService {

    private final UserRepository userRepository;

    // tạo file excel từ dữ liệu trong database
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

    // kiểm tra file có phải là file excel không
    @Override
    public boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // lấy dữ liệu từ file excel
    @Override
    public List<User> getUsersDataFromExcel(InputStream inputStream) {
        // đọc dữ liệu từ file excel và trả về list user
        List<User> users = new ArrayList<>();

        try{
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

                User user = new User();

                // Lấy giá trị từ mỗi ô và xử lý tương ứng với kiểu dữ liệu
                // không cần id vì id tự tăng
                while(cellIterator.hasNext()){
                    // lấy cell hiện tại
                    Cell cell = cellIterator.next();
                    // set giá trị cho customer với từng hàng là tuoong ứng với từng cell
                    // giá trị này phải trùng với số thứ tự trong file excel, bắt đầu từ số 0 là role rồi email, firstname, lastname, password
                    switch (cellIndex){
                        case 0:
                            try {
                                Role role = Role.valueOf(cell.getStringCellValue().toUpperCase()); // Chuyển đổi cellValue thành enum Role
                                user.setRole(role);
                            } catch (IllegalArgumentException e) {
                                // Nếu cellValue không phù hợp với bất kỳ giá trị enum nào, mặc định là ROLE_USER
                                user.setRole(Role.USER);
                            }
                            break;
                        case 1:
                            user.setEmail(cell.getStringCellValue());
                            break;
                        case 2:
                            user.setFirstName(cell.getStringCellValue());
                            break;
                        case 3:
                            user.setLastName(cell.getStringCellValue());
                            break;
                        case 4:
                            // nếu cell là string thì mã hóa password, nếu chỉ bao gồm số thì apache poi sẽ biến nó
                            // thành numering nên không thể xử lý như string được
                            if(cell.getCellType() == CellType.STRING){
                                user.setPassword(new BCryptPasswordEncoder().encode(cell.getStringCellValue()));
                            }else if(cell.getCellType() == CellType.NUMERIC){
                                user.setPassword(new BCryptPasswordEncoder().encode(String.valueOf(cell.getNumericCellValue())));
                            }
                            break;
                    }
                    // tăng index của cell
                    cellIndex++;
                }


                // thêm user vào list
                users.add(user);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }

        return users;
    }



}
