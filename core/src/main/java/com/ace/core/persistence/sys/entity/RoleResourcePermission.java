package com.ace.core.persistence.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RoleResourcePermission implements Serializable {
    private Long id;

    private Long roleId;

    private Long resourceId;

    private Set<Long> permissionIds;

    private Role role;

    public RoleResourcePermission() {}

    public RoleResourcePermission(Long roleId, Long resourceId) {
        this(roleId, resourceId, null);
    }

    public RoleResourcePermission(Long roleId, Long resourceId, Set<Long> permissionIds) {
        if (permissionIds == null) {
            permissionIds = new HashSet<Long>();
            permissionIds.add(1l);
        }
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.permissionIds = permissionIds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Set<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "RoleResourcePermission{id=" + this.getId() +
                ",roleId=" + (role != null ? role.getId() : "null") +
                ", resourceId=" + resourceId +
                ", permissionIds=" + permissionIds +
                '}';
    }
}