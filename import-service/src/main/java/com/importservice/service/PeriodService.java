package com.importservice.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public interface PeriodService {

    void saveImportPeriod (XSSFWorkbook myExcelBook);

}
