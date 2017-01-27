package iut.lp2017.acpi.imageproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.controllers.ImageController;
import iut.lp2017.acpi.imageproject.daohandlers.AsyncXMLsaxParser;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.I_Async;
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
        downloadProgress.setMessage("téléchargement d'images...");
        downloadProgress.show();
    }

    @Override
    public void asyncProcessDone()
    {
        if(downloadProgress!= null)
        {
            if (!downloadProgress.isShowing())
                downloadProgress.show();
            downloadProgress.setMessage("téléchargement terminé!");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    downloadProgress.dismiss();
                    initList(AXSP.getReachedImages());
                }
            }, 1500);
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

                ImageModel iM = (ImageModel) parent.getAdapter().getItem(position);
                ImageController.getInstance().showSelectedImageDialog(context,iM);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_categories) {
            ImageController.getInstance().showCatogoryListDialog(context,AXSP.getReachedDistinctCategories());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
