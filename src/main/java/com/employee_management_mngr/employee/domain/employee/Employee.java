package com.employee_management_mngr.employee.domain.employee;

import java.time.LocalDateTime;

import com.employee_management_mngr.employee.domain.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String department;    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private String status;
    
    @Column(name = "assigned_by", nullable = false)
    private Integer assignedBy;

    @Column(name = "role_assigned_at", nullable = false)
    private LocalDateTime roleAssignedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
