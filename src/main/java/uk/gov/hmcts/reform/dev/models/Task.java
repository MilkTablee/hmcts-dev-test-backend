package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "case_number")
    private String caseNumber;

    @Column(name = "title")
    private String title;                   // Task title

    @Column(name = "description")
    private String description;             // Task description

    @Column(name = "due_date")
    private LocalDateTime dueDate;          // Due date for the task

    @Column(name = "status")
    private String status;                  // Task status (e.g., "in progress", "completed")

    @Column(name = "created_date")
    private LocalDateTime createdDate;      // Task creation date
}
