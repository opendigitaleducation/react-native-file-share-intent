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
        String result = "";

        try {
            if (uri.getScheme().equals("content")) {
                Cursor cursor = this.reactContext.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null || "".equals(result)) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        catch( Exception e) {
        }
        return result;
    }

    public String getMimeType(Uri uri) {
        String type = "";

        try {
            type = this.reactContext.getContentResolver().getType(uri);
        }
        catch( Exception e) {
            type = "";
        }
        finally {}

        return type;
    }

    public String getFilePath(Uri uri) {
        String[] columns = { MediaStore.Images.Media.DATA };
        String result = "";
        Cursor cursor = null;
        try {
            if (uri.getScheme().equals("content")) {
                cursor = this.reactContext.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex;
                        columnIndex = cursor.getColumnIndex(columns[0]);
                        result = cursor.getString(columnIndex);
                    }
            }
            if (result == null || "".equals(result)) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        catch( Exception e) {}
        finally {
            if (cursor != null)
                cursor.close();
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
