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
import iut.lp2017.acpi.imageproject.ListImagesActivity;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.DialogCategoriesLayout;
import iut.lp2017.acpi.imageproject.views.DialogImageLayout;

/**
 * Created on 18/01/17.
 */
public class ImageController {

    private static final String IMAGE_PATH_TAG = "intent.tpun.acpi.image_source";
    private static final String IMAGE_NAME_TAG = "intent.tpun.acpi.image_name";

    private static ImageController instance;
    private static ListImagesActivity listImagesActivity;

    public ImageController() {}

    public static ImageController getInstance()
    {
        if (instance == null)
            instance = new ImageController();
        return instance;
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
                ImageController.getInstance().showFullScreen(context, iM.getLocalpath(),iM.getNom());
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

    public void showCatogoryListDialog(final Context context,List<String> categoryList)
    {
        final Dialog dialog = new Dialog(context);

        DialogCategoriesLayout diagLayout = new DialogCategoriesLayout(context, categoryList);

        for (final Button categoryButton : diagLayout.getCategoryButtons())
            categoryButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String categoryToFilter = categoryButton.getText().toString();
                    listImagesActivity.filterListViewByCategoryName(categoryToFilter);
                    dialog.dismiss();
                    Toast.makeText(
                            context,
                            context.getResources().getString(R.string.filtred_by)
                                    + " '" + categoryToFilter + "'",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        diagLayout.getNoCategoryFilterButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listImagesActivity.initList();
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

    public void setListImageActivity(ListImagesActivity listImagesActivity)
    {
        this.listImagesActivity = listImagesActivity;
    }
}




