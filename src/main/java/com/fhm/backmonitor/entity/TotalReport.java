package com.fhm.backmonitor.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by fhm on 2018/7/24.
 */
@Entity
@Table(name = "total_report")
public class TotalReport {

    private String uuid;    //主键

    private Long srpId;     //
    private Integer monitorNum; //监控项个数
    private Integer errorCount; //失败数

    private Date startTime;     //复合主键,监控开始执行时间
    private Date endTime;

    private List<DetailReport> detailReports;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 100)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    //    @Id
    @Column(name = "srp_id")
    public Long getSrpId() {
        return srpId;
    }

    public void setSrpId(Long srpId) {
        this.srpId = srpId;
    }

//    @Id
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "monitor_num")
    public Integer getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(Integer monitorNum) {
        this.monitorNum = monitorNum;
    }

    @Column(name = "error_count")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "totalReport")
    public List<DetailReport> getDetailReports() {
        return detailReports;
    }

    public void setDetailReports(List<DetailReport> detailReports) {
        this.detailReports = detailReports;
    }

    @Override
    public String toString() {
        return "TotalReport{" +
                "uuid='" + uuid + '\'' +
                ", srpId=" + srpId +
                ", monitorNum=" + monitorNum +
                ", errorCount=" + errorCount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
