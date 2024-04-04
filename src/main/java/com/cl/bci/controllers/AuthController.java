package com.cl.bci.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cl.bci.jwt.JwtUtil;
import com.cl.bci.model.Login;
import com.cl.bci.model.User;
import com.cl.bci.util.Utils;
import com.cl.bci.dao.UserDao;

@RestController
public class AuthController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	public String login(@RequestBody Login login) {

		boolean validaMail = Utils.validaEmail(login.getEmail());

		if (!validaMail) {
			return jsonOut("El formato de correo no es valido " + login.getEmail());

		}

		boolean isLogin = userDao.loginUser(login);

		if (!isLogin) {
			return jsonOut("Usuario o Password incorrectos");
		}

		User user = userDao.getUserByMail(login);
		// actualizo fecha de login exitoso
		userDao.updateDateLogin(Utils.dateFormat(), user.getUuid());

		if (login != null) {
			String tokenJwt = jwtUtil.create(String.valueOf(user.getUuid()), user.getEmail());
			return tokenJwt;
		}

		return jsonOut("Error al obtener sesion");
	}

	private String jsonOut(String in) {

		JSONObject json = new JSONObject();

		json.put("mensaje", in);

		return json.toString();
	}

}
