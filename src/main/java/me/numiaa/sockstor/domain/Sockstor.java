package me.numiaa.sockstor.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(appliesTo = "sockstor")
@NoArgsConstructor
public class Sockstor {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;
    @NotBlank
    @Column(name = "color")
    private String color;
    @Min(0)
    @Max(100)
    @Column(name = "cotton_part")
    private Byte cottonPart;
    @PositiveOrZero
    @Column(name = "quantity")
    private Long quantity;
}
