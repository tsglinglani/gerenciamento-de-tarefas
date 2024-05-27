package com.tsg.cbyk.gerenciamentodetarefas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BlackListToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blackListId;

    private String token;

    public BlackListToken() {
    }

    private BlackListToken(BlackListTokenBuilder blackListTokenBuilder) {
        this.token = blackListTokenBuilder.token;
    }

    public long getBlackListId() {
        return blackListId;
    }

    public void setBlackListId(long blackListId) {
        this.blackListId = blackListId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class BlackListTokenBuilder {
        private String token;

        public BlackListTokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public BlackListToken build() {
            return new BlackListToken(this);
        }
    }
}
