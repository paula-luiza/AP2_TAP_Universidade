package br.edu.ibmec.resource;

import br.edu.ibmec.dto.MensalidadeDTO;
import br.edu.ibmec.service.MensalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensalidade")
public class MensalidadeResource {

    @Autowired
    private MensalidadeService mensalidadeService;

    @GetMapping("/{matricula}")
    public ResponseEntity<MensalidadeDTO> calcular(@PathVariable int matricula) {
        try {
            return ResponseEntity.ok(mensalidadeService.calcularMensalidade(matricula));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}