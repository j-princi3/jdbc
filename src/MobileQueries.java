import java.sql.*;

public class MobileQueries {

    // Method to establish a connection to MySQL
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "princi";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database!");

                // Query a. Total number of iPhones
                String queryA = "SELECT COUNT(*) AS total_iphones FROM mobiles WHERE Brand = 'Apple' AND Smartphone LIKE '%iPhone%'";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(queryA)) {
                    if (rs.next()) {
                        int totaliPhones = rs.getInt("total_iphones");
                        System.out.println("Total number of iPhones: " + totaliPhones);
                    }
                }

                // Query b. Phone names of all phones within $500 along with model number
                String queryB = "SELECT Smartphone, Model FROM mobiles WHERE FinalPrice <= 500";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(queryB)) {
                    System.out.println("\nPhones within $500:");
                    while (rs.next()) {
                        String smartphone = rs.getString("Smartphone");
                        String model = rs.getString("Model");
                        System.out.println(smartphone + " - " + model);
                    }
                }

                // Query c. All models belonging to brand Samsung
                String queryC = "SELECT Model FROM mobiles WHERE Brand = 'Samsung'";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(queryC)) {
                    System.out.println("\nModels of Samsung phones:");
                    while (rs.next()) {
                        String model = rs.getString("Model");
                        System.out.println(model);
                    }
                }

                // Query d. The cheapest phone in Nothing brand
                String queryD = "SELECT Smartphone, Model, FinalPrice FROM mobiles WHERE Brand = 'Nothing' ORDER BY FinalPrice LIMIT 1";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(queryD)) {
                    if (rs.next()) {
                        String smartphone = rs.getString("Smartphone");
                        String model = rs.getString("Model");
                        double price = rs.getDouble("FinalPrice");
                        System.out.println("\nThe cheapest phone in Nothing brand:");
                        System.out.println("Smartphone: " + smartphone + ", Model: " + model + ", Price: $" + price);
                    }
                }

                // Query e. Sorted phones in the order of price range
                String queryE = "SELECT Smartphone, Model, FinalPrice FROM mobiles ORDER BY FinalPrice";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(queryE)) {
                    System.out.println("\nPhones sorted by price range:");
                    while (rs.next()) {
                        String smartphone = rs.getString("Smartphone");
                        String model = rs.getString("Model");
                        double price = rs.getDouble("FinalPrice");
                        System.out.println(smartphone + " - " + model + ", Price: $" + price);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
