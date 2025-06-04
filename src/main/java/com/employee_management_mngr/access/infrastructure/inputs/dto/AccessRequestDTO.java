package com.employee_management_mngr.access.infrastructure.inputs.dto;

import java.time.LocalDateTime;

import com.employee_management_mngr.access.domain.AccessRequest;
import com.employee_management_mngr.access.domain.AccessRequestStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessRequestDTO {
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private Integer systemId;
    private String systemName;
    private AccessRequestStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime resolutionDate;
    private Integer assignedById;

    public static AccessRequestDTO fromEntity(AccessRequest entity) {
        AccessRequestDTO dto = new AccessRequestDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setEmployeeName(entity.getEmployee().getName());
        dto.setSystemId(entity.getSystem().getId());
        dto.setSystemName(entity.getSystem().getName());
        dto.setStatus(entity.getStatus());
        dto.setRequestDate(entity.getRequestDate());
        dto.setResolutionDate(entity.getResolutionDate());
        dto.setAssignedById(entity.getAssignedBy());
        return dto;
    }
}
