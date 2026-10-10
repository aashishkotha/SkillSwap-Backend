package com.skillswap.controller;

import com.skillswap.model.SwapRequest;
import com.skillswap.service.SwapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/swaps")
public class SwapController {

    private final SwapService swapService;

    public SwapController(SwapService swapService) {
        this.swapService = swapService;
    }

    @PostMapping
    public SwapRequest createSwap(
            @RequestBody Map<String, Object> request
    ) {

        Long senderId = Long.valueOf(
                request.get("senderId").toString()
        );

        Long receiverId = Long.valueOf(
                request.get("receiverId").toString()
        );

        String skillOffered =
                request.get("skillOffered").toString();

        String skillRequested =
                request.get("skillRequested").toString();

        String contactMedium = request.get("contactMedium") != null
                ? request.get("contactMedium").toString() : null;

        String contactDetails = request.get("contactDetails") != null
                ? request.get("contactDetails").toString() : null;

        String message = request.get("message") != null
                ? request.get("message").toString() : null;

        return swapService.createSwap(
                senderId,
                receiverId,
                skillOffered,
                skillRequested,
                contactMedium,
                contactDetails,
                message
        );
    }

    @GetMapping("/incoming/{userId}")
    public List<SwapRequest> incoming(
            @PathVariable Long userId
    ) {
        return swapService.getIncomingRequests(userId);
    }

    @GetMapping("/outgoing/{userId}")
    public List<SwapRequest> outgoing(
            @PathVariable Long userId
    ) {
        return swapService.getOutgoingRequests(userId);
    }

    @GetMapping("/user/{userId}")
    public List<SwapRequest> allSwaps(
            @PathVariable Long userId
    ) {
        return swapService.getAllUserSwaps(userId);
    }

    @PutMapping("/{swapId}/accept")
    public SwapRequest accept(
            @PathVariable Long swapId,
            @RequestBody(required = false) Map<String, Object> body
    ) {

        String receiverNote = body != null && body.get("receiverNote") != null
                ? body.get("receiverNote").toString() : null;

        return swapService.updateStatus(
                swapId,
                SwapRequest.Status.ACCEPTED,
                receiverNote
        );
    }

    @PutMapping("/{swapId}/reject")
    public SwapRequest reject(
            @PathVariable Long swapId
    ) {

        return swapService.updateStatus(
                swapId,
                SwapRequest.Status.REJECTED
        );
    }

    @PutMapping("/{swapId}/complete")
    public SwapRequest complete(
            @PathVariable Long swapId
    ) {

        return swapService.updateStatus(
                swapId,
                SwapRequest.Status.COMPLETED
        );
    }
}