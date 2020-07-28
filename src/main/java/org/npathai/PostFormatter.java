package org.npathai;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PostFormatter {

    private static final List<ChronoPair> DESC_SUPPORTED_UNITS =
            List.of(
                    ChronoPair.of(ChronoUnit.DAYS, "day"),
                    ChronoPair.of(ChronoUnit.HOURS, "hour"),
                    ChronoPair.of(ChronoUnit.MINUTES, "minute"),
                    ChronoPair.of(ChronoUnit.SECONDS, "second")
            );

    private final Clock clock;

    public PostFormatter(Clock clock) {
        this.clock = clock;
    }

    public String format(Post post) {
        return post.user + " - " + post.message + " (" + elapsedTime(post) + ")";
    }

    private String elapsedTime(Post post) {
        LocalDateTime now = LocalDateTime.now(clock);

        for (ChronoPair pair : DESC_SUPPORTED_UNITS) {
            long elapsedTimeInUnit = pair.chronoUnit.between(post.createdAt, now);
            if (elapsedTimeInUnit == 0) {
                continue;
            }

            String prettyTime = elapsedTimeInUnit + " " + pair.prettyUnit;
            if (elapsedTimeInUnit > 1) {
                prettyTime += "s";
            }
            return prettyTime + " ago";
        }

        return "just now";
    }

    static class ChronoPair {
        ChronoUnit chronoUnit;
        String prettyUnit;

        ChronoPair(ChronoUnit chronoUnit, String prettyUnit) {
            this.chronoUnit = chronoUnit;
            this.prettyUnit = prettyUnit;
        }

        public static ChronoPair of(ChronoUnit unit, String prettyUnit) {
            return new ChronoPair(unit, prettyUnit);
        }
    }
}
