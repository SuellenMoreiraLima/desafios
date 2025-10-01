package santander.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import santander.desafio.model.CepLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CepLogRepository extends JpaRepository<CepLog, Long> {

    List<CepLog> findByCepOrderByConsultaTimestampDesc(String cep);
    
    List<CepLog> findByConsultaTimestampBetween(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT c FROM CepLog c WHERE c.sucesso = :sucesso ORDER BY c.consultaTimestamp DESC")
    List<CepLog> findBySucessoOrderByConsultaTimestampDesc(@Param("sucesso") Boolean sucesso);
    
    long countByCep(String cep);
}