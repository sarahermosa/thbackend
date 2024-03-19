package com.roshka.thbackend.model.payload;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class MensajeResponse  implements Serializable {

    private String mnesaje;
    private Object object;

}
