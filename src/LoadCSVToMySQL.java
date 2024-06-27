import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoadCSVToMySQL {
    public static void main(String[] args) {
        String csvFilePath = "/home/princi/Desktop/test/data/smartphones.csv";

        String sql = "INSERT INTO mobiles (Smartphone, Brand, Model, RAM, Storage, Color, Free, FinalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnectionExample.getConnection();
             BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
             PreparedStatement statement = conn.prepareStatement(sql)) {

            String lineText;
            lineReader.readLine(); // Skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                
                if (data.length == 8) { // Ensure the data has all 8 columns
                    String smartphone = data[0];
                    String brand = data[1];
                    String model = data[2];
                    String ram = data[3];
                    String storage = data[4];
                    String color = data[5];
                    String free = data[6];
                    String finalPrice = data[7];

                    statement.setString(1, smartphone);
                    statement.setString(2, brand);
                    statement.setString(3, model);
                    statement.setString(4, ram);
                    statement.setString(5, storage);
                    statement.setString(6, color);
                    statement.setString(7, free);
                    statement.setString(8, finalPrice);

                    statement.addBatch();
                }
            }

            statement.executeBatch();
            System.out.println("Data has been inserted successfully.");

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
