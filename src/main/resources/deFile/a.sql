select a,b,c,d,e,f,g,h,i
from demo_table
where 1=1
    and a in (:orgCd)
    -- addWhere:addSql
    -- addWhereEnd

    -- optional:taxRtnTp
    and b in (:taxRtnTp)
    -- optionalend

    -- optional:stkLstOtcMk
    and c = :stkLstOtcMk
    -- optionalend

    -- optional:ttlSSaleAmt
    and i = :ttlSSaleAmt
    -- optionalend

    -- optional:trdRnk
    -- if:[12345]
    and b is null
    -- else
    -- if:[1231]
    and b is not null
    -- else
    and d = :trdRnk
    -- optionalend

    -- optional:isGoodMk
    and e = :isGoodMk
    -- optionalend

    -- optional:isAplRglr
    and f = :isAplRglr
    -- optionalend

    -- optional:isClsMk
    and g = :isClsMk
    -- optionalend