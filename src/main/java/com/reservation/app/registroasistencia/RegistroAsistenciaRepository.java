package com.reservation.app.registroasistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.app.sesion.Sesion;
import com.reservation.app.usuario.Usuario;
import java.util.List;

@Repository
public interface RegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Long> {
    // Métodos personalizados si es necesario
    //List<RegistroAsistencia> obtenerUsuarioPorId(Usuario usuario);
    //List<RegistroAsistencia> obtenerSesionPorId(Sesion sesion);
    List<RegistroAsistencia> findByUsuario(Usuario usuario);
    List<RegistroAsistencia> findBySesion(Sesion sesion);
}