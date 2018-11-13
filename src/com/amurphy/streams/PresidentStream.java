package com.amurphy.streams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PresidentStream {

	public static void main(String[] args) throws IOException{
		
		// set up the stream to pull the lines from the presidents tab separated value (tsv) file
		Stream<String> presidents = Files.lines(Paths.get("resources/presidents.tsv"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("resources/presidentsOutputSorted.txt", true));
		
		// split array on tab, skip the labels at the top, filter by dems, print each name, printing out a blank spot if no middle name
		presidents
			.map(x -> x.split("\t"))
			.skip(1)
			.filter(x -> x[8].contains("Dem"))
			.forEach(x -> {
				try {
					writer.write("President " + x[0] +": " + x[1] + " " + (x[2].equals("") ? "" : (x[2] + " ")) + x[3] + 
							     "\n\tParty: " + x[8] + "\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
//			.forEach(x -> System.out.println("Name: " + x[1] + " " + (x[2].equals("") ? "" : (x[2] + " ")) + x[3] + "\n\tParty: " + x[8]));
		presidents.close();
		writer.close();
		
		
		
	}

}
