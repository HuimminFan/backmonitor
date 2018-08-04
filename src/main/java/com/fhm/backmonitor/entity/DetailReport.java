package com.fhm.backmonitor.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by fhm on 2018/7/24.
 */


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "detail_report")
@IdClass(MonitorItemTimeMultiKey.class)
public class DetailReport {
    private Long monitorId; //复合主键


    private Boolean code;   //成功、失败
    private String message; //报警信息

    private Time createTime;//复合主键,数据插入时间

    private String uuid;    //外键
    private TotalReport totalReport;


    @Id
    @Column(name = "monitor_id")
    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    @Id
    @Column(name = "create_time")
    @CreatedDate
    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    @Column(length = 100)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name="uuid",insertable=false,updatable=false,length = 100)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JoinColumn(name = "uuid")
    @ManyToOne(fetch = FetchType.LAZY)
    public TotalReport getTotalReport() {
        return totalReport;
    }

    public void setTotalReport(TotalReport totalReport) {
        this.totalReport = totalReport;
    }
}
