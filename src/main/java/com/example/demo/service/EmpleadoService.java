package com.example.demo.service;

import com.example.demo.entity.Empleado;
import com.example.demo.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> obtenerEmpleadosActivos() {
        return empleadoRepository.findByActivo(true);
    }

    public List<Empleado> obtenerEmpleadosInactivos() {
        return empleadoRepository.findByActivo(false);
    }

    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public Empleado obtenerEmpleadoPorDocumento(String documento) {
        return empleadoRepository.findByDocumentoIdentidad(documento);
    }

    public Empleado obtenerEmpleadoPorEmail(String email) {
        return empleadoRepository.findByEmail(email);
    }

    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Long id, Empleado empleado) {
        Empleado empleadoExistente = obtenerEmpleadoPorId(id);
        if (empleadoExistente != null) {
            empleado.setId(empleadoExistente.getId());
            return empleadoRepository.save(empleado);
        }
        return null;
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}