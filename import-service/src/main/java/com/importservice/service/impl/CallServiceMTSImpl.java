package com.importservice.service.impl;

import com.importservice.xml.ReportMTS;
import com.importservice.service.CallServiceMTS;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CallServiceMTSImpl implements CallServiceMTS {
    @Override
    @SneakyThrows
    public void createCall() {
        String body;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(checkForUtf8BOM(Files.newInputStream(Path.of("./MTS4.xml")))))) {
            body = br.lines().collect(Collectors.joining());
        }
        StringReader reader = new StringReader(body);
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
}
