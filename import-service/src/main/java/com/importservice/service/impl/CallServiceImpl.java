package com.importservice.service.impl;

import com.importservice.dto.CallDTO;
import com.importservice.entity.Call;
import com.importservice.reposiitory.CallRepository;
import com.importservice.service.CallService;
import com.importservice.service.OneTimeCallServiceService;
import com.importservice.service.mapper.CallListMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallServiceImpl implements CallService {


    private final CallRepository callRepository;

    private final CallListMapper callListMapper;

    private final OneTimeCallServiceService oneTimeCallServiceService;

    private final KafkaTemplate<String, List<CallDTO>> kafkaTemplate;


    @Override
    @Transactional
    @SneakyThrows
    public void createCall(XSSFWorkbook myExcelBook, LocalDate invoiceDate) {
        log.info(String.valueOf(LocalDateTime.now()));

        List<Call> calls = readFromExcelSeveralPage(myExcelBook, invoiceDate);
        oneTimeCallServiceService.saveOneTimeCallService(calls);

        saveAndSendCall(calls);

    }

    @SneakyThrows
    private void saveAndSendCall(List<Call> calls) {
        List<Call> callList = calls.stream()
                .filter(call -> !(call.getSum().equals(BigDecimal.valueOf(0.0))))
                .toList();
        callRepository.saveAll(callList);
        kafkaTemplate.send("callA1", callListMapper.listCallToListCallDto(callList));
    }


    @SneakyThrows
    private List<Call> readFromExcelSeveralPage(XSSFWorkbook myExcelBook, LocalDate invoiceDate) {

        List<Call> calls = new ArrayList<>();
        String ownerNumberTemp = "";
        String callService = "";
        for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(i);
            if (myExcelSheet.getSheetName().contains("CallDetails")) {
                for (int j = 5; j < myExcelSheet.getLastRowNum(); j++) {
                    XSSFRow row = myExcelSheet.getRow(j);
                    if ((row == null) || (row.getCell(0).getCellType() == CellType.BLANK)
                            || (row.getCell(0).getStringCellValue().equals("Дата"))
                            || (row.getCell(0).getStringCellValue().equals("Итого"))
                            || (row.getCell(0).getStringCellValue().equals("Дата и время начала соединения"))
                            || (row.getCell(0).getStringCellValue().equals("Итого для абонента"))) {
                        continue;
                    } else if (row.getCell(0).getStringCellValue().equals("Абонент: ")) {
                        ownerNumberTemp = row.getCell(1).getStringCellValue();
                        continue;
                    } else if ((row.getCell(0).getCellType() == CellType.STRING)
                            && ((row.getCell(2) == null)
                            || (row.getCell(2).getCellType() == CellType.BLANK))) {
                        callService = row.getCell(0).getStringCellValue();
                        continue;
                    }
                    calls.add(fillingCall(ownerNumberTemp, callService, row, invoiceDate));
                }
            }
        }
        myExcelBook.close();
        log.info(String.valueOf(LocalDateTime.now()));
        return calls;
    }

    private Call fillingCall(String ownerNumberTemp, String callService, Row row, LocalDate invoiceDate) {
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
        call.setInvoiceDate(invoiceDate);
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
        call.setMobileOperator(1);
        return call;
    }
}
