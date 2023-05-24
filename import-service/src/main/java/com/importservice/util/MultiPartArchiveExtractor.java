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

public class MultiPartArchiveExtractor {
    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @SneakyThrows
    public static InputStream extractFromArchive(MultipartFile file) {

//        Files.createDirectories(Path.of("./archive"));
//        Path filepath = Paths.get("./archive", file.getOriginalFilename());
//
//        try (OutputStream os = Files.newOutputStream(filepath)) {
//            os.write(file.getBytes());
//        }

        MultiPartArchiveExtractor.ArchiveOpenVolumeCallback archiveOpenVolumeCallback = null;
        IInArchive inArchive = null;
        try {

            archiveOpenVolumeCallback = new MultiPartArchiveExtractor.ArchiveOpenVolumeCallback();
            IInStream inStream = archiveOpenVolumeCallback.getStream("E:/1/MTS_207306892385_202304_9102704622.part01.rar");
            inArchive = SevenZip.openInArchive(null, inStream, archiveOpenVolumeCallback);

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
//            Files.delete(filepath);
            outputStream.close();
            return inputStream;
        }
    }

    private static class ArchiveOpenVolumeCallback implements IArchiveOpenVolumeCallback, IArchiveOpenCallback {

        private Map<String, RandomAccessFile> openedRandomAccessFileList =
                new HashMap<String, RandomAccessFile>();

        private String name;

        public Object getProperty(PropID propID) {
            switch (propID) {
                case NAME:
                    return name;
            }
            return null;
        }

        public IInStream getStream(String filename) throws SevenZipException {

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

        public void setCompleted(Long files, Long bytes) {
        }

        public void setTotal(Long files, Long bytes) {
        }
    }
}
