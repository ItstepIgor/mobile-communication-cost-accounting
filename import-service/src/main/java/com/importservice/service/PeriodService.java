package com.importservice.service;

import com.importservice.xml.ReportMTS;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public interface PeriodService {

    void saveImportPeriodA1(XSSFWorkbook myExcelBook);

    void saveImportPeriodMTS(ReportMTS reportMTS);
}
