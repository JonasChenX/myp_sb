select col_nm columnNm,
       col_data_type columnType,
       col_desc columnDesc
from myp.myp_meta_dtl_tbl
where JOB_NM = :jobNm
order by SEQ_NO