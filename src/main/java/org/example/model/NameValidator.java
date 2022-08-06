package org.example.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator{

	public static boolean isValidName(String name){
		String regexp= "^[a-zá-űA-ZÁ-Ű]+[ ]*+[a-zá-űA-ZÁ-Ű]+[ ]*+[a-zá-űA-ZÁ-Ű]*[ ]*[a-zá-űA-ZÁ-Ű]*[ ]*[a-zá-űA-ZÁ-Ű]+[ ]*[a-zá-űA-ZÁ-Ű]";

		Pattern p= Pattern.compile(regexp);

		if(name == null || name.isEmpty()){
			return false;
		}

		Matcher m = p.matcher(name);
		return m.matches();
	}
}
