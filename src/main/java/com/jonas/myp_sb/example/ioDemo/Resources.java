package com.jonas.myp_sb.example.ioDemo;

import com.jonas.myp_sb.example.query.Query;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

@Component
public class Resources {
    private final ResourceLoader resourceLoader;

    public Resources(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * @param location 文件路徑
     * @return 文件內容
     * **/
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

    /**
     * 讀取SQL轉Query物件進行操作
     * @param location SQL檔路徑
     * @param parameters 帶入參數
     * @return
     */
    public Query readAsQuery(String location, Object... parameters) {
        return readAsQueryBuilder(location, parameters).build();
    }

    public Query.Builder readAsQueryBuilder(String location, Object... parameters) {
        String string = readAsString(location);
        return Query.builder(string, parameters);
    }

    /**
     * @param location 文件路徑
     * @return 獲取文件的輸入流
     * **/
    public InputStream getResourceInputStream(String location){
        Resource resource = resourceLoader.getResource(location);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 將輸入流寫入文件
     * @param fileName 檔案名稱
     * @param directoryName 儲存資料夾名稱
     * @param inputStream 輸入流
     * **/
    public void writeToFile(String fileName, String directoryName,  InputStream inputStream) {
        try {
            // 檢查資料夾是否存在，不存在則創建新的目錄
            Path directoryPath = Paths.get("src/main/resources/" + directoryName);
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            // 將inputStream中的內容複製到指定的檔案路徑中 若有相同檔名則覆蓋
            Path filePath = Paths.get("src/main/resources/" + directoryName + "/" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 釋放資源
     * @param inputStream 輸入流
     * **/
    public void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
