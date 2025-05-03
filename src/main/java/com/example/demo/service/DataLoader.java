// Parece que no funciona cuando ya existe al menos un registro insertado de obras civiles en la BD

package com.example.demo.service;

import com.example.demo.entity.ObraCivil;
import com.example.demo.entity.Permiso;
import com.example.demo.repository.ObraCivilRepository;
import com.example.demo.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ObraCivilRepository obraCivilRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public void run(String... args) throws Exception {
        /*if (obraCivilRepository.count() == 0) {
            // Ejemplo 1
            obraCivilRepository.save(new ObraCivil(
                    "Residencial Santa María",
                    "Descripción de ejemplo de un proyecto residencial.",
                    7500000.00, // costoEstimado
                    7100000.00, // costo
                    8000000.00, // presupuesto
                    900000.00,  // presupuestoDisponible
                    Date.valueOf("2023-01-15"),
                    Date.valueOf("2024-12-15"),
                    "En progreso",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.VIVIENDA,
                    "Av. Principal 123, Ciudad X",
                    -17.726357, //latitud
                    -63.179531, // longitud
                    "https://www.edrawsoft.com/templates/images/electrical-plan.png",
                    Date.valueOf("2024-12-15")
            ));

            // Ejemplo 2
            obraCivilRepository.save(new ObraCivil(
                    "Edificio Corporativo",
                    "Proyecto de construcción de un edificio corporativo de 10 pisos.",
                    15000000.00,
                    14000000.00,
                    16000000.00,
                    2000000.00,
                    Date.valueOf("2023-05-01"),
                    Date.valueOf("2026-05-01"),
                    "En construcción",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.COMERCIAL,
                    "Calle Ficticia 456, Zona Industrial",
                    -17.692702, //latitud
                    -63.144197, // longitud
                    "https://www.edrawsoft.com/templates/images/office-layout.png",
                    Date.valueOf("2026-05-01")
            ));

            // Ejemplo 3
            obraCivilRepository.save(new ObraCivil(
                    "Puente sobre el río X",
                    "Construcción de un puente para conectar dos ciudades.",
                    5000000.00,
                    4500000.00,
                    5200000.00,
                    700000.00,
                    Date.valueOf("2023-07-15"),
                    Date.valueOf("2025-07-15"),
                    "En progreso",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.INDUSTRIAL,
                    "Carretera Nacional, km 100",
                    -17.774436, //latitud
                    -63.107653, // longitud
                    "https://www.edrawsoft.com/templates/images/home-reflected-ceiling-plan.png",
                    Date.valueOf("2025-07-15")
            ));

            // Ejemplo 4
            obraCivilRepository.save(new ObraCivil(
                    "Centro Comercial Plaza Norte",
                    "Desarrollo de un centro comercial de 5 niveles.",
                    12000000.00,
                    11500000.00,
                    12500000.00,
                    1500000.00,
                    Date.valueOf("2024-02-01"),
                    Date.valueOf("2027-02-01"),
                    "Planificación",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.COMERCIAL,
                    "Avenida 12 de Octubre, Zona Norte",
                    -17.838316, //latitud
                    -63.123181, // longitud
                    "https://www.edrawsoft.com/templates/images/electrical-plan.png",
                    Date.valueOf("2027-02-01")
            ));

            // Ejemplo 5
            obraCivilRepository.save(new ObraCivil(
                    "Hospital General Sur",
                    "Construcción de un hospital de alta especialización.",
                    30000000.00,
                    29000000.00,
                    31000000.00,
                    4000000.00,
                    Date.valueOf("2023-09-10"),
                    Date.valueOf("2026-09-10"),
                    "En planificación",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.OTROS,
                    "Calle de la Salud 789, Sur de la Ciudad",
                    -17.867001, //latitud
                    -63.159342, // longitud
                    "https://www.edrawsoft.com/templates/images/office-layout.png",
                    Date.valueOf("2026-09-10")
            ));

            // Ejemplo 6
            obraCivilRepository.save(new ObraCivil(
                    "Autopista Central",
                    "Construcción de una autopista de 100 km.",
                    50000000.00,
                    48000000.00,
                    52000000.00,
                    6000000.00,
                    Date.valueOf("2023-06-01"),
                    Date.valueOf("2028-06-01"),
                    "En construcción",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.OTROS,
                    "Autopista Central, tramo 1",
                    -17.865037, //latitud
                    -63.203040, // longitud
                    "https://www.edrawsoft.com/templates/images/home-reflected-ceiling-plan.png",
                    Date.valueOf("2028-06-01")
            ));

            // Ejemplo 7
            obraCivilRepository.save(new ObraCivil(
                    "Planta de Energía Solar",
                    "Construcción de una planta fotovoltaica.",
                    10000000.00,
                    9500000.00,
                    10500000.00,
                    1000000.00,
                    Date.valueOf("2024-04-10"),
                    Date.valueOf("2027-04-10"),
                    "En planificación",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.INDUSTRIAL,
                    "Zona industrial, Parque Solar",
                    -17.841930, //latitud
                    -63.233385, // longitud
                    "https://www.edrawsoft.com/templates/images/electrical-plan.png",
                    Date.valueOf("2027-04-10")
            ));

            // Ejemplo 8//
            obraCivilRepository.save(new ObraCivil(
                    "Viviendas Sociales El Paraíso",
                    "Proyecto de construcción de viviendas para familias de bajos recursos.",
                    6000000.00,
                    5800000.00,
                    6500000.00,
                    800000.00,
                    Date.valueOf("2023-11-01"),
                    Date.valueOf("2025-11-01"),
                    "En construcción",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.VIVIENDA,
                    "Calle La Paz 123, El Paraíso",
                    -17.754218, //latitud
                    -63.226480, // longitud
                    "https://www.edrawsoft.com/templates/images/office-layout.png",
                    Date.valueOf("2025-11-01")
            ));

            // Ejemplo 9
            obraCivilRepository.save(new ObraCivil(
                    "Estadio Olímpico",
                    "Construcción de un estadio para competiciones internacionales.",
                    70000000.00,
                    68000000.00,
                    72000000.00,
                    10000000.00,
                    Date.valueOf("2023-03-20"),
                    Date.valueOf("2027-03-20"),
                    "En diseño",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.OTROS,
                    "Avenida Olímpica 789, Ciudad Deportiva",
                    -17.728778, //latitud
                    -63.217774, // longitud
                    "https://www.edrawsoft.com/templates/images/home-reflected-ceiling-plan.png",
                    Date.valueOf("2027-03-20")
            ));

            // Ejemplo 10
            obraCivilRepository.save(new ObraCivil(
                    "Red de Agua Potable",
                    "Construcción de una nueva red de distribución de agua en la ciudad.",
                    12000000.00,
                    11000000.00,
                    13000000.00,
                    1500000.00,
                    Date.valueOf("2023-08-01"),
                    Date.valueOf("2026-08-01"),
                    "En planificación",
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ObraCivil.TipoObra.OTROS,
                    "Zona centro de la ciudad",
                    -17.785936, //latitud
                    -63.215882, // longitud
                    "https://www.edrawsoft.com/templates/images/electrical-plan.png",
                    Date.valueOf("2026-08-01")
            ));

            System.out.println("Datos de ejemplo insertados en la base de datos.");
        }*/

        if (permisoRepository.count() == 0) {
            permisoRepository.save(new Permiso(
                    "Crear Usuario",
                    "Permite la creación de nuevos usuarios en el sistema"
            ));
            permisoRepository.save(new Permiso(
                    "Editar Usuario",
                    "Permite modificar los datos y configuraciones de un usuario existente"
            ));
            permisoRepository.save(new Permiso(
                    "Eliminar Usuario",
                    "Permite eliminar un usuario del sistema"
            ));
            permisoRepository.save(new Permiso(
                    "Crear Rol",
                    "Permite la creación de nuevos roles dentro del sistema"
            ));
            permisoRepository.save(new Permiso(
                    "Editar Rol",
                    "Permite modificar los permisos o detalles de un rol existente"
            ));
            permisoRepository.save(new Permiso(
                    "Eliminar Rol",
                    "Permite eliminar un rol del sistema"
            ));
            permisoRepository.save(new Permiso(
                    "Crear Permiso",
                    "Permite la creación de nuevos permisos dentro del sistema"
            ));
            permisoRepository.save(new Permiso(
                    "Editar Permiso",
                    "Permite modificar los permisos existentes para diferentes usuarios o roles"
            ));
            permisoRepository.save(new Permiso(
                    "Eliminar Permiso",
                    "Permite eliminar un permiso del sistema"
            ));

            System.out.println("Datos de permiso insertados en la base de datos.");
        }
    }
}
