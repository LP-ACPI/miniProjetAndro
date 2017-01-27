package iut.lp2017.acpi.imageproject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.controllers.ImageController;
import iut.lp2017.acpi.imageproject.daohandlers.AsyncXMLsaxParser;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.I_Async;
import iut.lp2017.acpi.imageproject.views.ImageDialogLayout;
import iut.lp2017.acpi.imageproject.views.ImageListAdapter;

/**
 * Created by Marek on 27/01/2017.
 */

public class ListImagesActivity extends Activity implements I_Async {

    final Context context = this;
    private ListView tpListView;
    private static AsyncXMLsaxParser AXSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *  TODO : implementation+délégation de I_Async+ses méthodes -> dans l'activité qui va lister les images (catégories plus tard)
         */
        AXSP = new AsyncXMLsaxParser(this);
        AXSP.delegateViewEventsTo(this);
        AXSP.execute("http://public.ave-comics.com/gabriel/iut/images.xml");

        setContentView(R.layout.activity_list);

        tpListView = (ListView) findViewById(R.id.tpListView);
        initListsItemDialog(tpListView);
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
        if(downloadProgress!= null)
        {
            if (!downloadProgress.isShowing())
                downloadProgress.show();
            downloadProgress.setMessage("téléchargement terminé!\nVoir résultats dans logCat");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    downloadProgress.dismiss();
                    initList(AXSP.getReachedImages());
                }
            }, 2000);
        }
    }

    private void initList(List<ImageModel> imageList){

        final ImageListAdapter listAdapter = new ImageListAdapter(this, imageList);
        tpListView.setAdapter(listAdapter);
    }

    private void initListsItemDialog(ListView listV){

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                final Dialog dialog = new Dialog(context);
                ImageModel iM = (ImageModel) parent.getAdapter().getItem(position);
                dialog.setContentView(initDialogView(dialog, iM));
                dialog.show();
            }
        });

    }

    private RelativeLayout initDialogView(final Dialog diag, final ImageModel iM){

        diag.setTitle("image n°" + iM.getId() + " " + iM.getNom());
        ImageDialogLayout diagLayout = new ImageDialogLayout(context , iM);

        diagLayout.getDismissButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag.dismiss();
            }
        });

        diagLayout.getFullScreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageController.getInstance().showFullScreen(context, iM.getLocalpath(),iM.getNom());
            }
        });

        return diagLayout;
    }
}
