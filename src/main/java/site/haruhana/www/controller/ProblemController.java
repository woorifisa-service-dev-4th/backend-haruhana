package site.haruhana.www.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.haruhana.www.entity.Problem;
import site.haruhana.www.service.ProblemService;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getProblemById(id));
    }

    @PostMapping
    public ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
        return new ResponseEntity<>(problemService.createProblem(problem), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Problem> updateProblem(@PathVariable Long id, @RequestBody Problem problem) {
        return ResponseEntity.ok(problemService.updateProblem(id, problem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return ResponseEntity.noContent().build();
    }

    // problems?category=

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Problem>> getProblemsByCategory(@PathVariable Long categoryId) {
        List<Problem> problems = problemService.getProblemsByCategoryId(categoryId);
        if (problems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(problems);
    }

}
