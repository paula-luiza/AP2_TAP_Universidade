package br.edu.ibmec.resource; // Certifique-se que o "package" está correto

// 1. Imports MUDARAM de "javax.ws.rs" para "org.springframework..."
import br.edu.ibmec.dto.DisciplinaDTO;
import br.edu.ibmec.entity.Disciplina;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController

@RequestMapping("/api/disciplinas")
public class DisciplinaResource {

    @Autowired
    private DisciplinaService disciplinaService;


    @GetMapping("/{codigo}")
    public ResponseEntity<DisciplinaDTO> buscarDisciplina(@PathVariable("codigo") int codigo) {
        try {
            DisciplinaDTO disciplinaDTO = disciplinaService.buscarDisciplina(codigo);

            return ResponseEntity.ok(disciplinaDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<Collection<Disciplina>> listarDisciplinas() {
        try {
            Collection<Disciplina> disciplinas = disciplinaService.listarDisciplinas();
            return ResponseEntity.ok(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping
    public ResponseEntity<DisciplinaDTO> cadastrarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            disciplinaService.cadastrarDisciplina(disciplinaDTO);
            return ResponseEntity.created(null).body(disciplinaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<DisciplinaDTO> alterarDisciplina(@PathVariable("codigo") int codigo, @RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            disciplinaDTO.setCodigo(codigo);
            disciplinaService.alterarDisciplina(disciplinaDTO);
            return ResponseEntity.ok(disciplinaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> removerDisciplina(@PathVariable("codigo") int codigo) {
        try {
            disciplinaService.removerDisciplina(codigo);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}