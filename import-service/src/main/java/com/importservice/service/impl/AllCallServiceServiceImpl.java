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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public void createCallService(XSSFWorkbook myExcelBook, LocalDate invoiceDate) {
        log.info(String.valueOf(LocalDateTime.now()));
        allCallServiceRepository.saveAll(readServiceFromExcel(myExcelBook, invoiceDate));
    }

    private List<AllCallService> readServiceFromExcel(XSSFWorkbook myExcelBook, LocalDate invoiceDate) {
        List<AllCallService> calls = new ArrayList<>();
        Set<String> oneTimeCallServiceName = findOneTimeCallServiceName();
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Page4");

        String ownerNumberTemp = "";

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
            calls.add(fillingCommonCallService(oneTimeCallServiceName, ownerNumberTemp, row, invoiceDate));
        }
        return calls;
    }

    private Set<String> findOneTimeCallServiceName() {
        return oneTimeCallServiceService.findOneTimeCallServiceName();
    }

    private AllCallService fillingCommonCallService(Set<String> oneTimeCallServiceName, String ownerNumberTemp, XSSFRow row, LocalDate invoiceDate) {
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
        allCallService.setSumWithNDS(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
        allCallService.setMobileOperator(1);
        allCallService.setOneTimeCallService(oneTimeCallServiceName.contains(allCallService.getCallServiceName()));
        return allCallService;
    }
}
