package injection.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import injection.senders.MessageSender;

/**
 * L'annotazione @Component indica a Spring che vogliamo che crei un'istanza di questa classe
 * da inserire nell'ApplicationContext.
 */

@Component
public class BloodAnalysisDevice implements MedicalDevice {
	
	private MessageSender messageSender;
	
	/**
	 * L'annotazione @Autowired indica a Spring che deve usare questo costruttore per istanziare
	 * il bean. Spring si accorgerà che la classe BloodAnalysisDevice ha bisogno di un oggetto che
	 * implementi l'interfaccia MessageSender per funzionare: se nell'ApplicationContext esiste un
	 * bean di questo tipo, esso verrà iniettato automaticamente come parametro del costruttore.
	 * 
	 * Se ci sono più bean che implementano l'interfaccia MessageSender e non indichiamo esplicitamente
	 * il nome del bean che vogliamo iniettare, Spring cercherà di iniettare l'implementazione annotata
	 * con @Primary, se presente, oppure inietterà il primo bean compatibile che troverà. In questo caso
	 * viene iniettato un bean di classe HttpMessageSender.
	 * 
	 * (vedi HttpMessageSender.java)
	 */
	
	@Autowired
	public BloodAnalysisDevice(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public void startExam() {
		System.out.println("Sending command to start blood analysis...");
		messageSender.sendMessage("test");
	}
}
