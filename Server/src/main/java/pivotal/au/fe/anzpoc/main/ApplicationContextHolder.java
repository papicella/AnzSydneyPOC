package pivotal.au.fe.anzpoc.main;

import com.gemstone.gemfire.cache.Declarable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class ApplicationContextHolder implements Declarable
{
    private static ClassPathXmlApplicationContext applicationContext;

    public static ClassPathXmlApplicationContext getInstance()
    {
        if(applicationContext == null)
        {
            applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        }
        return applicationContext;
    }

    public static ApplicationContext getInstance(String contextLocation)
    {
        if(applicationContext == null)
        {
            applicationContext = new ClassPathXmlApplicationContext(contextLocation);
        }
        return applicationContext;
    }

    @Override
    public void init(Properties properties) {
        getInstance();
    }
}
