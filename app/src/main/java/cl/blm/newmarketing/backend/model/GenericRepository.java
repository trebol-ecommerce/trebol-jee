package cl.blm.newmarketing.backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GenericRepository<T, I>
    extends JpaRepository<T, I>, QuerydslPredicateExecutor<T> {

}