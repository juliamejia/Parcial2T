package edu.eci.pdsw.samples.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.mybatisimpl.MyBatisDAOPaciente;
import edu.eci.pdsw.samples.services.ServiciosPaciente;
import edu.eci.pdsw.samples.services.impl.ServiciosPacienteImpl;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class GuiceContextListener {
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(Injector.class.getName());
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Injector injector = Guice.createInjector(new XMLMyBatisModule() {
                 @Override
                 protected void initialize() {

                     install(JdbcHelper.MySQL);

                     setEnvironmentId("development");

                     setClassPathResource("mybatis-config.xml");

                     // Blog
                     bind(ServiciosPaciente.class).to(ServiciosPacienteImpl.class);

                     // Users
                     bind(DaoPaciente.class).to(MyBatisDAOPaciente.class);
                 }
                                                 }

        );

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(Injector.class.getName(), injector);
    }
}
