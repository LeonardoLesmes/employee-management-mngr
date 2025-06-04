package com.employee_management_mngr.access.infrastructure.adapters.inputs.dto;

import com.employee_management_mngr.access.domain.AccessRequestStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccessRequestStatusDTO {
    private AccessRequestStatus status;
}
