package iut.lp2017.acpi.imageproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by necesanym on 04/01/17.
 */
public class ImageListAdapter extends ArrayAdapter<ImageModel>
{
    private Context context;

    public ImageListAdapter(Context context, List<ImageModel> list)
    {
        super(context, 0, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_list_item,parent,false);
        }

        imageListHolder viewHolder = (imageListHolder) convertView.getTag();
        ImageModel item = getItem(position);

        if(viewHolder == null)
        {
            viewHolder = new imageListHolder();
            viewHolder.imgV = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.nom = (TextView) convertView.findViewById(R.id.imageName);
            viewHolder.description = (TextView) convertView.findViewById(R.id.imageDescription);
            viewHolder.size = (TextView) convertView.findViewById(R.id.imgSize);
            convertView.setTag(viewHolder);
        }

        viewHolder.nom.setText(item.getNom());
        viewHolder.description.setText(item.getDescription());
        Bitmap img = BitmapScaler.scaleToFill(item.getImageBitmap(), (int)BitmapScaler.dpToPx(60,context),(int)BitmapScaler.dpToPx(80,context));
        viewHolder.imgV.setImageBitmap(img);
        viewHolder.size.setText(Double.toString(item.getTaille()));

        return convertView;
    }

    private class imageListHolder
    {
        public ImageView imgV;
        public TextView nom;
        public TextView description;
        public TextView size;
    }
}
