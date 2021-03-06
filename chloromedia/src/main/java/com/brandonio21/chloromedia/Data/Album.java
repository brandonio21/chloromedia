package com.brandonio21.chloromedia.Data;

import android.content.Context;
import android.graphics.Bitmap;

import com.brandonio21.chloromedia.Async.ChloromediaTask;
import com.brandonio21.chloromedia.Async.PostExecutionOperation;
import com.brandonio21.chloromedia.Async.PreExecutionOperation;
import com.brandonio21.chloromedia.Async.ProgressUpdateOperation;
import com.brandonio21.chloromedia.MediaProviders.MediaProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album extends ProviderItem {
    List<ProviderItem> children;
    Bitmap coverImage;
    String name;

    public Album(MediaProvider provider,
                 ProviderItemMetadata metadata,
                 String name) {

        super(provider, metadata);
        this.children = new ArrayList<ProviderItem>();
        this.name = name;
    }

    @Override
    public Bitmap getPreviewImage(Context context) {
        return this.getCoverImage(context);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Long getSize() {
        return null;
    }

    ChloromediaTask<Void, ProviderItem, List<ProviderItem>> getChildren(PreExecutionOperation preOperation,
                                                                        ProgressUpdateOperation<ProviderItem> progressOperation,
                                                                        PostExecutionOperation<List<ProviderItem>> postOperation) {
        if (this.childrenHaveBeenLoaded())
            return new GetLoadedItemsTask(preOperation, progressOperation, postOperation);
        else
            return this.getChildrenFromProvider(preOperation, progressOperation, postOperation);
    }

    boolean childrenHaveBeenLoaded() {
        return this.children.size() > 0;
    }

    ChloromediaTask<Void, ProviderItem, List<ProviderItem>> getChildrenFromProvider(PreExecutionOperation preOperation,
                                                                                    ProgressUpdateOperation<ProviderItem> progressOperation,
                                                                                    final PostExecutionOperation<List<ProviderItem>> postOperation) {
        PostExecutionOperation<List<ProviderItem>> cacheLocallyThenPerformUserPost = new PostExecutionOperation<List<ProviderItem>>() {
            @Override
            public void execute(List<ProviderItem> result) {
                children = result;
                postOperation.execute(result);
            }
        };
        return this.mediaProvider.getSubItems(this, preOperation, progressOperation, cacheLocallyThenPerformUserPost);
    }

    List<ProviderItem> sort(SortMode sortMode, SortDirection sortDirection) {
        return null;
    }

    void setCoverImage(Bitmap image) {
        this.coverImage = image;
    }

    Bitmap getCoverImage(Context context) {
        if (this.coverImage != null)
            return this.coverImage;

        Bitmap coverImage;

        if (this.children.size() > 0)
            coverImage = this.children.iterator().next().getPreviewImage(context);
        else
            coverImage = this.mediaProvider.getImage(context, this);

        this.setCoverImage(coverImage);
        return coverImage;
    }

    class GetLoadedItemsTask extends ChloromediaTask<Void, ProviderItem, List<ProviderItem>> {

        protected GetLoadedItemsTask(
                PreExecutionOperation preExecutionOperation,
                ProgressUpdateOperation progressUpdateOperation,
                PostExecutionOperation postExecutionOperation) {
            super(preExecutionOperation, progressUpdateOperation, postExecutionOperation);
        }

        @Override
        protected List<ProviderItem> doInBackground(Void... voids) {
            for (ProviderItem child : children)
                publishProgress(child);

            return children;
        }
    }
}
