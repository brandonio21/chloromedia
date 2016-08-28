package com.brandonio21.chloromedia.Data;

import android.graphics.Bitmap;

import com.brandonio21.chloromedia.MediaProviders.MediaProvider;

import java.util.Date;

public abstract class ProviderItem {
    MediaProvider mediaProvider;
    ProviderItemMetadata metadata;

    ProviderItem(MediaProvider mediaProvider, ProviderItemMetadata metadata) {
        this.mediaProvider = mediaProvider;
        this.metadata = metadata;
    }

    public abstract Bitmap getPreviewImage();

    public abstract String getName();
    public abstract Date getDate();
    public abstract Long getSize();

    public ProviderItemMetadata getMetadata() {
        return this.metadata;
    }

    void move(Album newParent) {
        this.mediaProvider.moveItem(this, newParent);
    }

    void delete() {
        this.mediaProvider.deleteItem(this);
    }

    void rename(String newName) {
        this.mediaProvider.renameItem(this, newName);
    }
}
