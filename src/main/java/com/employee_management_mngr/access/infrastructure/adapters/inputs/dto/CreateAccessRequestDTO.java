package com.employee_management_mngr.access.infrastructure.adapters.inputs.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessRequestDTO {
    private Integer employeeId;
    private List<Integer> systemIds;
    private Integer assignedById;
}
