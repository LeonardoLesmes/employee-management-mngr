package com.employee_management_mngr.employee.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(nullable = false)
    private String status;

    @Column(name = "assigned_by")
    private Integer assignedBy;

    @Column(name = "role_assigned_at")
    private LocalDateTime roleAssignedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
