import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("henry", "Blue"));
        people.add(new Person("anna", "Blue"));
        people.add(new Person("billy", "Joe"));
        people.add(new Person("susan", "Smith"));
        people.add(new Person("john", "Adams"));
        people.add(new Person("alice", "Cooper"));

        System.out.println("-- People BEFORE --");
        people.forEach(p -> System.out.println(p.getFullName()));

        Collections.sort(people);
        System.out.println("-- People AFTER  --");
        people.forEach(p -> System.out.println(p.getFullName()));

        List<Rectangle> rects = new ArrayList<>();
        rects.add(new Rectangle(4, 5));
        rects.add(new Rectangle(2, 12));
        rects.add(new Rectangle(3, 3));
        rects.add(new Rectangle(10, 1));
        rects.add(new Rectangle(6, 4));

        System.out.println("-- Rect areas BEFORE --");
        rects.forEach(r -> System.out.println(r.getArea()));

        Collections.sort(rects);
        System.out.println("-- Rect areas AFTER  --");
        rects.forEach(r -> System.out.println(r.getArea()));
    }
}
