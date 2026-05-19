package com.dsa.week5board.board.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private Integer views;
    private LocalDateTime createdAt;
}
