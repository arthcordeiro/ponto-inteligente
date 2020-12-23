package com.estudo.pontointeligente.controllers;

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
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/cadastro-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    public CadastroPJController() {}

    @PostMapping
    public ResponseEntity<ResponseDTO<CadastroPJDTO>> cadastrar(@Valid @RequestBody CadastroPJDTO cadastroPj, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando PJ: {}", cadastroPj.getCnpj());
        ResponseDTO<CadastroPJDTO> response = new ResponseDTO<CadastroPJDTO>();

        validaDadosExistentes(cadastroPj, result);
        Empresa empresa = convertCadastroPJDTOEmpresa(cadastroPj);
        Funcionario funcionario = convertCadastroPJDTOFuncionario(cadastroPj, result);

        if(result.hasErrors()) {
            log.error("Erro na validação dos dados de cadastro. {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.save(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.save(funcionario);

        response.setData(this.convertFuncionarioCadastroPJDTO(funcionario));
        return ResponseEntity.status(201).body(response);
    }

    private void validaDadosExistentes(CadastroPJDTO cadastroPJDTO, BindingResult bindingResult) {
        this.empresaService.findByCnpj(cadastroPJDTO.getCnpj()).ifPresent(empresa ->
                bindingResult.addError(new ObjectError("empresa", "Empresa já cadastrada.")));
        this.funcionarioService.findByCpf(cadastroPJDTO.getCpf()).ifPresent(funcionario ->
                bindingResult.addError(new ObjectError("funcionario", "CPF já cadastrado.")));
        this.funcionarioService.findByEmail(cadastroPJDTO.getEmail()).ifPresent(funcionario ->
                bindingResult.addError(new ObjectError("funcionario", "E-mail já cadastrado.")));
    }

    /**
     * Converte DTO para entidade Empresa
     *
     * @param cadastroPJDTO
     * @return Empresa
     */
    private Empresa convertCadastroPJDTOEmpresa(CadastroPJDTO cadastroPJDTO) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDTO.getCnpj());
        empresa.setRazaoSocial(cadastroPJDTO.getRazaoSocial());

        return empresa;
    }

    /**
     * Converte um DTO para entidade Funcionario
     *
     * @param cadastroPJDTO
     * @param bindingResult
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario convertCadastroPJDTOFuncionario(CadastroPJDTO cadastroPJDTO, BindingResult bindingResult) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setName(cadastroPJDTO.getNome());
        funcionario.setEmail(cadastroPJDTO.getEmail());
        funcionario.setCpf(cadastroPJDTO.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarSenhaCriptografada(cadastroPJDTO.getSenha()));

        return funcionario;
    }

    /**
     * Realiza a conversão da entidade funcionario para DTO CadastroPJ
     *
     * @param funcionario
     * @return CadastroPJDTO
     */
    private CadastroPJDTO convertFuncionarioCadastroPJDTO(Funcionario funcionario) {
        CadastroPJDTO cadastroPJDTO = new CadastroPJDTO();
        cadastroPJDTO.setId(funcionario.getId());
        cadastroPJDTO.setNome(funcionario.getName());
        cadastroPJDTO.setEmail(funcionario.getEmail());
        cadastroPJDTO.setCpf(funcionario.getCpf());
        cadastroPJDTO.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDTO.setCnpj(funcionario.getEmpresa().getCnpj());

        return cadastroPJDTO;
    }
}
