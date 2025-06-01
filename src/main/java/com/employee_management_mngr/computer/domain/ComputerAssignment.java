package com.employee_management_mngr.computer.domain;

import java.time.LocalDateTime;
import com.employee_management_mngr.employee.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "computer_assignments")
public class ComputerAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id")
    private Computer computer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComputerAssignmentStatus status;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "resolution_date")
    private LocalDateTime resolutionDate;

    @Column(name = "assignment_date")
    private LocalDateTime assignmentDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private Employee assignedBy;
}
