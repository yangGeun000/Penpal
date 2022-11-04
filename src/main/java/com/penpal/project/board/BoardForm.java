package com.penpal.project.board;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

    @NotEmpty(message="제목을 작성해주세요.")
    @Size(max=50)
    private String title;
    
    @NotEmpty(message="내용을 작성해주세요.")
    @Size(max=300)
    private String content;
    
    @NotEmpty(message="카테고리를 선택해주세요")
    @Size(max=30)
    private String category;
    
    @NotEmpty(message="지역을 선택해주세요")
    @Size(max=100)
    private String location;

    @NotEmpty(message="국가를 선택해주세요")

    @Size(max=100)
    private String country;
}
