package com.dungpham.springboot_excel_export_import;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "COURSE_DTLS")
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer cid;
    private String cname;
    private Double price;
}
