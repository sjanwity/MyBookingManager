package booking;

import java.util.Date;

public interface BookingManager {
    boolean isRoomAvailable(Integer room, Date date);
    void addBooking(String guest, Integer room, Date date);
    Iterable<Integer> getAvailableRooms(Date date);
}