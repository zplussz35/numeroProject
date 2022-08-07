package org.example.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import org.example.model.DateValidator;
import org.example.model.NameValidator;
import org.example.model.Nem;
import org.example.model.NumerologyModel;
import org.example.model.PersonNumbers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartPageController {
	private static final String ELEMZES_FXML_NAME = "/fxmls/elemzesek.fxml";

	@FXML
	public CheckBox editCheckbox;

	@FXML
	public TextArea textArea1;

	@FXML
	public TextArea textArea2;

	@FXML
	public TextArea textArea3;

	@FXML
	public TextArea textArea4;

	@FXML
	public TextArea textArea5;

	@FXML
	public TextArea textArea6;

	@FXML
	public TextArea textArea7;

	@FXML
	public TextArea textArea8;

	@FXML
	public TextArea textArea9;

	@FXML
	public GridPane gridPane;

	public NumerologyModel numerologyModel = new NumerologyModel();

	public PersonNumbers personNumbers;

	public ChoiceBox<String> preNames;

	@FXML
	public Button elemzesekButton;

	public ChoiceBox<String> nemMenu;

	DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;

	public DateValidator dateValidator = new DateValidator(dateFormatter);

	@FXML
	public TextField nameTextField;

	@FXML
	public TextField dateTextField;

	public TextArea resultTextArea;

	@FXML
	public void initialize() {
		personNumbers = new PersonNumbers();
		elemzesekButton.setVisible(false);
		ObservableList<String> preNameOptions =
				FXCollections.observableArrayList(
						"",
						"DOKTOR",
						"IFJABB",
						"IDŐSEBB",
						"ÖZVEGY",
						"GRÓF"
				);
		this.preNames.setItems(preNameOptions);

		ObservableList<String> nemOptions =
				FXCollections.observableArrayList(
						"",
						"FÉRFI",
						"NŐ"
				);
		this.nemMenu.setItems(nemOptions);
	}

	public void selectPreName(ActionEvent actionEvent) {

		Object o = actionEvent.getTarget();
		if (o == null) {
			return;
		}
		String preName = String.valueOf(((ChoiceBox<String>) actionEvent.getTarget()).getValue());
		preName = numerologyModel.ekezetTorles(preName).toLowerCase().strip();
		personNumbers.setPreName(preName);
		personNumbers.setPreNameNumber(numerologyModel.sumNameCalculator(preName));
		personNumbers.setPreNameInnerNumber(numerologyModel.sumNameCalculator(preName));
		personNumbers.setPreNameOuterNumber(numerologyModel.sumNameCalculator(preName));
	}

	@FXML
	public void saveData(ActionEvent actionEvent) {
		String name = nameTextField.getText().toLowerCase();
		String dob = dateTextField.getText();
		String validAbleDob = dob.replace(".", "");

		String ekezetMentesNev;
		String ekezetMentesPrename;

		if (dateValidator.isValid(validAbleDob) && NameValidator.isValidName(name) && personNumbers.getNem() != null) {

			personNumbers.setName(name);
			personNumbers.setBirthDate(dob);

			ekezetMentesPrename = numerologyModel.ekezetTorles(personNumbers.getPreName());
			ekezetMentesNev = numerologyModel.ekezetTorles(personNumbers.getName());
			ekezetMentesNev = ekezetMentesPrename + " " + ekezetMentesNev;
			ekezetMentesNev = ekezetMentesNev.strip();

			Map<Character, Integer> BETUERTEK = numerologyModel.createBetuErtekFromFile();
			//Map<Integer, String> JELENTES = numerologyModel.createJelentesFromFile();

			String kuldetes = numerologyModel.calculatePrintableMissionNumber(dob);
			String nameValues = numerologyModel.getNameAndValues(ekezetMentesNev, BETUERTEK);

			resultTextArea.setText(kuldetes + "\n" + nameValues + "\n\nÉletkor: " + personNumbers.getAge());

			List<TextArea> textAreas = gridPane.getChildren().stream().filter(n -> n instanceof TextArea).map(n -> (TextArea) n)
					.toList();
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < 10; i++) {

				for (int n : personNumbers.getDateNumbers()) {
					if (n == i) {
						sb.append("X");
					}
				}

				if (personNumbers.getAdultNumber() == i) {
					if (personNumbers.getDay() > 9) {
						sb.append(" F");
					} else {
						if (sb.charAt(sb.length() - 1) == 'X') {
							sb.deleteCharAt(sb.length() - 1);
						}
						sb.append(" F");
					}
				}
				if (personNumbers.getChildrenNumber() == i) {
					if (personNumbers.getMonth() > 9) {
						sb.append(" Gy");
					} else {
						if (sb.charAt(sb.length() - 1) == 'X') {
							sb.deleteCharAt(sb.length() - 1);
						}
						sb.append(" Gy");
					}
				}
				if (personNumbers.getOldNumber() == i) {
					sb.append(" Id");
				}

				String firstNumber = Integer.toString(i);

				String stringBigOld = Integer.toString(personNumbers.getBigOldNumber());
				if (stringBigOld.startsWith(firstNumber)) {
					sb.append(" ö(").append(personNumbers.getBigOldNumber()).append(")");
				}

				String stringBigChild = Integer.toString(personNumbers.getBigChildrenNumber());
				if (stringBigChild.startsWith(firstNumber) && stringBigChild.length() > 1) {
					sb.append(" gy(").append(personNumbers.getBigChildrenNumber()).append(")");
				}

				String stringBigAdult = Integer.toString(personNumbers.getBigAdultNumber());
				if (stringBigAdult.startsWith(firstNumber) && stringBigAdult.length() > 1) {
					sb.append(" f(").append(personNumbers.getBigAdultNumber()).append(")");
				}

				String stringBigMission = Integer.toString(personNumbers.getBigMissionNumber());
				if (stringBigMission.startsWith(firstNumber)) {
					sb.append(" k(").append(personNumbers.getBigMissionNumber()).append(")");
				}
				if (personNumbers.getMissionNumber() == i) {
					sb.append(" (K)");
				}

				sb.append("\n"); //------------------------------------------------

				for (char c : ekezetMentesNev.toCharArray()) {
					if (numerologyModel.isMGH(c) && BETUERTEK.get(c) == i) {
						sb.append("o");
					}
				}
				if (numerologyModel.isInnerNumber(ekezetMentesNev, i)) {
					sb.append(" BÉ");
				}

				String bigInnerNumber = Integer.toString(personNumbers.getBigInnerNumber());
				if (bigInnerNumber.startsWith(firstNumber)) {
					sb.append(" ").append(bigInnerNumber);
				}

				sb.append("\n"); //------------------------------------------------

				String stringBigPersonNum = Integer.toString(personNumbers.getBigPersonalityNumber());
				if (stringBigPersonNum.startsWith(firstNumber)) {
					sb.append(" sz(").append(personNumbers.getBigPersonalityNumber()).append(")");
				}

				if (numerologyModel.isPersonalityNumber(ekezetMentesNev, i)) {
					sb.append(" Sz");
				}

				sb.append("\n"); //------------------------------------------------

				for (char c : ekezetMentesNev.toCharArray()) {
					if (numerologyModel.isMSH(c) && BETUERTEK.get(c) == i) {
						sb.append("o");
					}
				}
				if (numerologyModel.isOuterNumber(ekezetMentesNev, i)) {
					sb.append(" KÉ");
				}
				String bigOuterNumber = Integer.toString(personNumbers.getBigOuterNumber());
				if (bigOuterNumber.startsWith(firstNumber)) {
					sb.append(" ").append(bigOuterNumber);
				}

				textAreas.get(i - 1).setText(sb.toString());
				sb.delete(0, sb.length());
			}
			elemzesekButton.setVisible(true);

		} else {
			ekezetMentesNev = numerologyModel.ekezetTorles(personNumbers.getName());

			elemzesekButton.setVisible(false);
			List<TextArea> textAreas = gridPane.getChildren().stream().filter(n -> n instanceof TextArea).map(n -> (TextArea) n)
					.toList();
			textAreas.forEach(t -> t.setText(""));

			resultTextArea.setText("(saveData)-> Ilyen név (" + ekezetMentesNev + ")\nVagy ilyen dátum (" + dob
					+ ") nem létezik!\nVagy lehet nem adtad meg a nemed!\nKíséreld meg újra!");
		}
	}

	public void tickEditCheckbox(ActionEvent actionEvent) {
		resultTextArea.setEditable(editCheckbox.isSelected());
	}

	public static void main(String[] args) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
		DateValidator dateValidator1 = new DateValidator(dateFormatter);
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 10; i++) {
			System.out.print("dob: ");
			String date = sc.nextLine();
			if (dateValidator1.isValid(date)) {
				System.out.println("valid");
			} else {
				System.out.println("NOT!");
			}
		}
	}

	public void getElemzesek(ActionEvent actionEvent) {
		try {
			Stage stage1 = new Stage();
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ELEMZES_FXML_NAME)));
			stage1.setUserData(this.personNumbers);
			stage1.setScene(new Scene(root));
			stage1.show();
		} catch (Exception e) {
			resultTextArea.setText("Elemzéseket nem sikerült betölteni.\nMegjelenítő fájl nem található");
		}
	}

	@FXML
	public void exit(ActionEvent actionEvent) {
		Platform.exit();
	}

	public void selenctNem(ActionEvent actionEvent) {
		String nem = String.valueOf(((ChoiceBox<String>) actionEvent.getTarget()).getValue());
		if (nem.equals("FÉRFI")) {
			personNumbers.setNem(Nem.FERFI);
		} else if (nem.equals("NŐ")) {
			personNumbers.setNem(Nem.NO);
		} else {
			personNumbers.setNem(null);
		}

	}
}
