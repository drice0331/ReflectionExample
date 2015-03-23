
public class Person {

	String name;
	int age;
    int ssNumber;
    Person friend;
	
	public Person() {
		name = "unknown";
		age = 0;
		ssNumber = 0;
	}
	
	public Person(String name, int age, int ssNumber) {
		this.name = name;
		this.age = age;
		this.ssNumber = ssNumber;
	}

	public int getSsNumber() {
		return ssNumber;
	}

	public void setSsNumber(int ssNumber) {
		this.ssNumber = ssNumber;
	}
	
	public void yell() {
		System.out.println("Yelling");
	}
	public void yell(String s) {
		System.out.println("Yelling " + s);
	}
}
