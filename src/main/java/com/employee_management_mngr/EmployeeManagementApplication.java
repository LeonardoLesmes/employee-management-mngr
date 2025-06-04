package com.employee_management_mngr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "com.employee_management_mngr.auth.domain", "com.employee_management_mngr.employee.domain",
        "com.employee_management_mngr.system.domain", "com.employee_management_mngr.access.domain",
        "com.employee_management_mngr.computer.domain", })
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

}
