package test;

import org.apache.logging.log4j.core.appender.rolling.PatternProcessor;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PatternProcessorTest {

    @Test
    public void testGetNextTimeOutsideOfDstChange() {
    	
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime dateTime = ZonedDateTime.of(2025, 3, 27, 12, 0, 0, 0, zoneId);
        long currentTime = dateTime.toInstant().toEpochMilli();

        PatternProcessor processor = new PatternProcessor("app-%d{yyyy-MM-dd}{timezone}.log");

        long nextTime = processor.getNextTime(currentTime, 1, true);

        ZonedDateTime expectedNextMidnight = ZonedDateTime.of(2025, 3, 28, 0, 0, 0, 0, zoneId);
        long expectedMillis = expectedNextMidnight.toInstant().toEpochMilli();

        // Print debug info
        System.out.println("Current Time (millis):     " + currentTime);
        System.out.println("Current Time (Zoned):      " + dateTime);
        System.out.println("Expected Next Rollover:    " + expectedMillis);
        System.out.println("Expected Zoned:            " + expectedNextMidnight);
        System.out.println("Actual Next Rollover:      " + nextTime);
        System.out.println("Actual Zoned:              " + Instant.ofEpochMilli(nextTime).atZone(zoneId));

        assertEquals(expectedMillis, nextTime);
    }
	
    @Test
    public void testGetNextTimeBeforeDstChange() {
    	
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime dateTime = ZonedDateTime.of(2025, 3, 29, 12, 0, 0, 0, zoneId);
        long currentTime = dateTime.toInstant().toEpochMilli();

        PatternProcessor processor = new PatternProcessor("app-%d{yyyy-MM-dd}{timezone}.log");

        long nextTime = processor.getNextTime(currentTime, 1, true);

        ZonedDateTime expectedNextMidnight = ZonedDateTime.of(2025, 3, 30, 0, 0, 0, 0, zoneId);
        long expectedMillis = expectedNextMidnight.toInstant().toEpochMilli();

        // Print debug info
        System.out.println("Current Time (millis):     " + currentTime);
        System.out.println("Current Time (Zoned):      " + dateTime);
        System.out.println("Expected Next Rollover:    " + expectedMillis);
        System.out.println("Expected Zoned:            " + expectedNextMidnight);
        System.out.println("Actual Next Rollover:      " + nextTime);
        System.out.println("Actual Zoned:              " + Instant.ofEpochMilli(nextTime).atZone(zoneId));

        assertEquals(expectedMillis, nextTime);
    }
}