package com.fhm.backmonitor.repository;

import com.fhm.backmonitor.entity.DetailReport;
import com.fhm.backmonitor.entity.MonitorItemTimeMultiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;

/**
 * Created by fhm on 2018/7/24.
 */
public interface DetailReportRepository extends JpaRepository<DetailReport,MonitorItemTimeMultiKey> {
    // monitorId,找出所有详细监控报告
    List<DetailReport> findByMonitorId(Long monitorId);

    // 根据监控执行时间,找出所有详细监控报告
    List<DetailReport> findByCreateTime(Time createTime);

    // 根据monitorId和监控执行开始时间，找出所有的DetailReport
    List<DetailReport> findByMonitorIdAndCreateTime(Long monitorId, Time createTime);
}
