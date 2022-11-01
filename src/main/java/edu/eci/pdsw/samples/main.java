package edu.eci.pdsw.samples;

import edu.eci.pdsw.samples.entities.TipoIdentificacion;
import edu.eci.pdsw.samples.services.ExcepcionServiciosSuscripciones;
import edu.eci.pdsw.samples.services.ServiciosPaciente;
import edu.eci.pdsw.samples.services.ServiciosPacientesFactory;

public class main {
    public static void main(String[] args) throws ExcepcionServiciosSuscripciones {
        ServiciosPaciente ser = ServiciosPacientesFactory.getInstance().getServiciosPaciente();
        System.out.println(ser.consultarPacientes());
        System.out.println(ser.consultarPacientesPorId(549541321, TipoIdentificacion.TI));
    }
}
