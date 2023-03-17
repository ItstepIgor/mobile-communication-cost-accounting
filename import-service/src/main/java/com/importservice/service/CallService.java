package com.importservice.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface CallService {


    void createCall(XSSFWorkbook myExcelBook);
}
