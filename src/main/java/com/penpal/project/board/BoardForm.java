package com.penpal.project.board;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    // by 조성빈, 메시지 변경(전체)
    @NotEmpty(message="Enter your Content's Title")
    @Size(max=50)
    private String title;
    
    @NotEmpty(message="Enter your Content")
    @Size(max=300)
    private String content;
    
    @NotEmpty(message="Select Category about your Content")
    @Size(max=30)
    private String category;
    
    @NotEmpty(message="Select Nationality about your Content")
    @Size(max=100)
    private String location;

    @NotEmpty(message="Select Country about your Content")
    @Size(max=100)
    private String country;
}
