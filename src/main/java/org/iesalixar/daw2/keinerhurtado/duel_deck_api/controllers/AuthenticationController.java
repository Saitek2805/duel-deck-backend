package org.iesalixar.daw2.keinerhurtado.duel_deck_api.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.AuthRequestDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.dtos.AuthResponseDTO;
import org.iesalixar.daw2.keinerhurtado.duel_deck_api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager; // Maneja la lógica de autenticación

    @Autowired
    private JwtUtil jwtUtil; // Utilidad personalizada para manejar tokens JWT

    /**
     * Endpoint para autenticar usuarios.
     * Este método valida las credenciales proporcionadas y, si son correctas,
     * genera un token JWT que incluye información del usuario y sus roles.
     *
     * @param authRequest Un objeto {@link AuthRequestDTO} que contiene el nombre de usuario y la contraseña.
     * @return Una respuesta HTTP con un token JWT en caso de éxito o un error en caso de fallo.
     */
    @Operation(summary = "Autenticación de usuario", description = "Autentica al usuario con las credenciales proporcionadas (nombre de usuario y contraseña) y retorna un token JWT si la autenticación es exitosa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, se devuelve el token JWT",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida, el nombre de usuario y la contraseña son obligatorios"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas, nombre de usuario o contraseña incorrectos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            // Validar datos de entrada (opcional si no usas validación adicional en DTO)
            if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario y la contraseña son obligatorios."));
            }
            // Intenta autenticar al usuario con las credenciales proporcionadas
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            // Obtiene el nombre de usuario autenticado
            String username = authentication.getName();
            // Extrae los roles del usuario autenticado desde las autoridades asignadas
            List<String> roles = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority()) // Convierte cada autoridad en su representación de texto
                    .toList();
            // Genera un token JWT para el usuario autenticado, incluyendo sus roles
            String token = jwtUtil.generateToken(username, roles);
            // Retorna una respuesta con el token JWT y un mensaje de éxito
            return ResponseEntity.ok(new AuthResponseDTO(token, "Authentication successful"));
        } catch (BadCredentialsException e) {
            // Manejo de credenciales inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(null, "Credenciales inválidas. Por favor, verifica tus datos."));
        } catch (Exception e) {
            // Manejo de cualquier otro error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Ocurrió un error inesperado. Por favor, inténtalo de nuevo más tarde."));
        }
    }

    /**
     * Maneja excepciones no controladas que puedan ocurrir en el controlador.
     *
     * @param e La excepción lanzada.
     * @return Una respuesta HTTP con el mensaje de error y el estado HTTP correspondiente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponseDTO(null, "Ocurrió un error inesperado: " + e.getMessage()));
    }
}