import java.time.LocalDate;

public class Event {
    private final LocalDate date;
    private final String title;
    private final String description;

    public Event(LocalDate date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return  "Дата: " + date +
                ", Название: " + title +
                ", Описание: " + description;
    }
}