package com.acme.dbo.address.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Past;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
// @Entity
// @Table(name = "ADDRESS")

public class Address {
//    @Id
//    @Column(name = "ID", columnDefinition = "INTEGER")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(dataType = "Long")
    @EqualsAndHashCode.Exclude
    @Nullable
    Long id;

//    @Column(name = "AMOUNT", columnDefinition = "DECIMAL(31, 16)")
    @ApiModelProperty(dataType = "String", required = true)
    @NonNull String city;

    //    @Column(name = "AMOUNT", columnDefinition = "DECIMAL(31, 16)")
    @ApiModelProperty(dataType = "String", required = true)
    @NonNull String streetAddress;
}
