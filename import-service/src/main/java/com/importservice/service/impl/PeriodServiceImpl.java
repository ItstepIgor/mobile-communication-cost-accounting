package com.importservice.service.impl;

import com.importservice.entity.ImportPeriod;
import com.importservice.exception.ImportException;
import com.importservice.reposiitory.ImportPeriodRepository;
import com.importservice.service.PeriodService;
import com.importservice.xml.ReportMTS;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final ImportPeriodRepository importPeriodRepository;

    private Set<String> findImportPeriod(String mobileOperator) {
        return importPeriodRepository.findPeriod(mobileOperator);
    }


    @Override
    public void saveImportPeriodA1(XSSFWorkbook myExcelBook) {
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Page3");
        XSSFRow xssfRow = myExcelSheet.getRow(3);
        String period = xssfRow.getCell(1).getStringCellValue();

        savePeriod(period, "A1");
    }

    @Override
    public void saveImportPeriodMTS(ReportMTS reportMTS) {
        String period = reportMTS.getB().get(0).getBd();

        savePeriod(period, "MTS");
    }

    private void savePeriod(String period, String mobileOperator) {
        Set<String> periods = findImportPeriod(mobileOperator);

        if (!periods.contains(period)) {
            importPeriodRepository.save(ImportPeriod
                    .builder()
                    .period(period)
                    .creationDate(LocalDateTime.now())
                    .mobileOperator(mobileOperator)
                    .build());
        } else {
            throw new ImportException("Импорт за период " + period + " уже произведен");
        }
    }
}
