package injection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import injection.devices.BloodAnalysisDevice;
import injection.devices.MagneticResonanceDevice;
import injection.devices.MedicalDevice;
import injection.devices.UrineAnalysisDevice;
import injection.senders.HttpMessageSender;
import injection.senders.UsbMessageSender;

@SpringBootApplication
public class DependencyInjectionApp {
	public static void main(String[] args) {
		dependencyInjectionBySpring();
		dependencyInjectionByHand();
	}
	
	private static void dependencyInjectionBySpring() {
		/**
		 * Il metodo SpringApplication.run() fa in modo che Spring ispezioni tutte le
		 * classi del package alla ricerca di annotazioni che gli indichino quali oggetti
		 * deve istanziare. Gli oggetti istanziati dal framework, che ne gestisce 
		 * automaticamente anche la dependency injection, sono chiamati "bean".
		 */
		
		ApplicationContext ctx = SpringApplication.run(DependencyInjectionApp.class);

		/**
		 * Il metodo ci restituisce un oggetto che implementa l'interfaccia ApplicationContext
		 * (il tipo specifico di context dipende dal tipo di applicazione, es. webapp). All'interno
		 * del context sono contenuti i bean istanziati da Spring, e possiamo usare diversi metodi
		 * per recuperarli.
		 * 
		 * Supponiamo di sviluppare un'applicazione per un ospedale. Vogliamo che l'applicazione invii
		 * messaggi ai vari dispositivi medici (es. analizzatore del sangue) per avviare gli esami in
		 * maniera automatica.
		 * 
		 * Alcuni dispositivi possono ricevere messaggi via Wi-Fi, comunicando con protocollo HTTP,
		 * mentre altri sono collegati al computer con un cavo USB: in altre parole, per comunicare con
		 * ciascun dispositivo è necessario adottare una strategia diversa.
		 * 
		 * Grazie a Spring, non abbiamo bisogno di creare e configurare manualmente gli oggetti necessari
		 * a comunicare con le macchine (es. decidendo se devono comunicare tramite HTTP o USB): scrivendo
		 * le classi in maniera appropriata, sarà il framework a farlo in automatico al posto nostro.
		 * 
		 * (vedi BloodAnalysisDevice.java)
		 */

		System.out.println("\n=== STARTING EXAMS ===\n");
		
		MedicalDevice bloodAnalysisDevice = ctx.getBean(BloodAnalysisDevice.class);
		MedicalDevice urineAnalysisDevice = ctx.getBean(UrineAnalysisDevice.class);
		MedicalDevice magneticResonanceDevice = ctx.getBean(MagneticResonanceDevice.class);
		
		bloodAnalysisDevice.startExam();
		urineAnalysisDevice.startExam();
		magneticResonanceDevice.startExam();
		
		/**
		 * Se ci sono più bean che implementano una stessa interfaccia e non indichiamo esplicitamente
		 * quale vogliamo, in certi casi Spring va in errore perché non sa come risolvere l'ambiguità.
		 */
		
		try {
			MedicalDevice genericDevice = ctx.getBean(MedicalDevice.class);
			genericDevice.startExam();
			
		} catch(NoSuchBeanDefinitionException e) {
			System.out.println(e.getMessage());
		}
				
		/**
		 * Un'altra cosa che si può fare è conoscere i nomi di tutti i bean creati da Spring.
		 * Notate che ci sono molti altri bean oltre a quelli definiti esplicitamente da noi:
		 * questi servono al funzionamento interno del framework, ma teoricamente potremmo
		 * usarli come dipendenze per i nostri bean (se sapessimo cosa farcene). Se fate lo
		 * stesso esperimento su una webapp, noterete tutta una serie di bean aggiuntivi
		 * palesemente legati al funzionamento del webserver e simili: questi bean sono
		 * definiti all'interno dello starter web di Spring Boot.
		 * 
		 * A proposito di questi meccanismi, potrebbe interessarvi approfondire per conto
		 * vostro le annotazioni @ComponentScan e @EnableAutoConfiguration, le quali sono
		 * automaticamente applicate quando si usa l'annotazione @SpringBootApplication.
		 */
		
		System.out.println("\n=== CHECKING ALL BEAN NAMES ===\n");

		for(String beanName : ctx.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}
	}
	
	
	
	/**
	 * Ricordate che non siamo obbligati a usare un framework come Spring per gestire la
	 * dependency injection: lo si può fare anche "a mano", istanziando e iniettando
	 * manualmente gli oggetti necessari al funzionamento dell'applicazione.
	 * 
	 * Un framework come Spring rende la gestione della dependency injection estremamente
	 * automatica: da un lato questo ci permette di scrivere meno codice e velocizzare lo
	 * sviluppo di un'applicazione.
	 * 
	 * D'altro canto, c'è chi sostiene che un'applicazione Spring possa diventare difficile
	 * da debuggare in caso di errori (specialmente per uno sviluppatore che non conosce bene
	 * il framework): in effetti, siccome molte operazioni vengono svolte da Spring in maniera
	 * "implicita" tramite l'uso della reflection, non è sempre possibile seguire il flusso
	 * logico dell'applicazione leggendo semplicemente la sequenza di istruzioni del codice.
	 */
	
	private static void dependencyInjectionByHand() {
		HttpMessageSender httpMessageSender = new HttpMessageSender();
		UsbMessageSender usbMessageSender = new UsbMessageSender();
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		usbMessageSender.setDateFormat(df);
		
		MedicalDevice bloodAnalysisDevice = new BloodAnalysisDevice(httpMessageSender);
		MedicalDevice urineAnalysisDevice = new UrineAnalysisDevice(usbMessageSender);
		MedicalDevice magneticResonanceDevice = new MagneticResonanceDevice(httpMessageSender);
		
		// ...
	}
}
