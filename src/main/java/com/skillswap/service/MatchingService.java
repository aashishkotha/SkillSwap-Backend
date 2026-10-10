package com.skillswap.service;

import com.skillswap.model.User;
import com.skillswap.model.UserSkill;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchingService {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    public MatchingService(
            UserRepository userRepository,
            UserSkillRepository userSkillRepository
    ) {
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public List<Map<String, Object>> getMatches(Long currentUserId) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        List<UserSkill> currentSkills =
                userSkillRepository.findByUserId(currentUserId);

        List<String> currentTeach = new ArrayList<>();
        List<String> currentLearn = new ArrayList<>();

        for (UserSkill userSkill : currentSkills) {

            String skill =
                    userSkill.getSkill()
                            .getName()
                            .toLowerCase();

            if (userSkill.getType()
                    == UserSkill.SkillType.TEACH) {

                currentTeach.add(skill);

            } else {

                currentLearn.add(skill);
            }
        }

        List<User> allUsers = userRepository.findAll();

        List<Map<String, Object>> results =
                new ArrayList<>();

        for (User otherUser : allUsers) {

            if (otherUser.getId()
                    .equals(currentUserId)) {
                continue;
            }

            List<UserSkill> otherSkills =
                    userSkillRepository
                            .findByUserId(otherUser.getId());

            List<String> otherTeach = new ArrayList<>();
            List<String> otherLearn = new ArrayList<>();

            for (UserSkill skill : otherSkills) {

                String skillName =
                        skill.getSkill()
                                .getName()
                                .toLowerCase();

                if (skill.getType()
                        == UserSkill.SkillType.TEACH) {

                    otherTeach.add(skillName);

                } else {

                    otherLearn.add(skillName);
                }
            }

            int score = 0;

            List<String> matches = new ArrayList<>();

            /*
             * User teaches something
             * the other user wants to learn.
             */
            for (String skill : currentTeach) {

                if (otherLearn.contains(skill)) {

                    score += 50;

                    matches.add(
                            "You can teach "
                                    + capitalize(skill)
                    );
                }
            }

            /*
             * Other user teaches something
             * current user wants.
             */
            for (String skill : currentLearn) {

                if (otherTeach.contains(skill)) {

                    score += 50;

                    matches.add(
                            otherUser.getName()
                                    + " can teach you "
                                    + capitalize(skill)
                    );
                }
            }

            if (score > 100) {
                score = 100;
            }

            if (score > 0) {

                Map<String, Object> match =
                        new HashMap<>();

                match.put(
                        "userId",
                        otherUser.getId()
                );

                match.put(
                        "name",
                        otherUser.getName()
                );

                match.put(
                        "bio",
                        otherUser.getBio()
                );

                match.put(
                        "matchScore",
                        score
                );

                match.put(
                        "reason",
                        String.join(
                                ". ",
                                matches
                        )
                );

                results.add(match);
            }
        }

        results.sort(
                (a, b) -> Integer.compare(
                        (Integer) b.get("matchScore"),
                        (Integer) a.get("matchScore")
                )
        );

        return results;
    }

    private String capitalize(String value) {

        if (value == null || value.isEmpty()) {
            return value;
        }

        return value.substring(0, 1)
                .toUpperCase()
                + value.substring(1);
    }
}