package com.importservice.util;

import com.importservice.xml.ReportMTS;
import jakarta.xml.bind.JAXBContext;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Unmarshaller {
    @SneakyThrows
    public static ReportMTS unmarshallerMTS(InputStream inputStream) {
            String body;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(checkForUtf8BOM(inputStream), StandardCharsets.UTF_8))) {
                body = br.lines().collect(Collectors.joining());
            }

        try (StringReader reader = new StringReader(body)) {
            JAXBContext context = JAXBContext.newInstance(ReportMTS.class);
            jakarta.xml.bind.Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ReportMTS) unmarshaller.unmarshal(reader);
        }
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
