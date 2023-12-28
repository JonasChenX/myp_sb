package com.jonas.myp_sb.web.myp501;

import com.jonas.myp_sb.example.ioDemo.Resources;
import com.jonas.myp_sb.example.query.SqlExecutor;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class Myp501Service {

    private final Logger log = LoggerFactory.getLogger(Myp501Service.class);

    private final Myp501Component myp501Component;
    private Resources resources;
    private final SqlExecutor sqlExecutor;


    public Myp501Service(Myp501Component myp501Component, Resources resources, SqlExecutor sqlExecutor) {
        this.myp501Component = myp501Component;
        this.resources = resources;
        this.sqlExecutor = sqlExecutor;
    }

    public List getMetaDtlTblList(Map<String,Object> parameters) {
        String findSdgMetaDtlTblListSqlPath = "classpath:myp501/findMypMetaDtlTbl.sql";
        String findSdgMetaDtlTblListSql = resources.readAsString(findSdgMetaDtlTblListSqlPath);
        List result = sqlExecutor.queryForList(findSdgMetaDtlTblListSql, parameters, mypMetaDtlTblDTO.class);
        return result;
    }

    public Map<String, Object> uploadFileCheck(Map<String,Object> form, MultipartFile uploadFile){
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> getMetaDtlTblListParameters = new HashMap<>();
        String tableNm = form.get("tableNm").toString().toUpperCase();

        getMetaDtlTblListParameters.put("jobNm",tableNm);
        List metaDtlTblList = getMetaDtlTblList(getMetaDtlTblListParameters);
        result.put("metaDtlTblList", metaDtlTblList);

        //檢核 無檔案
        if (uploadFile == null) {
            log.error("無檔案");
            result.put("error", "無檔案");
            return result;
        }
        String fileExtension = getFileExtension(uploadFile);
        //檢核 非csv
        if(!("CSV".equals(fileExtension))){
            log.error("副檔名非csv或d檔");
            result.put("error", "副檔名非csv或d檔");
            return result;
        }
        try {
            //utf8 讀取檔案內容
            String fileContent = new String(uploadFile.getBytes(), StandardCharsets.UTF_8);
            //內容最後若\n則刪除\n
            if (fileContent.endsWith("\n")) {
                fileContent = fileContent.substring(0, fileContent.length() - 1); // 删除末尾的换行符
            }

            //處理第一行
            Boolean isHeaderNm = (Boolean) form.get("isHeader");
            int firstNewLineIndex = fileContent.indexOf("\n");
            String modifiedContent = fileContent;
            //若有勾包含表頭 要把檔案內容第一行拿掉
            if(isHeaderNm){
                modifiedContent = fileContent.substring(firstNewLineIndex + 1);
                if(firstNewLineIndex == -1){
                    log.error("去除表頭後無內容請確認");
                    result.put("error", "去除表頭後無內容請確認");
                    return result;
                }
            }
            String firstLine = null;
            //只有一行的狀況
            if(firstNewLineIndex > 0){
                firstLine = fileContent.substring(0, firstNewLineIndex);
            }else {
                firstLine = fileContent;
            }

            //處理UTF8 BOM問題
            if(firstLine.startsWith("\uFEFF")){
                firstLine = firstLine.substring(1);
            }

            //取最後一行
            int lastNewLineIndex = fileContent.lastIndexOf("\n");
            String lastLine = null;
            //只有一行的狀況
            if(lastNewLineIndex > 0){
                lastLine = fileContent.substring(lastNewLineIndex + 1);
            }else {
                lastLine = fileContent;
            }

            //取最後一行的資料轉回陣列
            List<String> lastDataArr = null;
            if("CSV".equals(fileExtension)){
                lastDataArr = Arrays.asList(lastLine.split(","));
            }
            result.put("lastDataArr",lastDataArr);

            //依據是否包含表頭 移除首行，並存下首行header欄位
            List<String> header = null; //要存
            if(isHeaderNm){
                if("CSV".equals(fileExtension)){
                    header = Arrays.asList(firstLine.split(","));
                }
            }else {
                int size = metaDtlTblList.size();
                header = IntStream.range(0, size)
                        .mapToObj(index -> "欄位" + (index + 1))
                        .collect(Collectors.toList());
            }
            result.put("headerList",header);

            String fileName = setFileName(tableNm); //要存
            Path uploadPath = myp501Component.getUploadFilesCheckRoot();
            uploadFileHandle(fileName + "." + fileExtension, modifiedContent, uploadPath);

            //存檔案名稱
            result.put("fileName", fileName);

        } catch (IOException e) {
            log.error(e.toString());
        }

        return result;
    }

    public Map<String, Object> uploadFile(Map<String,Object> form){

        Map<String, Object> result = new HashMap<>();

        String fileName = String.valueOf(form.get("fileName")).toUpperCase() + ".CSV";

        //先讀CSV檔
        String fileDPathStr = myp501Component.getUploadFilesCheckRoot() + File.separator + fileName;
        Path fileDPath = Paths.get(fileDPathStr);

        //抓取數值型態的column
        List<Map<String, String>> headerDataArr = (List<Map<String, String>>) form.get("columnsValueArr");
        String numTypeStr = "decimal";
        Map<String, String> findFirstNumResult = headerDataArr.stream().filter(map -> map.get("columnType").toLowerCase().contains(numTypeStr))
                .findFirst()
                .map(matchedMap -> {
                    //取得索引值
                    int index = IntStream.range(0, headerDataArr.size())
                            .filter(i -> headerDataArr.get(i) == matchedMap)
                            .findFirst()
                            .orElse(-1);

                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("index", String.valueOf(index));
                    resultMap.put("columnNm", matchedMap.get("columnNm"));
                    resultMap.put("columnType", matchedMap.get("columnType"));
                    return resultMap;
                }).orElse(null);
        Boolean isNumType = findFirstNumResult != null ? true : false;

        //  計算數值總數 計算筆數 確認每一行欄位數都跟header數一樣
        BigDecimal sum = BigDecimal.ZERO; //總數
        BigDecimal rowNum = BigDecimal.ZERO; //筆數
        try (BufferedReader reader = Files.newBufferedReader(fileDPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                rowNum = rowNum.add(BigDecimal.valueOf(1));
                //若有數值型態則計算
                if(isNumType) {
                    Integer index = Integer.valueOf(findFirstNumResult.get("index")); //有數值的索引
                    Map<String, Object> resetLineResult = resetLineStr(line,",");
                    if (resetLineResult.containsKey("error")) {
                        String errorStr = String.valueOf(resetLineResult.get("error"));
                        result.put("error", errorStr + ",請確認第" + rowNum + "行");
                        return result;
                    }
                    List lineArr = (List) resetLineResult.get("result");
                    if (!NumberUtils.isCreatable(String.valueOf(lineArr.get(index)))) {
                        log.error("請確認第{}行,左邊數來第{}個為非數值", rowNum.toString(), index + 1);
                        result.put("error", "請確認第" + rowNum + "行,左邊數來第" + (index + 1) + "個為非數值");
                        return result;
                    }
                    BigDecimal numValue = new BigDecimal(String.valueOf(lineArr.get(index)));
                    sum = sum.add(numValue);
                }
            }
        }catch (IOException e) {
            log.error(e.toString());
        }
        //設置H參數
        String tableNm = String.valueOf(form.get("tableNm")).toUpperCase();
        Map<String,Object> fileHParam = new HashMap<>();
        fileHParam.put("H_FILE_NAME",tableNm+".H");
        fileHParam.put("DATA_FILE_NAME",tableNm+".CSV");
        fileHParam.put("DATA_CNT",rowNum.toString());
        fileHParam.put("DATA_CHECK_COLUMN",isNumType ? findFirstNumResult.get("columnNm").toUpperCase() : "");
        fileHParam.put("DATA_CHECK_VALUE",isNumType ? sum.toString() : "");
        fileHParam.put("DATA_DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        fileHParam.put("DATA_TIME", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        fileHParam.put("TARGET_TBL_NAME",tableNm + "_PRESTAGE");

        String exampleFileHPath = "classpath:myp501/exampleFileH.H";
        String exampleFileHContent = resources.readAsString(exampleFileHPath);
        String modifiedContentFileH = resetFileHContent(exampleFileHContent, fileHParam);

        Path uploadPath = myp501Component.getUploadFilesRoot();
        uploadFileHandle(tableNm + ".H", modifiedContentFileH, uploadPath);
        copyFile(tableNm + ".CSV",fileDPath);


        return null;
    }

    /**
     * 取得副檔名
     * */
    private String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null && originalFileName.contains(".")) {
            return originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toUpperCase();
        }
        return null;
    }

    /**
     * 上傳檔案
     * */
    private String uploadFileHandle(String fileName, String fileContent,Path uploadPath){
        if (fileContent != null) {

            log.info("uploadPath=>{}",uploadPath.toString());
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    log.warn("Failed to create path {}.", uploadPath, e);
                }
            }

            Path file = uploadPath.resolve(fileName);
            try {
                //刪除已存在檔案 儲存新檔案(覆蓋)
                Files.deleteIfExists(file);
                Files.write(file, fileContent.getBytes());

            } catch (IOException e) {
                return "失敗";
            }
        }
        return "成功";
    }

    /**
     * 將暫存區的檔案複製到另一個路徑
     * */
    private void copyFile(String fileName,Path sourcePath){
        Path toPath = myp501Component.getUploadFilesRoot();
        log.info("copyPath=>{}",toPath.toString());
        log.info("sourcePath=>{}",sourcePath.toString());
        if(!Files.exists(toPath)){
            try {
                Files.createDirectories(toPath);
            } catch (IOException e) {
                log.warn("Failed to create path {}.", toPath, e);
            }
        }

        try {
            Path file = toPath.resolve(fileName);
            //刪除已存在檔案 儲存新檔案(覆蓋)
            Files.deleteIfExists(file);
            Files.copy(sourcePath, file);
        } catch (IOException e) {
            log.error("Failed to create path {}.", toPath);
        }
    }

    /**
     * 設置檔案名
     * */
    private String setFileName(String TableNm){
        //儲存 rename {TableNm}_timestamp.d
        long currentTimestampMillis = System.currentTimeMillis();
        return TableNm.toUpperCase() + "_" + currentTimestampMillis;
    }

    /**
     * 分割為陣列
     * */
    private Map<String,Object> resetLineStr(String line,String delimiter){
        Map<String, Object> result = new HashMap<>();
        List<String> lineArr = null;

        int delimiterIndex = line.indexOf(delimiter);
        if(delimiterIndex != -1){
            lineArr = Arrays.asList(line.split(Pattern.quote(delimiter)));
            result.put("result", lineArr);
        }else {
            log.error("找不到欄分割符號為{}格式",delimiter);
            result.put("error", "找不到欄分割符號為"+delimiter+"格式");
            return result;
        }
        return result;
    }

    /**
     * 重設H檔裡面的內容
     * */
    private String resetFileHContent(String fileHContent, Map<String, Object> parameters){
        StringBuilder modifiedContentFileH = new StringBuilder();
        String resultStr = null;
        try (BufferedReader reader = new BufferedReader(new StringReader(fileHContent))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //把註解刪除
                int commentIndex = line.indexOf("//");
                if (commentIndex != -1) {
                    line = line.substring(0, commentIndex); // 删除 //後面的内容
                }
                // 抓取 ${}
                line = replaceVariables(line,parameters);
                line = line.trim(); //一律不留空格
                modifiedContentFileH.append(line+"\n");
            }
            resultStr = modifiedContentFileH.toString();
            if (modifiedContentFileH.toString().endsWith("\n")) {
                resultStr = resultStr.substring(0, resultStr.length() - 1); // 删除末尾的换行符
            }
        } catch (IOException e) {
            log.error(e.toString());
        }

        return resultStr;
    }

    /**
     * 匹配${...}格式 若有parameters有相同的key則取代  否則不動
     * */
    private String replaceVariables(String line, Map<String, Object> parameters){
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(line);
        //StringBuilder
        StringBuilder result = new StringBuilder(); // 改成 StringBuilder
        int previousEnd = 0;
        while (matcher.find()) {
            String variableKey = matcher.group(1);
            Object variableValue = parameters.get(variableKey);
            result.append(line, previousEnd, matcher.start());

            if (variableValue != null) {
                result.append(variableValue.toString());
            } else {
                result.append("${").append(variableKey).append("}");
            }

            previousEnd = matcher.end();
        }
        result.append(line.substring(previousEnd));
        return result.toString();
    }
}
