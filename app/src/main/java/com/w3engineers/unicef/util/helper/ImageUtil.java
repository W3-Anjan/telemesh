package com.w3engineers.unicef.util.helper;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 10/29/2018 at 4:21 PM.
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 10/29/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.w3engineers.unicef.TeleMeshApplication;
import com.w3engineers.unicef.telemesh.R;
import com.w3engineers.unicef.telemesh.data.helper.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ImageUtil {
    private static final String LOCAL_RESOURCE_SCHEME = "res";
    private static Map<Integer, Integer> imageIndexMap;

    static {
        imageIndexMap = new HashMap<>();
        imageIndexMap.put(0, R.mipmap.avatar0);
        imageIndexMap.put(1, R.mipmap.avatar1);
        imageIndexMap.put(2, R.mipmap.avatar2);
        imageIndexMap.put(3, R.mipmap.avatar3);
        imageIndexMap.put(4, R.mipmap.avatar4);
        imageIndexMap.put(5, R.mipmap.avatar5);
        imageIndexMap.put(6, R.mipmap.avatar6);
        imageIndexMap.put(7, R.mipmap.avatar7);
        imageIndexMap.put(8, R.mipmap.avatar8);
        imageIndexMap.put(9, R.mipmap.avatar9);
        imageIndexMap.put(11, R.mipmap.avatar11);
        imageIndexMap.put(12, R.mipmap.avatar12);
        imageIndexMap.put(13, R.mipmap.avatar13);
        imageIndexMap.put(14, R.mipmap.avatar14);
        imageIndexMap.put(15, R.mipmap.avatar15);
        imageIndexMap.put(16, R.mipmap.avatar16);
        imageIndexMap.put(17, R.mipmap.avatar17);
        imageIndexMap.put(18, R.mipmap.avatar18);
        imageIndexMap.put(19, R.mipmap.avatar19);
        imageIndexMap.put(20, R.mipmap.avatar20);
    }

    public static List<Integer> getAllImages() {
        List<Integer> imageIds = new ArrayList<>();
        for (int i = 0; i < imageIndexMap.size(); i++) {
            imageIds.add(i);
        }
        return imageIds;
    }

    private static Uri getUserImageUri(int imageIndex) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(LOCAL_RESOURCE_SCHEME).path(String.valueOf(imageIndexMap.get(imageIndex)));
        return builder.build();
    }

    public static Bitmap getUserImageBitmap(int userImageIndex) {
        Uri uri = getUserImageUri(userImageIndex);
        Bitmap bitmap = getCenterCropBitmap(uri, 400, 400);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(TeleMeshApplication.getContext().getResources(), imageIndexMap.get(userImageIndex));
        }
        return getCroppedBitmap(bitmap);
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {

        if (bitmap == null) return null;

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap getCenterCropBitmap(Uri imageUri, int width, int height) {
        try {
            return Glide.with(TeleMeshApplication.getContext())
                    .asBitmap()
                    .load(imageUri)
                    .apply(getRequestOptions())
                    .submit(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String bitmapSave(String fileName, Bitmap bitmap) {

        Bitmap compressedBitmap = processImageSize(bitmap);
        return bitmapSaveFunctionality(compressedBitmap, fileName);
    }

    private Bitmap processImageSize(Bitmap bitmap) {

        if (bitmap == null)
            return null;

        float imageHeight = 100;
        float imageWidth = 100;

        float width = bitmap.getWidth();
        float height = bitmap.getHeight();

        float scaleWidth = (width > imageWidth) ? imageWidth / width : 1;
        float scaleHeight = (height > imageHeight) ? imageHeight / width : 1;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, false);

    }


    private static RequestOptions getRequestOptions() {
        return RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    private String bitmapSaveFunctionality(Bitmap imageBitmap, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }

                return getBase64String(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getBase64String(String imagePath) {

        File imageFilePath = new File(imagePath);

        if (!imageFilePath.exists()) return null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Remove options from decode file during decode it is providing null bitmap
        // Might be the low size image the options are optional for decoding image path
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath.getAbsolutePath());
        if (bitmap == null) return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();

        if (byteArray != null) {
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        return null;
    }

    public void directoryChecker() {
        File feedProviderLogoDir = new File(Constants.Directory.FEED_PROVIDER_LOGO_DIRECTORY);

        if (feedProviderLogoDir.exists())
            return;

        File rootDirectory = new File(Constants.Directory.ROOT_DIRECTORY);

        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }
        feedProviderLogoDir.mkdirs();
    }

    public String downloadImageFromUriAndSaveLocally(Uri uri, int height, int width, String fileName) {
        directoryChecker();
        Bitmap bitmap = getCenterCropBitmap(uri, width, height);
        return bitmapSave(fileName, bitmap);
    }
}
