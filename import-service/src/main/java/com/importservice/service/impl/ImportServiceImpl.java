package com.importservice.service.impl;

import com.importservice.service.*;
import com.importservice.util.Extractor;
import com.importservice.util.Unmarshaller;
import com.importservice.xml.ReportMTS;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final CallService callService;

    private final CallServiceMTS callServiceMTS;

    private final PeriodService periodService;

    private final AllCallServiceService allCallServiceService;

    private final FilesStorageService storageService;

    @Override
    @SneakyThrows
    public void importA1(MultipartFile file) {
        XSSFWorkbook myExcelBook;
//Можно использовать метод извлесения из ZIP архива
//        try (InputStream inputStream = Extractor.extractZip(file)) {
        try (InputStream inputStream = Extractor.extractFromArchive(file)) {
            myExcelBook = new XSSFWorkbook(inputStream);
        }

        XSSFSheet myExcelSheet1 = myExcelBook.getSheet("Page4");
        XSSFRow xssfRow = myExcelSheet1.getRow(4);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate invoiceDate = LocalDate.parse(xssfRow.getCell(1)
                .getStringCellValue().substring(0, 10), dateTimeFormatter);

        periodService.saveImportPeriodA1(myExcelBook);
        callService.createCall(myExcelBook, invoiceDate);
        allCallServiceService.createCallService(myExcelBook, invoiceDate);
    }

    @Override
    @SneakyThrows
    public void importMTS(MultipartFile file) {
        ReportMTS reportMTS;
        try (InputStream inputStreams = Extractor.extractFromArchive(file)) {
            reportMTS = Unmarshaller.unmarshallerMTS(inputStreams);
        }
        saveToDataBase(reportMTS);
    }

    @Override
    @SneakyThrows
    public void multiImportMTS(MultipartFile[] files) {
        storageService.deleteAll();
        storageService.init();

        List<String> fileNames = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {
            storageService.save(file);
            fileNames.add(file.getOriginalFilename());
        });
        Collections.sort(fileNames);

        ReportMTS reportMTS;
        try (InputStream inputStreams = Extractor.extractFromMultiFileArchive(fileNames.get(0))) {
            reportMTS = Unmarshaller.unmarshallerMTS(inputStreams);
        }
        storageService.deleteAll();
        saveToDataBase(reportMTS);
    }

    private void saveToDataBase(ReportMTS reportMTS) {
        periodService.saveImportPeriodMTS(reportMTS);
        callServiceMTS.saveToDataBaseFromFileMTS(reportMTS);
    }

//Методы использовались когда не работала одновременная загрузка из нескольких файлов
    @Override
    @SneakyThrows
    public void extract(String originalFilename) {
        ReportMTS reportMTS;
        try (InputStream inputStreams = Extractor.extractFromMultiFileArchive(originalFilename)) {
            reportMTS = Unmarshaller.unmarshallerMTS(inputStreams);
        }
        storageService.deleteAll();
        saveToDataBase(reportMTS);
    }

//Методы использовались когда не работала одновременная загрузка из нескольких файлов
    @Override
    public void saveToFile(MultipartFile file) {
        storageService.init();
        storageService.save(file);
    }
}
