public class ProductX {
    private String productName;
    private String category;
    private String size;
    private String color;
    private String material;
    private String condition;
    private String description;

    public ProductX(String productName, String category, String size, String color, String material, String condition, String description) {
        this.productName = productName;
        this.category = category;
        this.size = size;
        this.color = color;
        this.material = material;
        this.condition = condition;
        this.description = description;
    }

    // Getters and setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}