package domain;

import booking.internal.DateUtils;

import java.util.Date;
import java.util.Objects;

public class Day {
    private final String date;

    public Day(Date date) {
        this.date = DateUtils.DAY.format(date);
    }

    public Day(String date) {
        this.date = date;
    }

    public boolean isOnTheSameDayAs(Date date) {
        return DateUtils.DAY.format(date).equals(this.date);
    }

    public String getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Day other = (Day) obj;
        return Objects.equals(this.date, other.date);
    }
}
