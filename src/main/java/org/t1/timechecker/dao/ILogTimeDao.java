package org.t1.timechecker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.t1.timechecker.entity.LogTime;

import java.util.List;

@Repository
public interface ILogTimeDao extends JpaRepository<LogTime, Long> {
    @Query("SELECT AVG(m.duration) FROM LogTime m WHERE m.method = :method")
    Double avgDurationByMethod(@Param("method") String method);

    @Query("SELECT MIN(m.duration) FROM LogTime m WHERE m.method = :method")
    Long minDurationByMethod(@Param("method") String method);

    @Query("SELECT MAX(m.duration) FROM LogTime m WHERE m.method = :method")
    Long maxDurationByMethod(@Param("method") String method);

    @Query("SELECT COUNT(*) FROM LogTime m WHERE m.method = :method")
    Long countMethodСallsByNameMethod(@Param("method") String method);

    @Query("SELECT m.method FROM LogTime m group by m.method order by m.method")
    List<String> getAllMethods();

    @Query("SELECT m.method FROM LogTime m WHERE m.type = :type group by m.method order by m.method")
    List<String> getAllMethodByType(String type);

    @Query("SELECT m.type FROM LogTime m group by m.type order by m.type")
    List<String> getAllTypes();
}
