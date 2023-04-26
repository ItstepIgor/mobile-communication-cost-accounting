package com.importservice.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;

public interface CallService {


    void createCall(XSSFWorkbook myExcelBook, LocalDate invoiceDate);
}
