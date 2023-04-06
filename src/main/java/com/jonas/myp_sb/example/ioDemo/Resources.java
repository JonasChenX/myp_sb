package com.jonas.myp_sb.example.ioDemo;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class Resources {
    private final ResourceLoader resourceLoader;

    public Resources(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readAsString(String location) {
        Resource resource = resourceLoader.getResource(location);
        try (
                InputStream inputStream = resource.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
        ) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public InputStream getResourceInputStream(String location) throws IOException {
        Resource resource = resourceLoader.getResource(location);
        return resource.getInputStream();
    }
}
