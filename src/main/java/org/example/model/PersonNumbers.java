package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonNumbers {
	DateValidator dateValidator = new DateValidator(DateTimeFormatter.BASIC_ISO_DATE);

	NumerologyModel numerologyModel = new NumerologyModel();

	private int bigMissionNumber;

	private int missionNumber;

	private int bigChildrenNumber;

	private int childrenNumber;

	private Nem nem;

	private int bigAdultNumber;

	private int adultNumber;

	private int bigOldNumber;

	private int oldNumber;

	private int bigInnerNumber;

	private int innerNumber;

	private int bigOuterNumber;

	private int outerNumber;

	private int personalityNumber;

	private int bigPersonalityNumber;

	private String preName;

	private int preNameNumber;
	private int preNameInnerNumber;
	private int preNameOuterNumber;

	private String familyName;

	private int familyNameNumber;
	private int familyInnerNameNumber;
	private int familyOuterNameNumber;


	private String surname1;

	private int surname1Number;
	private int surname1InnerNumber;
	private int surname1OuterNumber;

	private String surname2;

	private int surname2Number;
	private int surname2InnerNumber;
	private int surname2OuterNumber;

	private List<Integer> dateNumbers = new ArrayList<>();

	private List<Integer> nameNumbers = new ArrayList<>();

	private String name;

	private String birthDate;

	private char[] nameCharArray;

	private int year;

	private int month;

	private int day;

	private int age;

	private int personalYear;

	private int personalMonth;

	private int personalDay;

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		calculateDateNumbers();
	}

	public void setName(String name) {
		this.name = name;
		calculateNameNumbers();
	}

	public PersonNumbers(String name, String birthDate) {
		this.name = name;
		this.birthDate = birthDate;
		calculateDateNumbers();
		calculateNameNumbers();
	}

	public PersonNumbers(String name, String birthDate, String preName) {
		this.name = name;
		this.birthDate = birthDate;
		setPrenameWithName(preName);
		calculateDateNumbers();
		calculateNameNumbers();
	}

	public void setPreName(String preName) {

		this.preName = preName.toLowerCase();
	}

	public void calculateDateNumbers() {
		try {
			if (birthDate == null) {
				return;
			}
			String dob = birthDate;
			String validableDob = dob.replace(".", "");
			if (birthDate.isEmpty() || !dateValidator.isValid(validableDob)) {
				System.out.println("calculateNumbers()->Nem valid dátum! (" + birthDate + ")");
				return;
			}
			List<Integer> numbers = Arrays.stream(birthDate.split("\\.")).map(Integer::parseInt).limit(3).toList();

			year = numbers.get(0);
			month = numbers.get(1);
			day = numbers.get(2);

			age = calculateAge(year, month, day);

			personalYear = calculatePersonalYear(year, month, day);
			personalMonth = calculatePersonalMonth(year, month, day);
			personalDay = calculatePersonalDay(year, month, day);

			bigChildrenNumber = this.month;
			bigAdultNumber = this.day;
			bigOldNumber = numerologyModel.sumNumberBitCalculator(this.year);

			oldNumber = numerologyModel.reduct(year);
			childrenNumber = numerologyModel.reduct(month);
			adultNumber = numerologyModel.reduct(day);

			int sum = 0;
			if (Integer.toString(numbers.get(0)).startsWith("20")) {
				sum = 18;
			}

			dateNumbers.clear();
			for (Integer num : numbers) {
				while (num > 0) {
					if (num % 10 != 0) {
						dateNumbers.add(num % 10);
					}
					sum += num % 10;
					num /= 10;
				}
			}
			this.bigMissionNumber = sum;
			this.missionNumber = numerologyModel.reduct(sum);

		} catch (ClassCastException cce) {
			System.out.println(cce.getMessage());
		}
	}

	public boolean isValidDate(int year, int month, int day) {
		String validYear = String.valueOf(year);
		String validMonth = String.valueOf(month);
		String validDay = String.valueOf(day);
		if (month < 10) {
			validMonth = "0".concat(String.valueOf(month));
		}
		if (day < 10) {
			validDay = "0".concat(String.valueOf(month));
		}
		String dateStr = validYear.concat(validMonth).concat(validDay);
		return dateValidator.isValid(dateStr);
	}

	private int calculatePersonalDay(int year, int month, int day) {
		if (!isValidDate(year, month, day)) {
			return 0;
		}
		LocalDate now = LocalDate.now();
		int sumBitsDay = numerologyModel.sumNumberBitCalculator(now.getDayOfMonth());
		return numerologyModel.reduct(calculatePersonalMonth(year, month, day) + sumBitsDay);
	}

	private int calculatePersonalYear(int year, int month, int day) {
		if (!isValidDate(year, month, day)) {
			return 0;
		}
		LocalDate now = LocalDate.now();
		int sumBitsYear = numerologyModel.sumNumberBitCalculator(now.getYear());
		int sumBitsMonth = numerologyModel.sumNumberBitCalculator(month);
		int sumBitsDay = numerologyModel.sumNumberBitCalculator(day);
		if (now.getMonthValue() > month) {
			return numerologyModel.reduct(sumBitsYear + sumBitsMonth + sumBitsDay);
		} else if (now.getMonthValue() == month) {
			if (now.getDayOfMonth() >= day) {
				return numerologyModel.reduct(sumBitsYear + sumBitsMonth + sumBitsDay);
			}
			return numerologyModel.reduct(sumBitsYear - 1 + sumBitsMonth + sumBitsDay);
		}
		return numerologyModel.reduct(sumBitsYear - 1 + sumBitsMonth + sumBitsDay);
	}

	private int calculatePersonalMonth(int year, int month, int day) {
		if (!isValidDate(year, month, day)) {
			return 0;
		}
		LocalDate now = LocalDate.now();
		int sumBitsMonth = numerologyModel.sumNumberBitCalculator(now.getMonthValue());
		if (now.getDayOfMonth() >= day) {
			return numerologyModel.reduct(calculatePersonalYear(year, month, day) + sumBitsMonth);
		}
		return numerologyModel.reduct(calculatePersonalYear(year, month, day) + sumBitsMonth - 1);
	}

	public static int calculateAge(int year, int month, int day) {
		LocalDate now = LocalDate.now();
		if (now.getMonthValue() > month) {
			return LocalDate.now().getYear() - year;
		} else {
			if (now.getMonthValue() == month) {
				if (now.getDayOfMonth() >= day) {
					return LocalDate.now().getYear() - year;
				} else {
					return LocalDate.now().getYear() - year - 1;
				}
			} else {
				return LocalDate.now().getYear() - year - 1;
			}
		}
	}

	public void calculateNameNumbers() {
		Map<Character, Integer> BETUERTEK = numerologyModel.createBetuErtekFromFile();
		this.nameCharArray = String.join("", name.split(" ")).toCharArray();
		String[] nameParts = name.split(" ");
		if (this.preName == null || this.preName.equals("")){
			if (nameParts.length == 1) {
				this.surname1 = nameParts[0];
				this.surname1Number = numerologyModel.sumNameCalculator(surname1);
				this.surname1InnerNumber = numerologyModel.sumMGHCalculator(surname1);
				this.surname1OuterNumber = numerologyModel.sumMSHCalculator(surname1);

			}
			if (nameParts.length == 2) {
				this.familyName = nameParts[0];
				this.surname1 = nameParts[1];
				this.familyNameNumber = numerologyModel.sumNameCalculator(familyName);
				this.familyInnerNameNumber = numerologyModel.sumMGHCalculator(familyName);
				this.familyOuterNameNumber = numerologyModel.sumMSHCalculator(familyName);

				this.surname1Number = numerologyModel.sumNameCalculator(surname1);
				this.surname1InnerNumber = numerologyModel.sumMGHCalculator(surname1);
				this.surname1OuterNumber = numerologyModel.sumMSHCalculator(surname1);
			}
			if (nameParts.length == 3) {
				this.familyName = nameParts[0];
				this.surname1 = nameParts[1];
				this.surname2 = nameParts[2];
				this.familyNameNumber = numerologyModel.sumNameCalculator(familyName);
				this.familyInnerNameNumber = numerologyModel.sumMGHCalculator(familyName);
				this.familyOuterNameNumber = numerologyModel.sumMSHCalculator(familyName);

				this.surname1Number = numerologyModel.sumNameCalculator(surname1);
				this.surname1InnerNumber = numerologyModel.sumMGHCalculator(surname1);
				this.surname1OuterNumber = numerologyModel.sumMSHCalculator(surname1);

				this.surname2Number = numerologyModel.sumNameCalculator(surname2);
				this.surname2InnerNumber = numerologyModel.sumMGHCalculator(surname2);
				this.surname2OuterNumber = numerologyModel.sumMSHCalculator(surname2);
			}
		} else{
			if (nameParts.length == 2) {
				this.surname1 = nameParts[1];

				this.surname1Number = numerologyModel.sumNameCalculator(surname1);
				this.surname1InnerNumber = numerologyModel.sumMGHCalculator(surname1);
				this.surname1OuterNumber = numerologyModel.sumMSHCalculator(surname1);
			}
			if (nameParts.length == 3) {
				this.familyName= nameParts[1];
				this.surname1 = nameParts[2];
				this.familyNameNumber = numerologyModel.sumNameCalculator(familyName);
				this.familyInnerNameNumber = numerologyModel.sumMGHCalculator(familyName);
				this.familyOuterNameNumber = numerologyModel.sumMSHCalculator(familyName);

				this.surname1Number = numerologyModel.sumNameCalculator(surname1);
				this.surname1InnerNumber = numerologyModel.sumMGHCalculator(surname1);
				this.surname1OuterNumber = numerologyModel.sumMSHCalculator(surname1);
			}
			if (nameParts.length == 4){
				this.familyName= nameParts[1];
				this.surname1 = nameParts[2];
				this.surname2 = nameParts[3];
				this.familyNameNumber = numerologyModel.sumNameCalculator(familyName);
				this.familyInnerNameNumber = numerologyModel.sumMGHCalculator(familyName);
				this.familyOuterNameNumber = numerologyModel.sumMSHCalculator(familyName);

				this.surname1Number = numerologyModel.sumNameCalculator(surname1);
				this.surname1InnerNumber = numerologyModel.sumMGHCalculator(surname1);
				this.surname1OuterNumber = numerologyModel.sumMSHCalculator(surname1);

				this.surname2Number = numerologyModel.sumNameCalculator(surname2);
				this.surname2InnerNumber = numerologyModel.sumMGHCalculator(surname2);
				this.surname2OuterNumber = numerologyModel.sumMSHCalculator(surname2);
			}

		}


		for (char c : nameCharArray) {
			nameNumbers.add(BETUERTEK.get(c));
		}
		this.bigInnerNumber = numerologyModel.sumMGHCalculator(name);
		this.bigOuterNumber = numerologyModel.sumMSHCalculator(name);
		this.bigPersonalityNumber = numerologyModel.sumNameCalculator(name);
		this.innerNumber = numerologyModel.reduct(numerologyModel.sumMGHCalculator(name));
		this.outerNumber = numerologyModel.reduct(numerologyModel.sumMSHCalculator(name));
		this.personalityNumber = numerologyModel.reduct(numerologyModel.sumNameCalculator(name));
	}

	public static void main(String[] args) {
		PersonNumbers personNumbers = new PersonNumbers();
		System.out.println(personNumbers.calculatePersonalDay(2002, 2, 22));
		System.out.println(personNumbers.calculatePersonalMonth(2002, 2, 22));
		System.out.println(personNumbers.calculatePersonalYear(2002, 2, 22));

	}

	public void setPrenameWithName(String selectedPrename) {

		if (selectedPrename == null || selectedPrename.equals("null")) {
			return;
		}
		this.preName = selectedPrename;
		this.name = selectedPrename.toLowerCase().concat(" " + this.name);
	}
}
