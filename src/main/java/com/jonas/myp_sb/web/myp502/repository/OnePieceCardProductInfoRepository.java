package com.jonas.myp_sb.web.myp502.repository;

import com.jonas.myp_sb.web.myp502.model.OnepieceCardProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnePieceCardProductInfoRepository extends JpaRepository<OnepieceCardProductInfo, Long> {
}
