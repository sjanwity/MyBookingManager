package domain;

import java.util.Objects;

public final class Guest {
    private final String name;

    public Guest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Guest other = (Guest) obj;
        return Objects.equals(this.name, other.name);
    }
}
