package com.jonas.myp_sb.example.query;

import org.intellij.lang.annotations.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class SqlExecutor {
    private static final Logger log = LoggerFactory.getLogger(SqlExecutor.class);
    private Class<? extends RowMapper> rowMapper = BeanPropertyRowMapper.class;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SqlExecutor(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int delete(@Language("SQL") String sql) {
        return this.delete(new Query(sql));
    }

    public int[] delete(@Language("SQL") String sql, List<Map<String, ?>> listOfParameters) {
        return this.executeInBatch(sql, listOfParameters);
    }

    public int delete(@Language("SQL") String sql, Map<String, ?> parameters) {
        return this.delete(new Query(sql, parameters));
    }

    public int delete(Query query) {
        return this.execute(query);
    }

    private int execute(Query query) {
        Objects.requireNonNull(query, "Parameter \"query\" must not be null.");
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        this.logSqlExecute(queryString, parameters);
        return this.jdbcTemplate.update(queryString, parameters);
    }

    private int[] executeInBatch(String sql, List<Map<String, ?>> parameters) {
        if (!this.isNotNullAndNotEmpty(sql)) {
            throw new IllegalArgumentException("Query string must not be null.");
        } else {
            Objects.requireNonNull(parameters, "Conditions must not be null. SQL without parameter is inadequate to use in batch method.");
            this.logExecuteInBatch(sql, parameters);
            return this.jdbcTemplate.batchUpdate(sql, (Map[])parameters.toArray(new Map[parameters.size()]));
        }
    }

    public int insert(@Language("SQL") String sql) {
        return this.insert(new Query(sql));
    }

    public int[] insert(@Language("SQL") String sql, List<Map<String, ?>> listOfParameters) {
        return this.executeInBatch(sql, listOfParameters);
    }

    public int insert(@Language("SQL") String sql, Map<String, ?> parameters) {
        return this.insert(new Query(sql, parameters));
    }

    public int insert(Query query) {
        return this.execute(query);
    }

    private boolean isNotNullAndNotEmpty(String sql) {
        return sql != null && !sql.trim().isEmpty();
    }

    private void logSqlExecute(String sql, Map<String, ?> parameters) {
        log.info("執行的SQL: {}", sql);
        if (parameters != null && !parameters.isEmpty()) {
            Iterator var3 = parameters.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, ?> entry = (Map.Entry)var3.next();
                log.info("執行的Param: {}, Value: {}", entry.getKey(), entry.getValue());
            }
        } else {
            log.info("With NO parameters!");
        }

    }

    private void logExecuteInBatch(String sql, List<Map<String, ?>> parametersList) {
        if (log.isDebugEnabled()) {
            log.debug("Executing SQL:{}", sql);

            for(int i = 0; i < parametersList.size(); ++i) {
                Map<String, ?> parameters = (Map)parametersList.get(0);
                log.debug("List[{}]", i);
                if (parameters != null && !parameters.isEmpty()) {
                    Iterator var5 = parameters.entrySet().iterator();

                    while(var5.hasNext()) {
                        Map.Entry<String, ?> entry = (Map.Entry)var5.next();
                        log.debug("Param:{}, Value:{}", entry.getKey(), entry.getValue());
                    }
                } else {
                    log.debug("With NO parameters!");
                }
            }
        }

    }

    public <T> List<T> queryForList(@Language("SQL") String sql, Class<T> clazz) {
        return this.queryForList(new Query(sql), clazz);
    }

    public <T> List<T> queryForList(@Language("SQL") String sql, Map<String, ?> parameters, Class<T> clazz) {
        return this.queryForList(new Query(sql, parameters), clazz);
    }

    public <T> List<T> queryForList(Query query, Class<T> clazz) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        this.logSqlExecute(queryString, parameters);
        if (this.isSimpleType(clazz)) {
            return this.jdbcTemplate.queryForList(queryString, parameters, clazz);
        } else {
            RowMapper mapper;
            try {
                mapper = (RowMapper)this.rowMapper.getDeclaredConstructor(Class.class).newInstance(clazz);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException var8) {
                String message = String.format("Failed to initialize RowMapper of class %s.", this.rowMapper);
                throw new RuntimeException(message, var8);
            }

            return this.jdbcTemplate.query(queryString, parameters, mapper);
        }
    }

    private <T> boolean isSimpleType(Class<T> clazz) {
        return ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.equals(String.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class);
    }

    public int update(@Language("SQL") String sql) {
        return this.update(new Query(sql));
    }

    public int[] update(@Language("SQL") String sql, List<Map<String, ?>> listOfParameters) {
        return this.executeInBatch(sql, listOfParameters);
    }

    public int update(@Language("SQL") String sql, Map<String, ?> parameters) {
        return this.update(new Query(sql, parameters));
    }

    public int update(Query query) {
        return this.execute(query);
    }

    public Class<? extends RowMapper> getRowMapper() {
        return this.rowMapper;
    }

    public void setRowMapper(Class<? extends RowMapper> rowMapper) {
        this.rowMapper = rowMapper;
    }
}
