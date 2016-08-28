package com.brandonio21.chloromedia.Async;

import com.brandonio21.chloromedia.MediaProviders.MediaProvider;

public abstract class ChloromediaMediaProviderTask<Params, Progress, Result>
        extends ChloromediaTask<Params, Progress, Result> {
    MediaProvider mediaProvider;

    protected ChloromediaMediaProviderTask(MediaProvider mediaProvider,
                                           PreExecutionOperation preExecutionOperation,
                                           ProgressUpdateOperation<Progress> progressUpdateOperation,
                                           PostExecutionOperation<Result> postExecutionOperation) {
        super(preExecutionOperation, progressUpdateOperation, postExecutionOperation);
        this.mediaProvider = mediaProvider;
    }

    public MediaProvider getMediaProvider() {
        return this.mediaProvider;
    }
}
