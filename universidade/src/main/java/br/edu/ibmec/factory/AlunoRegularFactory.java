package br.edu.ibmec.factory;

import br.edu.ibmec.dto.AlunoDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.EstadoCivil;
import br.edu.ibmec.entity.TipoAluno; // Seu Enum
import org.springframework.stereotype.Component;

import static br.edu.ibmec.service.AlunoService.getData;

@Component("FACTORY_REGULAR")
public class AlunoRegularFactory implements AlunoFactory {

    @Override
    public Aluno criarAluno(AlunoDTO dto) {
        Aluno aluno = new Aluno();

        aluno.setNome(dto.getNome());
        aluno.setMatricula(dto.getMatricula());
        aluno.setDataNascimento(getData(dto.getDtNascimento().toString()));        aluno.setIdade(dto.getIdade());
        aluno.setMatriculaAtiva(true);
        aluno.setTelefones(dto.getTelefones());
        aluno.setTipoAluno(TipoAluno.regular);
        aluno.setEstadoCivil(
                EstadoCivil.valueOf(dto.getEstadoCivilDTO().toString())
        );

        return aluno;
    }
}