package domain;

import java.util.*;

public final class BookingEntry {
    private final Guest guest;
    private final Room room;
    private final Set<Day> dates;

    public BookingEntry(Guest guest, Room room, Set<Day> dates) {
        this.guest = guest;
        this.room = room;
        this.dates = dates;
    }

    public BookingEntry(Guest guest, Room room) {
        this.guest = guest;
        this.room = room;
        this.dates = Collections.emptySet();
    }

    public BookingEntry withDate(Date date) {
        final HashSet<Day> updated = new HashSet<>(new HashSet<>(dates));
        updated.add(new Day(date));
        return new BookingEntry(guest, room, updated);
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public Set<Day> getDates() {
        return dates;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guest, room, dates);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BookingEntry other = (BookingEntry) obj;
        return Objects.equals(this.guest, other.guest)
          && Objects.equals(this.room, other.room)
          && Objects.equals(this.dates, other.dates);
    }
}
