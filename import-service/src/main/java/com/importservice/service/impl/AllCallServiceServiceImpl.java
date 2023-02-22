package com.importservice.service.impl;

import com.importservice.dto.AllCallServiceDTO;
import com.importservice.entity.AllCallService;
import com.importservice.reposiitory.AllCallServiceRepository;
import com.importservice.service.CallService;
import com.importservice.service.AllCallServiceService;
import com.importservice.service.mapper.AllCallServiceListMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AllCallServiceServiceImpl implements AllCallServiceService {

    private final AllCallServiceRepository allCallServiceRepository;

    private final AllCallServiceListMapper allCallServiceListMapper;
    private final CallService callService;

    @Override
    public List<AllCallServiceDTO> findAll() {
        return allCallServiceListMapper
                .listAllCallServiceToListAllCallServiceDto(allCallServiceRepository.findAll());
    }

    //TODO Удалить после настройки импорта
    @SneakyThrows
    @Transactional
    public void createFromFile(String file) {
        System.out.println(LocalDateTime.now());
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        allCallServiceRepository.saveAll(readServiceFromExcel(myExcelBook));
    }
    //до это строки удалить

    private List<AllCallService> readServiceFromExcel(XSSFWorkbook myExcelBook) {
        List<AllCallService> calls = new ArrayList<>();
        Set<String> callType = findAllCallServiceName();
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
            calls.add(fillingCommonCallService(callType, ownerNumberTemp, row));
        }
        return calls;
    }

    private Set<String> findAllCallServiceName() {
        return callService.findAllByCallType();
    }


    private AllCallService fillingCommonCallService(Set<String> callType, String ownerNumberTemp, XSSFRow row) {
        AllCallService callService = new AllCallService();
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
        callService.setOneTimeCallService(callType.contains(callService.getCallServiceName()));
        return callService;
    }


}
