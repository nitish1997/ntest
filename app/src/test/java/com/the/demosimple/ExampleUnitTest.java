package com.the.demosimple;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void addition_isTrue() {
        assertEquals(0, 2 + 2);
    }

//    @Test
//    private static boolean dateEqualOrAfter(Date dateInQuestion, Date date2)
//    {
//        if (dateInQuestion.equals(date2))
//            return true;
//        return (dateInQuestion.after(date2));
//    }
//    @Test
//    private static boolean dateEqualOrBefore(Date dateInQuestion, Date date2)
//    {
//        if (dateInQuestion.equals(date2))
//            return true;
//        return (dateInQuestion.before(date2));
//
//    }
}