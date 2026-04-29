package br.com.fiap.autoescola.service;

import br.com.fiap.autoescola.dto.request.AgendamentoInstrucaoRequest;
import br.com.fiap.autoescola.dto.request.CancelamentoInstrucaoRequest;
import br.com.fiap.autoescola.dto.response.InstrucaoResponse;
import br.com.fiap.autoescola.exception.RecursoNaoEncontradoException;
import br.com.fiap.autoescola.exception.RegraNegocioException;
import br.com.fiap.autoescola.model.Instrucao;
import br.com.fiap.autoescola.repository.InstrucaoRepository;
import br.com.fiap.autoescola.repository.InstrutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrucaoService {

    private final InstrucaoRepository instrucaoRepository;
    private final AlunoService alunoService;
    private final InstrutorService instrutorService;
    private final InstrutorRepository instrutorRepository;

    public InstrucaoResponse agendar(AgendamentoInstrucaoRequest request) {
        var dataHora = request.dataHora();

        // Regra: horário de funcionamento - segunda a sábado, 06:00 às 20:00 (ultima instrução começa às 20h, termina às 21h)
        validarHorarioFuncionamento(dataHora);

        // Regra: antecedência mínima de 30 minutos
        if (dataHora.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new RegraNegocioException("O agendamento deve ser feito com pelo menos 30 minutos de antecedência.");
        }

        var aluno = alunoService.getAluno(request.alunoId());

        // Regra: aluno ativo
        if (!aluno.isAtivo()) {
            throw new RegraNegocioException("Não é permitido agendar instrução para aluno inativo.");
        }

        // Regra: máximo 2 instruções por dia para o mesmo aluno
        long instrucoesDia = instrucaoRepository.countInstrucoesByAlunoAndData(aluno.getId(), dataHora.toLocalDate());
        if (instrucoesDia >= 2) {
            throw new RegraNegocioException("O aluno já possui 2 instruções agendadas neste dia.");
        }

        // Instrutor: se não informado, escolhe aleatoriamente
        var instrutor = request.instrutorId() != null
                ? instrutorService.getInstrutor(request.instrutorId())
                : escolherInstrutorAleatorio(dataHora);

        // Regra: instrutor ativo
        if (!instrutor.isAtivo()) {
            throw new RegraNegocioException("Não é permitido agendar instrução com instrutor inativo.");
        }

        // Regra: instrutor sem conflito de horário
        if (instrucaoRepository.existsByInstrutorIdAndDataHoraAndAtivaTrue(instrutor.getId(), dataHora)) {
            throw new RegraNegocioException("O instrutor já possui uma instrução agendada neste horário.");
        }

        var instrucao = new Instrucao(null, aluno, instrutor, dataHora, true, null, null);
        return InstrucaoResponse.from(instrucaoRepository.save(instrucao));
    }

    public InstrucaoResponse cancelar(Long id, CancelamentoInstrucaoRequest request) {
        var instrucao = instrucaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Instrução não encontrada: " + id));

        if (!instrucao.isAtiva()) {
            throw new RegraNegocioException("Esta instrução já foi cancelada.");
        }

        // Regra: antecedência mínima de 24 horas
        if (instrucao.getDataHora().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new RegraNegocioException("O cancelamento deve ser feito com pelo menos 24 horas de antecedência.");
        }

        instrucao.setAtiva(false);
        instrucao.setMotivoCancelamento(request.motivo());
        instrucao.setDataCancelamento(LocalDateTime.now());

        return InstrucaoResponse.from(instrucaoRepository.save(instrucao));
    }

    public List<InstrucaoResponse> listarTodas() {
        return instrucaoRepository.findAll().stream()
                .map(InstrucaoResponse::from)
                .toList();
    }

    public InstrucaoResponse buscarPorId(Long id) {
        return instrucaoRepository.findById(id)
                .map(InstrucaoResponse::from)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Instrução não encontrada: " + id));
    }

    private void validarHorarioFuncionamento(LocalDateTime dataHora) {
        DayOfWeek dia = dataHora.getDayOfWeek();
        if (dia == DayOfWeek.SUNDAY) {
            throw new RegraNegocioException("A auto-escola não funciona aos domingos.");
        }
        int hora = dataHora.getHour();
        if (hora < 6 || hora >= 21) {
            throw new RegraNegocioException("O horário de funcionamento é de 06:00 às 21:00.");
        }
        // última instrução pode começar às 20:00 (termina às 21:00)
        if (hora == 20 && dataHora.getMinute() > 0) {
            throw new RegraNegocioException("A última instrução do dia começa às 20:00.");
        }
    }

    private br.com.fiap.autoescola.model.Instrutor escolherInstrutorAleatorio(LocalDateTime dataHora) {
        var disponiveis = instrutorRepository.findInstrutoresDisponiveisPorHorario(dataHora);
        if (disponiveis.isEmpty()) {
            throw new RegraNegocioException("Não há instrutores disponíveis neste horário.");
        }
        int index = (int) (Math.random() * disponiveis.size());
        return disponiveis.get(index);
    }
}
