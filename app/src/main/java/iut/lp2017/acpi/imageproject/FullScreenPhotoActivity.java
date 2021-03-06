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
import android.widget.TextView;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.views.FullScreenView;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created on 18/01/17.
 */
public class FullScreenPhotoActivity extends Activity
{
    private static final String IMAGE_PATH_TAG = "intent.acpi.image_source";
    private static final String IMAGE_NAME_TAG = "intent.acpi.image_name";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent ListActivityInt = getIntent();

        String imageSource = ListActivityInt.getStringExtra(IMAGE_PATH_TAG);
        String imageName = ListActivityInt.getStringExtra(IMAGE_NAME_TAG);

        setContentView(R.layout.activity_fullscreen);
        FullScreenView fsView = (FullScreenView) findViewById(R.id.fullscreenview);
        TextView imageNameText = (TextView) findViewById(R.id.imageName);
        imageNameText.setText(imageName);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bmp = BitmapFactory.decodeFile(imageSource, options);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        boolean isPortrait = Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
        if (isPortrait)
            fsView.set_bmpImage(BitmapScaler.scaleToFitWidth(bmp,metrics.widthPixels));
        else
            fsView.set_bmpImage(BitmapScaler.scaleToFitHeight(bmp,metrics.heightPixels));
    }
}
