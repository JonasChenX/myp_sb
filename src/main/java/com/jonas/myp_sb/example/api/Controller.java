package com.jonas.myp_sb.example.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "test 相關api")
@RestController
public class Controller {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ServiceTest serviceTest;

    @GetMapping("/select")
    public List select(){
        String sql = "select * from demo_test";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, new HashMap<>());
        return list;
    }

    @GetMapping("/model/{id}")
    public ResponseEntity<Model> getModel(@PathVariable Integer id){
        /**
        * Controller -> Service -> Dao <-rowMapper(Model<-dbFiled )-> db
        * */
        Model modelById = serviceTest.getModelById(id);
        if(modelById != null){
            return ResponseEntity.status(HttpStatus.OK).body(modelById);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
