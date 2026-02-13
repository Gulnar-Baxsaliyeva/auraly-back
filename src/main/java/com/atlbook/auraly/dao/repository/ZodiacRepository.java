package com.atlbook.auraly.dao.repository;

import com.atlbook.auraly.dao.entity.ZodiacEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZodiacRepository extends JpaRepository<ZodiacEntity,Long> {
}
