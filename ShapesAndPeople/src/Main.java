import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Person> people = new ArrayList<>();
        people.add(new Person("henry","Blue"));
        people.add(new Person("anna", "Blue"));
        people.add(new Person("billy","Joe"));
        people.add(new Person("susan", "Smith"));
        people.add(new Person("john",  "Adams"));
        people.add(new Person("alice", "Cooper"));

        System.out.println("-- People BEFORE --");
        for (Person p : people) System.out.println(p.getFullName());

        Sorter.sort(people, new Person("", ""));
        System.out.println("-- People AFTER  --");
        for (Person p : people) System.out.println(p.getFullName());


        List<Rectangle> rects = new ArrayList<>();
        rects.add(new Rectangle(4,5));
        rects.add(new Rectangle(2,12));
        rects.add(new Rectangle(3,3));
        rects.add(new Rectangle(10,1));
        rects.add(new Rectangle(6,4));

        System.out.println("-- Rect areas BEFORE --");
        for (Rectangle r : rects) System.out.println(r.getArea());

        Sorter.sort(rects, new Rectangle(1,1));
        System.out.println("-- Rect areas AFTER  --");
        for (Rectangle r : rects) System.out.println(r.getArea());
    }
}
