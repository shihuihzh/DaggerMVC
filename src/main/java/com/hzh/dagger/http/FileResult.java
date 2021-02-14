package com.hzh.dagger.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResult extends Result {

    private static final Logger logger = LoggerFactory.getLogger(FileResult.class);

    private final String fileName;

    public FileResult(int statusCode, String contentType, File file, String fileName, Cookie[] cookies) {
        super(statusCode, contentType, file, cookies);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static FileResultBuilder newBuilder() {
        return new FileResultBuilder();
    }

    public static class FileResultBuilder extends Result.Builder<FileResultBuilder> {

        private File file;
        private String fileName;

        public FileResultBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FileResultBuilder withFile(File file) {
            this.file = file;

            if (file != null && file.exists()) {
                try {
                    this.contentType = Files.probeContentType(Path.of(file.getAbsolutePath()));
                } catch (IOException e) {
                    logger.info("Get file type error {}, cause: {}", file.getPath(), e.getMessage());
                }
            }
            return this;
        }

        public FileResult build() {
            return new FileResult(statusCode, contentType, file, fileName != null ? fileName : file.getName(), cookies);
        }

    }
}
