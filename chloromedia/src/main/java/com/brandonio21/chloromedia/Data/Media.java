package com.brandonio21.chloromedia.Data;

import android.graphics.Bitmap;

import com.brandonio21.chloromedia.MediaTypes.MediaType;

public class Media extends ProviderItem {
    MediaType mediaType;

    @Override
    Bitmap getPreviewImage() {
        return null;
    }
}
