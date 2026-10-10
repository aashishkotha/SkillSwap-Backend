package com.skillswap.controller;

import com.skillswap.service.MatchingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(
            MatchingService matchingService
    ) {
        this.matchingService = matchingService;
    }

    @GetMapping("/users/{userId}")
    public List<Map<String, Object>> getMatches(
            @PathVariable Long userId
    ) {

        return matchingService.getMatches(userId);
    }
}