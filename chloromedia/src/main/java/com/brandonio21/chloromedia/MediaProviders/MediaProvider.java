package com.brandonio21.chloromedia.MediaProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.brandonio21.chloromedia.Async.ChloromediaTask;
import com.brandonio21.chloromedia.Async.PostExecutionOperation;
import com.brandonio21.chloromedia.Async.PreExecutionOperation;
import com.brandonio21.chloromedia.Async.ProgressUpdateOperation;
import com.brandonio21.chloromedia.Data.Album;
import com.brandonio21.chloromedia.Data.ProviderItem;
import com.brandonio21.chloromedia.ExclusionTables.ExclusionTable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MediaProvider {
    ExclusionTable exclusionTable;

    static final Map<String, Integer> PERMISSIONS = new HashMap<String, Integer>();
    static {
        PERMISSIONS.put(Manifest.permission.READ_EXTERNAL_STORAGE, 0);
    }

    protected MediaProvider(ExclusionTable exclusionTable) {
        this.exclusionTable = exclusionTable;
    }

    public static void askForPermission(Activity activity) {
        for (String permission : PERMISSIONS.keySet()) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[] {permission}, PERMISSIONS.get(permission));
            }
        }
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
