package com.rekordb.rekordb.user.dto;

import com.rekordb.rekordb.user.domain.userInfo.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RekorCreateDTO {
    @NotNull
    private String phone;
    @NotNull
    private String password;

    private Password encPassword; //이건 내부에서 값을 넣을 예정.
}
