package com.penpal.project.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMemberForm {
    
    @NotEmpty(message = "필수항목입니다.")
    private String currentPw;
    
    @NotEmpty(message = "필수항목입니다.")
    private String newPw;
    
    @NotEmpty(message = "필수항목입니다.")
    private String checkNewPw;
    
}
