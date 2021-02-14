package com.hzh.dagger.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UploadedFile {

    private io.muserver.UploadedFile rawUploadedFile;

    public UploadedFile(io.muserver.UploadedFile rawUploadedFile) {
        this.rawUploadedFile = rawUploadedFile;
    }

    public io.muserver.UploadedFile getRawUploadedFile() {
        return rawUploadedFile;
    }


    public File asFile() throws IOException {
        return rawUploadedFile.asFile();
    }

    public String asString() throws IOException {
        return rawUploadedFile.asString();
    }

    public byte[] asBytes() throws IOException {
        return rawUploadedFile.asBytes();
    }

    public String contentType() {
        return rawUploadedFile.contentType();
    }

    public String filename() {
        return rawUploadedFile.filename();
    }

    public String extension() {
        return rawUploadedFile.extension();
    }

    public void saveTo(File dest) throws IOException {
        rawUploadedFile.saveTo(dest);
    }

    public long size() {
        return rawUploadedFile.size();
    }

    public InputStream asStream() throws IOException {
        return rawUploadedFile.asStream();
    }
}
