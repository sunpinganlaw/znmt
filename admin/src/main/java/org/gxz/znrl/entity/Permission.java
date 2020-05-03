package org.gxz.znrl.entity;

import java.io.Serializable;

public class Permission implements Serializable{
    /** 
	 * @description: 
	 * @version 1.0
	 * @author vincent
	 * @createDate 2014-1-19;下午10:50:26
	 */
	private static final long serialVersionUID = -4929576921462366464L;
	
	// 用于菜单显示
	public final static String PERMISSION_SHOW = "show";
	
	public final static String PERMISSION_CREATE = "save";
	
	public final static String PERMISSION_READ = "view";
	
	public final static String PERMISSION_UPDATE = "edit";
	
	public final static String PERMISSION_DELETE = "delete";

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_permission.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_permission.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_permission.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_permission.short_name
     *
     * @mbggenerated
     */
    private String shortName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_permission.module_id
     *
     * @mbggenerated
     */
    private Long moduleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_permission.id
     *
     * @return the value of security_permission.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_permission.id
     *
     * @param id the value for security_permission.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_permission.description
     *
     * @return the value of security_permission.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_permission.description
     *
     * @param description the value for security_permission.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_permission.name
     *
     * @return the value of security_permission.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_permission.name
     *
     * @param name the value for security_permission.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_permission.short_name
     *
     * @return the value of security_permission.short_name
     *
     * @mbggenerated
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_permission.short_name
     *
     * @param shortName the value for security_permission.short_name
     *
     * @mbggenerated
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_permission.module_id
     *
     * @return the value of security_permission.module_id
     *
     * @mbggenerated
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_permission.module_id
     *
     * @param moduleId the value for security_permission.module_id
     *
     * @mbggenerated
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    private Module module;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
    
}