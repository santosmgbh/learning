package br.com.almir.util;

import java.text.ParseException;
import java.util.Date;

import br.com.almir.model.User;


public class WsUtil {

	public static String generateAuthKey(String username, String password) {
		String hash = username+":"+password+":"+new Date().getTime();
		//Todo criptografar login e senha;
		return hash;
	}

	public static User parseKey(String key) {
		String[] values = key.split(":");
		if(values.length != 3)
			return null;
		User user = new User();
		user.setLogin(values[0]);
		user.setSenha(values[1]);
		//Todo: validar data de expiração.
		return user;
	}

}
