package control;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import products.Box;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class is used to export data to a JSON document
 * 
 */
public class Exporter {

	public void outputBoxesToJSON(String jsonFilePath, List<Box> boxes) {

		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		String json = prettyGson.toJson(boxes);
		try {

			FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
			jsonFileWriter.write(json);
			jsonFileWriter.flush();
			jsonFileWriter.close();
			//System.out.print(json);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}
