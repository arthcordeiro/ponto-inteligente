package com.estudo.pontointeligente.controllers;

import com.estudo.pontointeligente.dto.FuncionarioDTO;
import com.estudo.pontointeligente.dto.ResponseDTO;
import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.services.FuncionarioService;
import com.estudo.pontointeligente.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    public FuncionarioController() {}

    /**
     * Endpoint para atualizar os dados de um funcionario
     *
     * @param id
     * @param dto
     * @param result
     * @return ResponseEntity<ResponseDTO<FuncionarioDTO>>
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO<FuncionarioDTO>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDTO dto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Atualizando funcionario id {}", id);
        ResponseDTO<FuncionarioDTO> response = new ResponseDTO<FuncionarioDTO>();

        Optional<Funcionario> funcionario = this.funcionarioService.findById(id);
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
        }

        this.atualizaDadosFuncionario(funcionario.get(), dto, result);

        if (result.hasErrors()) {
            log.error("Erro na validação do funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.save(funcionario.get());
        response.setData(this.convertFuncionarioDTO(funcionario.get()));

        return ResponseEntity.status(204).body(null);
    }

    /**
     * Atualiza os dados do funcionario com base nos dados do DTO
     *
     * @param funcionario
     * @param dto
     * @param result
     * @throws NoSuchAlgorithmException
     */
    private void atualizaDadosFuncionario(Funcionario funcionario, FuncionarioDTO dto, BindingResult result) throws NoSuchAlgorithmException {
        funcionario.setName(dto.getName());

        if (!funcionario.getEmail().equals(dto.getEmail())) {
            this.funcionarioService.findByEmail(dto.getEmail())
                    .ifPresent(func -> result.addError(new ObjectError("email", "E-mail já cadastrado.")));
        }

        funcionario.setQtdHoraAlmoco(null);
        dto.getQtdHorasAlmoco().ifPresent(
                qtdHoraAlmoco -> funcionario.setQtdHoraAlmoco(Float.valueOf(qtdHoraAlmoco)));

        funcionario.setQtdHoraTrabalhoDia(null);
        dto.getQtdHorasTrabalhoDia().ifPresent(
                qtdHoraTrabalhoDia -> funcionario.setQtdHoraTrabalhoDia(Float.valueOf(qtdHoraTrabalhoDia)));

        funcionario.setValorHora(null);
        dto.getValorHora().ifPresent(
                valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        if (dto.getSenha().isPresent()) {
            funcionario.setSenha(PasswordUtils.gerarSenhaCriptografada(dto.getSenha().get()));
        }
    }

    /**
     * Converte fucionario para DTO
     *
     * @param funcionario
     * @return FuncionarioDTO
     */
    private FuncionarioDTO convertFuncionarioDTO(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setEmail(funcionario.getEmail());
        dto.setName(funcionario.getName());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(
                qtdHoraAlmoco -> dto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHoraAlmoco))));
        funcionario.getQtdHoraTrabalhoDiaOpt().ifPresent(
                qtdHoraTrabalhoDia -> dto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHoraTrabalhoDia))));
        funcionario.getValorHoraOpt().ifPresent(
                valorHora -> dto.setValorHora(Optional.of(valorHora.toString())));
        return dto;
    }
}
