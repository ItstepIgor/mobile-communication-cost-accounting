package com.importservice.util;

import lombok.SneakyThrows;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Extractor {

    private static final String FOLDER = "./archive";

    @SneakyThrows
    public static InputStream extractFromArchive(MultipartFile file) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Files.createDirectories(Path.of(FOLDER));
        Path filepath = Paths.get(FOLDER, file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filepath.toString(), "r")) {
            try (RandomAccessFileInStream randomAccessFileStream = new RandomAccessFileInStream(randomAccessFile)) {

                try (IInArchive inArchive = SevenZip.openInArchive(null, randomAccessFileStream)) {
                    getOutputStream(outputStream, inArchive);
                }
            }
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray())) {
            Files.delete(filepath);
            return inputStream;
        }
    }

    @SneakyThrows
    public static InputStream extractFromMultiFileArchive(String fileNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Path filepath = Paths.get(FOLDER, fileNames);

        Extractor.ArchiveOpenVolumeCallback archiveOpenVolumeCallback = null;
        IInArchive inArchive = null;
        try {
            archiveOpenVolumeCallback = new Extractor.ArchiveOpenVolumeCallback();
            IInStream inStream = archiveOpenVolumeCallback.getStream(filepath.toString());
            inArchive = SevenZip.openInArchive(null, inStream, archiveOpenVolumeCallback);

            Extractor.getOutputStream(outputStream, inArchive);
        } catch (Exception e) {
            System.err.println("Error occurs: " + e);
        } finally {
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (SevenZipException e) {
                    System.err.println("Error closing archive: " + e);
                }
            }
            if (archiveOpenVolumeCallback != null) {
                try {
                    archiveOpenVolumeCallback.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e);
                }
            }
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray())) {
            Files.delete(filepath);
            return inputStream;
        }
    }

    private static class ArchiveOpenVolumeCallback implements IArchiveOpenVolumeCallback, IArchiveOpenCallback {

        private Map<String, RandomAccessFile> openedRandomAccessFileList =
                new HashMap<>();

        private String name;

        public Object getProperty(PropID propID) {
            if (Objects.requireNonNull(propID) == PropID.NAME) {
                return name;
            }
            return null;
        }

        public IInStream getStream(String filename) {

            try {
                RandomAccessFile randomAccessFile = openedRandomAccessFileList
                        .get(filename);
                if (randomAccessFile != null) { // Cache hit.
                    randomAccessFile.seek(0);
                    name = filename;

                    return new RandomAccessFileInStream(randomAccessFile);
                }
                randomAccessFile = new RandomAccessFile(filename, "r");
                openedRandomAccessFileList.put(filename, randomAccessFile);
                name = filename;
                return new RandomAccessFileInStream(randomAccessFile);
            } catch (FileNotFoundException fileNotFoundException) {
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void close() throws IOException {
            for (RandomAccessFile file : openedRandomAccessFileList.values()) {
                file.close();
            }
        }

        public void setCompleted(Long files, Long bytes) {// default implementation ignored
        }

        public void setTotal(Long files, Long bytes) {// default implementation ignored
        }
    }

    static void getOutputStream(ByteArrayOutputStream outputStream, IInArchive inArchive) throws SevenZipException {
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
