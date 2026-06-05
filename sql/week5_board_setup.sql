CREATE DATABASE IF NOT EXISTS week5_board DEFAULT CHARACTER SET utf8mb4;
USE week5_board;

DROP TABLE IF EXISTS board_tags;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS tags;

-- 1. 댓글 테이블
CREATE TABLE comments (
                          id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                          board_id    BIGINT NOT NULL,
                          writer      VARCHAR(50) NOT NULL,
                          content     TEXT NOT NULL,
                          created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_comments_board FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
                          INDEX idx_comments_board (board_id)
);

-- 2. 태그 테이블
CREATE TABLE tags (
                      id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name  VARCHAR(50) NOT NULL UNIQUE
);

-- 3. 게시글-태그 매핑 테이블
CREATE TABLE board_tags (
                            board_id BIGINT NOT NULL,
                            tag_id   BIGINT NOT NULL,
                            PRIMARY KEY (board_id, tag_id),
                            CONSTRAINT fk_board_tags_board FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
                            CONSTRAINT fk_board_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
                            INDEX idx_board_tags_tag (tag_id)
);

INSERT INTO comments (board_id, writer, content) VALUES
                                                     (1, 'donghun', '동적 쿼리 잘 정리됐네요.'),
                                                     (1, 'mentor',  '깔끔합니다.'),
                                                     (1, 'reader',  '실무에서도 자주 씁니다.'),
                                                     (2, 'mentor',  'foreach 빈 리스트 방어 중요'),
                                                     (3, 'donghun', 'cursor 좋네요.'),
                                                     (3, 'mentor',  'limit + 1 트릭'),
                                                     (4, 'reader',  '동시성 부분 어렵네요.'),
                                                     (5, 'donghun', 'REST 6주차 기대됩니다.');

-- 태그 시드 데이터
INSERT INTO tags (name) VALUES
                            ('mybatis'), ('jpa'), ('paging'), ('concurrency'), ('rest');

-- 게시글-태그 매핑 시드 데이터
INSERT INTO board_tags (board_id, tag_id) VALUES
                                              (1, 1), (1, 3),
                                              (2, 1),
                                              (3, 1), (3, 3),
                                              (4, 1), (4, 4),
                                              (5, 5);