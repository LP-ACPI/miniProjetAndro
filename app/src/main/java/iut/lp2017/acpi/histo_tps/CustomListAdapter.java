package iut.lp2017.acpi.histo_tps;

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
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by necesanym on 04/01/17.
 */
public class CustomListAdapter extends ArrayAdapter<ListItem> {

    private Context context;

    public CustomListAdapter(Context context, List<ListItem> list) {
        super(context, 0, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,parent, false);
        }

        customListHolder viewHolder = (customListHolder) convertView.getTag();
        ListItem item = getItem(position);

        if(viewHolder == null){
            viewHolder = new customListHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.nameListItem);
            viewHolder.prenom = (TextView) convertView.findViewById(R.id.firstNameListItem);
            viewHolder.imgV = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.size = (TextView) convertView.findViewById(R.id.imgSize);
            convertView.setTag(viewHolder);
        }

        viewHolder.nom.setText(item.getNom());
        viewHolder.prenom.setText(item.getPrenom());
        Bitmap img = BitmapScaler.scaleToFitHeight(item.getImageBitmap(), (int)BitmapScaler.dpToPx(80,context));
        viewHolder.imgV.setImageBitmap(img);
        viewHolder.size.setText(Double.toString(item.getTaille()));

        return convertView;
    }

     private class customListHolder{
        public ImageView imgV;
        public TextView nom;
        public TextView prenom;
        public TextView size;

    }
}
