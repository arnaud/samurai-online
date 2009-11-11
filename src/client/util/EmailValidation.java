package client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* Vérifie la validité d'une adresse mail.
* Est utilisée lors de la création d'un compte par le client.
*/
public class EmailValidation {
	public static boolean check(String email){
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
