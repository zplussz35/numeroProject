package org.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.Helper;
public class NumerologyModel {

	//private Map<Character,Integer> BETUERTEK = createBetuErtekFromFile();
	//private Map<Integer,String> JELENTES= createJelentesFromFile();

	private Map<Character,Integer> BETUERTEK = createBetuErtek();
	private Map<Integer,String> JELENTES= createJelentes();

	public String calculatePrintableMissionNumber(String birthDate) {
		StringBuilder sb = new StringBuilder("Küldetés: ");
		try {
			List<Integer> numbers = Arrays.stream(birthDate.split("\\.")).map(Integer::parseInt).limit(3).toList();
			int sum = 0;
			if (Integer.toString(numbers.get(0)).startsWith("20")) {
				sum = 18;
			}
			List<Integer> numberBits = new ArrayList<>();
			for (Integer num : numbers) {
				while (num > 0) {
					if (num % 10 != 0) {
						numberBits.add(num % 10);
					}
					sum += num % 10;
					num /= 10;
				}
			}
			sb.append(sum);
			int sum2 = sum;
			if (sum2 > 9) {
				sb.append(printableReduct(sum2));
			}
		} catch (ClassCastException cce) {
			System.out.println(cce.getMessage());
		}
		return sb.toString();
	}

	public String printableReduct(int sum2) {
		StringBuilder sb = new StringBuilder();
		while (sum2 > 9) {
			sb.append("->");
			int sum = sum2;
			sum2 = 0;
			while (sum > 0) {
				sum2 += sum % 10;
				sum /= 10;
			}
			sb.append(sum2);
		}
		return sb.toString();
	}

	public Map<Integer, String> createJelentesFromFile() {
		Map<Integer, String> JELENTES = new HashMap<>();
		List<String> lines = Helper.readFromFile(Helper.JELENTES_SOURCE);
		assert lines != null;
		for (String line : lines) {
			String[] parts = line.split(":");
			JELENTES.put(Integer.parseInt(parts[0]), parts[1]);
		}
		return JELENTES;
	}


	public Map<Character, Integer> createBetuErtekFromFile() {
		Map<Character, Integer> BETUERTEK = new HashMap<>();
		List<String> lines = Helper.readFromFile(Helper.BETUERTEK_SOURCE);
		assert lines != null;
		for (String line : lines) {
			String[] parts = line.split(":");
			BETUERTEK.put(parts[0].toCharArray()[0], Integer.parseInt(parts[1]));
		}
		return BETUERTEK;
	}


	public Map<Integer, String> createJelentes() {
		Map<Integer, String> JELENTES = new HashMap<>();
		JELENTES.put(1,"Kreatív vezető");
		JELENTES.put(2,"segítő együttműködő alkalmazkodó");
		JELENTES.put(3,"szórakoztató kommunikatív");
		JELENTES.put(4,"Realista");
		JELENTES.put(5,"Optimista");
		JELENTES.put(6,"Harmónikus, családcentrikus");
		JELENTES.put(7,"Céltudatos szellemi vezető");
		JELENTES.put(8,"Karrier siker szenvedély");
		JELENTES.put(9,"Szellemiség");

		return JELENTES;
	}


	public Map<Character, Integer> createBetuErtek() {
		Map<Character, Integer> BETUERTEK = new HashMap<>();
		BETUERTEK.put('a',1); BETUERTEK.put('á',1); BETUERTEK.put('b',2); BETUERTEK.put('c',3);
		BETUERTEK.put('d',4); BETUERTEK.put('e',5); BETUERTEK.put('é',5); BETUERTEK.put('f',6);
		BETUERTEK.put('g',7); BETUERTEK.put('h',8); BETUERTEK.put('i',9); BETUERTEK.put('í',9);
		BETUERTEK.put('j',1); BETUERTEK.put('k',2); BETUERTEK.put('l',3); BETUERTEK.put('m',4);
		BETUERTEK.put('n',5); BETUERTEK.put('o',6); BETUERTEK.put('ó',6); BETUERTEK.put('ö',6);
		BETUERTEK.put('ő',6); BETUERTEK.put('p',7); BETUERTEK.put('q',8); BETUERTEK.put('r',9);
		BETUERTEK.put('s',1); BETUERTEK.put('t',2); BETUERTEK.put('u',3); BETUERTEK.put('ú',3);
		BETUERTEK.put('ü',3); BETUERTEK.put('ű',3); BETUERTEK.put('v',4); BETUERTEK.put('w',5);
		BETUERTEK.put('x',6); BETUERTEK.put('y',7); BETUERTEK.put('z',8);
		return BETUERTEK;
	}



	public int reduct(int sum2) {
		while(sum2>9){
			int sum=sum2;
			sum2=0;
			while(sum>0){
				sum2+=sum%10;
				sum/=10;
			}
		}
		return sum2;
	}


	public boolean isInnerNumber(String name,int n){
		return reduct(sumMGHCalculator(name)) == n;
	}
	public boolean isOuterNumber(String name,int n){
		return reduct(sumMSHCalculator(name)) == n;
	}
	public boolean isPersonalityNumber(String name,int n){
		return reduct(sumNameCalculator(name)) == n;
	}

	public int sumNumberBitCalculator(int num){
		int sumBit=0;
		while(num>0){
			sumBit+=num%10;
			num/=10;
		}
		return sumBit;
	}
	public int sumMSHCalculator(String name){
		int sumMSH=0;
		for (char c : name.toCharArray()) {
			if (isMSH(c)) {
				sumMSH += BETUERTEK.get(c);
			}
		}
		return sumMSH;
	}

	public int sumMGHCalculator(String name){
		int sumMGH=0;
		for (char c : name.toCharArray()) {
			if (isMGH(c)) {
				sumMGH += BETUERTEK.get(c);
			}
		}
		return sumMGH;
	}
	public int sumNameCalculator(String name){
		int sumAllChar=0;
		if(NameValidator.isValidName(name)){
			for (char c : name.toCharArray()) {
				if(!Character.isWhitespace(c)){
					sumAllChar += BETUERTEK.get(c);
				}
			}
			return sumAllChar;
		}
		return 0;
	}

	public boolean isMGH(Character c) {
		return "aáeéiíoóöőuúüű".contains(c.toString());
	}

	public boolean isMSH(Character c) {
		return "bcdfghjklmnpqrstvwxyz".contains(c.toString());
	}

	public Map<Character,Character> createEkezetMentesMgh(){
		Map<Character,Character> ekezetMentesMgh= new HashMap<>();
		ekezetMentesMgh.put('á','a');
		ekezetMentesMgh.put('é','e');
		ekezetMentesMgh.put('í','i');
		ekezetMentesMgh.put('ó','o');
		ekezetMentesMgh.put('ö','o');
		ekezetMentesMgh.put('ő','o');
		ekezetMentesMgh.put('ú','u');
		ekezetMentesMgh.put('ü','u');
		ekezetMentesMgh.put('ű','u');

		ekezetMentesMgh.put('Á','A');
		ekezetMentesMgh.put('É','E');
		ekezetMentesMgh.put('Í','I');
		ekezetMentesMgh.put('Ó','O');
		ekezetMentesMgh.put('Ö','O');
		ekezetMentesMgh.put('Ő','O');
		ekezetMentesMgh.put('Ú','U');
		ekezetMentesMgh.put('Ü','U');
		ekezetMentesMgh.put('Ű','U');
		return ekezetMentesMgh;
	}

	public boolean isEkezetes(Character c){
		return "áéíóöőúűüÁÉÍÓÖŐÚŰÜ".contains(c.toString());
	}


	public String ekezetTorles(String name){
		if(name == null || name.isEmpty()){
			return "";
		}
		Map<Character,Character> ekezetMentesAbc= createEkezetMentesMgh();
		StringBuilder sb = new StringBuilder();
		for (char c: name.toCharArray()) {
			if(isEkezetes(c)){
				sb.append(ekezetMentesAbc.get(c));
			} else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public String getNameAndValues(String name, Map<Character, Integer> BETUERTEK) {
		char[] nameCharArray;
		name=name.toLowerCase().strip();

		if(NameValidator.isValidName(name)){
			 nameCharArray= String.join("", name.split(" ")).toCharArray();
		} else{
			return "(getnameAndValues)->Nincs ilyen név: ("+name+")\nKíséreld meg újra!";
		}


		StringBuilder sb = new StringBuilder("\t\t\t");
		int sumMGH = 0;
		int sumMSH = 0;
		int sumAllBetu = 0;
		for (char c : nameCharArray) {
			if (isMGH(c)) {
				sumMGH += BETUERTEK.get(c);
				sb.append(BETUERTEK.get(c)).append(" ");
			} else {
				sb.append("  ");
			}
		}
		sb.append(sumMGH);
		sb.append(printableReduct(sumMGH));
		sb.append("\n\t\t\t");
		for (char c : nameCharArray) {
			sumAllBetu += BETUERTEK.get(c);
			sb.append(c).append(" ");
		}
		sb.append(sumAllBetu);
		sb.append(printableReduct(sumAllBetu));
		sb.append("\n\t\t\t");
		for (char c : nameCharArray) {
			if (isMSH(c)) {
				sumMSH += BETUERTEK.get(c);
				sb.append(BETUERTEK.get(c)).append(" ");
			} else {
				sb.append("  ");
			}
		}
		sb.append(sumMSH);
		sb.append(printableReduct(sumMSH));
		return sb.toString();
	}

	public static void main(String[] args) {
		NumerologyModel numerologyModel = new NumerologyModel();
		int i = 0;
		while (i < 10) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Név: ");
			String name = sc.nextLine();
			NumerologyModel numerologyModel1 = new NumerologyModel();
			Map<Character, Integer> BETUERTEK = numerologyModel1.createBetuErtekFromFile();
			//System.out.println(numerologyModel1.printNameAndValues(name,BETUERTEK));
			i++;
		}
	}
}