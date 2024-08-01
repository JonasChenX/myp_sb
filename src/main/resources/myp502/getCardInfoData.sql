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
    oci.acquisition_info acquisitionInfo
FROM myp.onepiece_card_info oci
WHERE 1=1
    /* 收入商品 */
    --optional:code
    and oci.code = :code
    --optionalend
    
    /* 卡名 */
    --optional:cardName
    and oci.card_name like :cardName
    --optionalend
    
    /* 屬性 */
    --optional:attribute
    and oci.attribute like :attribute
    --optionalend
    
    /* 顏色 */
    --optional:color
    and oci.color like :color
    --optionalend
    
    /* 種類 */
    --optional:type
    and oci.type = :type
    --optionalend
    
    /* 反擊 */
    --optional:counter
    and oci.counter = :counter
    --optionalend
    
    /* 力量 */
    --optional:power
    and oci.power between :powerStart and :powerEnd
    --optionalend
    
    /* 效果 */
    --optional:effect
    and oci.effect like :effect
    --optionalend
    
    /* 特徵 */
    --optional:feature
    and oci.feature like :feature
    --optionalend
    
    /* 稀有度 */
    --optional:rarity
    and oci.rarity = :rarity
    --optionalend
    
    /* 費用 */
    --optional:cost
    and oci.cost = :cost
    --optionalend
    
    /* 是否排除非觸發 */
    --optional:isTriggerEvent
    and oci.trigger_event is not null
    --optionalend