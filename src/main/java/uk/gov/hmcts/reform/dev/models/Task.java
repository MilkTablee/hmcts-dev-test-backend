package uk.gov.hmcts.reform.dev.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    private int id;
    private String caseNumber;
    private String title;                   // Task title
    private String description;             // Task description
    private LocalDateTime dueDate;          // Due date for the task
    private String status;                  // Task status (e.g., "in progress", "completed")
    private LocalDateTime createdDate;      // Task creation date
}
