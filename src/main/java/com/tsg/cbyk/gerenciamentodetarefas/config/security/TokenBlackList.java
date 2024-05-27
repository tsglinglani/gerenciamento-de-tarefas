package com.tsg.cbyk.gerenciamentodetarefas.config.security;

import com.tsg.cbyk.gerenciamentodetarefas.model.BlackListToken;

public interface TokenBlackList {
    void salvarTokenBlackList(String token);
    BlackListToken getTokenBlacklisted(String token);
}
