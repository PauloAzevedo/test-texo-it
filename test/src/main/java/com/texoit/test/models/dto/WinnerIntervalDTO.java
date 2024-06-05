package com.texoit.test.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WinnerIntervalDTO {
    private List<WinnerDTO> min = new ArrayList<>();
    private List<WinnerDTO> max = new ArrayList<>();
}
