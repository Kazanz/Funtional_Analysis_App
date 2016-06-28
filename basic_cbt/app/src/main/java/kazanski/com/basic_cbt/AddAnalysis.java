package kazanski.com.basic_cbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
            Toast.makeText(AddAnalysis.this, "Saved! Great job! Keep it up!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
