package com.example.modulodocentes.controller;

import com.example.modulodocentes.model.Preferencia;
import com.example.modulodocentes.service.PreferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciaController {

    @Autowired
    private PreferenciaService preferenciaService;

    @PostMapping
    public ResponseEntity<?> createPreferencia(@RequestBody Preferencia preferencia) {
        try {
            Preferencia createdPreferencia = preferenciaService.createPreferencia(preferencia);
            return ResponseEntity.ok(createdPreferencia);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public List<Preferencia> getAllPreferencias() {
        return preferenciaService.getAllPreferencias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Preferencia> getPreferenciaById(@PathVariable String id) {
        return preferenciaService.getPreferenciaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Preferencia>> getPreferenciasByDocenteId(@PathVariable String docenteId) {
        List<Preferencia> preferencias = preferenciaService.getPreferenciasByDocenteId(docenteId);
        return ResponseEntity.ok(preferencias);
    }

    @GetMapping("/sede/{sede}")
    public ResponseEntity<List<Preferencia>> getPreferenciasBySede(@PathVariable String sede) {
        List<Preferencia> preferencias = preferenciaService.getPreferenciasBySede(sede);
        return ResponseEntity.ok(preferencias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePreferencia(@PathVariable String id, @RequestBody Preferencia preferenciaDetails) {
        try {
            Preferencia updatedPreferencia = preferenciaService.updatePreferencia(id, preferenciaDetails);
            return ResponseEntity.ok(new MessageResponse("Preferencia actualizada correctamente", updatedPreferencia));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePreferencia(@PathVariable String id) {
        try {
            preferenciaService.deletePreferencia(id);
            return ResponseEntity.ok(new MessageResponse("Preferencia eliminada correctamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }
}