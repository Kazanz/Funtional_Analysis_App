package kazanski.com.basic_cbt;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class AddAnalysis extends AppCompatActivity {

    public EditText trigger;
    public EditText feeling;
    public EditText behavior;
    public EditText positive;
    public EditText negative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_analysis);

        trigger = (EditText) findViewById(R.id.editTextTrigger);
        feeling = (EditText) findViewById(R.id.editTextFeelings);
        behavior = (EditText) findViewById(R.id.editTextBehavior);
        positive = (EditText) findViewById(R.id.editTextPositive);
        negative = (EditText) findViewById(R.id.editTextNegative);
    }

    public void save(View view) {
        // Do save

        String triggerText = trigger.getText().toString();
        String feelingText = feeling.getText().toString();
        String behaviorText = behavior.getText().toString();
        String positiveText = positive.getText().toString();
        String negativeText = negative.getText().toString();
        if (triggerText.matches("") || feelingText.matches("") || behaviorText.matches("") ||
                positiveText.matches("") || negativeText.matches("")) {
            Toast.makeText(AddAnalysis.this, "All Fields must be filled out",
                    Toast.LENGTH_SHORT).show();
        } else {
            Analysis analysis = new Analysis(triggerText, feelingText, behaviorText, positiveText,
                    negativeText);
            analysis.save();
            logData();
            Toast.makeText(AddAnalysis.this, "Saved! Great job! Keep it up!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void logData() {
        JSONObject data = new JSONObject();
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            data.put("id", android_id);
            data.put("type", "control");
            data.put("method", "add activity");
            data.put("trigger_len", String.valueOf(trigger.getText().length()));
            data.put("feeling_len", String.valueOf(feeling.getText().length()));
            data.put("behavior_len", String.valueOf(behavior.getText().length()));
            data.put("positive_len", String.valueOf(positive.getText().length()));
            data.put("negative_len", String.valueOf(negative.getText().length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://10.0.3.2:3000/add";
        new JSONPostRequest().execute(url, data.toString());
    }
}
