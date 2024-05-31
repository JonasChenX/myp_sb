package com.jonas.myp_sb.example.task.main.filter.annotations;

import org.jobrunr.jobs.filters.JobFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JobMetadata {
    String USER_ID = "USER_ID";

    String USER_REALM = "USER_REALM";

    String USER_UNIT = "USER_UNIT";

    String GROUP_ID = "GROUP_ID";

    String FUNCTION_ID = "FUNCTION_ID";

    String groupId() default "";

    String functionId() default "";

    String name() default "";

    int retries() default 3;

    Class<? extends JobFilter>[] jobFilters() default {};
}
