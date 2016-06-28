package kazanski.com.basic_cbt;

import com.orm.SugarRecord;

/**
 * Created by kazanz on 6/27/16.
 */
public class Analysis extends SugarRecord {
    String trigger;
    String feelings;
    String behavior;
    String positive_consequences;
    String negative_consequences;

    public Analysis() {}

    public Analysis(String trigger, String feelings, String behavior,
                    String postivie_consequences, String negative_consequences) {
        this.trigger = trigger;
        this.feelings = feelings;
        this.behavior = behavior;
        this.positive_consequences = postivie_consequences;
        this.negative_consequences = negative_consequences;
    }
}
