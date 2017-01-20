package iut.lp2017.acpi.tps_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by necesanym on 14/12/2016.
 */
public class SecondActivity extends Activity {

    public final static String NOM_LABEL = "intent.tpun.acpi.NOM_LABEL";
    public final static String PRENOM_LABEL = "intent.tpun.acpi.PRENOM_LABEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent MainActivityInt = getIntent();

        String nom = MainActivityInt.getStringExtra(MainActivity.NOM);
        TextView gotName = (TextView) findViewById(R.id.gotName);
        gotName.setText(nom);

        String prenom = MainActivityInt.getStringExtra(MainActivity.PRENOM);
        TextView gotFirstName = (TextView) findViewById(R.id.gotFirstName);
        gotFirstName.setText(prenom);

        initListButton();

    }


    private void initListButton(){
        Button listButton = (Button) findViewById(R.id.toListButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivityInt = new Intent(SecondActivity.this, ListActivity.class);

                TextView gotInputName = (TextView) findViewById(R.id.gotName);
                String name = gotInputName.getText().toString();
                listActivityInt.putExtra(NOM_LABEL, name);

                TextView gotInputFirstName = (TextView) findViewById(R.id.gotFirstName);
                String firstName = gotInputFirstName.getText().toString();
                listActivityInt.putExtra(PRENOM_LABEL, firstName);

                startActivity(listActivityInt);
            }
        });
    }



}
