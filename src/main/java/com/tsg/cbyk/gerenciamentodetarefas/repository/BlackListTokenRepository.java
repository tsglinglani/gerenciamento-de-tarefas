package com.tsg.cbyk.gerenciamentodetarefas.repository;

import com.tsg.cbyk.gerenciamentodetarefas.model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {

    BlackListToken findBlackListTokenByToken(String token);
}
