package com.atlbook.auraly.dao.repository;

import com.atlbook.auraly.dao.entity.PlanetEntity;
import com.atlbook.auraly.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<PlanetEntity,Long> {
    void deleteByUserEntity(UserEntity users);
}
