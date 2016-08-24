package com.brandonio21.chloromedia.Data;

import android.graphics.Bitmap;

import com.brandonio21.chloromedia.MediaProviders.MediaProvider;

import java.util.Date;
import java.util.Map;

public abstract class ProviderItem {
    MediaProvider mediaProvider;
    Map<String, ProviderItemMetadata> metadata;

    ProviderItem(MediaProvider mediaProvider, Map<String, ProviderItemMetadata> metadata) {
        this.mediaProvider = mediaProvider;
        this.metadata = metadata;
    }

    abstract Bitmap getPreviewImage();

    abstract String getName();
    abstract Date getDate();
    abstract Long getSize();

    <T extends ProviderItemMetadata> T getMetadata(String metadataKey) {
        return (T) this.metadata.get(metadataKey);
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
