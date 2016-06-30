package kazanski.com.basic_cbt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public ListView modulesList;
    public List<String> moduleData = new ArrayList<String>();

    public ImageView petImage;
    public TextView petMsg;
    public TextView petIQ;
    public TextView petExplain;
    public int petIQValue;

    public static final String MY_PREFERENCES = "TriggerTherapist";
    public static final String PREFERENCE = "PetIQ";
    public SharedPreferences myPreferences;

    // Identifies whether the app should be a control or test app.
    public boolean control = false;
    public boolean datalogged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logData();

        myPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        petIQValue = myPreferences.getInt(PREFERENCE, 2);

        petExplain = (TextView) findViewById(R.id.petExplain);
        petImage = (ImageView) findViewById(R.id.pet);
        petMsg = (TextView) findViewById(R.id.petMsg);
        petIQ = (TextView) findViewById(R.id.petIQ);
        petIQ.setText("PET IQ: " + String.valueOf(petIQValue));
        updatePet(true, false);

        if (control) {
            petExplain.setVisibility(View.GONE);
            petImage.setVisibility(View.GONE);
            petMsg.setVisibility(View.GONE);
            petIQ.setVisibility(View.GONE);
        }

        modulesList = (ListView) findViewById(R.id.modulesList);
        moduleData.add("What is a Trigger?");
        moduleData.add("What is a Behavior?");
        moduleData.add("What are Good and Bad Consequences?");
        moduleData.add("How to make an Analysis?");
        moduleData.add("See an example Analysis?");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                moduleData );

        modulesList.setAdapter(arrayAdapter);
        modulesList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) modulesList.getItemAtPosition(position);
        if (name == moduleData.get(0)) {
            Intent intent = new Intent(this, TriggerExplain.class);
            startActivity(intent);
        } else if (name == moduleData.get(1)) {
            Intent intent = new Intent(this, BehaviorExplain.class);
            startActivity(intent);
        } else if (name == moduleData.get(2)) {
            Intent intent = new Intent(this, ConsequencesExplain.class);
            startActivity(intent);
        } else if (name == moduleData.get(3)) {
            Intent intent = new Intent(this, HowToActivity.class);
            startActivity(intent);
        } else if (name == moduleData.get(4)) {
            Intent intent = new Intent(this, Example.class);
            startActivity(intent);
        }
    }

    public void AddAnalysis(View view) {
        Intent intent = new Intent(this, AddAnalysis.class);
        startActivityForResult(intent, 1);
    }

    public void Previous(View view) {
        Intent intent = new Intent(this, ViewPrevious.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK &&
                data.getStringExtra("success").equals("true")) {
            try {
                updatePetIQ(1, 12);
                updatePet(true, true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updatePet(Boolean allowHappy, Boolean forceHappy) {
        ArrayList<String> moods = new ArrayList<String>();
        HashMap<String, String[]> messages = new HashMap<>();
        if (allowHappy) {
            moods.add("happy");
            messages.put("happy", new String[]{
                    "I am so happy to see you master!",
                    "Oh boy oh boy oh boy!",
                    "You make me want to wag my tail!"
            });
        }
        if (!forceHappy) {
            moods.add("hungry");
            messages.put("hungry", new String[]{
                    "I am hungry master, may I please have your shoe?",
                    "Food, food, food, food, food, yum, yum, yum, yum!",
                    "Treats, I can do tricks for treat, want me to roll over?"
            });
            moods.add("mad");
            messages.put("mad", new String[]{
                    "I do not want to bark...uh...talk right now.",
                    "You did not take me to the park, I am ignoring you.",
                    "I must sit in the corner until I learn not to eat cake off the table."
            });
            moods.add("threat");
            messages.put("threat", new String[]{
                    "I will get you one day Mr. mail man!",
                    "Do not go outside master, there is a squirral!",
                    "Grrrrrrr......rrrrrrGGGggggrrRRR!"
            });
        }
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(moods.size());
        String mood = moods.get(index);
        String[] msgs = messages.get(mood);
        String msg = msgs[randomGenerator.nextInt(msgs.length)];
        petMsg.setText(msg);
        if(mood.equals("happy")) {
            petImage.setImageResource(R.drawable.happy2);
            new SwitchPetImage(this).execute();
        } else if (mood.equals("hungry")) {
            petImage.setImageResource(R.drawable.hungry);
        } else if (mood.equals("mad")) {
            petImage.setImageResource(R.drawable.mad);
        } else if (mood.equals("threat")) {
            petImage.setImageResource(R.drawable.threat);
        } else {
            petImage.setImageResource(R.drawable.happy1);
        }
    }

    public void updatePetIQ(int min, int max) throws InterruptedException {
        if (!control) {
            Random rand = new Random();
            int randomNum = min + rand.nextInt((max - min) + 1);
            int initial = petIQValue;
            if (randomNum != 0) {
                for (int i = 0; i <= randomNum; i++) {
                    petIQValue += 1;
                    petIQ.setText("PET IQ: " + String.valueOf(petIQValue));
                }
                String change = String.valueOf(petIQValue - initial);
                Toast.makeText(MainActivity.this, "Pet IQ increased by: " + change, Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putInt(PREFERENCE, petIQValue);
                editor.commit();
            }
        }
    }

    public void logData() {
        JSONObject data = new JSONObject();
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        try {
            data.put("id", android_id);
            data.put("is_control", String.valueOf(control));
            data.put("method", "app load");
            data.put("date", datetime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // String url = "http://10.0.3.2:3000/add";
        String url = "http://68.233.232.240:3000/add";
        new JSONPostRequest().execute(url, data.toString());
        datalogged = true;
    }
}

