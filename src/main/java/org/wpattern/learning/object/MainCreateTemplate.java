package org.wpattern.learning.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainCreateTemplate {
	private static final Random RANDOM = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<String> objects = new ArrayList<>();
		String strObject;

		try {
			System.out.println("What's the number of start rules?");
			int numberStartRules = scanner.nextInt();

			System.out.println("What's the number of rules?");
			int numberRules = scanner.nextInt();
			int counterIdentifier = 0, counter = 0;

			System.out.println("<rules>");
			System.out.println("<learningObjects>");

			for (int i = 1; i <= numberStartRules; i++) {
				int numberCompetences = RANDOM.nextInt(2) + 1;

				System.out.println("<learningObject>");
				System.out.println("<duration>10</duration>");
				System.out.println(String.format("<identifier>Identifier-%s</identifier>", counterIdentifier++));
				System.out.println("<competencies>");
				for (int j = 0; j < numberCompetences; j++) {
					strObject = String.format("object-%s", counter++);
					System.out.println("<string>" + strObject + "</string>");
					objects.add(strObject);
				}
				System.out.println("</competencies>");
				System.out.println("</learningObject>");
				System.out.println();
			}

			for (int i = 1; i <= numberRules; i++) {
				int numberConstraints = RANDOM.nextInt(3) + 1;
				int numberCompetences = (i == numberRules) ? objects.size() : RANDOM.nextInt(3) + 1;

				System.out.println("<learningObject>");
				System.out.println("<duration>10</duration>");
				System.out.println(String.format("<identifier>Identifier-%s</identifier>", counterIdentifier++));

				System.out.println("<constraints>");
				for (int j = 0; (j < numberConstraints) &&  !objects.isEmpty(); j++) {
					strObject = objects.remove(RANDOM.nextInt(objects.size()));
					System.out.println("<string>" + strObject + "</string>");
				}
				System.out.println("</constraints>");

				System.out.println("<competencies>");
				for (int j = 0; j < numberCompetences; j++) {
					strObject = String.format("object-%s", counter++);
					System.out.println("<string>" + strObject + "</string>");
					objects.add(strObject);
				}
				System.out.println("</competencies>");
				System.out.println("</learningObject>");
				System.out.println();
			}

			System.out.println("	</learningObjects>");
			System.out.println("</rules>");
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

}
