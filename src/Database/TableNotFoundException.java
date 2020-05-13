package Database;

import java.util.Collection;

public class TableNotFoundException extends Throwable {
    private final String requested;
    private final Collection<String> present;

    public TableNotFoundException(String requested, Collection<String> present) {
        this.requested = requested;
        this.present = present;
    }

    @Override
    public String toString() {
        return String.format("Table \"%s\" not found in %s", requested, present);
    }
}
