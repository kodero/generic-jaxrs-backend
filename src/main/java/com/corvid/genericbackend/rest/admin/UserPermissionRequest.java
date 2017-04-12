package com.corvid.genericbackend.rest.admin;

import com.corvid.genericbackend.model.admin.UserPermission;
import com.corvid.genericdto.data.gdto.GenericDTO;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Widget for POSTING user permission json
 *
 * @author mokua
 */
@XmlRootElement
public class UserPermissionRequest implements Serializable {
    @NotNull
    private BigInteger permissionId;

    @NotNull
    private BigInteger userId;

    @NotNull
    private Boolean status = Boolean.FALSE;

    private String userName;

    private String permissionName;

    private String permissionDescription;

    private String modelType;

    private String namespace;


    public UserPermissionRequest() {
    }

    public UserPermissionRequest(BigInteger permissionId, BigInteger userId, Boolean status) {
        this.permissionId = permissionId;
        this.userId = userId;
        this.status = status;
    }

    public UserPermissionRequest(Long permissionId, Long userId, Boolean status) {
        this(BigInteger.valueOf(permissionId), BigInteger.valueOf(userId), status);
    }

    public UserPermissionRequest(BigInteger permissionId, BigInteger userId, Boolean status, String userName, String permissionName, String permissionDescription, String modelType, String namespace) {
        this.permissionId = permissionId;
        this.userId = userId;
        this.status = status;
        this.userName = userName;
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
        this.modelType = modelType;
        this.namespace = namespace;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public BigInteger getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(BigInteger permissionId) {
        this.permissionId = permissionId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public GenericDTO genericDTO() {
        GenericDTO dto = new GenericDTO(UserPermission.class.getName());
        dto.addBigInteger("permissionId", getPermissionId());
        dto.addBigInteger("userId", getUserId());
        dto.addBoolean("status", getStatus());
        dto.addString("userName", getUserName());
        dto.addString("permissionName", getPermissionName());
        dto.addString("permissionDescription", getPermissionDescription());
        dto.addString("modelType", getModelType());
        dto.addString("namespace", getNamespace());
        return dto;
    }
}
