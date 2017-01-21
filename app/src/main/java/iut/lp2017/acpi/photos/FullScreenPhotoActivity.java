package iut.lp2017.acpi.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by necesanym on 18/01/17.
 */
public class FullScreenPhotoActivity extends Activity {


    private static final String PHOTO_NAME_LABEL = "intent.tpun.acpi.photo_source";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent ListActivityInt = getIntent();

        String imageSource = ListActivityInt.getStringExtra(PHOTO_NAME_LABEL);


        setContentView(initFullScreenView(imageSource));

    /* adapt the image to the size of the display */

    }

    public RelativeLayout initFullScreenView(String image){
        RelativeLayout fullScreen = new RelativeLayout(this);

        RelativeLayout.LayoutParams fulScreenParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        fullScreen.setLayoutParams(fulScreenParams);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        InputStream stream = null;
        try {
            stream = getAssets().open(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(stream),width,height,true);
        ImageView fullscreenImage = new ImageView(this);
        fullscreenImage.setImageBitmap(bmp);

        fullScreen.addView(fullscreenImage);
        return fullScreen;
    }

}
