package com.itsol.recruit.repository;

import com.itsol.recruit.entity.OTPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OTPsRepository extends JpaRepository<OTPS, Long> {
    @Query("select o from OTPS o where o.user.id =?1 and o.code =?2")
    OTPS findByUserId(Long userId, Integer code);
}
