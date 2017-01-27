package iut.lp2017.acpi.imageproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.views.FullScreenView;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by necesanym on 18/01/17.
 */
public class FullScreenPhotoActivity extends Activity
{
    private static final String IMAGE_PATH_TAG = "intent.tpun.acpi.image_source";
    private static final String IMAGE_NAME_TAG = "intent.tpun.acpi.image_name";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent ListActivityInt = getIntent();

        String imageSource = ListActivityInt.getStringExtra(IMAGE_PATH_TAG);
        String imageName = ListActivityInt.getStringExtra(IMAGE_NAME_TAG);

        setContentView(R.layout.activity_fullscreen);
        FullScreenView fsView = (FullScreenView) findViewById(R.id.fullscreenview);
        fsView.set_IMGName(imageName);

        Bitmap bmp = null;
        try {
            InputStream stream = getAssets().open(imageSource);
            bmp = BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bmp = BitmapFactory.decodeFile(imageSource, options);
        }
        
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        boolean isPortrait = Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
        if (isPortrait)
            fsView.set_BMPimage(BitmapScaler.scaleToFitWidth(bmp,metrics.widthPixels));
        else
            fsView.set_BMPimage(BitmapScaler.scaleToFitHeight(bmp,metrics.heightPixels));
    }
}
