package com.amurphy.streams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class CensusCODStream {

	final static String regex = "(?:(\".+\"))?(?:($1|,))";
	final static String regexAll = ".*";

	public static void main(String[] args) throws IOException {

		Scanner input = new Scanner(System.in);
		queryInput(input);
		input.close();
	}

	public static void queryInput(Scanner input) throws IOException {
		String state, causeOfDeath, year;
		String fileName = "resources/";

		System.out.println("Hello! Welcome to your cheerful death analysis program.");
		System.out.println("What state would you like to view?(\"A\" for all)");
		state = input.nextLine();
		System.out.println("Input state is " + state);
		System.out.println("And the cause of death? (\"A\" for all)");
		causeOfDeath = input.nextLine();
		System.out.println("Cause of death is " + causeOfDeath);
		System.out.println("For what year? (\"A\" for all)");
		year = input.nextLine();
		System.out.println("Producing your file for " + state + " " + causeOfDeath + " " + year);
		
		if (state.equals("A") || state.equals("a")) {
			state = regexAll;
			fileName = fileName + "allStates";
		} else {
			fileName = fileName + state;
		}
		
		if (causeOfDeath.equals("A") || causeOfDeath.equals("a")) {
			causeOfDeath = regexAll;
			fileName = fileName + "allCauses";
		} else {
			causeOfDeath = causeOfDeath.toLowerCase();
			fileName = fileName + causeOfDeath;
		}

		if (year.equals("A") || year.equals("a")) {
			year = regexAll;
			fileName = fileName + "allYears.txt";
		} else {
			fileName = fileName + year + ".txt";
		}
		System.out.println();

		censusFilter(state, causeOfDeath, year, fileName);

	}

	public static void censusFilter(String state, String cause, String year, String fileName) throws IOException {

		Comparator<String[]> stateComp = new StateComparator();
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));

		Stream<String> census = Files.lines(Paths.get("resources/causeOfDeathCensus.csv"));

		census
			.map(x -> x.split(regex))
			.filter(x -> (x[3].contains(state)) || x[3].matches(state))
			.filter(x -> ((x[2].toLowerCase().contains(cause) || x[1].toLowerCase().contains(cause)))
						|| x[2].matches(cause))
			.filter(x -> (x[0].contains(year)) || x[0].matches(year))
			.sorted(stateComp)
			.forEach(x -> {
					try {
						writer.write("State: " + x[3] + " Year: " + x[0] + "\n\tCause: " + x[2] + "\n\tTotal deaths: "
								+ x[4] + "\n\tRate per 100k: " + x[5] + "\n\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

		census.close();
		writer.close();

	}

	static class StateComparator implements Comparator<String[]> {

		@Override
		public int compare(String[] o1, String[] o2) {

			int stateCompare = o1[3].toUpperCase().compareTo(o2[3].toUpperCase());
			if (stateCompare == 0) {
				return o1[0].compareTo(o2[0]);
			} else {
				return stateCompare;
			}
			
		}

	}

}
