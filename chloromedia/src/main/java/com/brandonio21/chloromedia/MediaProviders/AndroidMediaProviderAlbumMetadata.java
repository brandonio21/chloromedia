package com.brandonio21.chloromedia.MediaProviders;

import com.brandonio21.chloromedia.Data.ProviderItemMetadata;

import java.util.ArrayList;
import java.util.List;

public class AndroidMediaProviderAlbumMetadata extends ProviderItemMetadata {
    String albumBucketId;
    List<String> mediaIds;

    public AndroidMediaProviderAlbumMetadata(String albumBucketId) {
        this.albumBucketId = albumBucketId;
        this.mediaIds = new ArrayList<String>();
    }

    public void addMedia(String mediaId) {
        this.mediaIds.add(mediaId);
    }
}
