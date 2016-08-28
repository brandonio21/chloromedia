package com.brandonio21.chloromedia.Data;

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
    Bitmap getPreviewImage() {
        return null;
    }

    @Override
    String getName() {
        return null;
    }

    @Override
    Date getDate() {
        return null;
    }

    @Override
    Long getSize() {
        return null;
    }
}
