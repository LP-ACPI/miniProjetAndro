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

import java.util.ArrayList;
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
    private List<ImageModel> imageModelList;
    private List<String> categoryList;
    private static AsyncXMLsaxParser AXSP;
    private static ImageController imgCotroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgCotroller = ImageController.getInstance();
        imgCotroller.setListImageActivity(this);

        AXSP = new AsyncXMLsaxParser(this);
        AXSP.delegateViewEventsTo(this);
        String officialUrl = "http://public.ave-comics.com/gabriel/iut/images.xml";
        String testUrl = "http://infolimon.iutmontp.univ-montp2.fr/~necesanym/images.xml";
        AXSP.execute(testUrl);

        setContentView(R.layout.activity_list);

        tpListView = (ListView) findViewById(R.id.tpListView);
        initListsItemDialog(tpListView);
    }

    ProgressDialog downloadProgress;
    @Override
    public void asyncProcessBegan()
    {
        downloadProgress = new ProgressDialog(this);
        downloadProgress.setMessage(getResources().getString(R.string.downloading_images));
        downloadProgress.show();
    }

    @Override
    public void asyncProcessDone()
    {
        if(downloadProgress!= null)
        {
            if (!downloadProgress.isShowing())
                downloadProgress.show();
            downloadProgress.setMessage(getResources().getString(R.string.images_downloaded));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    downloadProgress.dismiss();
                    imageModelList = AXSP.getReachedImages();
                    categoryList = AXSP.getReachedDistinctCategories();
                    initList();
                }
            }, 1500);
        }
    }

    public void initList(){
        final ImageListAdapter listAdapter = new ImageListAdapter(context, imageModelList);
        tpListView.setAdapter(listAdapter);
    }

    private void initFilteredList(List<ImageModel> imageList){
        final ImageListAdapter listAdapter = new ImageListAdapter(context, imageList);
        tpListView.setAdapter(listAdapter);
    }

    private void initListsItemDialog(ListView listV){

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id)
            {
                ImageModel iM = (ImageModel) parent.getAdapter().getItem(position);
                imgCotroller.showSelectedImageDialog(context,iM);
            }
        });
    }

    public void filterListViewByCategoryName(String category){
        List<ImageModel> tmpImageList = new ArrayList<>(imageModelList);
        for(ImageModel iM : imageModelList)
        {
            if(!iM.getCategories().contains(category))
            {
                tmpImageList.remove(iM);
            }
        }
        initFilteredList(tmpImageList);
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
            imgCotroller.showCatogoryListDialog(context,categoryList);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
