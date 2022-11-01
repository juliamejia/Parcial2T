package edu.eci.pdsw.samples.services;

import static com.google.inject.Guice.createInjector;

import java.util.Optional;

import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.mybatisimpl.MyBatisDAOPaciente;
import edu.eci.pdsw.samples.services.impl.ServiciosPacienteImpl;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Injector;



public class ServiciosPacientesFactory {

    private static ServiciosPacientesFactory instance = new ServiciosPacientesFactory();

    private static Optional<Injector> optInjector = Optional.empty();

    private Injector myBatisInjector(String env, String pathResource, JdbcHelper jdbcHelper) {
        return createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                setEnvironmentId(env);
                install(jdbcHelper);
                setClassPathResource(pathResource);

                bind(ServiciosPaciente.class).to(ServiciosPacienteImpl.class);
                bind(DaoPaciente.class).to(MyBatisDAOPaciente.class);
            }
        });
    }

    private ServiciosPacientesFactory() {
    }

    public ServiciosPaciente getServiciosPaciente(){
        if (!optInjector.isPresent()) {
            optInjector = Optional.of(myBatisInjector("development","mybatis-config.xml", JdbcHelper.MySQL));
        }

        return optInjector.get().getInstance(ServiciosPaciente.class);
    }


    public ServiciosPaciente getBlogServicesTesting(){
        if (!optInjector.isPresent()) {
            optInjector = Optional.of(myBatisInjector("test","mybatis-config-h2.xml", JdbcHelper.H2_FILE));
        }

        return optInjector.get().getInstance(ServiciosPaciente.class);
    }



    public static ServiciosPacientesFactory getInstance(){
        return instance;
    }

}

