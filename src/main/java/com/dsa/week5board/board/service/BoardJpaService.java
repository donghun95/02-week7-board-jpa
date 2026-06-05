package com.dsa.week5board.board.service;

import com.dsa.week5board.board.domain.Board;
import com.dsa.week5board.board.dto.BoardCreateRequest;
import com.dsa.week5board.board.dto.BoardResponse;
import com.dsa.week5board.board.dto.BoardWithCommentsResponse;
import com.dsa.week5board.board.exception.BoardNotFoundException;
import com.dsa.week5board.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardJpaService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse create(BoardCreateRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .build();

        Board saved = boardRepository.save(board);
        return BoardResponse.from(saved);
    }

    public BoardResponse findById(Long id) {
        return boardRepository.findById(id)
                .map(BoardResponse::from)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException(id);
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse renameTitle(Long id, String newTitle) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
        board.changeTitle(newTitle);
        return BoardResponse.from(board);
    }

    public BoardWithCommentsResponse findWithComments(Long id) {
        return boardRepository.findWithCommentsById(id)
                .map(BoardWithCommentsResponse::from)
                .orElseThrow(() -> new BoardNotFoundException(id));
    }

    public List<BoardWithCommentsResponse> listLazy() {
        return boardRepository.findAllByOrderByIdDesc().stream()
                .map(BoardWithCommentsResponse::from)
                .toList();
    }

    public List<BoardWithCommentsResponse> listFetchJoin() {
        return boardRepository.findAllWithComments().stream()
                .map(BoardWithCommentsResponse::from)
                .toList();
    }
}
