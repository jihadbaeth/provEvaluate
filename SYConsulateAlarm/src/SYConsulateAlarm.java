import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 */

/**
 * @author jihad
 *
 */
public class SYConsulateAlarm {

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days

		return cal.getTime();
	}

	public static void sendNotificationMail(String link) {
		final String username = "senderEmail@gmail.com";
		final String password = "P@$$w0Rd";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@mail.com"));
			message.setSubject("Passport renewal!");
			message.setText("Dear subscriber," + "\n\n a vacant passport renewal date has been found."+
					"you can make a reservation immediatly by clicking on the link below"+
					"\n\n "+link+
					"\n\n our system will continue to notify you of any new vacant dates in the future."+
					"\n\n regards,"+
					"\n\n "+
					"\n\n Syrian consulate vacant appointment notification system");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		while (true) {
			Document doc;
			int numberOfOptions = 0;

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = cal.getTime();
			for (int i = 0; i < 30; i++) {
				try {
					// run
					System.out.println(
							"checking http://www.mofaex.gov.sy/istanbul-consulate/istanbul_system/test.php?var="
									+ format.format(myDate));
					doc = Jsoup.connect("http://www.mofaex.gov.sy/istanbul-consulate/istanbul_system/test.php?var="
							+ format.format(myDate)).get();
					// String title = doc.title();
					// String html = doc.html();
					if(doc.getElementById("app")!=null){
						Elements content = doc.getElementById("app").getAllElements();
						numberOfOptions = content.size();
						if(content.size()>6)
						{
							System.out.println("Passport renewal vacancy found!");

						}
						else{
							System.out.println("Available appointments are not for passport renewal");
						}
					}
					else{
						System.out.println("This date isn't open for appointments yet");
					}

					// for (Element opt : content) {
					// System.out.println(opt);
					//
					// }
					// System.out.println(content);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (numberOfOptions > 6) {
					sendNotificationMail("http://www.mofaex.gov.sy/istanbul-consulate/istanbul_system/test.php?var="
									+ format.format(myDate));
				}
				myDate = SYConsulateAlarm.addDays(myDate, 1);

			}

			try {
				System.out.println("suspending execution for 15 min");
				TimeUnit.MINUTES.sleep(15);
				System.out.println("continuuing...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
