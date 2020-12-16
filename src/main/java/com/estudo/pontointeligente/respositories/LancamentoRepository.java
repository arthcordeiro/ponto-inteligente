package com.estudo.pontointeligente.respositories;

import com.estudo.pontointeligente.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
        query = "SELECT LANC FROM LANCAMENTO LANC WHERE LANC.FUNCIONARIO.ID = :funcionarioId")
})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

    Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
