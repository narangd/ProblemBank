package person.sykim.problembank;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

import person.sykim.problembank.view.activity.IntroActivity;

@RunWith(AndroidJUnit4.class)
public class IntroActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> activityRule = new ActivityTestRule<>(IntroActivity.class);

}
