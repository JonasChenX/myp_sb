package com.jonas.myp_sb.example.ods;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class DownloadableResource implements Resource {
    private Resource resource;
    private String filename;

    public DownloadableResource(Resource resource) {
        this((Resource) Objects.requireNonNull(resource, "Parameter \"resource\" should not be null."), resource.getFilename());
    }

    public DownloadableResource(Resource resource, String filename) {
        this.resource = (Resource)Objects.requireNonNull(resource, "Parameter \"resource\" should not be null.");
        this.filename = (String)Objects.requireNonNull(filename, "Parameter \"filename\" should not be null.");
    }

    public boolean exists() {
        return this.resource.exists();
    }

    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    public boolean isReadable() {
        return this.resource.isReadable();
    }

    public boolean isOpen() {
        return this.resource.isOpen();
    }

    public URL getURL() throws IOException {
        return this.resource.getURL();
    }

    public URI getURI() throws IOException {
        return this.resource.getURI();
    }

    public File getFile() throws IOException {
        return this.resource.getFile();
    }

    public long contentLength() throws IOException {
        return this.resource.contentLength();
    }

    public long lastModified() throws IOException {
        return this.resource.lastModified();
    }

    public Resource createRelative(String relativePath) throws IOException {
        return this.resource.createRelative(relativePath);
    }

    public String getDescription() {
        return this.resource.getDescription();
    }

    public String getFilename() {
        return this.filename;
    }
}
