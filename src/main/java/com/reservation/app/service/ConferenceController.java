package com.reservation.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.reservation.app.sesion.Sesion;
import com.reservation.app.usuario.Usuario;
import com.reservation.app.registroasistencia.RegistroAsistencia;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;




@RestController
@RequestMapping("/api/conferencia")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    // Inyección de servicios
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    // Endpoints para Sesion
    @PostMapping("/sesiones")
    public Sesion crearSesion(@RequestBody Sesion sesion) {
        return conferenceService.guardarSesion(sesion);
    }

    @GetMapping("/sesiones/{id}")
    public Sesion obtenerSesion(@PathVariable Long id) {
        return conferenceService.obtenerSesionPorId(id);
    }

    @PutMapping("/sesiones/{id}")
    public Sesion actualizarSesion(@PathVariable Long id, @RequestBody Sesion sesion) {
        sesion.setId(id);
        return conferenceService.actualizarSesion(sesion);
    }

    @DeleteMapping("/sesiones/{id}")
    public void eliminarSesion(@PathVariable Long id) {
        conferenceService.eliminarSesion(id);
    }

    // Endpoints para RegistroAsistencia
    @PostMapping("/registros")
    public RegistroAsistencia crearRegistroAsistencia(@RequestBody RegistroAsistencia registroAsistencia) {
        Usuario usuario = registroAsistencia.getUsuario();
        Sesion sesion = registroAsistencia.getSesion();

        conferenceService.validarRegistroUsuario(usuario, sesion);
        return conferenceService.guardarRegistroAsistencia(registroAsistencia);
    }

    @GetMapping("/registros/{id}")
    public RegistroAsistencia obtenerRegistroAsistencia(@PathVariable Long id) {
        return conferenceService.obtenerRegistroAsistenciaPorId(id);
    }

    @PutMapping("/registros/{id}")
    public RegistroAsistencia actualizarRegistroAsistencia(@PathVariable Long id, @RequestBody RegistroAsistencia registroAsistencia) {
        registroAsistencia.setId(id);
        return conferenceService.actualizarRegistroAsistencia(registroAsistencia);
    }

    @DeleteMapping("/registros/{id}")
    public void eliminarRegistroAsistencia(@PathVariable Long id) {
        conferenceService.eliminarRegistroAsistencia(id);
    }

    // Endpoint para validar el token
@RestController
@RequestMapping("/api/auth")

public class AuthController {

   
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        // Validar credenciales
        if (usuario.getCorreoElectronico().equals("admin") && usuario.getContrasena().equals("admin")) {
            // Generar token
            byte[] key = "mySecretKey".getBytes();
            String token = Jwts.builder()
                    .setSubject(usuario.getCorreoElectronico())
                    .signWith(null, Keys.hmacShaKeyFor(key))
                    .compact();
            return token;
        } else {
            return "Credenciales incorrectas";
        }
    }

    
}

}
    