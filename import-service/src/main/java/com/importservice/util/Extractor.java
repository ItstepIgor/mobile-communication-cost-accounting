package com.importservice.util;

import lombok.SneakyThrows;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Extractor {

    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @SneakyThrows
    public static InputStream extractFromArchive(MultipartFile file) {

        Files.createDirectories(Path.of("./archive"));
        Path filepath = Paths.get("./archive", file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filepath.toString(), "r")) {
            try (RandomAccessFileInStream randomAccessFileStream = new RandomAccessFileInStream(randomAccessFile)) {

                try (IInArchive inArchive = SevenZip.openInArchive(null, randomAccessFileStream)) {
                    ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

                    for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                        if (!item.isFolder()) {
                            ExtractOperationResult result;
                            result = item.extractSlow(data -> {
                                try {
                                    outputStream.write(data);
                                    return data.length;
                                } catch (IOException e) {
                                    throw new SevenZipException(e.getMessage(), e.getCause());
                                }
                            });

                            if (result != ExtractOperationResult.OK) {
                                throw new RuntimeException(
                                        String.format("Error extracting archive. Extracting error: %s", result));
                            }
                        }
                    }
                }
            }
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray())) {
            Files.delete(filepath);
            outputStream.close();
            return inputStream;
        }
    }


    //Метод для извлечения из ZIP файла
    @SneakyThrows
    public static InputStream extractZip(MultipartFile file) {
        InputStream inputStream = null;
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                ByteArrayOutputStream fos = new ByteArrayOutputStream();
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                inputStream = new ByteArrayInputStream(fos.toByteArray());
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
        }
        return inputStream;
    }
}
