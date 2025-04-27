package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_counter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskCounter {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "current_value")
    private Integer currentValue;
}
