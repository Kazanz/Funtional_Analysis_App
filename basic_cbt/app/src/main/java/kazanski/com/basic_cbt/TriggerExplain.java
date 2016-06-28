package kazanski.com.basic_cbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TriggerExplain extends AppCompatActivity {

    public ListView triggerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger_explain);

        triggerList = (ListView) findViewById(R.id.triggerList);

        List<String> data = new ArrayList<String>();
        data.add("Being out at bars");
        data.add("Your job");
        data.add("An argument with a friend or family member");
        data.add("Withdrawal symptoms");
        data.add("Being at a certain friend's house");
        data.add("Peer pressure to use");
        data.add("Being home alone");
        data.add("Parties");
        data.add("Saturday night");
        data.add("Thought: I can't deal with this.");
        data.add("Thought: I need to get away.");
        data.add("Thought: I need to forget.");
        data.add("Thought: Using would be a blast.");
        data.add("Feeling: Anxiety");
        data.add("Feeling: Depression");
        data.add("Feeling: Anger");
        data.add("Feeling: Frustration");
        data.add("Feeling: Happiness");
        data.add("Feeling: Loneliness");
        data.add("Feeling: Elation");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                data );
        triggerList.setAdapter(arrayAdapter);
    }
}
