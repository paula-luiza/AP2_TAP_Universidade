package br.edu.ibmec.resource;

import br.edu.ibmec.dto.TurmaDTO;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/turmas")
public class TurmaResource {

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/{codigo}/{ano}/{semestre}")
    public ResponseEntity<TurmaDTO> buscarTurma(
            @PathVariable String codigo,
            @PathVariable int ano,
            @PathVariable int semestre) {
        try {
            TurmaDTO turmaDTO = turmaService.buscarTurma(codigo, ano, semestre);
            return ResponseEntity.ok(turmaDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Turma>> listarTurmas() {
        try {
            Collection<Turma> turmas = turmaService.listarTurmas();
            return ResponseEntity.ok(turmas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> cadastrarTurma(@RequestBody TurmaDTO turmaDTO) {
        try {
            turmaService.cadastrarTurma(turmaDTO);
            return ResponseEntity.created(null).body(turmaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{codigo}/{ano}/{semestre}")
    public ResponseEntity<TurmaDTO> alterarTurma(
            @PathVariable String codigo,
            @PathVariable int ano,
            @PathVariable int semestre,
            @RequestBody TurmaDTO turmaDTO) {
        try {
            turmaService.alterarTurma(turmaDTO);
            return ResponseEntity.ok(turmaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{codigo}/{ano}/{semestre}")
    public ResponseEntity<Void> removerTurma(
            @PathVariable String codigo,
            @PathVariable int ano,
            @PathVariable int semestre) {
        try {
            turmaService.removerTurma(codigo, ano, semestre);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}