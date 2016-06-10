package java.at.ls.tests;


import com.matictechnology.calculater.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

/**
 * Created by matic on 22/4/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
public class TestCalc
{
    private MainActivity activity;

    @Before
    public void setup()
    {
        activity= Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
    }

    @Test
    public void shouldNotBeNull()
    {

    }

}
