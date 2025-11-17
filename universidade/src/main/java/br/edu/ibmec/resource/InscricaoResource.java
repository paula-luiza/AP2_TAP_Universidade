package br.edu.ibmec.resource; // Garanta que o "package" está correto

// 1. Imports do Spring Boot e do seu projeto
import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/inscricao")
public class InscricaoResource {


    @Autowired
    private InscricaoService inscricaoService;


    @GetMapping("/{matricula}/{codigo}/{ano}/{semestre}")
    public ResponseEntity<InscricaoDTO> buscarInscricao(
            @PathVariable int matricula,
            @PathVariable String codigo,
            @PathVariable int ano,
            @PathVariable int semestre) {
        try {

            InscricaoDTO inscricaoDTO = inscricaoService.buscarInscricao(matricula, codigo, ano, semestre);
            return ResponseEntity.ok(inscricaoDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<Collection<Inscricao>> listarInscricoes() {
        try {
            Collection<Inscricao> inscricoes = inscricaoService.listarInscricoes();
            return ResponseEntity.ok(inscricoes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<InscricaoDTO> cadastrarInscricao(@RequestBody InscricaoDTO inscricaoDTO) {
        try {
            inscricaoService.cadastrarInscricao(inscricaoDTO);
            return ResponseEntity.created(null).body(inscricaoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<InscricaoDTO> alterarInscricao(@RequestBody InscricaoDTO inscricaoDTO) {
        try {
            inscricaoService.alterarInscricao(inscricaoDTO);
            return ResponseEntity.ok(inscricaoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{matricula}/{codigo}/{ano}/{semestre}")
    public ResponseEntity<Void> removerInscricao(
            @PathVariable int matricula,
            @PathVariable String codigo,
            @PathVariable int ano,
            @PathVariable int semestre) {
        try {
            inscricaoService.removerInscricao(matricula, codigo, ano, semestre);
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}