import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.edu.cesufoz.aulaejb.sessionbeans.singleton.ISingletonSession;
import br.edu.cesufoz.aulaejb.sessionbeans.singleton.SingletonSession;

public class MainSingleton {
	public static void main(String[] args) throws NamingException {
		
		final Properties properties = new Properties();
		//necessario para o JBoss remoting 
		properties.put("jboss.naming.client.ejb.context", true);
		//possibilita o acesso ao namespace "ejb:"
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		//factory do contexto JNDI
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		//endereco do servidor
		properties.put(Context.PROVIDER_URL, "remote://localhost:4447");
		//credenciais para acesso
		properties.put(Context.SECURITY_PRINCIPAL, "app");
		properties.put(Context.SECURITY_CREDENTIALS, "app123");
		final Context context = new InitialContext(properties);
		
		//EAR
        final String appName = "";
        //Modulo do EAR (ejb, war, jar)
        final String moduleName = "ejb-sessionbeans.ejb";
        // JBoss AS7 possibilita adicionar nomes customizados.
        final String distinctName = "";
        //Nome da implementacao EJB
        final String beanName = SingletonSession.class.getSimpleName();
        //Nome completo da interface remota
        final String viewClassName = ISingletonSession.class.getName();
        final String componentName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
        
        //faz o lookup
        ISingletonSession singletonSession = (ISingletonSession) context.lookup(componentName);
        singletonSession.setTemperatura(10);
        System.out.println("Temp:"+ singletonSession.getTemperatura() );
        
        singletonSession = (ISingletonSession) context.lookup(componentName);
        System.out.println("Temp:"+ singletonSession.getTemperatura() );
        singletonSession.setTemperatura(20);
        System.out.println("Temp:"+ singletonSession.getTemperatura() );
        
        singletonSession = (ISingletonSession) context.lookup(componentName);
        System.out.println("Temp:"+ singletonSession.getTemperatura() );
        singletonSession.setTemperatura(50);
        System.out.println("Temp:"+ singletonSession.getTemperatura() );
        
        singletonSession = (ISingletonSession) context.lookup(componentName);
        System.out.println("Temp:"+ singletonSession.getTemperatura() );
	}
}
