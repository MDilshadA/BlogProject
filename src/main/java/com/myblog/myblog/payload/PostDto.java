package com.myblog.myblog.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min = 8, max = 16 ,message = "title should be 8-16 charector")
    private String title;
    @NotEmpty
    private String description;
//    @NotNull
    @NotEmpty
    @Size(min = 20, max = 500,message = "Content should be 20-5000 charector")
    private String content;
}
