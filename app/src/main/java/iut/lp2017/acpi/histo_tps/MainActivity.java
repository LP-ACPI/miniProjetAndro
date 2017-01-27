package iut.lp2017.acpi.histo_tps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import iut.lp2017.acpi.R;

public class MainActivity extends Activity {

    private static final String CREATE  = "ON CREATE";
    private static final String START   = "ON START";
    private static final String RESUME  = "ON RESUME";
    private static final String STOP    = "ON STOP";
    private static final String DESTROY = "ON DESTROY";
    private static final String PAUSE   = "ON PAUSE";
    private static final String RESTART = "ON RESTART";

    //Flags de intents pour les récupérer dans la seconde activité
    public final static String NOM = "intent.tpun.acpi.NOM";
    public final static String PRENOM = "intent.tpun.acpi.PRENOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent secondActivityInt = new Intent(MainActivity.this, SecondActivity.class);

                EditText inputName = (EditText) findViewById(R.id.inputName);
                String name = inputName.getText().toString();
                secondActivityInt.putExtra(NOM, name);

                EditText inputFirstName = (EditText) findViewById(R.id.inputFirstName);
                String firstName = inputFirstName.getText().toString();
                secondActivityInt.putExtra(PRENOM, firstName);

                startActivity(secondActivityInt);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(START, " activity started");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(RESTART, " activity restarted");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(RESUME, " activity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(PAUSE, " activity paused");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(STOP, " activity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DESTROY, " activity destroyed");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
