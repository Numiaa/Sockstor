package me.numiaa.sockstor.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sockstor {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String color;
    @Min(0)
    @Max(100)
    private Byte cottonPart;
    @PositiveOrZero
    private Long quantity;
}
