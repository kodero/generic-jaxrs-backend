package com.corvid.genericbackend.model.admin;

import com.corvid.genericbackend.model.base.ModelBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by kodero on 12/30/14.
 */
@Entity
@Table(name = "configs")
public class Config extends ModelBase {

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String companyAddress;

    @Column(name = "update_days")
    private Integer updateDays;

    @Column(name = "last_audit_date")
    private Date lastAuditDate;

    //backup
    @Column(name = "backup_dir")
    private String backupDir;

    @Column(name = "dump_command")
    private String dumpCommand;

    @Column(name = "restore_command")
    private String restoreCommand;

    @Column(name = "backup_prefix")
    private String backupPrefix;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Integer getUpdateDays() {
        return updateDays;
    }

    public void setUpdateDays(Integer updateDays) {
        this.updateDays = updateDays;
    }

    public Date getLastAuditDate() {
        return lastAuditDate;
    }

    public void setLastAuditDate(Date lastAuditDate) {
        this.lastAuditDate = lastAuditDate;
    }

    public String getBackupDir() {
        return backupDir;
    }

    public void setBackupDir(String backupDir) {
        this.backupDir = backupDir;
    }

    public String getDumpCommand() {
        return dumpCommand;
    }

    public void setDumpCommand(String dumpCommand) {
        this.dumpCommand = dumpCommand;
    }

    public String getRestoreCommand() {
        return restoreCommand;
    }

    public void setRestoreCommand(String restoreCommand) {
        this.restoreCommand = restoreCommand;
    }

    public String getBackupPrefix() {
        return backupPrefix;
    }

    public void setBackupPrefix(String backupPrefix) {
        this.backupPrefix = backupPrefix;
    }
}
