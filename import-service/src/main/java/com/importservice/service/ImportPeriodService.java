package com.importservice.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public interface ImportPeriodService {

    void saveImportPeriod (XSSFWorkbook myExcelBook);

}
