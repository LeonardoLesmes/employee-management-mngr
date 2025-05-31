package com.employee_management_mngr.employee.infrastructure.adapters.output;

import org.springframework.stereotype.Component;

import com.employee_management_mngr.employee.application.ports.output.ThirdPartyServicePort;
import com.employee_management_mngr.employee.domain.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ThirdPartyServiceAdapter implements ThirdPartyServicePort {
    private final ObjectMapper objectMapper;

    public ThirdPartyServiceAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void notifyNewEmployee(Employee employee) {
        String employeeJson;
        try {
            employeeJson = objectMapper.writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            employeeJson = "{}";
        }
        System.out.println("Notifying third-party service about new employee: " + employeeJson);
    }
}