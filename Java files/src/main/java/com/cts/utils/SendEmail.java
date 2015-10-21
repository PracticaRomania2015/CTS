package com.cts.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

	public static boolean sendEmail(String emailTo, String subject, String msg) {

		String host = "smtp.gmail.com";
		final String user = "practica.romania@gmail.com";// change accordingly
		final String password = "Parolapracticaromania";// change accordingly

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject(subject);
			message.setText(msg);
			// send the message
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}
}