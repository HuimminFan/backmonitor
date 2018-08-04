package com.fhm.backmonitor.service;


import com.fhm.backmonitor.entity.DetailReport;

import java.sql.Time;
import java.util.List;

/**
 * Created by fhm on 2018/7/23.
 */

public interface DetailReportService {
    List<DetailReport> getDetailReportList(Time createTime);
}
