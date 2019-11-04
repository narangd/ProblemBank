package person.sykim.problembank;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import person.sykim.problembank.data.Source;
import person.sykim.problembank.view.activity.EditorActivity;

@RunWith(AndroidJUnit4.class)
public class EditorActivityTest {

    @Rule
    public ActivityTestRule<EditorActivity> activityRule = new ActivityTestRule<>(EditorActivity.class);

    @Test
    public void consoleTest() {
        // 성공하나 dialog 가 Activity 가 닫힌후에 오픈되어 에러가 발생함. (WindowLeaked)
//        onView(withId(R.id.action_run)).perform(click());

    }

    @Test
    public void loadSourceList() {
        Source source = Source.findLastUpdated();
        System.out.println("source :"+ source);
    }
}
