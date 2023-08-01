package Model;
class Review extends Entity {
    private int productId;
    private String customerName;
    private String reviewText;
    private int rating;

    public Review(int reviewId, int productId, String customerName, String reviewText, int rating) {
        super(reviewId);
        this.productId = productId;
        this.customerName = customerName;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review [reviewId=" + id + ", productId=" + productId + ", customerName=" + customerName
                + ", reviewText=" + reviewText + ", rating=" + rating + "]";
    }

	@Override
	public void displayInfo() {
		// TODO Auto-generated method stub
		
	}
}
