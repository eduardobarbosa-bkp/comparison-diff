package com.ebc.waes.diff.util;

import com.ebc.waes.diff.util.LCSComparison;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by eduardo.costa on 09/10/2017.
 */
public class LCSComparisonTest {

    private static final String LEFT = "AGGTAB";
    private static final String RIGHT = "AGTTBB";
    private static final String SOLUTION_LEFT_RIGHT = "AG[-]GT[+]T[-]AB[+]B";

    @Test
     public void testPerformDiff(){
        LCSComparison comparison = new LCSComparison(LEFT, RIGHT);
        String result = comparison.performDiff();
        assertThat(result, notNullValue());
        assertThat(result, equalTo(SOLUTION_LEFT_RIGHT));
    }

    @Test
    public void testPerformDiffLeftRightEquals(){
        LCSComparison comparison = new LCSComparison(LEFT, LEFT);
        String result = comparison.performDiff();
        assertThat(result, notNullValue());
        assertThat(result, equalTo(LEFT));
    }

    @Test
    public void testPerformDiffNull(){
        LCSComparison comparison = new LCSComparison(null, null);
        String result = comparison.performDiff();
        assertThat(result, nullValue());
    }

}
