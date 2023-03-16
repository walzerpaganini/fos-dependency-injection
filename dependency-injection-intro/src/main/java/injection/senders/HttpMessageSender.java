package injection.senders;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Supponiamo che la maggior parte dei dispositivi medici con la quale la nostra applicazione
 * dovrà comunicare sia in grado di ricevere messaggi via Wi-Fi con protocollo HTTP. In questo
 * caso possiamo annotare questo bean come @Primary: esso verrà iniettato di default in qualunque
 * altro bean che dipenda dall'interfaccia MessageSender.
 * 
 * Se in alcuni casi volessimo invece iniettare un'altra implementazione di MessageSender, dovremmo
 * indicare esplicitamente il nome del bean desiderato.
 * 
 * (vedi UrineAnalysisDevice.java)
 */

@Component("http-sender")
@Primary
public class HttpMessageSender implements MessageSender {

	@Override
	public void sendMessage(String msg) {
		System.out.println("Sending message via HTTP: " + msg);
		// ...
	}

}
