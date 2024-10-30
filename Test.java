import javax.smartcardio.Card;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {
    private static HashMap<String, String> lookup = new HashMap<>();
    static {
        lookup.put("w", "White");
        lookup.put("k", "Black");
        lookup.put("u", "Blue");
        lookup.put("r", "Red");
        lookup.put("g", "Green");
    }
    public void createCards(String filename) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
            String line = br.readLine();
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                HashMap<Gem, Integer> cost = new HashMap<String, Integer>();
                List<String> names = Arrays.asList(values[3].split("+"));
                for(String name : names) {
                    cost.put(new Gem(lookup.get(name.substring(1,2))), Integer.parseInt(name.substring(0,1)));
                }
                Card c = new Card(values[2], cost, values[0], new Gem(values[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
private class gem