package booking.internal;

import booking.BookingManager;
import domain.BookingEntry;
import domain.Guest;
import domain.Room;
import domain.exception.RoomUnavailableException;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public final class MyBookingManager implements BookingManager {

    private final Set<Room> rooms;
    private final Set<BookingEntry> entries = Collections.synchronizedSet(new HashSet<>());

    public MyBookingManager(Collection<Room> rooms) {
        this.rooms = new HashSet<>(rooms);
    }

    @Override
    public boolean isRoomAvailable(Integer room, Date date) {
        if (room == null) {
            throw new IllegalArgumentException("Room number can't be null");
        }

        if (date == null) {
            throw new IllegalArgumentException("Date can't be null");
        }

        if (!rooms.contains(new Room(room))) {
            throw new IllegalArgumentException("Provided room number does not exist");
        }

        if (date.before(new Date())) {
            throw new IllegalArgumentException("Date can't be in the past");
        }

        return entries.stream()
          .filter(e -> e.getRoom().getNumber() == room)
          .flatMap(bookingEntry -> bookingEntry.getDates().stream())
          .noneMatch(d -> d.isOnTheSameDayAs(date));
    }

    @Override
    public void addBooking(String guest, Integer room, Date date) {
        synchronized (this) {
            if (!isRoomAvailable(room, date)) {
                throw new RoomUnavailableException(room.toString());
            }

            final Optional<BookingEntry> existingEntry = entries.stream()
              .filter(e -> e.getGuest().getName().equals(guest))
              .filter(e -> e.getRoom().getNumber() == room)
              .findAny();

            if (existingEntry.isPresent()) {
                final BookingEntry booking = existingEntry.get();
                entries.remove(booking);

                entries.add(booking.withDate(date));
            } else {
                entries.add(new BookingEntry(new Guest(guest), new Room(room)).withDate(date));
            }
        }
    }
    
    @Override
    public Iterable<Integer> getAvailableRooms(Date date) {
        return rooms.stream()
          .filter(isAvailableFor(date))
          .map(Room::getNumber)
          .collect(toList());
    }

    private Predicate<Room> isAvailableFor(Date date) {
        return room -> entries.stream()
          .filter(e -> e.getRoom().equals(room))
          .flatMap(be -> be.getDates().stream())
          .noneMatch(d -> d.isOnTheSameDayAs(date));
    }
}
