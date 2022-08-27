package com.rekordb.rekordb.user.dto;

import com.rekordb.rekordb.user.domain.userInfo.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RekorCreateDTO {
    private String phone;
    private String password;

    private Password encPassword; //이건 내부에서 값을 넣을 예정.
}
