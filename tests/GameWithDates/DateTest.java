package GameWithDates;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {
    @Test
    public void isCorrectDate() throws Exception {
        assertTrue(Date.isCorrectDate(30, 11));
        assertFalse(Date.isCorrectDate(14, 31));
    }

}