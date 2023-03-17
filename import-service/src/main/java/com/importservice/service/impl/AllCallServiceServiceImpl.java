package com.importservice.service.impl;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.entity.AllCallService;
import com.importservice.reposiitory.AllCallServiceRepository;
import com.importservice.service.AllCallServiceService;
import com.importservice.service.OneTimeCallServiceService;
import com.importservice.service.mapper.AllCallServiceListMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AllCallServiceServiceImpl implements AllCallServiceService {

    private final AllCallServiceRepository allCallServiceRepository;

    private final AllCallServiceListMapper allCallServiceListMapper;

    private final OneTimeCallServiceService oneTimeCallServiceService;

    @Override
    public List<AllCallServiceDTO> findAllByDate(LocalDate date) {
        return allCallServiceListMapper
                .listAllCallServiceToListAllCallServiceDto(allCallServiceRepository.getAllCallServicesByDate(date));
    }

    @SneakyThrows
    @Transactional
    public void createCallService(XSSFWorkbook myExcelBook) {
        System.out.println(LocalDateTime.now());
        allCallServiceRepository.saveAll(readServiceFromExcel(myExcelBook));
    }

    private List<AllCallService> readServiceFromExcel(XSSFWorkbook myExcelBook) {
        List<AllCallService> calls = new ArrayList<>();
        Set<String> callService = findAllCallServiceName();
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Page4");
        XSSFRow xssfRow = myExcelSheet.getRow(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate invoiceDate = LocalDate.parse(xssfRow.getCell(1)
                .getStringCellValue().substring(0, 10), dateTimeFormatter);
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
            calls.add(fillingCommonCallService(callService, ownerNumberTemp, row, invoiceDate));
        }
        return calls;
    }

    private Set<String> findAllCallServiceName() {
        return oneTimeCallServiceService.findOneTimeCallServiceName();
    }

    private AllCallService fillingCommonCallService(Set<String> callService, String ownerNumberTemp, XSSFRow row, LocalDate invoiceDate) {
        AllCallService allCallService = new AllCallService();
        allCallService.setOwnerNumber(ownerNumberTemp);
        allCallService.setCallServiceName(row.getCell(0).getStringCellValue());
        if (row.getCell(2).getCellType() == CellType.STRING) {
            allCallService.setCallTime(row.getCell(2).getStringCellValue());
        } else {
            allCallService.setCallTime(String.valueOf(row.getCell(2).getNumericCellValue()));
        }
        allCallService.setVatTax(row.getCell(4).getStringCellValue());
        allCallService.setInvoiceDate(invoiceDate);
        allCallService.setNumber(Long.parseLong(ownerNumberTemp.substring(ownerNumberTemp.length() - 9)));
        allCallService.setSum(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
        allCallService.setOneTimeCallService(callService.contains(allCallService.getCallServiceName()));
        return allCallService;
    }
}
