package Model;

import java.sql.*;
import java.util.Scanner;

public class ProductReviewApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // JDBC connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/product_review";
        String username = "root";
        String password = "Sabarijkm@21"; 

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database.");

            while (true) {
                System.out.println("\n===== Product Review Console App =====");
                System.out.println("1. Admin (Add Product)");
                System.out.println("2. User (Add Review)");
                System.out.println("3. View Products");
                System.out.println("4. View Reviews");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character left by nextInt()

                switch (choice) {
                    case 1:
                        addProduct(scanner, connection);
                        break;
                    case 2:
                        addReview(scanner, connection);
                        break;
                    case 3:
                        displayProducts(connection);
                        break;
                    case 4:
                        displayReviews(connection);
                        break;
                    case 5:
                        System.out.println("Exiting the application.");
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("\n===== Add Product =====");
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Save the product to the database
        String query = "INSERT INTO products (product_name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, productName);
            statement.setString(2, description);
            statement.executeUpdate();

            // Retrieve the auto-generated productId from the database
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int productId = resultSet.getInt(1);
                    System.out.println("Product added successfully! Product ID: " + productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addReview(Scanner scanner, Connection connection) throws SQLException {
        System.out.println("\n===== Add Review =====");
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        // Check if the product exists
        String getProductQuery = "SELECT * FROM products WHERE product_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(getProductQuery)) {
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Product exists
                int productId = resultSet.getInt("product_id");
                System.out.print("Enter customer name: ");
                String customerName = scanner.nextLine();

                System.out.print("Enter review text: ");
                String reviewText = scanner.nextLine();

                System.out.print("Enter rating (1-5): ");
                int rating = scanner.nextInt();
                scanner.nextLine(); 

                String insertReviewQuery = "INSERT INTO reviews (product_id, customer_name, review_text, rating) VALUES (?, ?, ?, ?)";
                try (PreparedStatement reviewStatement = connection.prepareStatement(insertReviewQuery)) {
                    reviewStatement.setInt(1, productId);
                    reviewStatement.setString(2, customerName);
                    reviewStatement.setString(3, reviewText);
                    reviewStatement.setInt(4, rating);
                    reviewStatement.executeUpdate();
                    System.out.println("Review added successfully!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Product not found. Please enter a valid product name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayProducts(Connection connection) throws SQLException {
        System.out.println("\n===== Product List =====");
        String query = "SELECT * FROM products";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                System.out.println("Product ID: " + productId + ", Name: " + productName + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayReviews(Connection connection) throws SQLException {
        System.out.println("\n===== Review List =====");
        String query = "SELECT * FROM reviews";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int reviewId = resultSet.getInt("review_id");
                int productId = resultSet.getInt("product_id");
                String customerName = resultSet.getString("customer_name");
                String reviewText = resultSet.getString("review_text");
                int rating = resultSet.getInt("rating");
                System.out.println("Review ID: " + reviewId + ", Product ID: " + productId + ", Customer: " + customerName + ", Rating: " + rating + ", Review: " + reviewText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
