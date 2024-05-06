package com.dungpham.springboot_excel_import.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String country;
    private Integer telephone;
}
