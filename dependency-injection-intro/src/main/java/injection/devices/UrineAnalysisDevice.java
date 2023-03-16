package injection.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import injection.senders.MessageSender;

@Component
public class UrineAnalysisDevice implements MedicalDevice {
	
	private MessageSender messageSender;
	
	/**
	 * Supponiamo che il dispositivo per le analisi delle urine comunichi tramite cavo USB invece
	 * che tramite protocollo HTTP come gli altri dispositivi. In questo caso possiamo usare
	 * l'annotazione @Qualifier per indicare il nome del bean che vogliamo iniettare.
	 * 
	 * (vedi UsbMessageSender.java)
	 */
	
	@Autowired
	public UrineAnalysisDevice(@Qualifier("usb-sender") MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@Override
	public void startExam() {
		System.out.println("Sending command to start urine analysis...");
		messageSender.sendMessage("test");
	}
}
