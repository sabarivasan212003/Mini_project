package Model;
class Product extends Entity {
    private String productName;
    private String description;

    public Product(int productId, String productName, String description) {
        super(productId);
        this.productName = productName;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product [productId=" + id + ", productName=" + productName + ", description=" + description + "]";
    }

	@Override
	public void displayInfo() {
		// TODO Auto-generated method stub
		
	}
}
