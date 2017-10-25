package com.bridgeit.todo.Utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	public void sendVerificationMail(String emailTo, String emailkey) {
		final String username = "bridgeitjdbl@gmail.com";
		final String password = "fundoonotes";

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// get Session
		Session sessionOfSmtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// compose message
		try {

			Message message = new MimeMessage(sessionOfSmtp);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject("Testing Subject");
			/*
			 * message.setText("Dear Mail Crawler," +
			 * "\n\n No spam to my email, please!"); message.
			 * setContent("<a href='http://localhost:8080/ToDo/#!/emailVerification?email='>Click here to activate your account</a>"
			 * ,"text/html" );
			 */
			message.setContent("Hello user," + "\n\n click on the below link to activate your Fundoo Notes account."
					+ "\n\n <a href='http://localhost:8080/ToDo/emailVerification?email=" + emailkey
					+ "'>Fundoo Notes</a>", "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void resetPassword(String emailTo, String emailkey) {
		final String username = "bridgeitjdbl@gmail.com";
		final String password = "fundoonotes";

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// get Session
		Session sessionOfSmtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// compose message
		try {

			Message message = new MimeMessage(sessionOfSmtp);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject("Testing Subject");
			message.setContent("Hello user," + "\n\n click on the link to Reset your Fundoo Notes Password."
					+ "\n\n <a href='http://localhost:8080/ToDo/resetPasswordApi?email=" + emailkey
					+ "'>Fundoo Notes</a>", "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
