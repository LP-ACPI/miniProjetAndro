package iut.lp2017.acpi.photos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.photos.daohandlers.AsyncXMLsaxParser;
import iut.lp2017.acpi.photos.views.FullScreenView;
import iut.lp2017.acpi.photos.views.I_Async;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by necesanym on 18/01/17.
 */
public class FullScreenPhotoActivity extends Activity implements I_Async
{
    private static final String PHOTO_NAME_LABEL = "intent.tpun.acpi.photo_source";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent ListActivityInt = getIntent();

        String imageSource = ListActivityInt.getStringExtra(PHOTO_NAME_LABEL);

        setContentView(R.layout.activity_fullscreen);
        FullScreenView fsView = (FullScreenView) findViewById(R.id.fullscreenview);
        fsView.set_IMGName(imageSource);

        InputStream stream = null;
        try {
            stream = getAssets().open(imageSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bmp = BitmapFactory.decodeStream(stream);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        boolean isPortrait = Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
        if (isPortrait)
            fsView.set_BMPimage(BitmapScaler.scaleToFitWidth(bmp,metrics.widthPixels));
        else
            fsView.set_BMPimage(BitmapScaler.scaleToFitHeight(bmp,metrics.heightPixels));

        /**
         *  TODO : implementation+délégation de I_Async+ses méthodes -> dans l'activité qui va lister les images (catégories plus tard)
         */
        AsyncXMLsaxParser AXSP = new AsyncXMLsaxParser(this);
        AXSP.delegateViewEventsTo(this);
        AXSP.execute("http://public.ave-comics.com/gabriel/iut/images.xml");
    }

    ProgressDialog downloadProgress;
    @Override
    public void asyncProcessBegan()
    {
        downloadProgress = new ProgressDialog(this);
        downloadProgress.setMessage("téléchargement d'images");
        downloadProgress.show();
    }

    @Override
    public void asyncProcessDone()
    {
        if (downloadProgress != null)
        {

            if(downloadProgress!= null && downloadProgress.isShowing()) {
                downloadProgress.setMessage("téléchargement terminé!\nVoir résultats dans logCat");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        downloadProgress.dismiss();
                    }
                }, 3000);
            }
        }
    }
}
