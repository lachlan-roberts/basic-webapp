package org.example.test;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyTest
{
    @Test
    public void testSomething()
    {
        int i = 10;
        assertThat(i, Matchers.greaterThan(9));
    }

    @Test
    public void testSomethingElse()
    {
        int i = 10;
        assertTrue(i == 10);
    }
}
