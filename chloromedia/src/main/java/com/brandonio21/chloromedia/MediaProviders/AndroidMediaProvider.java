package com.brandonio21.chloromedia.MediaProviders;

import android.os.AsyncTask;

import com.brandonio21.chloromedia.Data.Album;
import com.brandonio21.chloromedia.Data.ProviderItem;
import com.brandonio21.chloromedia.ExclusionTables.ExclusionTable;

import java.util.Collection;

public class AndroidMediaProvider extends MediaProvider {

    protected AndroidMediaProvider(ExclusionTable exclusionTable) {
        super(exclusionTable);
    }

    @Override
    AsyncTask<Void, ProviderItem, Album> getRootAlbum() {
        return null;
    }

    @Override
    AsyncTask<Album, ProviderItem, Collection<ProviderItem>> getSubItems(Album album) {
        return null;
    }

    @Override
    void moveItem(ProviderItem item, Album newParent) {

    }

    @Override
    void deleteItem(ProviderItem item) {

    }

    @Override
    void renameItem(ProviderItem item, String name) {

    }
}
