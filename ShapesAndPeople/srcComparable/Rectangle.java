public class Rectangle implements Comparable<Rectangle> {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int compareTo(Rectangle other) {
        return Integer.compare(this.getArea(), other.getArea());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getArea() {
        return this.width * this.height;
    }

    @Override
    public String toString() {
        return "Rectangle{" + width + "x" + height + ", area=" + getArea() + "}";
    }
}
