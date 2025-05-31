package com.employee_management_mngr.employee.infrastructure.adapters.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.employee_management_mngr.employee.application.ports.output.ThirdPartyServicePort;
import com.employee_management_mngr.employee.domain.employee.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ThirdPartyServiceAdapter implements ThirdPartyServicePort {
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(ThirdPartyServiceAdapter.class);

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
        logger.info("Notifying third-party service about new employee: {}", employeeJson);
    }
}