package com.jonas.myp_sb.web.myp502;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonas.myp_sb.example.defFile.DefFileService;
import com.jonas.myp_sb.example.ioDemo.Resources;
import com.jonas.myp_sb.web.myp502.dto.*;
import com.jonas.myp_sb.web.myp502.enums.effectEnum;
import com.jonas.myp_sb.web.myp502.model.OnePieceCardInfo;
import com.jonas.myp_sb.web.myp502.model.OnepieceCardProductInfo;
import com.jonas.myp_sb.web.myp502.repository.OnePieceCardInfoRepository;
import com.jonas.myp_sb.web.myp502.repository.OnePieceCardProductInfoRepository;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.jsoup.Jsoup;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jonas.myp_sb.web.myp502.enums.effectEnum.codeToSql;

@Service
public class OnePieceCardService {


    private final Logger log = LoggerFactory.getLogger(OnePieceCardService.class);

    private final OnePieceCardInfoRepository onePieceCardInfoRepository;
    private final OnePieceCardProductInfoRepository onePieceCardProductInfoRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Resources resources;
    private final DefFileService defFileService;

    public OnePieceCardService(OnePieceCardInfoRepository onePieceCardInfoRepository, OnePieceCardProductInfoRepository onePieceCardProductInfoRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Resources resources, DefFileService defFileService) {
        this.onePieceCardInfoRepository = onePieceCardInfoRepository;
        this.onePieceCardProductInfoRepository = onePieceCardProductInfoRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.resources = resources;
        this.defFileService = defFileService;
    }

    public List<ResponseCodeOptionDTO> getCodeOption(){
        List<OnepieceCardProductInfo> productInfoList = onePieceCardProductInfoRepository.findAll();
        List<ResponseCodeOptionDTO> resultList = new ArrayList<>();
        for(OnepieceCardProductInfo onepieceCardProductInfo : productInfoList){
            ResponseCodeOptionDTO getCodeOptionDTO = new ResponseCodeOptionDTO();
            BeanUtils.copyProperties(onepieceCardProductInfo, getCodeOptionDTO);
            resultList.add(getCodeOptionDTO);
        }
        return resultList;
    }

    public List getAttributeOption(){
        String getAttributeOptionSQL = resources.readAsString("classpath:myp502/getAttributeOption.sql");
        return namedParameterJdbcTemplate.queryForList(getAttributeOptionSQL, new HashMap<>());
    }

    public List<ResponseFeatureOptionDTO> getFeatureOption(String code){
        String getFeatureOptionSQL = resources.readAsString("classpath:myp502/getFeatureOption.sql");
        String resetSQL = defFileService.filterOptionalParams(getFeatureOptionSQL, Map.of("model", Map.of("code",code)));
        List<Map<String, Object>> featureOptionList = namedParameterJdbcTemplate.queryForList(resetSQL, Map.of("code",code));
        return featureOptionList.stream().map(item -> {
            ResponseFeatureOptionDTO responseFeatureOptionDTO = new ResponseFeatureOptionDTO();
            responseFeatureOptionDTO.setFeatureName(String.valueOf(item.get("featureOption")));
            responseFeatureOptionDTO.setFeatureCode("%" + item.get("featureOption") + "%");
            return responseFeatureOptionDTO;
        }).collect(Collectors.toList());
    }

    public List<ResponseEffectOptionDTO> getEffectOption(){
        return Arrays.stream(effectEnum.values()).map(item -> {
            ResponseEffectOptionDTO responseEffectOptionDTO = new ResponseEffectOptionDTO();
            responseEffectOptionDTO.setEffectCode(item.getEffectCode());
            responseEffectOptionDTO.setEffectName(item.getText());
            return responseEffectOptionDTO;
        }).collect(Collectors.toList());
    }

    public List<ResponseCardInfoDTO> getCardInfoDTOList(RequestCardInfoDTO requestCardInfoDTO){
        //判斷是否排除重複
        String getDistinctCardInfoDataSQL = resources.readAsString("classpath:myp502/getCardInfoData.sql");
        if(requestCardInfoDTO.getIsDistinct()){
            getDistinctCardInfoDataSQL = resources.readAsString("classpath:myp502/getDistinctCardInfoData.sql");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params = objectMapper.convertValue(requestCardInfoDTO, Map.class);
        String resetSQL = defFileService.filterOptionalParams(getDistinctCardInfoDataSQL, Map.of("model",params));
        resetSQL = setEffectWhereSQL(resetSQL, params);
        log.info("resetQSL:{}",resetSQL);
        List<Map<String, Object>> cardInfoList = namedParameterJdbcTemplate.queryForList(resetSQL, params);
        List<ResponseCardInfoDTO> resultList = new ArrayList<>();
        for(Map cardInfo : cardInfoList){
            ResponseCardInfoDTO responseCardInfoDTO = cardInfoMapper(cardInfo);
            resultList.add(responseCardInfoDTO);
        }
        return resultList;
    }

    //效果搜尋另外處理
    private String setEffectWhereSQL(String sql, Map<String, Object> params){
        String effectSql = "";
        if(!Objects.isNull(params.get("effect"))){
            effectSql = codeToSql(String.valueOf(params.get("effect")));
        }
        return defFileService.addWhereParams(sql, Map.of("model", Map.of("effect", effectSql)));
    }

    private ResponseCardInfoDTO cardInfoMapper(Map cardInfo){
        ResponseCardInfoDTO responseCardInfoDTO = new ResponseCardInfoDTO();
        BeanUtils.copyProperties(cardInfo, responseCardInfoDTO);
        responseCardInfoDTO.setCardNumber(String.valueOf(cardInfo.get("cardNumber")));
        responseCardInfoDTO.setRarity(String.valueOf(cardInfo.get("rarity")));
        responseCardInfoDTO.setType(String.valueOf(cardInfo.get("type")));
        responseCardInfoDTO.setCardName(String.valueOf(cardInfo.get("cardName")));
        responseCardInfoDTO.setImageUrl(String.valueOf(cardInfo.get("imageUrl")));
        if(!Objects.isNull(cardInfo.get("cost"))){
            responseCardInfoDTO.setCost(String.valueOf(cardInfo.get("cost")));
        }
        if(!Objects.isNull(cardInfo.get("health"))){
            responseCardInfoDTO.setHealth(String.valueOf(cardInfo.get("health")));
        }
        responseCardInfoDTO.setAttribute(List.of(String.valueOf(cardInfo.get("attribute")).split(",")));
        if(!Objects.isNull(cardInfo.get("power"))){
            responseCardInfoDTO.setPower(String.valueOf(cardInfo.get("power")));
        }
        if(!Objects.isNull(cardInfo.get("counter"))){
            responseCardInfoDTO.setCounter(String.valueOf(cardInfo.get("counter")));
        }
        responseCardInfoDTO.setColor(List.of(String.valueOf(cardInfo.get("color")).split(",")));
        responseCardInfoDTO.setFeature(List.of(String.valueOf(cardInfo.get("feature")).split(",")));
        if(!Objects.isNull(cardInfo.get("effect"))){
            responseCardInfoDTO.setEffect(String.valueOf(cardInfo.get("effect")));
        }
        if(!Objects.isNull(cardInfo.get("triggerEvent"))){
            responseCardInfoDTO.setTriggerEvent(String.valueOf(cardInfo.get("triggerEvent")));
        }
        responseCardInfoDTO.setAcquisitionInfo(String.valueOf(cardInfo.get("acquisitionInfo")));
        return responseCardInfoDTO;
    }

    public void insertData(){
        String URL = "https://www.onepiece-cardgame.com/cardlist/";
        List<OnepieceCardProductInfo> OnepieceCardProductInfoList = onePieceCardProductInfoRepository.findAll();
        List<String> optionCodeList = OnepieceCardProductInfoList.stream().map(item -> item.getOptionCode()).collect(Collectors.toList());

        for (String optionCode : optionCodeList){
            MultiValueMap<String, Object> requestData  = new LinkedMultiValueMap<>();
            requestData.add("series", optionCode);
            log.info("optionCode:{}", optionCode);
            String responseStr = postDataToExternalAPI(URL, requestData);
            List<OnePieceCardInfo> onePieceCardDataList = getData(responseStr, optionCode);
            onePieceCardInfoRepository.saveAll(onePieceCardDataList);
        }
    }

    private static String postDataToExternalAPI(String url, MultiValueMap<String, Object> requestData){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity request = new HttpEntity<>(requestData, headers);

        return restTemplate.postForObject(url, request, String.class);
    }

    private static List<OnePieceCardInfo> getData(String html, String code){

        List<OnePieceCardInfo> onePieceCardDataList = new ArrayList<>();
        // 解析HTML字串
        Document doc = Jsoup.parse(html);
        Elements modalCols = doc.select("dl.modalCol");
        for (Element modalCol : modalCols) {
            OnePieceCardInfo onePieceCardModel = new OnePieceCardInfo();
            //get id
            String id = modalCol.id();
            onePieceCardModel.setId(id);

            //get cardNumber rarity type
            Elements spans = modalCol.select("div.infoCol").select("span");
            List<String> collect = spans.stream()
                    .map(Element::text)
                    .collect(Collectors.toList());
            onePieceCardModel.setCardNumber(collect.get(0));
            onePieceCardModel.setRarity(collect.get(1));
            onePieceCardModel.setType(collect.get(2));

            //get cardName
            String cardName = modalCol.select("div.cardName").text();
            onePieceCardModel.setCardName(cardName);

            //get imageUrl
            String imageUrl = modalCol.select("div.frontCol").select("img").attr("data-src").replace("..","https://www.onepiece-cardgame.com");
            int index = imageUrl.indexOf('?');
            imageUrl = (index != -1) ? imageUrl.substring(0, index) : imageUrl;
            onePieceCardModel.setImageUrl(imageUrl);

            //get cost / health
            Element costDiv = modalCol.selectFirst("div.cost");
            costDiv.select("h3").remove();
            String healthOrConst = costDiv.text().trim();
            Boolean isTypeForLeader = "LEADER".equals(onePieceCardModel.getType()) ? true : false;
            boolean isNumericByHealthOrConst = healthOrConst.matches("^\\d+\\.?\\d*$");
            if(isTypeForLeader){
                if(isNumericByHealthOrConst){
                    onePieceCardModel.setHealth(Integer.parseInt(healthOrConst));
                }else {
                    onePieceCardModel.setHealth(0);
                }
            }else {
                if(isNumericByHealthOrConst){
                    onePieceCardModel.setCost(Integer.parseInt(healthOrConst));
                }else {
                    onePieceCardModel.setCost(0);
                }
            }

            //get attribute
            String attr = modalCol.select("div.attribute").select("img").attr("alt");
            Boolean isTypeForEventOrStage = "EVENT".equals(onePieceCardModel.getType()) || "STAGE".equals(onePieceCardModel.getType()) ? true : false;
            if(!isTypeForEventOrStage){
                if(attr.contains("/")){
                    List<String> attrList = Stream.of(attr.trim().split("/"))
                            .collect(Collectors.toList());
                    attr = String.join(",", attrList);
                }
                onePieceCardModel.setAttribute(attr);
            }

            //get power
            Elements powerDiv = modalCol.select("div.power");
            powerDiv.select("h3").remove();
            String power = powerDiv.text().trim();
            if(!isTypeForEventOrStage){
                boolean isNumeric = power.matches("^\\d+\\.?\\d*$");
                if(isNumeric){
                    onePieceCardModel.setPower(Integer.parseInt(power));
                }else {
                    onePieceCardModel.setPower(0);
                }
            }

            //get counter
            Elements counterDiv = modalCol.select("div.counter");
            counterDiv.select("h3").remove();
            String counter = counterDiv.text().trim();
            if(!isTypeForEventOrStage && !isTypeForLeader){
                boolean isNumeric = counter.matches("^\\d+\\.?\\d*$");
                if(isNumeric){
                    onePieceCardModel.setCounter(Integer.parseInt(counter));
                }
            }

            //get color
            Elements colorDiv = modalCol.select("div.color");
            colorDiv.select("h3").remove();
            String color = colorDiv.text().trim();
            if(color.contains("/")){
                List<String> attrList = Stream.of(color.trim().split("/"))
                        .collect(Collectors.toList());
                color = String.join(",", attrList);
            }
            onePieceCardModel.setColor(color);

            //get feature
            Elements featureDiv = modalCol.select("div.feature");
            featureDiv.select("h3").remove();
            String comma = "/"; //分割符號
            if(featureDiv.text().contains("／")){
                comma = "／";
            }

            List<String> featureList = Stream.of(featureDiv.text().trim().split(comma))
                    .collect(Collectors.toList());
            onePieceCardModel.setFeature(String.join(",", featureList));

            //get effect
            Elements effectDiv = modalCol.select("div.text");
            effectDiv.select("h3").remove();
            String effect = effectDiv.text().trim();
            if(!"-".equals(effect)){
                onePieceCardModel.setEffect(effect);
            }

            //get trigger
            Elements triggerDiv = modalCol.select("div.trigger");
            if(!Strings.isBlank(String.valueOf(triggerDiv))){
                triggerDiv.select("h3").remove();
                String trigger = triggerDiv.text().trim();
                onePieceCardModel.setTriggerEvent(trigger);
            }

            //get acquisitionInfo
            Elements acquisitionInfoDiv = modalCol.select("div.getInfo");
            acquisitionInfoDiv.select("h3").remove();
            String acquisitionInfo = acquisitionInfoDiv.text().trim();
            onePieceCardModel.setAcquisitionInfo(acquisitionInfo);

            //set code
            onePieceCardModel.setCode(code);

            onePieceCardDataList.add(onePieceCardModel);
        }

        return onePieceCardDataList;
    }


}
