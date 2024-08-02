SELECT 
    oci.card_number cardNumber,
    oci.rarity rarity,
    oci.type type,
    oci.card_name cardName,
    oci.image_url imageUrl,
    oci.cost cost,
    oci.health health,
    oci.attribute attribute,
    oci.power power,
    oci.counter counter,
    oci.color color,
    oci.feature feature,
    oci.effect effect,
    oci.trigger_event triggerEvent,
    oci.acquisition_info acquisitionInfo,
    ROW_NUMBER() OVER (PARTITION BY card_number ORDER BY id) AS rn1
FROM onepiece_card_info oci
WHERE 1=1

    /* 收入商品 */
    -- optional:code
    and oci.code = :code
    -- optionalend

    /* 卡名 */
    -- optional:cardName
    and oci.card_name like CONCAT('%', :cardName, '%')
    -- optionalend

    /* 卡號 */
    -- optional:cardNum
    and oci.card_number like CONCAT('%', :cardNum, '%')
    -- optionalend

    /* 屬性 */
    -- optional:attribute
    and oci.attribute like :attribute
    -- optionalend

    /* 顏色 */
    -- optional:color
    and oci.color like :color
    -- optionalend

    /* 種類 */
    -- optional:type
    and oci.type = :type
    -- optionalend

    /* 反擊 */
    -- optional:counter
    and oci.counter = :counter
    -- optionalend

    -- optional:isCounterForNull
    and oci.counter is null
    -- optionalend

    /* 力量 */
    -- optional:powerStart
    and oci.power between :powerStart and :powerEnd
    -- optionalend

    /* 特徵 */
    -- optional:feature
    and oci.feature like :feature
    -- optionalend

    /* 稀有度 */
    -- optional:rarity
    and oci.rarity = :rarity
    -- optionalend

    /* 費用 */
    -- optional:cost
    and oci.cost = :cost
    -- optionalend

    /* 是否排除非觸發 */
    -- optional:isTriggerEvent
    and oci.trigger_event is not null
    -- optionalend

    /* 是否只顯示異圖 */
    -- optional:isDifferent
    -- optionalend

    /* 效果 */
    -- addWhere:effect
    -- addWhereEnd

