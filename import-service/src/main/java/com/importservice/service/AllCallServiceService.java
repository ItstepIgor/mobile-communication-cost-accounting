package com.importservice.service;

import com.importservice.dto.AllCallServiceDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.util.List;

public interface AllCallServiceService {

    void createCallService(XSSFWorkbook myExcelBook, LocalDate invoiceDate);

    List<AllCallServiceDTO> findAllByDate(LocalDate date);
}
