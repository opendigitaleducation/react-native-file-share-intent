package com.ajithab;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import android.net.Uri;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

public class FileHelper {

    private final ReactApplicationContext reactContext;

    public FileHelper(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    public String getFileName(Uri uri) {
        String result = null;

        if (uri.getScheme().equals("content")) {
            Cursor cursor = this.reactContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public String getMimeType(Uri uri) {
        return this.reactContext.getContentResolver().getType(uri);
    }

    public String getFilePath(Uri uri) {
        String[] columns = { MediaStore.Images.Media.DATA };
        String result = null;
        Cursor cursor = null;

        if (uri.getScheme().equals("content")) {
            cursor = this.reactContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex;
                    columnIndex = cursor.getColumnIndex(columns[0]);
                    result = cursor.getString(columnIndex);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return result;
    }

    public WritableMap getFileData(Uri uri) {
        WritableMap fileData = new WritableNativeMap();
        fileData.putString("name", this.getFileName(uri));
        fileData.putString("mime", this.getMimeType(uri));
        fileData.putString("path", "");
        fileData.putString("uri", uri.toString());

        return fileData;
    }
}
