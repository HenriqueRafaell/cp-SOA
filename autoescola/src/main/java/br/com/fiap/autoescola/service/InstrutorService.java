package br.com.fiap.autoescola.service;

import br.com.fiap.autoescola.dto.request.AtualizacaoInstrutorRequest;
import br.com.fiap.autoescola.dto.request.CadastroInstrutorRequest;
import br.com.fiap.autoescola.dto.response.InstrutorResponse;
import br.com.fiap.autoescola.exception.RecursoNaoEncontradoException;
import br.com.fiap.autoescola.exception.RegraNegocioException;
import br.com.fiap.autoescola.model.Endereco;
import br.com.fiap.autoescola.model.Instrutor;
import br.com.fiap.autoescola.repository.InstrutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrutorService {

    private final InstrutorRepository repository;

    public InstrutorResponse cadastrar(CadastroInstrutorRequest request) {
        if (repository.existsByEmail(request.email()))
            throw new RegraNegocioException("E-mail já cadastrado.");
        if (repository.existsByCnh(request.cnh()))
            throw new RegraNegocioException("CNH já cadastrada.");

        var endereco = new Endereco(
                request.endereco().logradouro(),
                request.endereco().numero(),
                request.endereco().complemento(),
                request.endereco().bairro(),
                request.endereco().cidade(),
                request.endereco().uf(),
                request.endereco().cep()
        );

        var instrutor = new Instrutor(null, request.nome(), request.email(),
                request.telefone(), request.cnh(), request.especialidade(), endereco, true);

        return InstrutorResponse.from(repository.save(instrutor));
    }

    public Page<InstrutorResponse> listar(int pagina) {
        return repository.findAllByAtivoTrueOrderByNomeAsc(PageRequest.of(pagina, 10))
                .map(InstrutorResponse::from);
    }

    public InstrutorResponse buscarPorId(Long id) {
        return InstrutorResponse.from(getInstrutor(id));
    }

    public InstrutorResponse atualizar(Long id, AtualizacaoInstrutorRequest request) {
        var instrutor = getInstrutor(id);

        if (request.nome() != null) instrutor.setNome(request.nome());
        if (request.telefone() != null) instrutor.setTelefone(request.telefone());
        if (request.endereco() != null) {
            var e = request.endereco();
            instrutor.setEndereco(new Endereco(e.logradouro(), e.numero(), e.complemento(),
                    e.bairro(), e.cidade(), e.uf(), e.cep()));
        }

        return InstrutorResponse.from(repository.save(instrutor));
    }

    public void excluir(Long id) {
        var instrutor = getInstrutor(id);
        instrutor.setAtivo(false);
        repository.save(instrutor);
    }

    public Instrutor getInstrutor(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Instrutor não encontrado: " + id));
    }
}
