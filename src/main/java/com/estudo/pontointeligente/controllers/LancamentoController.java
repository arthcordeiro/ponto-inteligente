package com.estudo.pontointeligente.controllers;

import com.estudo.pontointeligente.dto.LancamentoDTO;
import com.estudo.pontointeligente.dto.ResponseDTO;
import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.entities.Lancamento;
import com.estudo.pontointeligente.enums.TipoEnum;
import com.estudo.pontointeligente.services.FuncionarioService;
import com.estudo.pontointeligente.services.LancamentoService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(value = "*")
public class LancamentoController {

    private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    public LancamentoController() {}

    /**
     * Endpoint para listar os lançamentos de um funcionario pelo ID do funcionario
     *
     * @param funcionarioId
     * @param pag
     * @param ord
     * @param dir
     * @return ResponseEntity<ResponseDTO<Page<LancamentoDTO>>>
     */
    @GetMapping(value = "/funcionario/{funcionarioId}")
    public ResponseEntity<ResponseDTO<Page<LancamentoDTO>>> findByFuncionarioId(
            @PathVariable("funcionarioId") Long funcionarioId,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir) {

        log.info("Buscando os lançamentos do funcionario ID: {}, página: {}", funcionarioId, pag);
        ResponseDTO<Page<LancamentoDTO>> response = new ResponseDTO<Page<LancamentoDTO>>();

        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<Lancamento> lancamentos = this.lancamentoService.findByFuncionarioId(funcionarioId, pageRequest);
        Page<LancamentoDTO> lancamentoDTOS = lancamentos.map(lancamento -> this.convertLancamentoDTO(lancamento));

        response.setData(lancamentoDTOS);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Endpoint que busca um lançamento por ID
     *
     * @param id
     * @return ResponseEntity<ResponseDTO<LancamentoDTO>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<LancamentoDTO>> findById(@PathVariable("id") Long id) {
        log.info("Buscando lançamento com ID: {}", id);
        ResponseDTO<LancamentoDTO> response = new ResponseDTO<LancamentoDTO>();
        Optional<Lancamento> lancamento = this.lancamentoService.findById(id);

        if (!lancamento.isPresent()) {
            log.info("Lançamento não encontrado para o ID {}", id);
            response.getErrors().add("Lançamento não encontrado para o ID "+id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertLancamentoDTO(lancamento.get()));
        return ResponseEntity.ok().body(response);
    }

    /**
     * Endpoint para adicionar novo lançamento no banco
     *
     * @param dto
     * @param result
     * @return ResponseEntity<ResponseDTO<LancamentoDTO>>
     * @throws ParseException
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<LancamentoDTO>> adicionar(@Valid @RequestBody LancamentoDTO dto, BindingResult result) throws ParseException {
        log.info("Adicionando o lançamento: {}", dto.getId());
        ResponseDTO<LancamentoDTO> response = new ResponseDTO<LancamentoDTO>();
        validarFuncionario(dto, result);
        Lancamento lancamento = this.convertDtoLancamento(dto, result);

        if(result.hasErrors()) {
            log.error("Erro na validação do lançamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = this.lancamentoService.save(lancamento);
        response.setData(this.convertLancamentoDTO(lancamento));

        return ResponseEntity.status(201).body(response);
    }

    /**
     * Atualiza um lançamento
     *
     * @param id
     * @param dto
     * @param result
     * @return ResponseEntity<ResponseDTO<LancamentoDTO>>
     * @throws ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<LancamentoDTO>> atualizar(@PathVariable("id") Long id,
                                                                @Valid @RequestBody LancamentoDTO dto, BindingResult result) throws ParseException {
        log.info("Atualizando lançamento: {}", id);
        ResponseDTO<LancamentoDTO> response = new ResponseDTO<LancamentoDTO>();

        validarFuncionario(dto, result);
        dto.setId(Optional.of(id));
        Lancamento lancamento = this.convertDtoLancamento(dto, result);

        if (result.hasErrors()) {
            log.error("Erro ao validar lançamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = this.lancamentoService.save(lancamento);
        response.setData(this.convertLancamentoDTO(lancamento));

        return ResponseEntity.status(204).body(null);
    }

    /**
     * Remove um Lançamento do banco
     *
     * @param id
     * @return ResponseEntity<ResponseDTO<String>>
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseDTO<String>> remover(@PathVariable("id") Long id) {
        log.info("Removendo lançamento: {}", id);
        ResponseDTO<String> response = new ResponseDTO<String>();
        Optional<Lancamento> lancamento = this.lancamentoService.findById(id);

        if(!lancamento.isPresent()) {
            log.info("Erro ao remover lançamento. ID Inválido. {}", id);
            response.getErrors().add("Erro ao remover lançamento. Registro não encontrado para o ID "+ id);
            return ResponseEntity.badRequest().body(response);
        }

        this.lancamentoService.delete(lancamento.get());
        return ResponseEntity.status(204).body(null);
    }

    /**
     * Valida se existe funcionário cadastrado
     *
     * @param dto
     * @param result
     */
    private void validarFuncionario(LancamentoDTO dto, BindingResult result) {
        if (dto.getFuncionarioId() == null) {
            result.addError(new ObjectError("funcionario", "Funcionario não informado."));
            return;
        }
        log.info("Validando funcionario id {}", dto.getFuncionarioId());
        Optional<Funcionario> funcionario = this.funcionarioService.findById(dto.getFuncionarioId());
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID Inexistente."));
        }
    }

    /**
     * Convert uma entidade lançamento em um DTO
     *
     * @param lancamento
     * @return LancamentoDTO
     */
    private LancamentoDTO convertLancamentoDTO(Lancamento lancamento) {
        LancamentoDTO dto = new LancamentoDTO();
        dto.setId(Optional.of(lancamento.getId()));
        dto.setData(this.dateFormat.format(lancamento.getDataLancamento()));
        dto.setTipo(lancamento.getTipo().toString());
        dto.setDescricao(lancamento.getDescricao());
        dto.setLocalizacao(lancamento.getLocalizacao());
        dto.setFuncionarioId(lancamento.getFuncionario().getId());

        return dto;
    }

    /**
     * Convert DTO pra entidade Lancamento
     *
     * @param dto
     * @param result
     * @return Lancamento
     * @throws ParseException
     */
    private Lancamento convertDtoLancamento(LancamentoDTO dto, BindingResult result) throws ParseException {
        Lancamento lancamento = new Lancamento();

        if (dto.getId().isPresent()) {
            Optional<Lancamento> lanc = this.lancamentoService.findById(dto.getId().get());
            if (lanc.isPresent()) {
                lancamento = lanc.get();
            } else {
                result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
            }
        } else {
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(dto.getId().get());
        }

        lancamento.setDescricao(dto.getDescricao());
        lancamento.setLocalizacao(dto.getLocalizacao());
        lancamento.setDataLancamento(this.dateFormat.parse(dto.getData()));

        if (EnumUtils.isValidEnum(TipoEnum.class, dto.getTipo())) {
            lancamento.setTipo(TipoEnum.valueOf(dto.getTipo()));
        } else {
            result.addError(new ObjectError("lancamento", "Tipo Inválido."));
        }

        return lancamento;
    }
}
