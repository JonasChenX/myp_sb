package com.jonas.myp_sb.web.myp502.repository;

import com.jonas.myp_sb.web.myp502.model.OnePieceCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnePieceCardInfoRepository extends JpaRepository<OnePieceCardInfo, Long> {
}
