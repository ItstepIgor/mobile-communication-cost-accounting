package com.importservice.service.impl;

import com.importservice.entity.Call;
import com.importservice.service.CallService;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class CallServiceImpl implements CallService {
    @Override
    public void create(Call call) {

    }

    @SneakyThrows
    public void readFromExcel(String file) {

//        FileInputStream file = new FileInputStream(new File(fileLocation));
//        Workbook workbook = new XSSFWorkbook(file);


        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("AllCall");
        XSSFRow row = myExcelSheet.getRow(1);

        Call call = new Call();

        String timePattern = "HH:mm:ss";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);
//        09.01.2023 08:44:06
        String dateTimePattern = "dd.MM.yyyy HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

        call.setOwnerNumber(row.getCell(0).getStringCellValue());
        call.setCallType(row.getCell(1).getStringCellValue());
//        System.out.println(row.getCell(2).getStringCellValue());
        call.setCallDateTime(LocalDateTime.parse(row.getCell(2).getStringCellValue(), dateTimeFormatter));
        call.setCode(row.getCell(3).getStringCellValue());
//        System.out.println(row.getCell(4).getStringCellValue());
        call.setCallTime(LocalTime.parse(row.getCell(4).getStringCellValue(), timeFormatter));
        call.setNumber(row.getCell(5).getStringCellValue());
        call.setSum(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
        call.setShortNumber((long) row.getCell(7).getNumericCellValue());
        call.setDayOfWeek((int) row.getCell(8).getNumericCellValue());

        System.out.println(call);
        myExcelBook.close();

    }

}
