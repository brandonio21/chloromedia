package com.brandonio21.chloromedia.MediaProviders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.brandonio21.chloromedia.Async.ChloromediaMediaProviderTask;
import com.brandonio21.chloromedia.Async.ChloromediaTask;
import com.brandonio21.chloromedia.Async.PostExecutionOperation;
import com.brandonio21.chloromedia.Async.PreExecutionOperation;
import com.brandonio21.chloromedia.Async.ProgressUpdateOperation;
import com.brandonio21.chloromedia.Data.Album;
import com.brandonio21.chloromedia.Data.ProviderItem;
import com.brandonio21.chloromedia.ExclusionTables.ExclusionTable;

import java.util.Collection;
import java.util.List;

public abstract class MediaProvider {
    ExclusionTable exclusionTable;

    protected MediaProvider(ExclusionTable exclusionTable) {
        this.exclusionTable = exclusionTable;
    }

    void excludeItem(ProviderItem item) {
        this.exclusionTable.excludeItem(item);
    }

    void unexcludeItem(ProviderItem item) {
        this.exclusionTable.unexcludeItem(item);
    }

    public abstract AsyncTask<Context, Album, Collection<Album>> getAlbums(PreExecutionOperation preOperation,
                                                                          ProgressUpdateOperation<Album> progressOperation,
                                                                          PostExecutionOperation<Collection<Album>> postOperation);

    public abstract ChloromediaTask<Void, ProviderItem, List<ProviderItem>> getSubItems(Album album,
                                                                                        PreExecutionOperation preOperation,
                                                                                        ProgressUpdateOperation<ProviderItem> progressOperation,
                                                                                        PostExecutionOperation<List<ProviderItem>> postOperation);

    public abstract Bitmap getImage(ProviderItem item);

    public abstract void moveItem(ProviderItem item, Album newParent);
    public abstract void deleteItem(ProviderItem item);
    public abstract void renameItem(ProviderItem item, String name);
}
