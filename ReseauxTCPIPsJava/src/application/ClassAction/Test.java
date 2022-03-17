package application.ClassAction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String input = "777lgmmmmmmm/-:";
		String regex = ".*(m).*";
		boolean m = regex.matches(input);
		Pattern pat = Pattern.compile(regex);
		Matcher match = pat.matcher(input);
		
		if( input.contains(regex)) {
			System.out.println("true");
		} else {
			System.out.println("f");
		}
	}

}
