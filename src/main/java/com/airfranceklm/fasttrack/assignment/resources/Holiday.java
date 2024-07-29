package com.airfranceklm.fasttrack.assignment.resources;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "holiday",
        indexes = {
                @Index(name = "idx_employee_id", columnList = "employee_id")
        }
)
public class Holiday implements EntityClone<Holiday> {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;

    @Setter
    private String holidayLabel;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime startOfHoliday;

    private LocalDateTime endOfHoliday;

    @Enumerated(EnumType.ORDINAL)
    private HolidayStatus status;

    @Override
    public Holiday copyValue(Holiday copy) {
        this.holidayLabel = copy.holidayLabel;
        this.startOfHoliday = copy.startOfHoliday;
        this.endOfHoliday = copy.endOfHoliday;
        this.status = copy.status;
        return this;
    }
}
