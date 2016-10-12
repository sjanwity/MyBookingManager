import booking.internal.MyBookingManager;
import domain.Room;
import domain.exception.RoomUnavailableException;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MyBookingManagerTest {

    private final static Collection<Room> rooms = Stream.of(101, 102, 201, 203)
      .map(Room::new)
      .collect(Collectors.toList());

    @Test
    public void shouldBeAvailable() throws Exception {
        // given
        MyBookingManager givenBookingManager = new MyBookingManager(new HashSet<>(Arrays.asList(new Room(1))));

        // when
        final boolean result = givenBookingManager.isRoomAvailable(1, new Date());

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotBeAvailable() throws Exception {
        // given
        MyBookingManager givenBookingManager = new MyBookingManager(new HashSet<>(Arrays.asList(new Room(1))));
        givenBookingManager.addBooking("John", 1, new Date());

        // when
        final boolean result = givenBookingManager.isRoomAvailable(1,  new Date());

        // then
        assertThat(result).isFalse();
    }

    @Test(expected = RoomUnavailableException.class)
    public void shouldThrowWhenAlreadyBooked() throws Exception {
        // given
        MyBookingManager givenBookingManager = new MyBookingManager(new HashSet<>(Arrays.asList(new Room(1))));

        // when then
        givenBookingManager.addBooking("John", 1, new Date());
        givenBookingManager.addBooking("John", 1, new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenDateInThePast() throws Exception {
        // given
        MyBookingManager givenBookingManager = new MyBookingManager(new HashSet<>(Arrays.asList(new Room(1))));

        // when then
        givenBookingManager.addBooking("John", 1, new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(1)));
    }

    @Test
    public void shouldListAvailableRooms() throws Exception {
        // given
        MyBookingManager givenBookingManager = new MyBookingManager(new HashSet<>(Arrays.asList(new Room(1))));

        // when
        final Iterable<Integer> result = givenBookingManager.getAvailableRooms(new Date());

        // then
        assertThat(result).contains(1);
    }

    @Test
    public void shouldListNoRooms() throws Exception {
        // given
        MyBookingManager givenBookingManager = new MyBookingManager(new HashSet<>(Arrays.asList(new Room(1))));
        givenBookingManager.addBooking("None", 1, new Date());

        // when
        final Iterable<Integer> result = givenBookingManager.getAvailableRooms(new Date());

        // then
        assertThat(result).isEmpty();
    }
}