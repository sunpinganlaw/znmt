package org.gxz.znrl.entity.newsystem;

import java.util.List;

public class RoleEntity extends BaseEntity {

    private Long id;

    private String description;

    private String name;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    private List<org.gxz.znrl.entity.RolePermission> rolePermissions;

	public List<org.gxz.znrl.entity.RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<org.gxz.znrl.entity.RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}