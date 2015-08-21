package com.cts.controllers;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cts.dao.UserDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.entities.User;
import com.cts.errors.RegisterError;
import com.cts.utils.HashUtil;

/**
 * Handles requests for the application register page.
 */
@Controller
public class RegisterController {

	// Regular expression to check if the mail is in the cerner.com domain.
	private static final String emailRegexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[Gg][Mm][Aa][Ii][Ll].[Cc][Oo][Mm]$";
	private Pattern pattern = Pattern.compile(emailRegexp);

	// Boolean variable that checks if the incoming data is passing all the
	// local tests.
	private boolean localSuccess;
	private String errorMessageJson = "";

	private ObjectMapper objectMapper = new ObjectMapper();

	private RegisterError registerError;

	// Initializing the logger for this class.
	private static final Logger logger = Logger.getLogger(RegisterController.class.getName());
	private FileHandler fileHandler;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String firstThingCalled() {

		return "index";
	}

	// need to change the mapping.. somehow

	// Post method of the controller. the user data is hidden from the URL.
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@RequestBody User user) {

		try {
			// This block configure the logger with handler and formatter.
			fileHandler = new FileHandler("logs\\ApplicationLogFile.log", true);
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		} catch (SecurityException e1) {
		} catch (IOException e1) {
		}

		localSuccess = true;

		logger.info("######## Attempting a register...");

		try {

			// Checks if the fields are empty (eg: "")
			// To be changed to a more safer and better working method
			if (user.getEmail().trim().isEmpty() || user.getFirstName().trim().isEmpty()
					|| user.getLastName().trim().isEmpty() || user.getPassword().trim().isEmpty()) {

				localSuccess = false;
				logger.info("Error: One or more fields are empty");
				return generateErrorJson(6);
			} else {

				// Checks if the email is valid
				if (!isValidEmail(user.getEmail())) {

					localSuccess = false;
					logger.info("Error: Invalid Email");
					return generateErrorJson(5);
				}
			}

			// If the data passes the local tests, the database is called
			if (localSuccess) {

				// hash the password
				user.setPassword(HashUtil.getHash((user.getPassword())));
				UserDAOInterface userDAO = new UserDAO();

				if (userDAO.createAccount(user)) {

					logger.info("A new account was successfully created");
					return generateErrorJson(8);
				} else {

					logger.info("Error: email already exists.");
					return generateErrorJson(9);
				}
			}
			logger.info("Unknown error.");
			return generateErrorJson(-1);
		} catch (NullPointerException e) {

			logger.info("NullPointerException when trying to register a new account");
			return generateErrorJson(7);
		}
	}

	/**
	 * @param email
	 *            The email to be verified if it is in cerner.com domain
	 * @return true if the email is in the cerner.com domain, false if it is in
	 *         any other domain.
	 */
	private boolean isValidEmail(String email) {

		Matcher matcher = pattern.matcher(email);

		logger.info(matcher.matches() + " for " + email);

		if (!matcher.matches()) {

			return false;
		}
		return true;
	}

	/**
	 * @param errorCode
	 *            The code of the error that is wanted to be generated as Json,
	 *            ranging from 1 to 7
	 * @return The Json of the requested error
	 */
	private String generateErrorJson(int errorCode) {

		errorMessageJson = "";
		registerError = new RegisterError(errorCode);
		try {

			errorMessageJson = objectMapper.writeValueAsString(registerError);
		} catch (IOException e) {

			logger.info("IOException when trying to generate the Json for DB error");
		}
		return errorMessageJson;
	}
}
