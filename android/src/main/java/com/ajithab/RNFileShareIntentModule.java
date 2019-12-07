
package com.ajithab;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;


public class RNFileShareIntentModule extends ReactContextBaseJavaModule implements ActivityEventListener {

  private Callback successShareCallback;
  private final ReactApplicationContext reactContext;

  public RNFileShareIntentModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  public void onNewIntent(Intent intent) {
    Activity mActivity = getCurrentActivity();

    if(mActivity == null) { return; }

    mActivity.setIntent(intent);
    shareFile(intent);
  }

  @Override
  public void onActivityResult(Activity aActivity, int requestCode, int resultCode, Intent intent) {
    shareFile(intent);
  }

  @ReactMethod
  public void setCallback(Callback successCallback) {
    successShareCallback = successCallback;
  }

  @ReactMethod
  public void getFilePath(Callback successCallback) {
    successShareCallback = successCallback;
    reactContext.addActivityEventListener(this);

    Activity mActivity = getCurrentActivity();

    if(mActivity == null) { return; }

    Intent intent = mActivity.getIntent();
    shareFile(intent);
  }

  private void shareFile(Intent intent) {
    if (intent == null)
      return;

    String action = intent.getAction();
    String type = intent.getType();

    if(action == null || type == null)
      return;

    FileHelper fileHelper = new FileHelper(this.reactContext);

    WritableArray res = new WritableNativeArray();
    if (Intent.ACTION_SEND.equals(action)) {
      if (type.startsWith("text")) {
        String input = intent.getStringExtra(Intent.EXTRA_TEXT);
        successShareCallback.invoke(input, type);
      } else if (type.startsWith("application/") || type.startsWith("audio/") || type.startsWith("image/") || type.startsWith("message/") ||
          type.startsWith("video/") || type.startsWith("x-world/")) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (fileUri != null) {
          res.pushMap(fileHelper.getFileData(fileUri));
          successShareCallback.invoke(res);
        }
      }
    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
      if (type.startsWith("image/") || type.startsWith("video/") || type.startsWith("audio/") || type.startsWith("application/")) {
        ArrayList<Uri> fileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (fileUris != null) {
          for (Uri uri : fileUris) {
            res.pushMap(fileHelper.getFileData(uri));
          }
          successShareCallback.invoke(res);
        }
      }
    }
  }

  @ReactMethod
  public void clearFilePath() {
    Activity mActivity = getCurrentActivity();

    if(mActivity == null) { return; }

    Intent intent = mActivity.getIntent();
    String type = intent.getType();

    if (intent == null || type == null) { return; }

    if (type.startsWith("text/") || type.startsWith("x-world/")) {
      intent.removeExtra(Intent.EXTRA_TEXT);
    } else if (type.startsWith("image/") || type.startsWith("audio/") || type.startsWith("application/") ||
            type.startsWith("message/") || type.startsWith("video/")) {
      intent.removeExtra(Intent.EXTRA_STREAM);
    }
  }

  @Override
  public String getName() {
    return "RNFileShareIntent";
  }
}
