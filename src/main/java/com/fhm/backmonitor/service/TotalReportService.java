package com.fhm.backmonitor.service;


import com.fhm.backmonitor.entity.TotalReport;

import java.sql.Time;
import java.util.List;

/**
 * Created by fhm on 2018/7/23.
 */

public interface TotalReportService {
    List<TotalReport> getTotalReportList(Time createTime);
}
