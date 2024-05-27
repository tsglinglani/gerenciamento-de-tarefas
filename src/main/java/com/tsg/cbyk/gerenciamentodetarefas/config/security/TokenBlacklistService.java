package com.tsg.cbyk.gerenciamentodetarefas.config.security;

import com.tsg.cbyk.gerenciamentodetarefas.model.BlackListToken;
import com.tsg.cbyk.gerenciamentodetarefas.repository.BlackListTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService implements TokenBlackList {

    private final BlackListTokenRepository blackListTokenRepository;

    public TokenBlacklistService(BlackListTokenRepository blackListTokenRepository) {
        this.blackListTokenRepository = blackListTokenRepository;
    }

    @Override
    @Transactional
    public void salvarTokenBlackList(String token) {
        var tokenDelete = new BlackListToken.BlackListTokenBuilder()
                .token(token)
                .build();

        blackListTokenRepository.save(tokenDelete);
    }

    @Override
    public BlackListToken getTokenBlacklisted(String token) {
        return blackListTokenRepository.findBlackListTokenByToken(token);
    }
}
