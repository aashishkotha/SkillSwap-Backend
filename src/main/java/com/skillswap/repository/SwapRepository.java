package com.skillswap.repository;

import com.skillswap.model.SwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwapRepository extends JpaRepository<SwapRequest, Long> {

    List<SwapRequest> findBySenderId(Long senderId);

    List<SwapRequest> findByReceiverId(Long receiverId);

    List<SwapRequest> findBySenderIdOrReceiverId(
            Long senderId,
            Long receiverId
    );

    List<SwapRequest> findByReceiverIdAndStatus(
            Long receiverId,
            SwapRequest.Status status
    );
}