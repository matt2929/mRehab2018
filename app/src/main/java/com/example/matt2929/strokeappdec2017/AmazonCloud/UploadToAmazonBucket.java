/*
 * Copyright 2015-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.example.matt2929.strokeappdec2017.AmazonCloud;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * UploadActivity is a ListActivity of uploading, and uploaded records as well
 * as buttons for managing the uploads and creating new ones.
 */
public class UploadToAmazonBucket {

	// Indicates that no upload is currently selected
	private static final int INDEX_NOT_CHECKED = -1;

	// TAG for logging;
	private static final String TAG = "UploadActivity";

	// Button for upload operations
	private Button btnUploadFile;


	// The TransferUtility is the primary class for managing transfer to S3
	private TransferUtility transferUtility;

	// The SimpleAdapter adapts the data about transfers to rows in the UI
	private SimpleAdapter simpleAdapter;

	// A List of all transfers
	private List<TransferObserver> observers;

	/**
	 * This map is used to provide data to the SimpleAdapter above. See the
	 * fillMap() function for how it relates observers to rows in the displayed
	 * activity.
	 */
	private ArrayList<HashMap<String, Object>> transferRecordMaps;

	// Which row in the UI is currently checked (if any)
	private int checkedIndex;

	private Context context;

	public UploadToAmazonBucket(Context context) {
		transferUtility = Util.getTransferUtility(context);
		checkedIndex = INDEX_NOT_CHECKED;
		transferRecordMaps = new ArrayList<HashMap<String, Object>>();
		this.context = context;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	public void saveData(File file) {
		if (!file.exists()) {
			Log.e("Save file to bucket", "That file doesnt Exist?");
		} else {
			String path = file.getPath();
			beginUpload(path);
		}
	}

	/*
	 * Begins to upload the file specified by the file path.
	 */
	private void beginUpload(String filePath) {
		if (filePath == null) {
			return;
		}
		File file = new File(filePath);
		TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, file.getName(),
				file);
		observer.setTransferListener(new UploadListener());
	}

	@SuppressLint("NewApi")
	private String getPath(Uri uri) throws URISyntaxException {
		final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
		String selection = null;
		String[] selectionArgs = null;
		// Uri is different in versions after KITKAT (Android 4.4), we need to
		// deal with different Uris.
		if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				return Environment.getExternalStorageDirectory() + "/" + split[1];
			} else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				uri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
			} else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				if ("image".equals(type)) {
					uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				selection = "_id=?";
				selectionArgs = new String[]{
						split[1]
				};
			}
		}
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = {
					MediaStore.Images.Media.DATA
			};
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver()
						.query(uri, projection, selection, selectionArgs, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	/*
	 * A TransferListener class that can listen to a upload task and be notified
	 * when the status changes.
	 */
	private class UploadListener implements TransferListener {

		// Simply updates the UI list when notified.
		@Override
		public void onError(int id, Exception e) {
			Log.e(TAG, "Error during upload: " + id, e);

		}

		@Override
		public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
			Log.d(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
					id, bytesTotal, bytesCurrent));
			WorkoutData.progressCloud = (float) ((bytesCurrent / bytesTotal) * 100l);
			Log.e("Cloud Upload", "" + WorkoutData.progressCloud);
		}

		@Override
		public void onStateChanged(int id, TransferState newState) {
			Log.d(TAG, "onStateChanged: " + id + ", " + newState);

		}
	}
}
