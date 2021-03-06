package org.npathai.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.npathai.command.PostFormatter;
import org.npathai.domain.Post;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ViewTest {
    static final Post POST = new Post("Alice", "Hi, I am Alice", LocalDateTime.now());

    @Mock
    Console mockConsole;

    @Mock
    PostFormatter postFormatter;

    View view;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        view = new View(mockConsole, postFormatter);
    }

    @Test
    public void invokesPostFormatterAndDisplaysTheResultOnConsole() {
        String formattedPost = POST.user() + "-" + POST.message();
        when(postFormatter.format(POST)).thenReturn(formattedPost);

        view.display(POST);

        verify(postFormatter).format(POST);
        verify(mockConsole).writeLine(formattedPost);
    }
}