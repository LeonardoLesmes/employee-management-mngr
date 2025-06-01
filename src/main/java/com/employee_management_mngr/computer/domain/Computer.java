package com.employee_management_mngr.computer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "computers")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String model;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComputerStatus status;

    @Column(nullable = false)
    private String specs;
}
