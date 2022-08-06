package org.example.controllers;

import java.util.List;

import org.example.Helper;
import org.example.model.Nem;
import org.example.model.NumerologyModel;
import org.example.model.PersonNumbers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ElemzesekController {

	public PersonNumbers personNumbers;

	public NumerologyModel numerologyModel = new NumerologyModel();

	public Button feladatButton;

	public Button kuldetesButton;

	public Button szemelyisegButton;

	public TextArea textArea;

	public Button ciklusSzamok;

	@FXML
	public void initialize() {
	}

	@FXML
	public void exit(ActionEvent actionEvent) {
		Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
		stage.close();
	}

	public void clickOnkuldetesButton(ActionEvent actionEvent) {
		Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
		personNumbers = (PersonNumbers) stage.getUserData();
		int number = personNumbers.getBigMissionNumber();
		Nem nem = personNumbers.getNem();

		List<String> lines;

		if (nem == Nem.FERFI) {
			lines = Helper.readFromFile("src/main/resources/szamok/nemek/ferfi.txt");
		} else {
			lines = Helper.readFromFile("src/main/resources/szamok/nemek/no.txt");
		}

		StringBuilder sb = new StringBuilder();

		for (String line:lines) {
			sb.append(line).append("\n");
		}

		sb.append("\nKÜLDETÉS: ").append(number).append("\n\n");

		if (number > 9) {
			lines = Helper.readFromFile("src/main/resources/szamok/duplaszamok/" + number + ".txt");
		} else {
			lines = Helper.readFromFile("src/main/resources/szamok/szimplaszamok/" + number + ".txt");
		}

		for (String s : lines) {
			sb.append(s).append("\n");
		}
		textArea.setText(sb.toString());
	}

	public void clickOnFeladatButton(ActionEvent actionEvent) {
		Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
		personNumbers = (PersonNumbers) stage.getUserData();
		int age = personNumbers.getAge();
		int number;
		if (age >= 63) {
			number = personNumbers.getBigOldNumber();
		} else if (age >= 21) {
			number = personNumbers.getBigAdultNumber();
		} else {
			number = personNumbers.getBigChildrenNumber();
		}

		StringBuilder sb = new StringBuilder("FELADAT: " + number + "\n\n");
		List<String> lines;
		if (number > 9) {
			lines = Helper.readFromFile("src/main/resources/szamok/duplaszamok/" + number + ".txt");
		} else {
			lines = Helper.readFromFile("src/main/resources/szamok/szimplaszamok/" + number + ".txt");
		}
		for (String s : lines) {
			sb.append(s).append("\n");
		}
		textArea.setText(sb.toString());
	}

	public void clickOnSzemelyisegButton(ActionEvent actionEvent) {
		Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
		personNumbers = (PersonNumbers) stage.getUserData();
		int bigPersonNumber = personNumbers.getBigPersonalityNumber();
		int innerNumber = personNumbers.getBigInnerNumber();
		int outerNumber = personNumbers.getBigOuterNumber();
		int month = numerologyModel.reduct(personNumbers.getMonth());
		StringBuilder sb = new StringBuilder("SZEMÉLYISÉG: " + bigPersonNumber + "\n\n");
		sb.append("Gyerekkori szám (hónap, nagyon meghatározó a személyiségben): ").append(month);
		sb.append("\n\n");
		List<String> monthLines = Helper.readFromFile("src/main/resources/szamok/gyerekkori/" + month + ".txt");
		for (String line : monthLines) {
			sb.append(line).append("\n");
		}
		sb.append("\n\n");

		List<String> personLines;
		List<String> innerLines;
		List<String> outerLines;
		if (bigPersonNumber > 9) {
			personLines = Helper.readFromFile("src/main/resources/szamok/duplaszamok/" + bigPersonNumber + ".txt");
		} else {
			personLines = Helper.readFromFile("src/main/resources/szamok/szimplaszamok/" + bigPersonNumber + ".txt");
		}
		if (innerNumber > 9) {
			innerLines = Helper.readFromFile("src/main/resources/szamok/duplaszamok/" + innerNumber + ".txt");
		} else {
			innerLines = Helper.readFromFile("src/main/resources/szamok/szimplaszamok/" + innerNumber + ".txt");
		}
		if (outerNumber > 9) {
			outerLines = Helper.readFromFile("src/main/resources/szamok/duplaszamok/" + outerNumber + ".txt");
		} else {
			outerLines = Helper.readFromFile("src/main/resources/szamok/szimplaszamok/" + outerNumber + ".txt");
		}
		sb.append("\nBELSŐ ÉN (személyiség, amit nem tudatosan (ösztönösen) csinálsz): ").append(innerNumber).append("\n\n");
		for (String s : innerLines) {
			sb.append(s).append("\n");
		}
		sb.append("\nKÜLSŐ ÉN (személyiség, amit tudatosan mutatsz a világ felé, szeretnéd hogy ilyennek lássanak): ")
				.append(outerNumber).append("\n\n");
		for (String s : outerLines) {
			sb.append(s).append("\n");
		}
		sb.append("\nTELJES NÉVSZÁM (fő személyiség): ").append(bigPersonNumber).append("\n\n");
		for (String s : personLines) {
			sb.append(s).append("\n");
		}
		textArea.setText(sb.toString());
	}

	public void clickOnCiklusButton(ActionEvent actionEvent) {
		Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
		personNumbers = (PersonNumbers) stage.getUserData();

		int personalYear = personNumbers.getPersonalYear();
		int personalMonth = personNumbers.getPersonalMonth();
		int personalDay = personNumbers.getPersonalDay();

		StringBuilder sb = new StringBuilder("\t\t\tSZEMÉLYES SZÁMOK\n\n\n");

		sb.append("Személyes év: ").append(personalYear).append("\n\n");

		List<String> lines = Helper.readFromFile("src/main/resources/szamok/szemelyes/ev/" + personalYear + ".txt");
		for (String line : lines) {
			sb.append(line).append("\n");
		}

		sb.append("\n\nSzemélyes hónap: ").append(personalMonth).append("\n\n");
		;

		lines = Helper.readFromFile("src/main/resources/szamok/szemelyes/honap/" + personalMonth + ".txt");
		for (String line : lines) {
			sb.append(line).append("\n");
		}

		sb.append("\n\nSzemélyes nap: ").append(personalDay).append("\n\n");
		;

		lines = Helper.readFromFile("src/main/resources/szamok/szemelyes/nap/" + personalDay + ".txt");
		for (String line : lines) {
			sb.append(line).append("\n");
		}

		textArea.setText(sb.toString());
	}
}
