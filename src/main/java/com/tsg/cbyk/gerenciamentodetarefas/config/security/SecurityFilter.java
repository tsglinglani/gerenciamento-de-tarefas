package com.tsg.cbyk.gerenciamentodetarefas.config.security;

import com.tsg.cbyk.gerenciamentodetarefas.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
// usando filtro do Spring ao invés do java Filter

    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final TokenBlackList tokenBlackList;

    public SecurityFilter(TokenService tokenService,
                          UsuarioService usuarioService,
                          TokenBlackList tokenBlackList) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.tokenBlackList = tokenBlackList;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarTokenJWT(request);
        var tokenBlacklisted = tokenBlackList.getTokenBlacklisted(tokenJWT);

        if (tokenJWT != null && tokenBlacklisted == null) {
            var subject = tokenService.getSubject(tokenJWT);
            var userDetails = usuarioService.findByEmail(subject);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarTokenJWT(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
