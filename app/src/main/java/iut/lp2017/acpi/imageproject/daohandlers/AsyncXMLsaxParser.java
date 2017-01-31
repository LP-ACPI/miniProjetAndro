package iut.lp2017.acpi.imageproject.daohandlers;

import android.content.Context;
import android.os.AsyncTask;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.imageproject.views.IAsync;

/**
 * Created on 26/01/2017.
 */

public class AsyncXMLsaxParser extends AsyncTask<String, Void, String>
{
    private static IAsync _sDelegate;
    private static Context _sContext;
    private Exception _exception;
    private HttpURLConnection _httpConnection;
    private List<ImageModel> _listImages;
    private List<String> _listCategories;

    public AsyncXMLsaxParser(Context context)
    {
        _sContext = context;
    }

    @Override
    protected void onPreExecute()
    {
        _sDelegate.asyncProcessBegan();
        _listImages = new ArrayList<ImageModel>();
        _listCategories = new ArrayList<String>();
    }

    protected String doInBackground(String... urls)
    {
        try
        {
            URL url = new URL(urls[0]);

            _httpConnection = (HttpURLConnection) url.openConnection();
            int responseCode = _httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream stream = _httpConnection.getInputStream();
                InputSource source = new InputSource(stream);
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                ImageHandler imgHandler = new ImageHandler(_sContext);
                xr.setContentHandler(imgHandler);
                xr.parse(source);
                _listImages = imgHandler.getImgList();
                _listCategories = imgHandler.getDistinctCategories();

                return "in progress...";
            }

            return "BAD_SERVER_RESPONSE: " +responseCode;

        }
        catch (Exception e)
        {
            _exception = e;
            return "fail... ";
        }

    }

    @Override
    protected void onPostExecute(String res)
    {
        if(_exception != null)
        {
            _exception.printStackTrace();
        }
        _httpConnection.disconnect();
        _sDelegate.asyncProcessDone();
    }

    public static void delegateViewEventsTo(IAsync async)
    {
        _sDelegate = async;
    }

    /**
     * to call in delegated PostExecute
     * @return
     */
    public List<ImageModel> getReachedImages()
    {
        return _listImages;
    }

    /**
     * to call in delegated PostExecute
     * @return
     */
    public List<String> getReachedDistinctCategories()
    {
        return _listCategories;
    }

    public Exception getException(){ return _exception; }
}

