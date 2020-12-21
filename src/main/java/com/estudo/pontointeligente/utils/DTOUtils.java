package com.estudo.pontointeligente.utils;

import com.estudo.pontointeligente.dto.EmpresaDTO;
import com.estudo.pontointeligente.dto.FuncionarioDTO;
import com.estudo.pontointeligente.dto.LancamentoDTO;
import com.estudo.pontointeligente.entities.Empresa;
import com.estudo.pontointeligente.entities.Funcionario;
import com.estudo.pontointeligente.entities.Lancamento;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class DTOUtils {

    private ModelMapper modelMapper;

    public DTOUtils () {
        this.modelMapper = new ModelMapper();
    }

    public static final DTOUtils build() {
        return new DTOUtils();
    }

    //Converter Empresa DTO to entity
    public Empresa dtoToempresa(EmpresaDTO dto) {
        return this.modelMapper.map(dto, Empresa.class);
    }

    //Converter Empresa entity to DTO
    public EmpresaDTO empresaToDto(Empresa empresa) {

        return this.modelMapper.map(empresa, EmpresaDTO.class);
    }

    //Converter Funcionario DTO to entity
    public Funcionario dtoToFuncionario(FuncionarioDTO dto) {
        return this.modelMapper.map(dto, Funcionario.class);
    }

    //Converter Funcionario entity to DTO
    public FuncionarioDTO funcionarioToDto(Funcionario funcionario){
        return this.modelMapper.map(funcionario, FuncionarioDTO.class);
    }

    //Converter Lancamento DTO to entity
    public Lancamento dtoToLancamento(LancamentoDTO dto) {
        return this.modelMapper.map(dto, Lancamento.class);
    }

    //Converter Lancamento entity to DTO
    public LancamentoDTO lancamentoToDto(Lancamento lancamento){
        return this.modelMapper.map(lancamento, LancamentoDTO.class);
    }

    //Converter Page<Lancamento> to Page<LancamentoDTO>
    public Page<LancamentoDTO> toPageLancamentoDto(Page<Lancamento> objects) {
        Page<LancamentoDTO> dtos  = objects.map(this::lancamentoToDto);
        return dtos;
    }
}
