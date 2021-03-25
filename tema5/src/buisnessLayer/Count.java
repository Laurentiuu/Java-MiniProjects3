package buisnessLayer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import modelLayer.MonitoredData;

public class Count {

	private int daysNr;

	private String[] activitiesList = { "Leaving", "Toileting", "Showering", "Sleeping", "Breakfast", "Lunch", "Dinner",
			"Snack", "Spare_Time/TV", "Grooming" };

	//task 1
	public void printMonitoredData() {
		
		try {
			PrintWriter task1= new PrintWriter("Task_1(am pus adresele de la fiecare clasa de tipul MonitoredData).txt","UTF-8");
			for(MonitoredData i : ReadFromFile.getData()) {
				task1.println(i);
			}
			task1.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//task 2
	public int countDistinctDays() {
		
		int count;
		List<String> distinctDays=ReadFromFile.getData().stream().map(n->n.getEndTime().substring(0, 10)).distinct().collect(Collectors.toList());
		count=distinctDays.size();
		daysNr=count;
		
		// afisez
		try {
			PrintWriter task2 = new PrintWriter("Task_2.txt", "UTF-8");
			task2.println(daysNr);
			task2.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	//task 3
	public Map<String, Integer> countActivities() {

		HashMap<String, Integer> activitiesNr = new HashMap<String, Integer>();
		Integer[] activitiesCount = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		ReadFromFile.getData().stream().map(n->n.getActivity()).filter(n->{
			for(int j = 0; j < activitiesList.length; j++) {
				if(activitiesList[j].compareTo(n)==0) {
					activitiesCount[j]++;
					return true;
				}
			}
			return false;
		}).forEach(n->n.length());
		
		for (int j = 0; j < activitiesList.length; j++) {
			activitiesNr.put(activitiesList[j], activitiesCount[j]);
		}
		// afisez
		try {
			PrintWriter task3 = new PrintWriter("Task_3.txt", "UTF-8");
			activitiesNr.entrySet().forEach(n->task3.println(n));
			task3.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return activitiesNr;
	}

	//task 4
	public Map<Integer, Map<String, Integer>> countActivitiesEachDay() {

		try {
			PrintWriter task4 = new PrintWriter("Task_4.txt", "UTF-8");

			HashMap<Integer, Map<String, Integer>> activities = new HashMap<Integer, Map<String, Integer>>();
			HashMap<String, Integer> activitiesNr = new HashMap<String, Integer>();

			int k = daysNr;
			Integer[][] activitiesCount = new Integer[k][10];
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < 10; j++) {
					activitiesCount[i][j] = 0;
				}
			}

			Integer count = 1;

			String currentDate = null;
			String nextDate = new String();
			
			k = 0;
			for (MonitoredData i : ReadFromFile.getData()) {
				nextDate = i.getEndTime().substring(0, 10);
				if (currentDate != null) {
					// daca sunt in aceeasi zi activitatile, parcurg vectorul de activitati si creez
					// HashMapul activitiesNr
					if (currentDate.compareTo(nextDate) == 0) {
						for (int j = 0; j < activitiesList.length; j++) {
							if (activitiesList[j].compareTo(i.getActivity()) == 0) {
								activitiesCount[k][j]++;
								break;
							}
						}
					} else {
						for (int j = 0; j < activitiesList.length; j++) {
							activitiesNr.put(activitiesList[j], activitiesCount[k][j]);
							// System.out.print(activitiesCount[k][j]);
						}
						activities.put(count, activitiesNr);

						// afisez
						activities.entrySet().forEach(n->task4.println(n));

						activities.clear();
						k++;
						count++;
					}
				}
				// iau prima activitate si o numar pentru ca sar peste ea(Sleeping)
				else {
					for (int j = 0; j < activitiesList.length; j++) {
						if (activitiesList[j].compareTo(i.getActivity()) == 0) {
							activitiesCount[k][j]++;
							activitiesNr.put(activitiesList[j], activitiesCount[k][j]);
							break;
						}
					}
				}
				currentDate = nextDate;
			}

			task4.close();
			return activities;
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//task 5
	public Map<String, LocalTime> totalDuration() {

		LocalTime[] timeList = new LocalTime[10];
		HashMap<String, LocalTime> activities = new HashMap<String, LocalTime>();

		String seconds, minutes, hours;

		for (MonitoredData i : ReadFromFile.getData()) {
			seconds = i.getEndTime().substring(17, 19);
			minutes = i.getEndTime().substring(14, 16);
			hours = i.getEndTime().substring(11, 13);
			LocalTime aux = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
			LocalTime sec = aux.minusSeconds(Integer.parseInt(i.getStartTime().substring(17, 19)));
			LocalTime min = aux.minusMinutes(Integer.parseInt(i.getStartTime().substring(14, 16)));
			LocalTime h = aux.minusHours(Integer.parseInt(i.getStartTime().substring(11, 13)));

			aux = LocalTime.of(h.getHour(), min.getMinute(), sec.getSecond());

			for (int j = 0; j < activitiesList.length; j++) {
				if (activitiesList[j].compareTo(i.getActivity()) == 0) {
					if (timeList[j] == null) {
						timeList[j] = aux;
						break;
					} else {
						LocalTime total = timeList[j].plusHours(aux.getHour()).plusMinutes(aux.getMinute())
								.plusSeconds(aux.getSecond());
						timeList[j] = total;
					}
				}
			}
		}
		for (int j = 0; j < activitiesList.length; j++) {
			activities.put(activitiesList[j], timeList[j]);
		}

		// afisez
		try {
			PrintWriter task5 = new PrintWriter("Task_5.txt", "UTF-8");
			activities.entrySet().forEach(n->task5.println(n));
			
			task5.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return activities;
	}

}
