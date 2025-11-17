package br.edu.ibmec.resource;

import br.edu.ibmec.dto.ProfessorDTO;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/professor")
public class ProfessorResource {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO> buscarProfessor(@PathVariable String cpf) {
        try {
            ProfessorDTO professorDTO = professorService.buscarProfessor(cpf);
            return ResponseEntity.ok(professorDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Professor>> listarProfessores() {
        try {
            Collection<Professor> professores = professorService.listarProfessores();
            return ResponseEntity.ok(professores);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> cadastrarProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            professorService.cadastrarProfessor(professorDTO);
            return ResponseEntity.created(null).body(professorDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ProfessorDTO> alterarProfessor(
            @PathVariable String cpf,
            @RequestBody ProfessorDTO professorDTO) {
        try {
            professorDTO.setCpf(cpf); // Garante que o CPF da URL seja usado
            professorService.alterarProfessor(professorDTO);
            return ResponseEntity.ok(professorDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> removerProfessor(@PathVariable String cpf) {
        try {
            professorService.removerProfessor(cpf);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}