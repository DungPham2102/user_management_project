package com.dungpham.springboot_excel_import.controller;

import com.dungpham.springboot_excel_import.domain.Customer;
import com.dungpham.springboot_excel_import.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file")MultipartFile file){
        customerService.saveCustomerToDatabase(file);
        return ResponseEntity
                .ok(Map.of("message", "Uploaded successfully"));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.FOUND);
    }

}
