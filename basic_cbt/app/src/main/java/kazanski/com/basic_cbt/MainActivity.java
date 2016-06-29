package kazanski.com.basic_cbt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public ListView modulesList;
    public List<String> moduleData = new ArrayList<String>();

    public ImageView petImage;
    public TextView petMsg;
    public TextView petIQ;
    public int petIQValue;

    public static final String MY_PREFERENCES = "TriggerTherapist";
    public static final String PREFERENCE = "PetIQ";
    public SharedPreferences myPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        petIQValue = myPreferences.getInt(PREFERENCE, 20);

        petImage = (ImageView) findViewById(R.id.pet);
        petMsg = (TextView) findViewById(R.id.petMsg);
        petIQ = (TextView) findViewById(R.id.petIQ);
        petIQ.setText("DOG IQ: " + String.valueOf(petIQValue));
        updatePet(true, false);

        // TODO: Everythime main activity started record it.

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
        Random rand = new Random();
        int randomNum = min + rand.nextInt((max - min) + 1);
        if (randomNum != 0) {
            for (int i = 0; i <= randomNum; i++) {
                petIQValue += 1;
                petIQ.setText("DOG IQ: " + String.valueOf(petIQValue));
            }
            SharedPreferences.Editor editor = myPreferences.edit();
            editor.putInt(PREFERENCE, petIQValue);
            editor.commit();
        }
    }
}

