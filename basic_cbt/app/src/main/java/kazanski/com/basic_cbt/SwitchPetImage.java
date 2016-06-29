package kazanski.com.basic_cbt;

import android.os.AsyncTask;

/**
 * Created by kazanz on 6/28/16.
 */
public class SwitchPetImage extends AsyncTask<String, String, String> {
    public MainActivity activity;

    public SwitchPetImage(MainActivity a)
    {
        this.activity = a;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String result)
    {
        activity.updatePet(false, false);
    }
}
