package com.jonas.myp_sb.example.task.main.worker;

import com.jonas.myp_sb.example.task.main.context.TaskContext;
import com.jonas.myp_sb.example.task.main.context.TaskContextAware;

/**
 * {@link TaskWorker} 的基本實作，建議所有 Worker 都繼承我。
 */
public abstract class AbstractTaskWorker implements TaskWorker, TaskContextAware {
    private static final ThreadLocal<TaskContext> CONTEXT = new ThreadLocal<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setContext(TaskContext context) {
        CONTEXT.set(context);
    }

    /**
     * 設定 message.
     *
     * @param message
     *            設定 message
     */
    public final void setMessage(String message) {
        assertContextAvailable();
        getContext().setMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final TaskContext getContext() {
        return CONTEXT.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void clearContext() {
        CONTEXT.remove();
    }

    /**
     * 確認 {@code context} 內容是否有效。
     *
     */
    protected void assertContextAvailable() {
        if (CONTEXT.get() == null) {
            throw new IllegalStateException("\"context\" must be set before you can perform this operation.");
        }
    }
}
