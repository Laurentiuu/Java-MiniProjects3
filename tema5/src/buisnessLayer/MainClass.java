package buisnessLayer;

interface Tasks{
	void doTask(String taskMessage);
}

public class MainClass {

	public static void main(String[] args) {

		//java -jar PT2020_30226_Galis_Laurentiu_Assignment_5.jar
		
		new ReadFromFile("Activities.txt");
		Count c=new Count();
		
		Tasks t=out->System.out.println("Se ruleaza: "+out);
		
		c.printMonitoredData();//task1
		t.doTask("task 1");
		c.countDistinctDays();//task2
		t.doTask("task 2");
		c.countActivities();//task3
		t.doTask("task 3");
		c.countActivitiesEachDay();//task4
		t.doTask("task 4");
		c.totalDuration();//task5
		t.doTask("task 5");
		
	}

}
