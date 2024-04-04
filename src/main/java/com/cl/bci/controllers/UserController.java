package com.cl.bci.controllers;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.bci.dao.UserDao;
import com.cl.bci.jwt.JwtUtil;
import com.cl.bci.model.User;
import com.cl.bci.util.Utils;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	public String createUser(@Valid @RequestBody User user) {
		log.info("se ejecuta createUser()");

		try {

			boolean validaMail = Utils.validaEmail(user.getEmail());

			if (!validaMail) {
				log.error("El formato de correo no es valido");
				return jsonOutNOK("El formato de correo no es valido " + user.getEmail());

			}

			boolean existMail = userDao.getExistEmail(user.getEmail());

			if (existMail) {
				log.error("El correo" + user.getEmail() + " ya esta registrado");
				return jsonOutNOK("El correo " + user.getEmail() + " ya esta registrado");
			}

			user.setCreated(Utils.dateFormat());

			User out = userDao.createuser(user);

			out.setLast_login(out.getCreated());
			String mensaje = "Usuario creado OK";

			return jsonOutOK(out, "", false, mensaje);

		} catch (Exception e) {
			log.error("Error crear Usuario");
			return jsonOutNOK("Error crear Usuario");

		}

	}

	@GetMapping("{id}")
	public String searchUserById(@PathVariable(value = "id", required = true) Long id,
			@RequestHeader(value = "Authorization") String token) {
		log.info("se ejecuta searchUser()");

		try {

			if (!validarToken(token)) {
				return jsonOutNOK("Error usuario no ha iniciado sesion");
			}

			User out = userDao.getUserById(id);

			String mensaje = "OK al buscar el usuario " + id;

			return jsonOutOK(out, token, true, mensaje);

		} catch (Exception e) {
			log.error("Error al buscar el usuario " + id);
			return jsonOutNOK("Error al buscar el usuario " + id);
		}
	}

	@DeleteMapping("{id}")
	public String deleteUserById(@PathVariable(value = "id", required = true) Long id,
			@RequestHeader(value = "Authorization") String token) {
		log.info("se ejecuta deleteUser()");

		try {

			if (!validarToken(token)) {
				return jsonOutNOK("Error usuario no ha iniciado sesion");
			}

			userDao.deleteUser(id);

		} catch (Exception e) {
			log.error("Error al eliminar el usuario " + id);
			return jsonOutNOK("Error al eliminar el usuario " + id);
		}

		return jsonOutNOK("OK al eliminar el usuario " + id);

	}

	@PostMapping("{id}")
	public String updateUser(@Valid @RequestBody User user, @PathVariable(value = "id", required = true) Long id,
			@RequestHeader(value = "Authorization") String token) {
		log.info("se ejecuta updateUser()");

		try {

			if (!validarToken(token)) {
				return jsonOutNOK("Error usuario no ha iniciado sesion");
			}

			boolean validaMail = Utils.validaEmail(user.getEmail());

			if (!validaMail) {
				log.error("El formato de correo no es valido " + user.getEmail());
				return jsonOutNOK("El formato de correo no es valido " + user.getEmail());

			}

			User out = userDao.updateUser(user, id);

			String mensaje = "OK al actualizar el usuario";

			return jsonOutOK(out, token, true, mensaje);

		} catch (Exception e) {
			log.error("Error al actualizar el usuario " + id);
			return jsonOutNOK("Error al actualizar el usuario " + id);
		}

	}

	private boolean validarToken(String token) {

		String usuarioId = null;

		try {
			usuarioId = jwtUtil.getKey(token);
		} catch (Exception e) {
			log.error(e.getMessage());
			usuarioId = null;
		}

		return usuarioId != null;

	}

	private String jsonOutOK(User in, String token, boolean isactive, String mensaje) {

		JSONObject json = new JSONObject();

		json.put("id", in.getUuid());
		json.put("created", in.getCreated());
		json.put("modified", in.getModified());
		json.put("last_login", in.getLast_login());
		json.put("token", token);
		json.put("isactive", isactive);
		json.put("mensaje", mensaje);

		return json.toString();
	}

	private String jsonOutNOK(String in) {

		JSONObject json = new JSONObject();

		json.put("mensaje", in);

		return json.toString();
	}

}
