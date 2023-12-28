package com.jonas.myp_sb.web.myp501;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "myp501")
public class Myp501Component {

    private Path uploadFilesCheckRoot;

    private Path uploadFilesRoot;

    public Path getUploadFilesCheckRoot() {
        return uploadFilesCheckRoot;
    }

    public void setUploadFilesCheckRoot(Path uploadFilesCheckRoot) {
        this.uploadFilesCheckRoot = uploadFilesCheckRoot;
    }

    public Path getUploadFilesRoot() {
        return uploadFilesRoot;
    }

    public void setUploadFilesRoot(Path uploadFilesRoot) {
        this.uploadFilesRoot = uploadFilesRoot;
    }
}
