
--報表類別：成本類，報表名稱：1.01進貨對象集中於數家公司
WITH dt as (
    select calendar_ym
         , calendar_yr
         , roc_ym
         , roc_yr
from multi_tax.sbd_shr_date_cd
where roc_yr = :dataYr
group by calendar_ym
    , calendar_yr
    , roc_ym
    , roc_yr
    ),JVP AS (
SELECT DISTINCT YY.BAN
FROM (
    SELECT ZZ.BAN
    ,SUM(ZZ.LY1_CHK_MK) AS LY1_CHK_MK
    FROM(
    SELECT DISTINCT PP.BAN
    , PP.LY1_CHK_MK ::INT
    , PP.LY2_CHK_MK ::INT
    , PP.LY3_CHK_MK ::INT
    FROM (
    SELECT AA.BAN
    , coalesce(CASE WHEN AA.DATA_BL_YR= DT.calendar_ym_1 THEN AA.CHK_MK END,'0') AS LY1_CHK_MK
    , coalesce(CASE WHEN AA.DATA_BL_YR= DT.calendar_ym_2 THEN AA.CHK_MK END,'0') AS LY2_CHK_MK
    , coalesce(CASE WHEN AA.DATA_BL_YR= DT.calendar_ym_3 THEN AA.CHK_MK END,'0') AS LY3_CHK_MK
    FROM (
        SELECT A.DATA_BL_YR
        , A.IDN_BAN AS BAN
        , CASE WHEN A.CASE_TP IN ('01','02','06','07','10','14','17','18','19','20','21','23','35','36') AND A.CHK_TMS='01' THEN '1'
        WHEN A.CASE_TP='05' AND A.CHK_TMS='02' THEN '2'
        ELSE '3'
        END AS CHK_MK
        FROM BASE.SBD_JVPT001 A, DT
        WHERE A.DATA_BL_YR IN (DT.calendar_ym_1
            , DT.calendar_ym_2
            ) AND A.ASG_BUSI_TP = 'PNK'
        ) AA , DT
    WHERE AA.CHK_MK <> '3'

    ) PP
    )ZZ
    GROUP BY  ZZ.BAN
    ) YY
WHERE 1=1
  AND (YY.LY1_CHK_MK <3 AND YY.LY1_CHK_MK >0)
    ), BCI AS (
SELECT A.BAN
    , A.SEL_TP
FROM BASE.SBD_BCIT013 A, DT
WHERE A.SEL_YR IN (DT.calendar_ym_1
    , DT.calendar_ym
    )
GROUP BY A.BAN, A.SEL_TP
    ), PAC AS (
SELECT DISTINCT ZZ.BAN
FROM (
    SELECT PRC.BAN
    , PAC.APL_YR
    FROM (
    SELECT distinct A.DATA_YR
    , A.BAN
    FROM BASE.SBD_PRCT001 A, DT
    WHERE A.DATA_YR IN (DT.calendar_ym_1
    , DT.calendar_ym_2
    ) AND A.BAN IN (SELECT BAN FROM JVP)
    ) PRC

    JOIN DT
    ON PRC.DATA_YR IN (DT.calendar_ym_1
    , DT.calendar_ym_2
    )

    JOIN (
    SELECT AA.DATA_YR
    , AA.BAN
    , COUNT(AA.DATA_YR) OVER (PARTITION BY AA.BAN) AS APL_YR
    FROM (
    SELECT A.DATA_YR
    , A.BAN
    , SUBSTR(A.RECEV_NO,3,1) AS RECEV_MK
    , RANK() OVER (PARTITION BY A.DATA_YR, A.BAN, A.RECEV_NO ORDER BY A.CHK_TMS DESC) AS MAX_TMS
    FROM BASE.SBD_PACT030 A, DT
    WHERE A.DATA_YR IN (DT.calendar_ym_1
    , DT.calendar_ym_2
    )
    )AA WHERE MAX_TMS = 1
    AND AA.RECEV_MK IN ('0','1','2','3','4','5','6','7','8','C')
    ) PAC
    ON PAC.DATA_YR = PRC.DATA_YR
    AND PAC.BAN = PRC.BAN
    AND PAC.RECEV_NO = PRC.RECEV_NO--OK
    GROUP BY PRC.BAN, PAC.APL_YR
    )ZZ
WHERE (
    (ZZ.LY1_LEVY_INCM IS NOT NULL AND ZZ.LY1_LEVY_INCM <= 500000
  AND ZZ.LY2_LEVY_INCM IS NOT NULL AND ZZ.LY2_LEVY_INCM <= 500000
  AND ZZ.LY3_NET_PF_RATE IS NOT NULL AND ZZ.LY3_NET_PF_RATE < 0.1
    )
   OR ZZ.APL_YR < 3
    )
    ), PGM AS (--OK
SELECT LPAD(AGENT_LIC_NO,4,'0') AS AGENT_LIC_NO4, AGENT_IDN, CHI_NM
FROM BASE.SBD_PGMT011
where 1=1
    ),stk_resp AS (
select a.*
from (
    select resp.data_yr
    ,resp.ban
    ,slck2.ban_nm
    ,resp.trade_subj_ban
    from sts.sbd_pfs_prs_stk_resp_sumy resp

    where data_yr = (to_char((substr(:dataYr,1,3))::int+1911,'9999')||'0101')::date
	and resp.trd_s_num > 1
    and trd_rnk <= :trdRnk
    ) a

where a.ttl_s_sale_amt >= (:ttlSSaleAmt * 10000.0)
  and a.ttl_busi_cnt >= :ttlBusiCnt
    )
SELECT
     stk.ban       as "營利事業統編"
     ,stk.ban_nm    as "營利事業名稱"
     ,substr(to_char(stk.data_yr - interval '1911 Y', 'yyyymmdd'),2,3 )    as "資料年度"
     ,stk.trd_rnk   as "進貨對象進貨金額排名"
FROM (
         SELECT MAIN.DATA_YR
              , MAIN.ORG_CD
              , MAIN.BAN
              , MAIN.BAN_NM
         FROM (
                  SELECT PFLS.DATA_YR
                       , CASE WHEN LENGTH(PFLS.AGENT_LIC_NO) >= 4 THEN SUBSTR(PFLS.AGENT_LIC_NO,-4) ELSE PFLS.AGENT_LIC_NO END AS AGENT_LIC_NO
                       , CASE WHEN coalesce (PFLS.BUSI_NET_PF, 0) = 0 THEN 0
                              WHEN coalesce (PFLS.BUSI_RVNU_NET, 0) = 0 THEN coalesce (PFLS.BUSI_NET_PF, 0) / 1.00 ELSE ROUND (PFLS.BUSI_NET_PF / PFLS.BUSI_RVNU_NET, 4) * 100
                      END AS INCM_RATE --計算標準額
                  FROM (
                           SELECT A.*
                           FROM BASE.SBD_PRCT001 A, DT
                           WHERE A.DATA_YR = DT.calendar_ym
                             AND A.ORG_CD IN (:orgCd)--機關代號
                             --optional:checklistForBscd
                             AND A.BSCD IN (:checklistForBscd)--行業代號篩選
                             --optionalend
                             --optional:bscdTimeStart
                             AND A.BSCD >=:bscdTimeStart --行業代號區間
                             --optionalend
                             --optional:bscdTimeEnd
                             AND A.BSCD <=:bscdTimeEnd --行業代號區間
                             --optionalend
                             AND (1=1 --排除行業代號區間
                           --IF @行業代號區間3 or @行業代號區間4 僅輸入其一，就兩個都帶同個值
                           --optional:excludeBscdTimeStart :excludeBscdTimeEnd
                               AND A.BSCD NOT BETWEEN :excludeBscdTimeStart AND :excludeBscdTimeEnd
                               --optionalend
                               )

                             --營業收入級距(不含非營業收入)
                             --optional:saleAmtTotalStart
                             AND A.BUSI_RVNU_NET >= :saleAmtTotalStart
                             --optionalend
                             --optional:saleAmtTotalEnd
                             AND A.BUSI_RVNU_NET <= :saleAmtTotalEnd
                             --optionalend

                             --純益率級距
                             --optional:zapShRefStart
                             AND A.NET_PF_RATE >=:zapShRefStart
                             --optionalend
                             --optional:zapShRefEnd
                             AND A.NET_PF_RATE <=:zapShRefEnd
                             --optionalend

                             --申報類別
                             --optional:taxRtnTp
                             AND A.TAX_RTN_TP IN (:taxRtnTp)
                           --optionalend
                       ) PFLS
                           JOIN BASE.SBD_PRCT002 ASST
                                ON PFLS.BAN = ASST.BAN
                                    AND PFLS.RECEV_NO = ASST.RECEV_NO
                           JOIN (
                      SELECT A.* FROM (
                                          SELECT BAN
                                               , BAN_NM
                                               , CASE WHEN IMPT_UPDATE_REAS IN ('11','12','17','18','25','39','40','41','43','91') THEN 'N' ELSE 'Y' END AS CLS_MK
                                          FROM sts.SBD_VAT_SLCK_BAN_STUS
                                      )A WHERE 1=1
                                           --排除停、 歇業欄位
                                           --optional:isClsMk
                                           AND A.CLS_MK = :isClsMk
                                           --optionalend
                  ) VAT
                                ON PFLS.BAN = VAT.BAN
                  WHERE 1=1
                    --資本額級距
                    --optional:capitalStart
                    AND ASST.ACT_RECEV_CPTL >= :capitalStart
                    --optionalend
                    --optional:capitalEnd
                    AND ASST.ACT_RECEV_CPTL <= :capitalEnd
                  --optionalend
              ) MAIN
                  LEFT JOIN  BASE.SBD_KPVT103 B
                             ON MAIN.BAN = B.CO_BAN
         WHERE 1=1
           --營業收入級距(含非營業收入)
           --optional:saleAmtTotalStart
           AND MAIN.NON_RVNU >= :saleAmtTotalStart
           --optionalend
           --optional:saleAmtTotalEnd
           AND MAIN.NON_RVNU <= :saleAmtTotalEnd
           --optionalend

           --組織別( 獨資或合夥 )
           --optional:orgnTp
           AND MAIN.ORGN_TP = :orgnTp
           --optionalend

           --公開發行類別公司
           --optional:stkLstOtcMk
           AND CASE WHEN B.STK_LST_OTC_MK = '3' THEN ' ' ELSE COALESCE(B.STK_LST_OTC_MK, ' ') END IN (:stkLstOtcMk)
           --optionalend

           --行業代號大類
           --optional:checklistParentBscd
           AND C.LRG_BSCD IN (:checklistParentBscd)
         --optionalend
     )ZZ
         JOIN  stk_resp stk
               ON  ZZ.DATA_YR = stk.data_yr
                   AND ZZ.BAN  = stk.ban

WHERE 1=1
--排除公開發行公司、上市 (櫃)公司及興櫃公司
--optional:isStkPblcMk
  AND ZZ.STK_PBLC_MK = :isStkPblcMk
--optionalend

--排除達標準所得總額
--optional:isIncmStdMk
  AND ZZ.INCM_STD_MK = :isIncmStdMk
--optionalend

--排除統一發票績優營業人(整測參數有誤，已修改須重新進館)
--optional:isNGoodMk
  AND ZZ.N_GOOD_MK = :isNGoodMk
--optionalend

--排除電子發票績優營業人(整測參數有誤，已修改須重新進館)
--optional:isGoodMk
  AND ZZ.E_GOOD_MK = :isGoodMk
--optionalend

--排除連續3年無異常調整項目
--optional:isAplRglr
  AND ZZ.APL_RGLR = :isAplRglr
--optionalend



