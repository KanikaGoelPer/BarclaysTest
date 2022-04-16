package com.example.BarclaysTest.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

    @NotNull
    String tradeId;
    @NotNull
    int version;
    @NonNull
    String counterPartyId;
    @NotNull
    String bookId;
    @NotNull
    @FutureOrPresent(message = "maturityDate should be in present or future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    Date maturityDate;
}
