package com.victor.usuario.controller;

import com.victor.usuario.business.UsuarioService;
import com.victor.usuario.business.dto.EnderecoDTO;
import com.victor.usuario.business.dto.TelefoneDTO;
import com.victor.usuario.business.dto.UsuarioDTO;
import com.victor.usuario.infrastructure.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(
            @RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()
                )
        );

        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }



    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(
            @RequestParam("email") String email) {

        return ResponseEntity.ok(
                usuarioService.buscarUsuarioPorEmail(email)
        );
    }

    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUsuarioPorEmail(
            @PathVariable String email) {

        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(
            @RequestBody UsuarioDTO dto,
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                usuarioService.atualizaDadosUsuario(token, dto)
        );
    }

    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(
            @RequestBody EnderecoDTO dto,
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                usuarioService.cadastraEndereco(token, dto)
        );
    }

    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(
            @RequestBody TelefoneDTO dto,
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                usuarioService.cadastraTelefone(token, dto)
        );
    }

    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualiaEndereco(
            @RequestBody EnderecoDTO dto,
            @RequestParam("id") Long id) {

        return ResponseEntity.ok(
                usuarioService.atualizaEndereco(id, dto)
        );
    }

    @Operation(
            security = @SecurityRequirement(name = "bearer-key")
    )
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(
            @RequestBody TelefoneDTO dto,
            @RequestParam("id") Long id) {

        return ResponseEntity.ok(
                usuarioService.atualizaTelefone(id, dto)
        );
    }
}