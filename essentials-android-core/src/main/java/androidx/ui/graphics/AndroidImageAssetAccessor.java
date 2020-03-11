package androidx.ui.graphics;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

public final class AndroidImageAssetAccessor {

    @NotNull
    public static ImageAsset createAndroidImage(Bitmap bitmap) {
        //noinspection KotlinInternalInJava
        return new AndroidImageAsset(bitmap);
    }

}