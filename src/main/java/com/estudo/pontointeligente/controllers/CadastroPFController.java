package com.estudo.pontointeligente.controllers;

import com.estudo.pontointeligente.dto.CadastroPFDTO;
import com.estudo.pontointeligente.dto.CadastroPJDTO;
import com.estudo.pontointeligente.dto.ResponseDTO;
import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.enums.PerfilEnum;
import com.estudo.pontointeligente.services.EmpresaService;
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
import javax.xml.ws.Response;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/cadastro-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    public CadastroPFController() { }

    /**
     * Endpoint para salvar uma nova pessoa fisica no banco de dados
     *
     * @param cadastroPFDTO
     * @param result
     * @return ResponseEntity<ResponseDTO<CadastroPFDTO>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<CadastroPFDTO>> cadastrar(@Valid @RequestBody CadastroPFDTO cadastroPFDTO, BindingResult result) throws NoSuchAlgorithmException{
        log.info("Cadastrando PF {}: ", cadastroPFDTO.getCpf());

        ResponseDTO<CadastroPFDTO> response = new ResponseDTO<CadastroPFDTO>();

        validaDadosExistentes(cadastroPFDTO, result);
        Funcionario funcionario = convertDTOFuncionario(cadastroPFDTO, result);

        if (result.hasErrors()) {
            log.error("Erro na validação dos dados da PF {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Empresa> empresa = this.empresaService.findByCnpj(cadastroPFDTO.getCnpj());
        empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
        this.funcionarioService.save(funcionario);

        response.setData(this.convertFuncionarioCadastroPFDTO(funcionario));
        return ResponseEntity.status(201).body(response);
    }


    /**
     * Valida se os dados já existem no banco
     *
     * @param cadastroPFDTO
     * @param result
     */
    private void validaDadosExistentes(CadastroPFDTO cadastroPFDTO, BindingResult result) {
        Optional<Empresa> empresa = this.empresaService.findByCnpj(cadastroPFDTO.getCnpj());
        if (empresa.isPresent())
            result.addError(new ObjectError("empresa", "Empresa já cadastrada."));

        this.funcionarioService.findByCpf(cadastroPFDTO.getCpf())
                .ifPresent(error -> result.addError(new ObjectError("funcionario", "CPF Funcionario já cadastrado.")));
        this.funcionarioService.findByEmail(cadastroPFDTO.getEmail())
                .ifPresent(error -> result.addError(new ObjectError("funcionario", "E-mail Funcionario já cadastrado.")));
    }

    /**
     * Converte um DTO para Funcionario
     *
     * @param cadastroPFDTO
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    public Funcionario convertDTOFuncionario(CadastroPFDTO cadastroPFDTO, BindingResult result) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
            funcionario.setName(cadastroPFDTO.getNome());
            funcionario.setEmail(cadastroPFDTO.getEmail());
            funcionario.setCpf(cadastroPFDTO.getCpf());
            funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
            funcionario.setSenha(PasswordUtils.gerarSenhaCriptografada(cadastroPFDTO.getSenha()));
            cadastroPFDTO.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco ->
                    funcionario.setQtdHoraAlmoco(Float.valueOf(qtdHorasAlmoco)));
            cadastroPFDTO.getQtdHorasTrabalhoDia().ifPresent(qtdHorasTrabalhoDia ->
                    funcionario.setQtdHoraTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));
            cadastroPFDTO.getValorHora().ifPresent(valorHora ->
                    funcionario.setValorHora(new BigDecimal(valorHora)));

        return funcionario;
    }

    /**
     * Converte Funcionário para DTO
     *
     * @param funcionario
     * @return CadastroPFDTO
     */
    public CadastroPFDTO convertFuncionarioCadastroPFDTO(Funcionario funcionario) {
        CadastroPFDTO cadastroPFDTO = new CadastroPFDTO();
        cadastroPFDTO.setId(funcionario.getId());
        cadastroPFDTO.setNome(funcionario.getName());
        cadastroPFDTO.setEmail(funcionario.getEmail());
        cadastroPFDTO.setCpf(funcionario.getCpf());
        cadastroPFDTO.setCnpj(funcionario.getEmpresa().getCnpj());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco ->
                cadastroPFDTO.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
        funcionario.getQtdHoraTrabalhoDiaOpt().ifPresent(qtdHoraTrabalhoDiaOpt ->
                cadastroPFDTO.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHoraTrabalhoDiaOpt))));
        funcionario.getValorHoraOpt().ifPresent(valorHora ->
                cadastroPFDTO.setValorHora(Optional.of(valorHora.toString())));

        return cadastroPFDTO;
    }
}
