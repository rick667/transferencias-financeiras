package com.example.demo;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {
    
    @Autowired
    private TransferenciaRepository repository;

    public Transferencia agendarTransferencia(Transferencia transferencia) {
        long dias = ChronoUnit.DAYS.between(transferencia.getDataAgendamento(), transferencia.getDataTransferencia());
        BigDecimal taxa = calcularTaxa(dias, transferencia.getValor());

        if (taxa.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa inválida para a data de transferência escolhida.");
        }

        transferencia.setTaxa(taxa);
        return repository.save(transferencia);
    }

    private BigDecimal calcularTaxa(long dias, BigDecimal valor) {
        if (dias == 0) return valor.multiply(new BigDecimal("0.025")).add(new BigDecimal("3"));
        if (dias >= 1 && dias <= 10) return new BigDecimal("12");
        if (dias >= 11 && dias <= 20) return valor.multiply(new BigDecimal("0.082"));
        if (dias >= 21 && dias <= 30) return valor.multiply(new BigDecimal("0.069"));
        if (dias >= 31 && dias <= 40) return valor.multiply(new BigDecimal("0.047"));
        if (dias >= 41 && dias <= 50) return valor.multiply(new BigDecimal("0.017"));
        return new BigDecimal("-1"); // Indica erro
    }

    public List<Transferencia> listarAgendamentos() {
        return repository.findAll();
    }
}