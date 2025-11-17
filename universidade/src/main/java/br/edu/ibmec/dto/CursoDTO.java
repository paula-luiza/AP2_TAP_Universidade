package br.edu.ibmec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="curso")
public class CursoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer codigo;

	private String nome;

    public CursoDTO(String nome) {
        this.nome = nome;
    }

    @Override
	public String toString() {
		return "CursoDTO [codigo=" + codigo + ", nome=" + nome + "]";
	}

}
