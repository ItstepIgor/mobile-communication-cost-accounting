package com.importservice.service.impl;

import com.importservice.xml.ReportMTS;
import com.importservice.service.CallServiceMTS;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CallServiceMTSImpl implements CallServiceMTS {
    @Override
    @SneakyThrows
    public void createCall() {

        List<InputStream> extract = extract("./MTS_207306892385_202302_9092867848.rar");
        extract.set(0, checkForUtf8BOM(extract.get(0)));
        StringBuilder stringBuilder = new StringBuilder();
        for (InputStream inputStream1 : extract) {
            String body;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream1))) {
                System.out.println();
                body = br.lines().collect(Collectors.joining());
                stringBuilder.append(body);
            }
        }

        StringReader reader = new StringReader(stringBuilder.toString());
        JAXBContext context = JAXBContext.newInstance(ReportMTS.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ReportMTS reportMTS = (ReportMTS) unmarshaller.unmarshal(reader);

        System.out.println();
    }


    //удаление метки спецификации UTF-8 в начале файла (UTF-8 file with BOM)
    private static InputStream checkForUtf8BOM(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(new BufferedInputStream(inputStream), 3);
        byte[] bom = new byte[3];
        if (pushbackInputStream.read(bom) != -1) {
            if (!(bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF)) {
                pushbackInputStream.unread(bom);
            }
        }
        return pushbackInputStream;
    }

    //извлечение из файла из архива
    public List<InputStream> extract(String filePath) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        RandomAccessFileInStream randomAccessFileStream = new RandomAccessFileInStream(randomAccessFile);
        IInArchive inArchive = SevenZip.openInArchive(null, randomAccessFileStream);
        ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
        final List<InputStream> inputStreamList = new ArrayList<>();
        for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
            if (!item.isFolder()) {
                ExtractOperationResult result;
                result = item.extractSlow(data -> {
                    InputStream is = new ByteArrayInputStream(data);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
                    inputStreamList.add(bufferedInputStream);
                    return data.length;
                });

                if (result != ExtractOperationResult.OK) {
                    throw new RuntimeException(
                            String.format("Error extracting archive. Extracting error: %s", result));
                }
            }
        }
        return inputStreamList;
    }
}
