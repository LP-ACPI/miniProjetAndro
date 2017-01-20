package iut.lp2017.acpi.photos;

import android.content.Context;
import android.content.Intent;

/**
 * Created by necesanym on 18/01/17.
 */
public class PhotosController {

    private static final String PHOTO_NAME_LABEL = "intent.tpun.acpi.photo_source";

    private static PhotosController instance;

    public PhotosController() {}

    public static PhotosController getInstance() {
        if(instance == null)
            instance = new PhotosController();
        return instance;
    }

    public void showFullScreen(Context previousActivity,String nomImage){
        Intent fullScreenIntent = new Intent(previousActivity, FullScreenPhotoActivity.class);

        fullScreenIntent.putExtra(PHOTO_NAME_LABEL, nomImage);

        previousActivity.startActivity(fullScreenIntent);

    }

}
