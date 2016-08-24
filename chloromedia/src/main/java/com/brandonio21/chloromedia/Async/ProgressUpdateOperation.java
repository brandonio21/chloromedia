package com.brandonio21.chloromedia.Async;

public interface ProgressUpdateOperation<Progress> {
    public void execute(Progress... progressUpdate);
}
