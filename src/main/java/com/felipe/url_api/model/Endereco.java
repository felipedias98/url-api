package com.felipe.url_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@Entity(name = "urls")
public class Endereco {
    @Id // PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 2048)
    private String original_url;

    @Column(nullable = false, length = 20, unique = true)
    private String short_code;

    @Column(nullable = false)
    private Long acess_count = 0L;

    @Column()
    private LocalDateTime created_at;

    @Column()
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        this.created_at = agora;
        this.updated_at = agora;

        if (this.acess_count == null) {
            this.acess_count = 0L;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public Endereco() {

    }

    public Endereco(Long id, String original_url, String short_code, Long acess_count, LocalDateTime created_at,
            LocalDateTime updated_at) {
        this.id = id;
        this.original_url = original_url;
        this.short_code = short_code;
        this.acess_count = acess_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
