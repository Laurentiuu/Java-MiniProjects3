package buisnessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import modelLayer.MonitoredData;

import java.util.List;;

public class ReadFromFile {

	private static ArrayList<MonitoredData> data = new ArrayList<MonitoredData>();

	public ReadFromFile(String inputFile) {

		File f = new File(inputFile);
		String path = new String();
		if (f.exists()) {
			path = f.getAbsolutePath();
			System.out.println("File path: " + path + "\n");
		}

		File file = new File(path);
		Scanner sc;
		try {

			sc = new Scanner(file);
			while (sc.hasNext()) {
				String s = sc.nextLine();

				List<String> list=ReadFromFile.split(s);
				MonitoredData d = new MonitoredData();
				d.setStartTime(list.get(0));
				d.setEndTime(list.get(1));
				d.setActivity(list.get(2));
				data.add(d);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<String> split(String str) {
		return Stream
				.of(new String(str.substring(0, 19)), new String(str.substring(21, 40)), new String(str.substring(42)))
				.map(elem -> new String(elem)).collect(Collectors.toList());
	}

	public static ArrayList<MonitoredData> getData() {
		return data;
	}

}
