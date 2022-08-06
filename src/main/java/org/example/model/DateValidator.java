package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator{
	private DateTimeFormatter dateFormat;

	public DateValidator(DateTimeFormatter dateTimeFormatter) {
		this.dateFormat=dateTimeFormatter;
	}

	public boolean isValid(String dateStr) {
		try {

			LocalDate.parse(dateStr, this.dateFormat);

			int year=Integer.parseInt(dateStr.substring(0,4));
			int month=Integer.parseInt(dateStr.substring(4,6));
			int day=Integer.parseInt(dateStr.substring(6,8));
			if(LocalDate.now().isBefore(LocalDate.of(year,month,day))){
				return false;
			}
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
}
