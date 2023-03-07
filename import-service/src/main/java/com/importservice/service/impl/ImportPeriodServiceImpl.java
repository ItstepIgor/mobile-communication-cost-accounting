package com.importservice.service.impl;

import com.importservice.entity.ImportPeriod;
import com.importservice.exception.ImportException;
import com.importservice.reposiitory.ImportPeriodRepository;
import com.importservice.service.ImportPeriodService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImportPeriodServiceImpl implements ImportPeriodService {

    private final ImportPeriodRepository importPeriodRepository;

    private Set<String> findImportPeriod() {
        return importPeriodRepository.findPeriod();
    }


    @Override
    public void saveImportPeriod(XSSFWorkbook myExcelBook) {
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Page3");
        XSSFRow xssfRow = myExcelSheet.getRow(3);
        String cellValue = xssfRow.getCell(1).getStringCellValue();

        Set<String> period = findImportPeriod();

        if (!period.contains(cellValue)) {
            importPeriodRepository.save(ImportPeriod
                    .builder()
                    .period(cellValue)
                    .creationDate(LocalDateTime.now())
                    .build());
        } else {
            throw new ImportException("Импорт за период " + cellValue + " уже произведен");
        }
    }
}
