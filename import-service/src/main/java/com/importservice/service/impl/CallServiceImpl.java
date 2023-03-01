package com.importservice.service.impl;

import com.importservice.entity.Call;
import com.importservice.reposiitory.CallRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CallServiceImpl implements CallService {


    private final CallRepository callRepository;


    @Override
    @Transactional
    @SneakyThrows
    public void createCall(MultipartFile file) {
        System.out.println(LocalDateTime.now());
        XSSFWorkbook myExcelBook = new XSSFWorkbook(file.getInputStream());
        callRepository.saveAll(readFromExcelSeveralPage(myExcelBook));
    }

    @Override
    public Set<String> findAllByCallService() {
        return callRepository.findAllByCallService();
    }

    @SneakyThrows
    private List<Call> readFromExcelSeveralPage(XSSFWorkbook myExcelBook) {

        List<Call> calls = new ArrayList<>();
        String ownerNumberTemp = null;
        String callService = null;
        for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(i);
            if (myExcelSheet.getSheetName().contains("CallDetails")) {
                for (int j = 5; j < myExcelSheet.getLastRowNum(); j++) {
                    XSSFRow row = myExcelSheet.getRow(j);
                    if ((row != null) && (row.getCell(0).getCellType() != CellType.BLANK)
                            && (!row.getCell(0).getStringCellValue().equals("Дата"))
                            && (!row.getCell(0).getStringCellValue().equals("Итого"))
                            && (!row.getCell(0).getStringCellValue().equals("Дата и время начала соединения"))
                            && (!row.getCell(0).getStringCellValue().equals("Итого для абонента"))) {
                        if (row.getCell(0).getStringCellValue().equals("Абонент: ")) {
                            ownerNumberTemp = row.getCell(1).getStringCellValue();
                        } else if ((row.getCell(0).getCellType() == CellType.STRING)
                                && ((row.getCell(2) == null)
                                || (row.getCell(2).getCellType() == CellType.BLANK))) {
                            callService = row.getCell(0).getStringCellValue();
                        } else if (row.getCell(4).getNumericCellValue() != 0) {
                            calls.add(fillingCallSeveralPage(ownerNumberTemp, callService, row));
                        }
                    }
                }
            }
        }
        myExcelBook.close();
        System.out.println(LocalDateTime.now());
        return calls;
    }

    private Call fillingCallSeveralPage(String ownerNumberTemp, String callService, Row row) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        Call call = new Call();
        call.setOwnerNumber(ownerNumberTemp);
        call.setCallService(callService);
        call.setCallDateTime(LocalDateTime.parse(row.getCell(0).getStringCellValue().trim(), dateTimeFormatter));
        if (row.getCell(1).getCellType() == CellType.STRING) {
            call.setCode(row.getCell(1).getStringCellValue());
        } else {
            call.setCode(String.valueOf(row.getCell(1).getNumericCellValue()));
        }

        if (row.getCell(2).getCellType() == CellType.STRING) {
            call.setCallTime(row.getCell(2).getStringCellValue());
        } else {
            call.setCallTime(String.valueOf(row.getCell(2).getNumericCellValue()));
        }
        if (row.getCell(3).getCellType() == CellType.STRING) {
            call.setNumber(row.getCell(3).getStringCellValue());
        } else {
            call.setNumber(String.valueOf(row.getCell(3).getNumericCellValue()));
        }
        call.setSum(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
        call.setShortNumber(Long.parseLong(ownerNumberTemp.substring(1, 10)));
        call.setDayOfWeek(call.getCallDateTime().getDayOfWeek().getValue());
        return call;
    }
}
