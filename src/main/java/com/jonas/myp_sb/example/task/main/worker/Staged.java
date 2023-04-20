package com.jonas.myp_sb.example.task.main.worker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 標示為分段執行的 TaskWorker，執行結束並不代表作業完成，還需要等待外部的通知。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Staged {
}
