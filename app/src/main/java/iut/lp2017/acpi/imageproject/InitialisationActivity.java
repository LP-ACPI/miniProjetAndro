package iut.lp2017.acpi.imageproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.controllers.ImageController;
import iut.lp2017.acpi.imageproject.daohandlers.AsyncXMLsaxParser;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.IAsync;

/**
 * Created on 30/01/2017.
 */

public class InitialisationActivity extends Activity implements IAsync
{
    private static final String OFFICIAL_URL = "http://public.ave-comics.com/gabriel/iut/images.xml";
    private static final String TEST_URL     = "http://infolimon.iutmontp.univ-montp2.fr/~necesanym/images.xml";

    private ProgressDialog _downloadProgressDialog;
    private List<ImageModel> _imageModelList;
    private List<String> _categoryList;
    private static AsyncXMLsaxParser _sAsyncXSParser;
    private static ImageController _sImgCotroller = ImageController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _sImgCotroller.setInitActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_init);
        _sAsyncXSParser = new AsyncXMLsaxParser(this);
        _sAsyncXSParser.delegateViewEventsTo(this);
        _sAsyncXSParser.execute(TEST_URL);
    }

    @Override
    public void asyncProcessBegan()
    {
        _downloadProgressDialog = new ProgressDialog(this);
        _downloadProgressDialog.setMessage(getResources().getString(R.string.downloading_images));
        _downloadProgressDialog.show();
    }

    @Override
    public void asyncProcessDone()
    {
        final boolean errorOccured = _sAsyncXSParser.getException() != null;
        String finalMessage = getResources().getString(R.string.download_success);

        if(errorOccured)
            finalMessage = getResources().getString(R.string.download_fail);

        if (!_downloadProgressDialog.isShowing())
            _downloadProgressDialog.show();

        _downloadProgressDialog.setMessage(finalMessage);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                _downloadProgressDialog.dismiss();
                _imageModelList = _sAsyncXSParser.getReachedImages();
                _categoryList = _sAsyncXSParser.getReachedDistinctCategories();
                if(errorOccured)
                    showAlertDialog(getString(R.string.InternetConnectionWarning));
                else _sImgCotroller.runListImageActivity();
            }
        }, 1500);

    }

    private void showAlertDialog(String message)
    {
        findViewById(R.id.WarningDialog).setVisibility(View.VISIBLE);
        TextView warningText = (TextView)findViewById(R.id.warningText);
        warningText.setText(message);
    }

    public  List<ImageModel> getImageModelList()
    {
        return _imageModelList;
    }

    public List<String> getCategoryList()
    {
        return _categoryList;
    }

    public void finish(View view)
    {
        finish();
    }

    public void reset(View view)
    {
        recreate();
    }
}
