package com.uiudevelopershub.thinktanku.controller;

import com.uiudevelopershub.thinktanku.dto.request.QuizSessionRequestDto;
import com.uiudevelopershub.thinktanku.dto.response.QuizSessionResponseDto;
import com.uiudevelopershub.thinktanku.service.QuizSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("QuizSession")
public class QuizSessionController {
    public QuizSessionService quizSessionService;
    @Autowired
    public QuizSessionController(QuizSessionService quizSessionService) {
        this.quizSessionService = quizSessionService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("Create")
    public String  create(@RequestBody  QuizSessionRequestDto quizSessionRequestDto) {
        quizSessionService.CreateQuizSession(quizSessionRequestDto);
        return "Quiz session created";
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("{id}")
    public String Delete(@PathVariable Long id) {
        quizSessionService.DeleteQuizSessionById(id);
        return "Quiz session deleted";
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizSessionResponseDto>getById(@PathVariable Long id) {
      return ResponseEntity.ok(quizSessionService.GetQuizSessionById(id));
    }
}
