public  class  Soft {
    enum Type{
        SYSTEM,
        MALWARE,
        ANTIVIRUS,
        IDE,
       OFFICE
    }
    private final Type type;
    private final String title;
    private final  double price;
    private final  double size;

    public Soft(final Type type,String title, final double price,final double size) {

        this.type = type;
        this.title = title;
        this.price = price;
        this.size = size;
    }


    public  double getPrice() {
        return price;
    }

    public  double getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Soft{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", size=" + size +
                '}';
    }
}



