package kazanski.com.basic_cbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public ListView modulesList;
    public List<String> moduleData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
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
        startActivity(intent);
    }
}
