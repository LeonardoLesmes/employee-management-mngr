package com.employee_management_mngr.access.application.mappers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolePermissionMap {    
    public static class RolePermission {
        private final Integer roleId;
        private final List<Integer> allowedSystemIds;
        
        public RolePermission(Integer roleId, List<Integer> allowedSystemIds) {
            this.roleId = roleId;
            this.allowedSystemIds = allowedSystemIds;
        }
        
        public Integer getRoleId() {
            return roleId;
        }
        
        public List<Integer> getAllowedSystemIds() {
            return allowedSystemIds;
        }
    }
    
    private static final Map<Integer, List<Integer>> ROLE_PERMISSIONS = new HashMap<>();
    
    static {
        // DEV_JUNIOR
        ROLE_PERMISSIONS.put(23, Arrays.asList(2, 3, 5, 7, 9));
        // DEV_SEMI  
        ROLE_PERMISSIONS.put(24, Arrays.asList(2, 3, 5, 7, 9));
        // DEV_SENIOR
        ROLE_PERMISSIONS.put(25, Arrays.asList(2, 3, 5, 7, 8, 9));
        // TECH_LEAD
        ROLE_PERMISSIONS.put(26, Arrays.asList(2, 3, 5, 7, 8, 9));
        // QA_ANALYST
        ROLE_PERMISSIONS.put(27, Arrays.asList(2, 5, 7, 10));
        // QA_AUTOMATION
        ROLE_PERMISSIONS.put(28, Arrays.asList(2, 5, 7, 10));
        // DEVOPS_ENGINEER
        ROLE_PERMISSIONS.put(29, Arrays.asList(1, 4, 5, 7, 8, 9));
        // SRE
        ROLE_PERMISSIONS.put(30, Arrays.asList(1, 4, 5, 7, 8, 9));
        // UX_DESIGNER
        ROLE_PERMISSIONS.put(31, Arrays.asList(5, 6, 7));
        // UI_DESIGNER
        ROLE_PERMISSIONS.put(32, Arrays.asList(5, 6, 7));
        // PROJECT_MANAGER
        ROLE_PERMISSIONS.put(33, Arrays.asList(5, 6, 7, 8));
        // SCRUM_MASTER
        ROLE_PERMISSIONS.put(34, Arrays.asList(5, 7, 8, 9));
        // AGILE_COACH
        ROLE_PERMISSIONS.put(35, Arrays.asList(5, 7, 8, 9));
        // PRODUCT_OWNER
        ROLE_PERMISSIONS.put(36, Arrays.asList(5, 6, 7, 8));
    }
    
    public static List<Integer> getAllowedSystemIds(Integer roleId) {
        return ROLE_PERMISSIONS.getOrDefault(roleId, Arrays.asList());
    }
    
    public static boolean isSystemAllowedForRole(Integer roleId, Integer systemId) {
        List<Integer> allowedSystems = getAllowedSystemIds(roleId);
        return allowedSystems.contains(systemId);
    }
    
    public static boolean areSystemsAllowedForRole(Integer roleId, List<Integer> systemIds) {
        List<Integer> allowedSystems = getAllowedSystemIds(roleId);
        return allowedSystems.containsAll(systemIds);
    }
}
