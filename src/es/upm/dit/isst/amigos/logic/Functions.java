package es.upm.dit.isst.amigos.logic;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Functions {
	
	private String dominiogae = "amigo-invisible-1264";
	//private String dominiogae = "isst-grupo17-amigos";
	
	private static Functions instance;
	
	private Functions(){
		
	}
	
	public static Functions getInstance(){
		if (instance == null)
			instance = new Functions();
		return instance;
	}
	
	public String[] Randomize(String[] arr) {
		String[] randomizedArray = new String[arr.length];
	    System.arraycopy(arr, 0, randomizedArray, 0, arr.length);
	    Random rgen = new Random();
	    for (int i = 0; i < randomizedArray.length; i++) {
	    	int randomPosition = rgen.nextInt(randomizedArray.length);
		    String temp = randomizedArray[i];
		    randomizedArray[i] = randomizedArray[randomPosition];
		    randomizedArray[randomPosition] = temp;
	    }
	    return randomizedArray;
	}
	
	public String[] asignador (String[] usernames, String[] usernames_excls){
		String[] randomizedArray = new String[usernames.length];
		boolean excl = true;
		int n = 0;
		while (excl) {
			randomizedArray = Randomize(usernames);
			excl = false;
			n++;
			if (n >= 100){
				return null; //Demasiados intentos, quiz� no se pueda realizar el sorteo
			}
		    for (int i = 0; i < randomizedArray.length; i++) {
			    if (usernames_excls[i] != "") {
			    	int id_in = Arrays.asList(randomizedArray).indexOf(usernames[i]);
			    	if (id_in < randomizedArray.length - 1) {
			    		if (randomizedArray[id_in+1].equals(usernames_excls[i])) {
				    		excl = true;
						}
			    	}
			    	else {
			    		if (randomizedArray[0].equals(usernames_excls[i])) {
				    		excl = true;
						}
			    	}
				}
			}
		}
		return randomizedArray;
	}

	
	public void enviarEmail(String[] randomizedArray, String msg, String money, String date, String mod_name, String[] emails, String[] usernames) throws IOException{
		for (int i = 0; i < randomizedArray.length; i++) {
			int id_in = Arrays.asList(usernames).indexOf(randomizedArray[i]);
			Message msg_results = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
			try {
				msg_results.setFrom(new InternetAddress("isst-amigoinvisible@"+dominiogae+".appspotmail.com", "Amigo Invisible")); // nombre (nombre@...) y dominio (...@aplicacion) de la app en GAE
				msg_results.addRecipient(Message.RecipientType.TO,  new InternetAddress(emails[id_in], "Participante en el amigo invisible"));
				msg_results.setSubject("El sorteo para el amigo invisible se ha realizado correctamente");
				if (i < randomizedArray.length - 1) {
					msg_results.setText("Hola " + randomizedArray[i] + " , " + mod_name + " ha organizado un sorteo por el Amigo Invisible con el siguiente asunto: " + msg + "\n Tras realizar el sorteo te informamos de que �te ha tocado hacer un regalo a... " + randomizedArray[i+1] + "! \n La cuant�a m�xima del regalo es de " + money + " � y los regalos se repartir�n en la fecha " + date + ". \n �A disfrutar del Amigo Invisible! ;)");
				}
				else {
					msg_results.setText("Hola " + randomizedArray[i] + " , " + mod_name + " ha organizado un sorteo por el Amigo Invisible con el siguiente asunto: " + msg + "\n Tras realizar el sorteo te informamos de que �te ha tocado hacer un regalo a... " + randomizedArray[0] + "! \n La cuant�a m�xima del regalo es de " + money + " � y los regalos se repartir�n en la fecha " + date + ". \n �A disfrutar del Amigo Invisible! ;)");
				}
		        Transport.send(msg_results);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void aviso(String nick, String mod_name) throws IOException{
		Message msg_results = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
		try {
			msg_results.setFrom(new InternetAddress("isst-amigoinvisible@"+dominiogae+".appspotmail.com", "Amigo Invisible")); // nombre (nombre@...) y dominio (...@aplicacion) de la app en GAE
			msg_results.addRecipient(Message.RecipientType.TO,  new InternetAddress(nick+"@gmail.com", "Participante en el amigo invisible"));
			msg_results.setSubject("Invitaci�n para pertenecer a un grupo del Amigo Invisible");
			msg_results.setText("Hola " + nick + " , " + mod_name + " ha organizado un sorteo por el Amigo Invisible en nuestra aplicaci�n y le ha invitado. Para acceder, y poder eliminarte del grupo desde la aplicaci�n en caso de no querer participar, debes pinchar en el siguiente enlace: \n http://1-dot-"+dominiogae+".appspot.com/Login");
			Transport.send(msg_results);
		} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public void chat(String nick, String grupo) throws IOException{
		Message msg_results = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
		try {
			msg_results.setFrom(new InternetAddress("isst-amigoinvisible@"+dominiogae+".appspotmail.com", "Amigo Invisible")); // nombre (nombre@...) y dominio (...@aplicacion) de la app en GAE
			msg_results.addRecipient(Message.RecipientType.TO,  new InternetAddress(nick+"@gmail.com", "Participante en el amigo invisible"));
			msg_results.setSubject("Nuevo mensaje en el chat del Amigo Invisible");
			msg_results.setText("Hola " + nick + " , has recibido un nuevo mensaje en el chat del grupo " + grupo + " del Amigo Invisible");
			Transport.send(msg_results);
		} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public void aviso_eliminado(String nick, String item, String mod_name) throws IOException{
		Message msg_results = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
		try {
			msg_results.setFrom(new InternetAddress("isst-amigoinvisible@"+dominiogae+".appspotmail.com", "Amigo Invisible")); // nombre (nombre@...) y dominio (...@aplicacion) de la app en GAE
			msg_results.addRecipient(Message.RecipientType.TO,  new InternetAddress(nick+"@gmail.com", "Participante en el amigo invisible"));
			msg_results.setSubject("Eliminacion de un deseo de tu lista del Amigo Invisible");
			msg_results.setText("Hola " + nick + " , el moderador de uno de tus grupos, " + mod_name + " ha eliminado el siguiente deseo de tu lista de deseos: " + item);
			Transport.send(msg_results);
		} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
}