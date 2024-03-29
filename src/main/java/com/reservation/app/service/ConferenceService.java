package com.reservation.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.app.registroasistencia.RegistroAsistencia;
import com.reservation.app.registroasistencia.RegistroAsistenciaRepository;
import com.reservation.app.sesion.Sesion;
import com.reservation.app.sesion.SesionRepository;
import com.reservation.app.usuario.Usuario;
import com.reservation.app.usuario.UsuarioRepository;
import com.reservation.app.exceptions.UsuarioYaRegistradoException;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class ConferenceService {
    // Inyección de repositorios

    @Autowired
    private final SesionRepository sesionRepository;

    
    @Autowired
    public ConferenceService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
        this.registroAsistenciaRepository = null;
        this.usuarioRepository = null;
    }

    @Autowired
    private final RegistroAsistenciaRepository registroAsistenciaRepository;

    //@Autowired
    public ConferenceService(RegistroAsistenciaRepository registroAsistenciaRepository) {
        this.sesionRepository = null;
        this.registroAsistenciaRepository = registroAsistenciaRepository;
        this.usuarioRepository = null;
    }

    @Autowired
    private final UsuarioRepository usuarioRepository;

    //@Autowired
    public ConferenceService(UsuarioRepository usuarioRepository) {
        this.sesionRepository = null;
        this.registroAsistenciaRepository = null;
        this.usuarioRepository = usuarioRepository;
    }

    public void validarRegistroUsuario(Usuario usuario, Sesion sesion) {
        // Validar si el usuario ya está registrado en una conferencia previa
        List<RegistroAsistencia> registrosPrevios = registroAsistenciaRepository.findByUsuario(usuario);
        for (RegistroAsistencia registro : registrosPrevios) {
            if (registro.getSesion().getFechaHora().isEqual(sesion.getFechaHora())) {
                throw new UsuarioYaRegistradoException("El usuario ya está registrado en una conferencia a la misma hora.");
            }
        }

        // Validar si el usuario está registrado en otra conferencia en el mismo horario
        List<RegistroAsistencia> registrosEnHorario = registroAsistenciaRepository.findBySesion(sesion);
        for (RegistroAsistencia registro : registrosEnHorario) {
            if (registro.getUsuario().equals(usuario)) {
                throw new UsuarioYaRegistradoException("El usuario ya está registrado en otra conferencia a la misma hora.");
            }
        }
    }

    // Método para guardar una sesión
    //@SuppressWarnings("null")
    public Sesion guardarSesion(Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    // Método para obtener una sesión por su ID
    //@SuppressWarnings("null")
    public Sesion obtenerSesionPorId(Long id) {
        return sesionRepository.findById(id).orElse(null);
    }

    // Método para actualizar una sesión
    //@SuppressWarnings("null")
    public Sesion actualizarSesion(Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    // Método para eliminar una sesión
    //@SuppressWarnings("null")
    public void eliminarSesion(Long id) {
        sesionRepository.deleteById(id);
    }

    // Método para guardar un registro de asistencia
    //@SuppressWarnings("null")
    public RegistroAsistencia guardarRegistroAsistencia(RegistroAsistencia registroAsistencia) {
        return registroAsistenciaRepository.save(registroAsistencia);
    }

    // Método para obtener un registro de asistencia por su ID
    //@SuppressWarnings("null")
    public RegistroAsistencia obtenerRegistroAsistenciaPorId(Long id) {
        return registroAsistenciaRepository.findById(id).orElse(null);
    }

    // Método para actualizar un registro de asistencia
    //@SuppressWarnings("null")
    public RegistroAsistencia actualizarRegistroAsistencia(RegistroAsistencia registroAsistencia) {
        return registroAsistenciaRepository.save(registroAsistencia);
    }

    // Método para eliminar un registro de asistencia
    //@SuppressWarnings("null")
    public void eliminarRegistroAsistencia(Long id) {
        registroAsistenciaRepository.deleteById(id);
    }

     // Estructura de datos: Lista de usuarios
     private List<Usuario> userList = new ArrayList<>();

     // Algoritmo de ordenamiento: Ordenar usuarios por nombre
     public List<Usuario> getUsersSortedByName() {
         userList.addAll(usuarioRepository.findAll());
         Collections.sort(userList, (u1, u2) -> u1.getNombre().compareTo(u2.getNombre()));
         return userList;
     }
 
     // Algoritmo de búsqueda: Buscar usuario por correo electrónico
     public Usuario findUserByEmail(String email) {
         for (Usuario user : userList) {
             if (user.getCorreoElectronico().equals(email)) {
                 return user;
             }
         }
         return null;
     }

    // Método para guardar un usuario
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Método para actualizar un usuario
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    

}
