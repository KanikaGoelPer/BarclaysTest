package com.example.BarclaysTest.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String tradeId;

    @Column
    int version;

    @Column
    String counterPartyId;

    @Column
    String bookId;

    @Column
    Date maturityDate;
    @Column
    Date createdDate;
    @Column
    char expired;
}
