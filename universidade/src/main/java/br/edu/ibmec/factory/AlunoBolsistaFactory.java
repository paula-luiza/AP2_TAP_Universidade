package br.edu.ibmec.factory;

import br.edu.ibmec.dto.AlunoDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.TipoAluno;
import org.springframework.stereotype.Component;

import static br.edu.ibmec.service.AlunoService.getData;

@Component("FACTORY_BOLSISTA")
public class AlunoBolsistaFactory implements AlunoFactory {

        @Override
        public Aluno criarAluno(AlunoDTO dto) {
            Aluno aluno = new Aluno();

            aluno.setNome(dto.getNome());
            aluno.setMatricula(dto.getMatricula());
            getData(dto.getDtNascimento().toString());
            aluno.setIdade(dto.getIdade());
            aluno.setMatriculaAtiva(true);
            getData(dto.getEstadoCivilDTO().toString());
            aluno.setTelefones(dto.getTelefones());
            aluno.setTipoAluno(TipoAluno.bolsista);

            return aluno;
        }
}
