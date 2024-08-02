package com.jonas.myp_sb.web.myp502;

import com.jonas.myp_sb.annotation.LogAnnotation;
import com.jonas.myp_sb.web.myp502.dto.RequestCardInfoDTO;
import com.jonas.myp_sb.web.myp502.dto.ResponseCardInfoDTO;
import com.jonas.myp_sb.web.myp502.dto.ResponseCodeOptionDTO;
import com.jonas.myp_sb.web.myp502.dto.ResponseEffectOptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/myp502")
@RestController
public class OnePieceCardResource {

    private final Logger log = LoggerFactory.getLogger(OnePieceCardResource.class);

    @Autowired
    OnePieceCardService onePieceCardService;

    //更新DB
    @GetMapping("/insertDataByOnePieceCard")
    @LogAnnotation(methodName = "insert資料到DB(onePieceCard)")
    public void insertDataByOnePieceCard(){
        onePieceCardService.insertData();
    }

    //取得卡牌資訊
    @PostMapping("/getCardInfoList")
    @LogAnnotation(methodName = "取得卡牌資訊(onePieceCard)")
    public List<ResponseCardInfoDTO> getCardInfoDTOList(@RequestBody RequestCardInfoDTO requestCardInfoDTO){
        log.info("requestCardInfoDTO:{}", requestCardInfoDTO);
        return onePieceCardService.getCardInfoDTOList(requestCardInfoDTO);
    }

    //取得商品代號選項
    @GetMapping("/getCodeOption")
    @LogAnnotation(methodName = "取得商品代號選項(onePieceCard)")
    public List<ResponseCodeOptionDTO> getCodeOption(){
        return onePieceCardService.getCodeOption();
    }

    //取得特徵選項 會依照商品過濾
    @GetMapping("/getFeatureOption/{code}")
    @LogAnnotation(methodName = "取得特徵選項(onePieceCard)")
    public List getFeatureOption(@PathVariable String code){
        return onePieceCardService.getFeatureOption(code);
    }

    //取得屬性選項
    @GetMapping("/getAttributeOption")
    @LogAnnotation(methodName = "取得屬性選項(onePieceCard)")
    public List getAttributeOption(){
        return onePieceCardService.getAttributeOption();
    }

    //取得屬性選項
    @GetMapping("/getEffectOption")
    @LogAnnotation(methodName = "取得屬性選項(onePieceCard)")
    public List<ResponseEffectOptionDTO> getEffectOption(){
        return onePieceCardService.getEffectOption();
    }

}
