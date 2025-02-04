package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService service;

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody Transferencia transferencia) {
        try {
            Transferencia novaTransferencia = service.agendarTransferencia(transferencia);
            return ResponseEntity.ok(novaTransferencia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Transferencia>> listar() {
        return ResponseEntity.ok(service.listarAgendamentos());
    }
}
