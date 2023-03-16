package injection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Se vogliamo che Spring istanzi dei bean appartenenti a classi a cui non possiamo aggiungere
 * l'annotazione @Component (es. perché si trovano in una libreria esterna), possiamo annotare
 * una classe come @Configuration, e scrivere al suo interno dei metodi annotati come @Bean che
 * istanzino esplicitamente gli oggetti richiesti.
 */

@Configuration
public class ExternalBeans {
	
	@Bean
	public DateFormat getDateFormat() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
}
