import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Создание объектов Event
        Event event1 = new Event(LocalDate.of(2024, 2, 26), "title-1", "description-1");
        Event event2 = new Event(LocalDate.of(2024, 2, 27), "title-2", "description-2");
        Event event3 = new Event(LocalDate.of(2024, 2, 28), "title-3", "description-3");

        // Создание объекта Schedule и добавление событий
        Schedule schedule = new Schedule(List.of(event1, event2, event3));

        // Вывод всех событий
        System.out.println("Вывод всех событий:");
        List<Event> allEvents = schedule.exportAll();
        allEvents.forEach(System.out::println);

        // Получение события по дате и названию
        System.out.println("Получение события по дате и названию:");
        LocalDate date = LocalDate.of(2024, 2, 27);
        String title = "title-2";
        Optional<Event> eventOptional = schedule.get(date, title);
        eventOptional.ifPresentOrElse(
                event -> System.out.println("Event found: " + event),
                () -> System.out.println("Event not found")
        );

        // Удаление события по дате и названию
        System.out.println("Удаление события по дате и названию");
        Optional<Event> removedEventOptional = schedule.remove(date, title);
        removedEventOptional.ifPresentOrElse(
                removedEvent -> System.out.println("Removed event: " + removedEvent),
                () -> System.out.println("Event not found")
        );

        // Вывод всех событий
        System.out.println("Вывод всех событий:");
        List<Event> afterDelete = schedule.exportAll();
        afterDelete.forEach(System.out::println);

        // Вывод всех событий после удаления
        schedule.removeAll();

        // Вывод всех событий после удаления
        System.out.println("Вывод всех событий после удаления:");
        List<Event> remainingEvents = schedule.exportAll();
        remainingEvents.forEach(System.out::println);
    }
}