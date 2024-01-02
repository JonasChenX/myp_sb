package com.jonas.myp_sb.example.ods;

import com.google.common.base.Charsets;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.ResourceHttpMessageConverter;

import java.io.IOException;
import java.net.URLEncoder;

public class DownloadableResourceMessageConverter extends ResourceHttpMessageConverter {
    public DownloadableResourceMessageConverter() {
    }

    protected boolean supports(Class<?> clazz) {
        return DownloadableResource.class.isAssignableFrom(clazz);
    }

    protected void writeInternal(Resource resource, HttpOutputMessage outputMessage) throws IOException {
        String filename = resource.getFilename();
        filename = URLEncoder.encode(filename, Charsets.UTF_8.name());
        outputMessage.getHeaders().add("Content-Disposition", "attachment; filename=\"" + filename + "\" ; filename*=UTF-8''" + filename);
        super.writeInternal(resource, outputMessage);
    }
}
