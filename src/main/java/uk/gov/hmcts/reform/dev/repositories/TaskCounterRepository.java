package uk.gov.hmcts.reform.dev.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uk.gov.hmcts.reform.dev.models.TaskCounter;

public interface TaskCounterRepository extends JpaRepository<TaskCounter, Integer> {
    // Add custom query methods here if necessary
    @Modifying
    @Transactional
    @Query("UPDATE TaskCounter t SET t.currentValue = t.currentValue + 1 WHERE t.id = 1")
    void incrementCaseNumber();

    @Query("SELECT t.currentValue FROM TaskCounter t WHERE t.id = 1")
    int getNextCaseNumber();
}
