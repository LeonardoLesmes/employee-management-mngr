package com.employee_management_mngr.access.domain;

import java.time.LocalDateTime;
import com.employee_management_mngr.employee.domain.employee.Employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "access_requests")
public class AccessRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id")
    private System system;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccessRequestStatus status;

    @Column(name = "request_date", nullable = true, updatable = false)
    private LocalDateTime requestDate;

    @Column(name = "assigned_by", nullable = false)
    private Integer assignedBy;

    @Column(name = "approved_by", nullable = true)
    private Integer approvedBy;

    @Column(name = "resolution_date", nullable = true)
    private LocalDateTime resolutionDate;

    @PrePersist
    protected void onCreate() {
        requestDate = LocalDateTime.now();
    }
}
