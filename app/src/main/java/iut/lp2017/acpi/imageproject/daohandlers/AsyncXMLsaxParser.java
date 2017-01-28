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
import iut.lp2017.acpi.imageproject.views.I_Async;

/**
 * Created on 26/01/2017.
 */

public class AsyncXMLsaxParser extends AsyncTask<String, Void, String>
{
    private I_Async delegate;
    private static Context context;
    private Exception exception;
    HttpURLConnection httpConnection;
    private List<ImageModel> listImages;
    private List<String> listCategories;

    public AsyncXMLsaxParser(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        listImages = new ArrayList<ImageModel>();
        listCategories = new ArrayList<String>();
        delegate.asyncProcessBegan();
    }

    protected String doInBackground(String... urls)
    {
        try
        {
            URL url = new URL(urls[0]);

            httpConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream stream = httpConnection.getInputStream();
                InputSource source = new InputSource(stream);
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                ImageHandler imgHandler = new ImageHandler(context);
                xr.setContentHandler(imgHandler);
                xr.parse(source);
                listImages = imgHandler.getImgList();
                listCategories = imgHandler.getDistinctCategories();

                return "in progress...";
            }

            return "BAD_SERVER_RESPONSE: " +responseCode;

        }
        catch (Exception e)
        {
            exception = e;
            return "fail... ";
        }

    }

    @Override
    protected void onPostExecute(String res)
    {
        if(exception != null)
        {
            exception.printStackTrace();
        }
        httpConnection.disconnect();
        delegate.asyncProcessDone();
    }

    public void delegateViewEventsTo(I_Async async)
    {
        delegate = async;
    }

    /**
     * to call in delegated PostExecute
     * @return
     */
    public List<ImageModel> getReachedImages()
    {
        return listImages;
    }

    /**
     * to call in delegated PostExecute
     * @return
     */
    public List<String> getReachedDistinctCategories()
    {
        return listCategories;
    }
}

