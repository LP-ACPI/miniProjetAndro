package iut.lp2017.acpi.imageproject.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.FullScreenPhotoActivity;
import iut.lp2017.acpi.imageproject.InitialisationActivity;
import iut.lp2017.acpi.imageproject.ListImagesActivity;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.DialogCategoriesLayout;
import iut.lp2017.acpi.imageproject.views.DialogImageLayout;

/**
 * Created on 18/01/17.
 */
public class ImageController {

    private static final String IMAGE_PATH_TAG = "intent.acpi.image_source";
    private static final String IMAGE_NAME_TAG = "intent.acpi.image_name";

    private static ImageController _sInstance;
    private static InitialisationActivity _sInitActivity;
    private static ListImagesActivity _sListImagesActivity;

    public ImageController() {}

    public static ImageController getInstance()
    {
        if (_sInstance == null)
            _sInstance = new ImageController();
        return _sInstance;
    }

    public void showFullScreen(Context previousActivity, String pathImage,String nameImage)
    {
        Intent fullScreenIntent = new Intent(previousActivity, FullScreenPhotoActivity.class);

        fullScreenIntent.putExtra(IMAGE_PATH_TAG, pathImage);
        fullScreenIntent.putExtra(IMAGE_NAME_TAG, nameImage);

        previousActivity.startActivity(fullScreenIntent);
    }

    public void showSelectedImageDialog(final Context context,final ImageModel iM)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle(iM.getNom() + " (id: " + iM.getId() + ")" );
        DialogImageLayout diagLayout = new DialogImageLayout(context , iM);

        diagLayout.getFullScreenButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showFullScreen(context, iM.getLocalpath(),iM.getNom());
            }
        });
        diagLayout.getDismissButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(diagLayout);
        dialog.show();
    }

    public void showCategoryListDialog(final Context context, List<String> categoryList)
    {
        final Dialog dialog = new Dialog(context);

        DialogCategoriesLayout diagLayout = new DialogCategoriesLayout(context, categoryList);

        for (final Button categoryButton : diagLayout.getCategoryButtons())
            categoryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String categoryToFilter = categoryButton.getText().toString();
                    _sListImagesActivity.filterListViewByCategoryName(categoryToFilter);
                    dialog.dismiss();
                    Toast.makeText(
                            context,
                            context.getResources().getString(R.string.filtred_by)
                                    + " '" + categoryToFilter + "'",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        diagLayout.getNoCategoryFilterButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                _sListImagesActivity.initList();
                dialog.dismiss();
            }
        });
        diagLayout.getDismissButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(diagLayout);
        dialog.show();
    }

    public void runListImageActivity()
    {
        Intent listActivityIntent = new Intent(_sInitActivity, ListImagesActivity.class);
        _sInitActivity.startActivity(listActivityIntent);
    }

    public void transferDownloadedData()
    {
        _sListImagesActivity.setImageModelList(_sInitActivity.getImageModelList());
        _sListImagesActivity.setCategoryList(_sInitActivity.getCategoryList());
        _sInitActivity.finish();
    }

    public void setListImageActivity(ListImagesActivity listImagesActivity)
    {
        this._sListImagesActivity = listImagesActivity;
    }

    public void setInitActivity(InitialisationActivity initActivity)
    {
        this._sInitActivity = initActivity;
    }
}




