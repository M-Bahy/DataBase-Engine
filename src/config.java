import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class config {
    public static void main(String[] args) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("MaximumRowsCountinTablePage", "2");
            prop.setProperty("MaximumEntriesinOctreeNode", "16");
            

            // save properties to project root folder
            prop.store(output, null);

            System.out.println("Config file created successfully.");

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
