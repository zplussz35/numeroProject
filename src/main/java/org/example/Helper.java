package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Helper {
	public static final String JELENTES_SOURCE="src/main/resources/szamok/jelentes.txt";
	public static final String BETUERTEK_SOURCE="src/main/resources/szamok/betuertek.txt";

	public static List<String> readFromFile(String stringPath){
		List<String> result=new ArrayList<>();
		try{
			result= read(stringPath);
			return result;
		}catch (IllegalStateException ise){
			System.out.println(ise.getMessage());
			return result;
		}
	}

	private static List<String> read(String stringPath){
		try{
			return Files.readAllLines((Paths.get(stringPath)));
		}catch (IOException ioe){
			throw new IllegalStateException("cannot read from file",ioe);
		}
	}
}
