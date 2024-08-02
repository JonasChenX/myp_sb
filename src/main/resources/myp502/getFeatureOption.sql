-- 使用 DISTINCT 來確保結果中沒有重複的值
SELECT DISTINCT
    -- 使用 SUBSTRING_INDEX 函數來提取每個逗號分隔的值
    SUBSTRING_INDEX(
        -- 外層的 SUBSTRING_INDEX 函數從逗號分隔的字符串中提取出指定的子串
            SUBSTRING_INDEX(feature, ',', numbers.n),
        -- 內層的 SUBSTRING_INDEX 函數從逗號分隔的字符串中提取出最後一部分
            ',',
            -1
    ) AS featureOption
FROM
    myp.onepiece_card_info oci
        -- 使用 CROSS JOIN 來生成一個數字表，以便能夠拆分字符串
        CROSS JOIN (
        -- 數字表，包含從 1 到 5 的數字
        SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
    ) numbers
where 1 = 1

    -- optional:code
    and oci.code = :code
    -- optionalend