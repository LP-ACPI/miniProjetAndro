package iut.lp2017.acpi.imageproject.daohandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Marek on 26/01/2017.
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
                /**
                 *  TODO :
                 *      - ImageListAdapter pour ImageModel (actuellement pour photoModel)
                 *      - Sortir la liste ci-dessous dans une ListView adaptée pour ImageModel, et dans une nouvelle activité
                 *      - gestion de catégories d'images (une activité qui les liste) -> trier ou pas images par catégorie(s)
                 */
                listImages = imgHandler.getImgList();
                listCategories = imgHandler.getDistinctCategories();
                for(ImageModel i: imgHandler.getImgList())
                    Log.i("Image",i.toString());
                for(String category: imgHandler.getDistinctCategories())
                    Log.i("Categorie",category);

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
    protected void onPostExecute(String res) {
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
     * to call in PostExecute
     * @return
     */
    public List<ImageModel> getReachedImages()
    {
        return listImages;
    }

    /**
     * to call in PostExecute
     * @return
     */
    public List<String> getReachedDistinctCategories()
    {
        return listCategories;
    }
}
