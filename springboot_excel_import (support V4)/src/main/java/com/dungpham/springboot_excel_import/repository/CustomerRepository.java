package com.dungpham.springboot_excel_import.repository;

import com.dungpham.springboot_excel_import.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
