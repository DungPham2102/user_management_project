package com.dungpham.springboot_excel_import.service;

import com.dungpham.springboot_excel_import.domain.Customer;
import com.dungpham.springboot_excel_import.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    // lưu dữ liệu từ file excel vào database
    public void saveCustomerToDatabase(MultipartFile file) {
        if(ExcelUploadService.isValidExcelFile(file)){
            try {
                // lấy dữ liệu từ file excel
                List<Customer> customers = ExcelUploadService.getCustomersDataFromExcel(file.getInputStream());
                // lưu dữ liệu vào database
                customerRepository.saveAll(customers);
            } catch (IOException e) {
                throw new RuntimeException("The file is not a valid excel file");
            }
        }
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

}
