package com.importservice.service.impl;

import com.importservice.service.*;
import com.importservice.util.Extractor;
import com.importservice.util.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final CallService callService;

    private final CallServiceMTS callServiceMTS;

    private final PeriodService periodService;

    private final AllCallServiceService allCallServiceService;

    @Override
    @SneakyThrows
    public void importA1(MultipartFile file) {
        InputStream inputStream = Extractor.extractFromArchive(file);
        XSSFWorkbook myExcelBook = new XSSFWorkbook(inputStream);
        periodService.saveImportPeriod(myExcelBook);
        callService.createCall(myExcelBook);
        allCallServiceService.createCallService(myExcelBook);
    }

    @Override
    public void importMTS(MultipartFile file) {
        InputStream inputStreams = Extractor.extractFromArchive(file);
        callServiceMTS.createCall(Unmarshaller.unmarshallerMTS(inputStreams));
    }
}
