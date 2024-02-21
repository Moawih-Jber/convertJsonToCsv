import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonToCsvConverter {

	public static void main(String[] args) {
		String jsonFilePath = "sample1.json";
		// Put your domain json file
		String csvFilePath = "file.csv";

		try {
			convertJsonToCsv(jsonFilePath, csvFilePath);
			System.out.println("Conversion completed successfully.");
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void convertJsonToCsv(String jsonFilePath, String csvFilePath) throws IOException {
		FileReader fileReader = new FileReader(jsonFilePath);
		JsonElement jsonElement = JsonParser.parseReader(fileReader);

		JsonArray jsonArray = jsonElement.getAsJsonArray();

		FileWriter csvWriter = new FileWriter(csvFilePath);

		JsonObject firstObject = jsonArray.get(0).getAsJsonObject();
		firstObject.keySet().forEach(key -> {
			try {
				csvWriter.append(key);
				csvWriter.append(',');
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		csvWriter.append('\n');

		for (JsonElement element : jsonArray) {
			JsonObject jsonObject = element.getAsJsonObject();
			jsonObject.entrySet().forEach(entry -> {
				try {
					csvWriter.append(entry.getValue().getAsString());
					csvWriter.append(',');
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			csvWriter.append('\n');
		}

		fileReader.close();
		csvWriter.flush();
		csvWriter.close();
	}
}
