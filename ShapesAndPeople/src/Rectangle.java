public class Rectangle implements Sortable<Rectangle> {
    private Integer width;
    private Integer height;

    public Rectangle(Integer width, Integer height){
        this.width = width;
        this.height = height;
    }

    public Integer getWidth(){return this.width;}
    public Integer getHeight(){return this.height;}
    public Integer getArea(){return (width * height);}

    public boolean isBigger(Rectangle a, Rectangle b){
        return(a.getArea() > b.getArea());
    }

}
