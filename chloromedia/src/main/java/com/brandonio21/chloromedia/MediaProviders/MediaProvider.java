package com.brandonio21.chloromedia.MediaProviders;

import android.os.AsyncTask;

import com.brandonio21.chloromedia.Async.ChloromediaTask;
import com.brandonio21.chloromedia.Async.PostExecutionOperation;
import com.brandonio21.chloromedia.Async.PreExecutionOperation;
import com.brandonio21.chloromedia.Async.ProgressUpdateOperation;
import com.brandonio21.chloromedia.Data.Album;
import com.brandonio21.chloromedia.Data.ProviderItem;
import com.brandonio21.chloromedia.ExclusionTables.ExclusionTable;

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

    public abstract ChloromediaTask<Void, ProviderItem, Album> getRootAlbum(PreExecutionOperation preOperation,
                                                                            ProgressUpdateOperation<ProviderItem> progressOperation,
                                                                            PostExecutionOperation<Album> postOperation);

    public abstract ChloromediaTask<Void, ProviderItem, List<ProviderItem>> getSubItems(Album album,
                                                                                        PreExecutionOperation preOperation,
                                                                                        ProgressUpdateOperation<ProviderItem> progressOperation,
                                                                                        PostExecutionOperation<List<ProviderItem>> postOperation);

    abstract void moveItem(ProviderItem item, Album newParent);
    abstract void deleteItem(ProviderItem item);
    abstract void renameItem(ProviderItem item, String name);
}
