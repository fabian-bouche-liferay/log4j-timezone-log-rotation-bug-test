# Log4j2 log rotation timezone bug

There's been a fix in log4j which was pushed after 

group: "com.liferay", name: "org.apache.logging.log4j.core", version: "2.17.1.LIFERAY-PATCHED-2"

https://github.com/apache/logging-log4j2/pull/1173

Log4j sets the next time log rotation should happen when it initializes:
https://github.com/apache/logging-log4j2/blob/2.x/log4j-core/src/main/java/org/apache/logging/log4j/core/appender/rolling/TimeBasedTriggeringPolicy.java#L152

And updates it whenever log rotation happens:
https://github.com/apache/logging-log4j2/blob/2.x/log4j-core/src/main/java/org/apache/logging/log4j/core/appender/rolling/TimeBasedTriggeringPolicy.java#L169

The determination of the next log rotation time is determined in that class:
https://github.com/apache/logging-log4j2/blob/2f79c39696a998ec1530aaf12de67a3c51ee1f13/log4j-core/src/main/java/org/apache/logging/log4j/core/appender/rolling/PatternProcessor.java#L152

It used not to take the timezone into account.

The consequence is that with the current 2.17.1 version we have in Liferay, we cannot have a reliable log rotation setup which properly accommodates for summer clock change,
log rotation will skip one day.
