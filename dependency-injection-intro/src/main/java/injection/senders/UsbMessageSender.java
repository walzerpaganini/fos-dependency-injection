package injection.senders;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Il nome di un bean può essere specificato come parametro dell'annotazione @Component. Se non
 * indichiamo un nome esplicito, verrà usato il nome della class (ma con l'iniziale minuscola).
 */

@Component("usb-sender")
public class UsbMessageSender implements MessageSender {

	private DateFormat dateFormat;
	
	/**
	 * In questo caso abbiamo anche una dipendenza opzionale: se Spring troverà un bean di tipo
	 * DateFormat, lo inietterà dopo la creazione del bean di tipo UsbMessageSender, usando questo
	 * setter annotato con @Autowired (di default, il parametro "required" ha valore "true").
	 * 
	 * (vedi ExternalBeans.java)
	 */
	
	@Autowired(required=false)
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Override
	public void sendMessage(String msg) {
		System.out.println("Sending message via USB port: " + msg);
		
		// ...

		if(dateFormat != null) {
			Date now = new Date();
			System.out.println("Message sent at: " + dateFormat.format(now));
		}
	}
}
