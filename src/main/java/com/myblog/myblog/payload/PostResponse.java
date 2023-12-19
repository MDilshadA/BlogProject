package com.myblog.myblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
   private List<PostDto> dto;
    private int pageSize;
    private  int pageNo;
    private boolean lastPage;
    private int totalPage;
}
