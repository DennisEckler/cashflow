package dev.eckler.cashflow.domain.identifier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentifierRepository extends JpaRepository<Identifier, Long> {

}
