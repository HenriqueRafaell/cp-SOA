package br.com.fiap.autoescola.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "instrucoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Instrutor instrutor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private boolean ativa = true;

    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    private LocalDateTime dataCancelamento;
}
