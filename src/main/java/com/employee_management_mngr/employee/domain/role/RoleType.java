package com.employee_management_mngr.employee.domain.role;

import lombok.Getter;

@Getter
public enum RoleType {
    // Admin roles
    ADMIN("ADMIN", true),
    IT("IT", true),
    
    // Leadership roles
    LEADER_DEV("LEADER_DEV", true),
    LEADER_QA("LEADER_QA", true),
    LEADER_PRODUCT("LEADER_PRODUCT", true),
    LEADER_AGILE("LEADER_AGILE", true),
    LEADER_UX("LEADER_UX", true),
    LEADER_DEVOPS("LEADER_DEVOPS", true),
    
    // Development roles
    DEV_JUNIOR("DEV_JUNIOR"),
    DEV_SEMI("DEV_SEMI"),
    DEV_SENIOR("DEV_SENIOR"),
    TECH_LEAD("TECH_LEAD"),
    
    // QA roles
    QA_ANALYST("QA_ANALYST"),
    QA_AUTOMATION("QA_AUTOMATION"),
    
    // DevOps roles
    DEVOPS_ENGINEER("DEVOPS_ENGINEER"),
    SRE("SRE"),
    
    // Design roles
    UX_DESIGNER("UX_DESIGNER"),
    UI_DESIGNER("UI_DESIGNER"),
    
    // Project Management roles
    PROJECT_MANAGER("PROJECT_MANAGER"),
    SCRUM_MASTER("SCRUM_MASTER"),
    AGILE_COACH("AGILE_COACH"),
    PRODUCT_OWNER("PRODUCT_OWNER");

    private final String name;
    private final boolean canLogin;

    RoleType(String value) {
        this.name = value;
        this.canLogin = false;
    }

    RoleType(String value, boolean canLogin) {
        this.name = value;
        this.canLogin = canLogin;
    }
}