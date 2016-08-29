package com.brandonio21.chloromedia.MediaProviders;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.brandonio21.chloromedia.Async.ChloromediaMediaProviderTask;
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

public class AndroidMediaProvider extends MediaProvider {

    public AndroidMediaProvider(ExclusionTable exclusionTable) {
        super(exclusionTable);
    }

    @Override
    public AsyncTask<Context, Album, Collection<Album>>
    getAlbums(PreExecutionOperation preOperation,
              ProgressUpdateOperation<Album> progressOperation,
              PostExecutionOperation<Collection<Album>> postOperation) {

        return new ChloromediaMediaProviderTask<Context, Album, Collection<Album>>(
                this,
                preOperation,
                progressOperation,
                postOperation) {



            @Override
            protected Collection<Album> doInBackground(Context... context) {
                if (context.length < 1 || context.length > 1) {
                    throw new IllegalArgumentException("Should provide a single context");
                }

                Context contextValue = context[0];

                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projection = {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                };


                Map<String, Album> ids = new HashMap<String, Album>();

                Cursor cursor;

                try {
                    cursor = contextValue.getContentResolver().query(uri, projection,
                            null, null, null);
                }
                catch (Exception ex) {
                    return ids.values();
                }

                if (cursor == null)
                    return ids.values();

                int albumIdColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                int albumNameColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int mediaIdColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);

                while (cursor.moveToNext()) {
                    String albumId = cursor.getString(albumIdColumnIndex);
                    String mediaId = cursor.getString(mediaIdColumnIndex);

                    if (!ids.containsKey(albumId)) {
                        AndroidMediaProviderAlbumMetadata metadata =
                                new AndroidMediaProviderAlbumMetadata(albumId);

                        String albumName = cursor.getString(albumNameColumnIndex);

                        Album album = new Album(this.getMediaProvider(), metadata, albumName);

                        ids.put(albumId, album);

                        publishProgress(album);
                    }

                    AndroidMediaProviderAlbumMetadata albumMetadata =
                            (AndroidMediaProviderAlbumMetadata) ids.get(albumId).getMetadata();

                    albumMetadata.addMedia(mediaId);
                }
                cursor.close();
                return ids.values();
            }
        };
    }

    @Override
    public ChloromediaTask<Void, ProviderItem, List<ProviderItem>> getSubItems(Album album, PreExecutionOperation preOperation, ProgressUpdateOperation<ProviderItem> progressOperation, PostExecutionOperation<List<ProviderItem>> postOperation) {
        return null;
    }

    String getMediaURIFromId(Context context, String id) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media.DATA
        };

        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    MediaStore.Images.Media._ID + "=?",
                    new String[] {id},
                    null);
        }
        catch (Exception ex) {
            return "";
        }

        int uriColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        while (cursor.moveToNext()) {
            return cursor.getString(uriColumnIndex);
        }
        return "";
    }

    @Override
    public Bitmap getImage(Context context, ProviderItem item) {
        if (item instanceof Album)
            return this.getImageForAlbum(context, (Album) item);
        else
            return null;
    }


    Bitmap getImageForAlbum(Context context, Album album) {
        AndroidMediaProviderAlbumMetadata albumMetadata = (AndroidMediaProviderAlbumMetadata) album.getMetadata();
        if (albumMetadata.mediaIds.size() > 0) {
            return this.getImageFromMediaId(context, albumMetadata.mediaIds.iterator().next());
        }
        else {
            return null;
        }
    }

    Bitmap getImageFromMediaId(Context context, String mediaId) {
        return MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(), Long.parseLong(mediaId), MediaStore.Images.Thumbnails.MINI_KIND, null);
    }


    @Override
    public void moveItem(ProviderItem item, Album newParent) {

    }

    @Override
    public void deleteItem(ProviderItem item) {

    }

    @Override
    public void renameItem(ProviderItem item, String name) {

    }
}
