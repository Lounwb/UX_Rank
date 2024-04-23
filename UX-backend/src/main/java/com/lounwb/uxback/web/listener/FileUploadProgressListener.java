package com.lounwb.uxback.web.listener;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class FileUploadProgressListener implements ProgressListener {
    private long bytesRead;
    private long contentLength;
    private int items;

    @Override
    public void update(long l, long l1, int i) {
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
        this.items = items;

        double progress = (double) bytesRead / contentLength * 100;
        log.info("Upload Progress: " + progress + "%");
    }
}
