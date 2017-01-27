package iut.lp2017.acpi.imageproject.controllers;

import android.content.Context;
import android.content.Intent;

import iut.lp2017.acpi.imageproject.FullScreenPhotoActivity;

/**
 * Created by necesanym on 18/01/17.
 */
public class ImageController {

    private static final String IMAGE_PATH_TAG = "intent.tpun.acpi.image_source";
    private static final String IMAGE_NAME_TAG = "intent.tpun.acpi.image_name";

    private static ImageController instance;

    public ImageController() {}

    public static ImageController getInstance() {
        if (instance == null)
            instance = new ImageController();
        return instance;
    }

    public void showFullScreen(Context previousActivity, String pathImage,String nameImage) {
        Intent fullScreenIntent = new Intent(previousActivity, FullScreenPhotoActivity.class);

        fullScreenIntent.putExtra(IMAGE_PATH_TAG, pathImage);
        fullScreenIntent.putExtra(IMAGE_NAME_TAG, nameImage);

        previousActivity.startActivity(fullScreenIntent);
    }
}




