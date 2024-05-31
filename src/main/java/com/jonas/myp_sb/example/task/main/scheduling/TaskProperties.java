package com.jonas.myp_sb.example.task.main.scheduling;

import org.springframework.stereotype.Component;

@Component
public class TaskProperties {
    /**
     * 是否要啟用 JobRunr #516 議題的 Workaround。
     *
     * @see <a href="https://github.com/jobrunr/jobrunr/issues/516">JobRunr Github Issue #516</a>
     */
    private boolean applyJobRunr516Workaroud = true;

    public boolean isApplyJobRunr516Workaroud() {
        return applyJobRunr516Workaroud;
    }

    public void setApplyJobRunr516Workaroud(boolean applyJobRunr516Workaroud) {
        this.applyJobRunr516Workaroud = applyJobRunr516Workaroud;
    }
}
