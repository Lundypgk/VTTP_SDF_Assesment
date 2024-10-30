package vttp.batch5.sdf.task01;

// Use this class as the entry point of your program

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.*;
import java.io.*;
import java.util.*;

import vttp.batch5.sdf.task01.models.BikeEntry;


public class Main {

	public static void main(String[] args) {
		try {
			File f = new File("./day.csv");
			BufferedReader br = new BufferedReader(new FileReader(f));
			ArrayList<BikeEntry> storage = new ArrayList<>();
			String curr_line;

			int totalLine = 0;
			boolean first = true;

			while ((curr_line = br.readLine()) != null) {
				if (first) {
					first = false;
					continue;
				}
				totalLine++;
				String[] toSend = parseCSVLine(curr_line);
				BikeEntry current = BikeEntry.toBikeEntry(toSend);
				storage.add(current);
			}
			br.close();
			Collections.sort(storage,new BikeComparator());
			//helper DS
			HashMap<Integer, String> positioning = new HashMap<>();
			positioning.put(0, "");
			positioning.put(1, "second");
			positioning.put(2, "third");
			positioning.put(3, "fourth");
			positioning.put(4, "fifth");


			HashMap<Integer, String> Weather = new HashMap<>();
			Weather.put(1,"Clear, Few clouds, Partly cloudy, Partly cloudy");
			Weather.put(2,"Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist");
			Weather.put(3,"Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds");
			Weather.put(4,"Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog");

			HashMap<Integer, String> day = new HashMap<>();
			day.put(0,"Monday");
			day.put(1,"Tuesday");
			day.put(2,"Wednesday");
			day.put(3,"Thursday");
			day.put(4,"Friday");
			day.put(5,"Saturday");
			day.put(6,"Sunday");

			for (int i =0; i <5; i++) {
				BikeEntry current = storage.get(i);
				System.out.printf("The %s highest recorded number of cyclist was in %s, on a %s in the month of %s.There were a total of %d cyclist.The weather was %s. %s was not a Holiday\n",
								  positioning.get(i),
								  Utilities.toSeason(current.getSeason()),
								  day.get(current.getWeekday()),
								  Utilities.toMonth(current.getMonth()),
								  current.getRegistered()+current.getCasual(),
								  Weather.get(current.getWeather()),
								  day.get(current.getWeekday()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// from previous assingmnet
	public static String[] parseCSVLine(String line) {
		boolean inQuotes = false;
		StringBuilder sb = new StringBuilder();
		List<String> fields = new ArrayList<>();

		for (char c : line.toCharArray()) {
			if (c == '"') {
				// Toggle the state of the quotes
				inQuotes = !inQuotes;
			} else if (c == ',' && !inQuotes) {
				fields.add(sb.toString().trim());
				sb.setLength(0); // Reset the StringBuilder for the next field
			} else {
				// Add character to the current field
				sb.append(c);
			}
		}
		// Add the last field
		fields.add(sb.toString().trim());

		// Convert list to array
		return fields.toArray(new String[0]);
	}

}
