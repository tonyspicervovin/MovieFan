package com.tony.moviefan;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.util.Checks;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.tony.moviefan.view.CurrentMovieListAdapter;
import com.tony.moviefan.view.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.tony.moviefan", appContext.getPackageName());
    }
    @Test
    public void click_favorite_button_verify_fragment_changes() {

        getClass();

        onView(withId(R.id.view_favorites_button)).perform(click());
        //locate and click favorite button

        onView(withId(R.id.search_favorite_string))
                .check(matches(isDisplayed()));
        //checking that the view fragment is displayed by checking if the search favorite element is on screen
    }

    @Test
    public void click_search_button_verify_fragment_changes() {

        getClass();

        onView(withId(R.id.new_button)).perform(click());
        //locate and click new release button

        onView(withId(R.id.movies_in_theatres))
                .check(matches(isDisplayed()));
        //checking that the new release fragment is displayed by checking if the movie in theatre favorite element is on screen
    }
    @Test
    public void click_new_release_button_verify_fragment_changes() {

        getClass();

        onView(withId(R.id.searchButton)).perform(click());
        //locate and click search button

        onView(withId(R.id.searchMatchingButton))
                .check(matches(isDisplayed()));
        //checking that the search fragment is displayed by checking if the search movie element is on screen
    }
    @Test
    public void check_view_favorite_displays_fragment() {

        getClass();

        onView(withId(R.id.view_favorites_button)).perform(click());
        //click view favorite button
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.show_favorite_list))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("DELETE")), click()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void check_search_movie_displays_movie_in_list() {
        getClass();

        onView(withId(R.id.searchButton)).perform(click());
        //clicking in to the search fragment
        onView(withId(R.id.search_favorite_string))
                .perform(typeText("Oldboy"));
        closeSoftKeyboard();
        //typing oldboy in the text box
        onView(withId(R.id.searchMatchingButton)).perform(click());
        //clicking the search button
        onView(withId(R.id.show_matching_list)).perform(RecyclerViewActions.scrollToPosition(0));
        //finding the first position in the recyclerview
        onView(withText("Oldboy")).check(matches(isDisplayed()));
        //checking to see that it contains the text Oldboy

    }
    @Test
    public void add_new_release_check_added() {

        getClass();

        onView(withId(R.id.searchButton)).perform(click());
        //clicking in to the search fragment
        onView(withId(R.id.search_favorite_string))
                .perform(typeText("Oldboy"));
        closeSoftKeyboard();
        //typing oldboy in the text box
        onView(withId(R.id.searchMatchingButton)).perform(click());
        //clicking the search button
        onView(withId(R.id.show_matching_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }


}
