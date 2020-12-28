package com.estudo.pontointeligente.controllers;

import com.estudo.pontointeligente.dto.EmpresaDTO;
import com.estudo.pontointeligente.dto.ResponseDTO;
import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final static Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    public EmpresaController() {}

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<ResponseDTO<EmpresaDTO>> findByCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Buscando empresa pelo CNPJ: {}", cnpj);
        ResponseDTO<EmpresaDTO> response = new ResponseDTO<EmpresaDTO>();
        Optional<Empresa> empresa = empresaService.findByCnpj(cnpj);

        if (!empresa.isPresent()) {
            log.info("Empresa não encontrada para o CNPJ: {}", cnpj);
            response.getErrors().add("Empresa não encontrada para o CNPJ "+cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertEmpresaDto(empresa.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Converte Entidade Empresa para DTO
     *
     * @param empresa
     * @return EmpresaDTO
     */
    private EmpresaDTO convertEmpresaDto(Empresa empresa) {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setId(empresa.getId());
        empresaDTO.setRazaoSocial(empresa.getRazaoSocial());
        empresaDTO.setCnpj(empresa.getCnpj());

        return empresaDTO;
    }
}
