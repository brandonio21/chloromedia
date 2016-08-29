package com.brandonio21.chloromedia.Data;

import android.content.Context;
import android.graphics.Bitmap;

import com.brandonio21.chloromedia.MediaProviders.MediaProvider;
import com.brandonio21.chloromedia.MediaTypes.MediaType;

import java.util.Date;

public class Media extends ProviderItem {
    MediaType mediaType;

    Media(MediaProvider mediaProvider, ProviderItemMetadata metadata) {
        super(mediaProvider, metadata);
    }

    @Override
    public Bitmap getPreviewImage(Context context) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Long getSize() {
        return null;
    }
}
