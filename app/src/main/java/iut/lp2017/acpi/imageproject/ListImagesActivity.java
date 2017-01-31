package iut.lp2017.acpi.imageproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.controllers.ImageController;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.ImageListAdapter;

/**
 * Created on 27/01/2017.
 */

public class ListImagesActivity extends Activity
{
    private final Context _context = this;

    private ListView _listView;
    private List<ImageModel> _imageModelList;
    private List<String> _categoryList;
    private boolean filterOn;

    private static ImageController _sImgCotroller = ImageController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _sImgCotroller.setListImageActivity(this);

        setContentView(R.layout.activity_list);
        _listView = (ListView) findViewById(R.id.tpListView);
        initListsItemDialog(_listView);

        _sImgCotroller.transferDownloadedData();
        initList();
    }

    public void initList()
    {
        final ImageListAdapter listAdapter = new ImageListAdapter(_context, _imageModelList);
        _listView.setAdapter(listAdapter);
        filterOn = false;
    }

    private void initFilteredList(List<ImageModel> imageList)
    {
        final ImageListAdapter listAdapter = new ImageListAdapter(_context, imageList);
        _listView.setAdapter(listAdapter);
        filterOn = true;
    }

    private void initListsItemDialog(ListView listV)
    {
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id)
            {
                ImageModel iM = (ImageModel) parent.getAdapter().getItem(position);
                _sImgCotroller.showSelectedImageDialog(_context,iM);
            }
        });
    }

    public void filterListViewByCategoryName(String category)
    {
        List<ImageModel> tmpImageList = new ArrayList<>(_imageModelList);
        for(ImageModel iM : _imageModelList)
        {
            if(!iM.getCategories().contains(category))
            {
                tmpImageList.remove(iM);
            }
        }
        initFilteredList(tmpImageList);
    }

    public void setImageModelList(List<ImageModel> imageModelList)
    {
        this._imageModelList = imageModelList;
    }

    public void setCategoryList(List<String> categoryList)
    {
        this._categoryList = categoryList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(filterOn)
            initList();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_categories && !_categoryList.isEmpty()) {
            _sImgCotroller.showCategoryListDialog(_context, _categoryList);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
