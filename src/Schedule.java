import java.time.LocalDate;
import java.util.*;

public class Schedule {
    private final Map<LocalDate, Map<String, Event>> events;

    public Schedule() {
        this.events = new HashMap<>();
    }

    public Schedule(Collection<Event> events) {
        this();
        for (Event e : events) {
            insert(e);
        }
    }

    public Schedule insert(Event e) {
        events.computeIfAbsent(e.getDate(), k -> new HashMap<>()).put(e.getTitle(), e);
        return this;
    }

    public Optional<Event> get(LocalDate d, String title) {
        Map<String, Event> eventsOnDate = events.getOrDefault(d, Collections.emptyMap());
        return Optional.ofNullable(eventsOnDate.get(title));
    }

    public List<Event> exportAll() {
        List<Event> allEvents = new ArrayList<>();
        events.values().forEach(eventsOnDate -> allEvents.addAll(eventsOnDate.values()));
        allEvents.sort(Comparator.comparing(Event::getDate));
        return Collections.unmodifiableList(allEvents);
    }

    public List<Event> exportDateRange(LocalDate fromIncluding, LocalDate toIncluding) {
        List<Event> eventsInRange = new ArrayList<>();
        for (LocalDate date = fromIncluding; !date.isAfter(toIncluding); date = date.plusDays(1)) {
            Map<String, Event> eventsOnDate = events.getOrDefault(date, Collections.emptyMap());
            eventsInRange.addAll(eventsOnDate.values());
        }
        eventsInRange.sort(Comparator.comparing(Event::getDate));
        return Collections.unmodifiableList(eventsInRange);
    }

    public List<Event> exportTitle(String title) {
        List<Event> eventsWithTitle = new ArrayList<>();
        events.forEach((date, eventsOnDate) -> {
            Event eventWithTitle = eventsOnDate.get(title);
            if (eventWithTitle != null) {
                eventsWithTitle.add(eventWithTitle);
            }
        });
        eventsWithTitle.sort(Comparator.comparing(Event::getDate));
        return Collections.unmodifiableList(eventsWithTitle);
    }

    public Optional<Event> remove(LocalDate d, String title) {
        Map<String, Event> eventsOnDate = events.get(d);
        if (eventsOnDate != null) {
            Event removedEvent = eventsOnDate.remove(title);
            if (removedEvent != null) {
                if (eventsOnDate.isEmpty()) {
                    events.remove(d);
                }
                return Optional.of(removedEvent);
            }
        }
        return Optional.empty();
    }

    public List<Event> removeDateRange(LocalDate fromIncluding, LocalDate toIncluding) {
        List<Event> removedEvents = new ArrayList<>();
        for (LocalDate date = fromIncluding; !date.isAfter(toIncluding); date = date.plusDays(1)) {
            Map<String, Event> eventsOnDate = events.remove(date);
            if (eventsOnDate != null) {
                removedEvents.addAll(eventsOnDate.values());
            }
        }
        return Collections.unmodifiableList(removedEvents);
    }

    public List<Event> removeTitle(String title) {
        List<Event> removedEvents = new ArrayList<>();
        events.values().forEach(eventsOnDate -> {
            Event removedEvent = eventsOnDate.remove(title);
            if (removedEvent != null) {
                removedEvents.add(removedEvent);
            }
        });
        return Collections.unmodifiableList(removedEvents);
    }
    public List<Event> removeAll() {
        List<Event> allEvents = exportAll();
        events.clear();
        return allEvents;
    }
}