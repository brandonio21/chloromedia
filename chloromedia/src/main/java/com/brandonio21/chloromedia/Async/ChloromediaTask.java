package com.brandonio21.chloromedia.Async;

import android.os.AsyncTask;

public abstract class ChloromediaTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    PreExecutionOperation preExecutionOperation;
    ProgressUpdateOperation progressUpdateOperation;
    PostExecutionOperation postExecutionOperation;

    protected ChloromediaTask(
            PreExecutionOperation preExecutionOperation,
            ProgressUpdateOperation progressUpdateOperation,
            PostExecutionOperation postExecutionOperation) {

        this.preExecutionOperation = preExecutionOperation;
        this.progressUpdateOperation = progressUpdateOperation;
        this.postExecutionOperation = postExecutionOperation;
    }

    @Override
    protected void onPreExecute() {
        if (this.preExecutionOperation != null)
            this.preExecutionOperation.execute();
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        if (this.progressUpdateOperation != null)
            this.progressUpdateOperation.execute(values);
    }

    @Override
    protected void onPostExecute(Result result) {
        if (this.postExecutionOperation != null)
            this.postExecutionOperation.execute(result);
    }
}
