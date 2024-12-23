
package Project_Sem2;

public class Product {
    private int itemId;
    private String itemName;
    private double itemPrice;
    private String itemSize;
    private String itemColor;
    private String imagePath;
    
    public Product(){
        itemId = 0;
        itemName = "";
        itemPrice = 0;
        itemSize = "";
        itemColor = "";
        imagePath = "";
    }
    
    public Product(int itemId, String itemName, double itemPrice, String itemSize, String itemColor, String imagePath){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemSize = itemSize;
        this.itemColor = itemColor;
        this.imagePath = imagePath;
    }
    
    public void setId(int itemId){
        this.itemId = itemId;
    }
    
    public int getId(){
        return this.itemId;
    }
    
    public void setName(String itemName){
        this.itemName = itemName;
    }
    
    public String getName(){
        return this.itemName;
    }
    
    public void setPrice(double itemPrice){
        this.itemPrice = itemPrice;
    }
    
    public double getPrice(){
        return this.itemPrice;
    }
    
    public void setSize(String itemSize){
        this.itemSize = itemSize;
    }
    
    public String getSize(){
        return this.itemSize;
    }
    
    public void setColor(String itemColor){
        this.itemColor = itemColor;
    }
    
    public String getColor(){
        return this.itemColor;
    }
    
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return this.imagePath;
    }
    
    public static Product deserialize(String row) {
        String[] data = row.split(",");
        if(data.length >= 6){
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            double price = Double.parseDouble(data[2]);
            String size = data[3];
            String color = data[4];
            String imagePath = data[5];
            return new Product(id, name, price, size, color, imagePath);
        }else{
            throw new IllegalArgumentException("Invalid data format"); // Exception if there is a error in the data format
        }
    }
    
    public String toString(){
        return (itemId + " " + itemName + "" + itemSize + " " + itemPrice + " " + itemColor + " " + imagePath);
    }
}


