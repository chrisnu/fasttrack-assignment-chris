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
public class Holiday implements EntityCopy<Holiday> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;

    @Column(nullable = false)
    private String holidayLabel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime startOfHoliday;

    @Column(nullable = false)
    private LocalDateTime endOfHoliday;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
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
