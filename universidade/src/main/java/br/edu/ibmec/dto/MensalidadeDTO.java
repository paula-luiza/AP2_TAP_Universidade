package br.edu.ibmec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensalidadeDTO {
    private String nomeAluno;
    private String curso;
    private long disciplinasMatriculadas;
    private Double valorPorDisciplina;
    private Double valorBruto;
    private String descontoAplicado;
    private Double valorFinal;
}