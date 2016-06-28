package kazanski.com.basic_cbt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewPrevious extends AppCompatActivity {

    private int current_index = 0;
    private int length = 0;
    private List<Analysis> analysisObjects = new ArrayList<Analysis>();

    private Button prevButton;
    private Button nextButton;
    private TextView triggerText;
    private TextView feelingsText;
    private TextView behaviorText;
    private TextView positiveText;
    private TextView negativeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous);

        prevButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        triggerText = (TextView) findViewById(R.id.triggerText);
        feelingsText = (TextView) findViewById(R.id.feelingsText);
        behaviorText = (TextView) findViewById(R.id.behaviorText);
        positiveText = (TextView) findViewById(R.id.positiveText);
        negativeText = (TextView) findViewById(R.id.negativeText);


        Iterator<Analysis> analysisIterator = Analysis.findAll(Analysis.class);
        while(analysisIterator.hasNext()) {
            Analysis analysis = analysisIterator.next();
            analysisObjects.add(analysis);
            length += 1;
        }

        if (length > 0) {
            renderAnalysis();
        } else {
            hideShowButtons();
        }
    }

    public void renderAnalysis() {
        Analysis currentAnalysis = analysisObjects.get(current_index);
        triggerText.setText(currentAnalysis.trigger.toString());
        feelingsText.setText(currentAnalysis.feelings.toString());
        behaviorText.setText(currentAnalysis.behavior.toString());
        positiveText.setText(currentAnalysis.positive_consequences.toString());
        negativeText.setText(currentAnalysis.negative_consequences.toString());
        hideShowButtons();
    }

    public void Next(View v) {
        current_index += 1;
        renderAnalysis();
    }

    public void Prev(View v) {
        current_index -= 1;
        renderAnalysis();
    }

    private void hideShowButtons () {
        if (current_index == 0) {
            prevButton.setVisibility(View.INVISIBLE);
        } else {
            prevButton.setVisibility(View.VISIBLE);
        }
        if (current_index == length -1) {
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
    }
}
