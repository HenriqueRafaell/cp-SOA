package br.com.fiap.autoescola.service;

import br.com.fiap.autoescola.dto.request.AtualizacaoAlunoRequest;
import br.com.fiap.autoescola.dto.request.CadastroAlunoRequest;
import br.com.fiap.autoescola.dto.response.AlunoResponse;
import br.com.fiap.autoescola.exception.RecursoNaoEncontradoException;
import br.com.fiap.autoescola.exception.RegraNegocioException;
import br.com.fiap.autoescola.model.Aluno;
import br.com.fiap.autoescola.model.Endereco;
import br.com.fiap.autoescola.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoResponse cadastrar(CadastroAlunoRequest request) {
        if (repository.existsByEmail(request.email()))
            throw new RegraNegocioException("E-mail já cadastrado.");
        if (repository.existsByCpf(request.cpf()))
            throw new RegraNegocioException("CPF já cadastrado.");

        var endereco = new Endereco(
                request.endereco().logradouro(),
                request.endereco().numero(),
                request.endereco().complemento(),
                request.endereco().bairro(),
                request.endereco().cidade(),
                request.endereco().uf(),
                request.endereco().cep()
        );

        var aluno = new Aluno(null, request.nome(), request.email(),
                request.telefone(), request.cpf(), endereco, true);

        return AlunoResponse.from(repository.save(aluno));
    }

    public Page<AlunoResponse> listar(int pagina) {
        return repository.findAllByAtivoTrueOrderByNomeAsc(PageRequest.of(pagina, 10))
                .map(AlunoResponse::from);
    }

    public AlunoResponse buscarPorId(Long id) {
        return AlunoResponse.from(getAluno(id));
    }

    public AlunoResponse atualizar(Long id, AtualizacaoAlunoRequest request) {
        var aluno = getAluno(id);

        if (request.nome() != null) aluno.setNome(request.nome());
        if (request.telefone() != null) aluno.setTelefone(request.telefone());
        if (request.endereco() != null) {
            var e = request.endereco();
            aluno.setEndereco(new Endereco(e.logradouro(), e.numero(), e.complemento(),
                    e.bairro(), e.cidade(), e.uf(), e.cep()));
        }

        return AlunoResponse.from(repository.save(aluno));
    }

    public void excluir(Long id) {
        var aluno = getAluno(id);
        aluno.setAtivo(false);
        repository.save(aluno);
    }

    public Aluno getAluno(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado: " + id));
    }
}
