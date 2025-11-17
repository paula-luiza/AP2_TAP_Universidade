package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Situacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="inscricao")
public class InscricaoDTO {

    private int idAluno;
    private int idTurma;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private float avaliacao1;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private float avaliacao2;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer numFaltas;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String situacao;
}