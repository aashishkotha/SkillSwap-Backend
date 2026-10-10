package com.skillswap.service;

import com.skillswap.model.SwapRequest;
import com.skillswap.model.User;
import com.skillswap.repository.SwapRepository;
import com.skillswap.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwapService {

    private final SwapRepository swapRepository;
    private final UserRepository userRepository;

    public SwapService(
            SwapRepository swapRepository,
            UserRepository userRepository
    ) {
        this.swapRepository = swapRepository;
        this.userRepository = userRepository;
    }

    public SwapRequest createSwap(
            Long senderId,
            Long receiverId,
            String skillOffered,
            String skillRequested,
            String contactMedium,
            String contactDetails,
            String message
    ) {

        if (senderId.equals(receiverId)) {
            throw new RuntimeException(
                    "You cannot send a swap request to yourself."
            );
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(
                        () -> new RuntimeException("Sender not found")
                );

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(
                        () -> new RuntimeException("Receiver not found")
                );

        SwapRequest swapRequest = new SwapRequest();

        swapRequest.setSender(sender);
        swapRequest.setReceiver(receiver);
        swapRequest.setSkillOffered(skillOffered);
        swapRequest.setSkillRequested(skillRequested);
        swapRequest.setContactMedium(contactMedium);
        swapRequest.setContactDetails(contactDetails);
        swapRequest.setMessage(message);
        swapRequest.setStatus(SwapRequest.Status.PENDING);

        return swapRepository.save(swapRequest);
    }

    public SwapRequest createSwap(
            Long senderId,
            Long receiverId,
            String skillOffered,
            String skillRequested
    ) {
        return createSwap(senderId, receiverId, skillOffered, skillRequested, null, null, null);
    }

    public List<SwapRequest> getIncomingRequests(Long userId) {

        return swapRepository.findByReceiverId(userId);
    }

    public List<SwapRequest> getOutgoingRequests(Long userId) {

        return swapRepository.findBySenderId(userId);
    }

    public List<SwapRequest> getAllUserSwaps(Long userId) {

        return swapRepository
                .findBySenderIdOrReceiverId(userId, userId);
    }

    public SwapRequest updateStatus(
            Long swapId,
            SwapRequest.Status status,
            String receiverNote
    ) {

        SwapRequest swapRequest = swapRepository.findById(swapId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Swap request not found"
                        )
                );

        swapRequest.setStatus(status);
        if (receiverNote != null && !receiverNote.trim().isEmpty()) {
            swapRequest.setReceiverNote(receiverNote);
        }

        return swapRepository.save(swapRequest);
    }

    public SwapRequest updateStatus(
            Long swapId,
            SwapRequest.Status status
    ) {
        return updateStatus(swapId, status, null);
    }
}