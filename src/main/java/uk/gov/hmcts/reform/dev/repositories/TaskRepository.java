package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.dev.models.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    // Add custom query methods here if necessary
}
