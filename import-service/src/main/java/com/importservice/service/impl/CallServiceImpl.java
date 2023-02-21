package com.importservice.service.impl;

import com.importservice.entity.Call;
import com.importservice.entity.ImportCallService;
import com.importservice.reposiitory.CallRepository;
import com.importservice.reposiitory.ImportCallServiceRepository;
import com.importservice.service.CallService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallServiceImpl implements CallService {


    private final CallRepository callRepository;
    private final ImportCallServiceRepository importCallServiceRepository;

    //TODO Удалить после настройки импорта
    @SneakyThrows
    @Transactional
    public void createFromFile(String file) {
        System.out.println(LocalDateTime.now());
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        importCallServiceRepository.saveAll(readServiceFromExcel(myExcelBook));
    }

    private List<ImportCallService> readServiceFromExcel(XSSFWorkbook myExcelBook) {
        List<ImportCallService> calls = new ArrayList<>();
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Page4");
        String ownerNumberTemp = null;

        for (int i = 7; i < myExcelSheet.getLastRowNum(); i++) {

            XSSFRow row = myExcelSheet.getRow(i);

            if ((row == null) || (row.getCell(0).getCellType() == CellType.BLANK)
                    || (row.getCell(0).getStringCellValue().equals("Итого начислений по абоненту"))
                    || (row.getCell(0).getStringCellValue()
                    .equals("Итого начислений по абоненту с учётом округлений"))) {
                continue;
            } else if (row.getCell(0).getStringCellValue().equals("Абонент: ")) {
                ownerNumberTemp = row.getCell(1).getStringCellValue();
                continue;
            }
            ImportCallService callService = new ImportCallService();
            callService.setOwnerNumber(ownerNumberTemp);
            callService.setCallServiceName(row.getCell(0).getStringCellValue());
            if (row.getCell(2).getCellType() == CellType.STRING) {
                callService.setCallTime(row.getCell(2).getStringCellValue());
            } else {
                callService.setCallTime(String.valueOf(row.getCell(2).getNumericCellValue()));
            }
            callService.setVatTax(row.getCell(4).getStringCellValue());
            callService.setNumber(Long.parseLong(ownerNumberTemp.substring(ownerNumberTemp.length() - 9)));
            callService.setSum(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
            callService.setMonthly(true);
            calls.add(callService);
        }
        return calls;
    }
    //до это строки удалить


    @Override
    @Transactional
    @SneakyThrows
    public void create(MultipartFile file) {
        System.out.println(LocalDateTime.now());
        XSSFWorkbook myExcelBook = new XSSFWorkbook(file.getInputStream());
        callRepository.saveAll(readFromExcel(myExcelBook));
    }

    @SneakyThrows
    private List<Call> readFromExcel(XSSFWorkbook myExcelBook) {

        List<Call> calls = new ArrayList<>();

        for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(i);
            if ((myExcelSheet.getSheetName().equals("AllCall")) || (myExcelSheet.getSheetName().equals("AllCall1"))) {
                for (Row row : myExcelSheet) {
                    calls.add(fillingCall(row));
                }
            }
        }
        myExcelBook.close();
        System.out.println(LocalDateTime.now());
        return calls;
    }

    private Call fillingCall(Row row) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Call call = new Call();
        call.setOwnerNumber(row.getCell(0).getStringCellValue());
        call.setCallType(row.getCell(1).getStringCellValue());
        call.setCallDateTime(LocalDateTime.parse(row.getCell(2).getStringCellValue().trim(), dateTimeFormatter));
        if (row.getCell(3).getCellType() == CellType.STRING) {
            call.setCode(row.getCell(3).getStringCellValue());
        } else {
            call.setCode(String.valueOf(row.getCell(3).getNumericCellValue()));
        }

        if (row.getCell(4).getCellType() == CellType.STRING) {
            call.setCallTime(row.getCell(4).getStringCellValue());
        } else {
            call.setCallTime(String.valueOf(row.getCell(4).getNumericCellValue()));
        }
        call.setNumber(row.getCell(5).getStringCellValue());
        call.setSum(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
        call.setShortNumber((long) row.getCell(7).getNumericCellValue());
        call.setDayOfWeek((int) row.getCell(8).getNumericCellValue());
        return call;
    }
}
