package injection.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import injection.senders.MessageSender;

@Component
public class MagneticResonanceDevice implements MedicalDevice {
	private MessageSender messageSender;
	
	@Autowired
	public MagneticResonanceDevice(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@Override
	public void startExam() {
		System.out.println("Sending command to start magnetic resonance exam...");
		messageSender.sendMessage("test");
	}
}
