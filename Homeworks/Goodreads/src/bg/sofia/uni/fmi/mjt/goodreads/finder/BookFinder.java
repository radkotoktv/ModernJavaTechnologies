package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookFinder implements BookFinderAPI {
    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    public Set<Book> allBooks() {
        return this.books;
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        if (authorName == null || authorName.isEmpty()) {
            throw new IllegalArgumentException("authorName cannot be empty!");
        }

        return allBooks().stream()
                .filter(p -> p.author().equals(authorName))
                .toList();
    }

    @Override
    public Set<String> allGenres() {
        Set<String> genres = new HashSet<>();
        Set<Book> books1 = allBooks();
        for (Book book : books1) {
            genres.addAll(book.genres());
        }
        return genres;
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        if (genres == null) {
            throw new IllegalArgumentException("genres cannot be null!");
        }

        List<Book> acceptedBooks = new ArrayList<>();
        for (Book book : books) {
            boolean matches = false;
            switch (option) {
                case MATCH_ALL -> {
                    matches = book.genres().containsAll(genres);
                }
                case MATCH_ANY -> {
                    matches = genres.stream()
                            .anyMatch(book.genres()::contains);
                }
            }
            if (matches) {
                acceptedBooks.add(book);
            }
        }
        return acceptedBooks;
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        List<Book> acceptedBooks = new ArrayList<>();
        for (Book book : books) {
            boolean matches = false;
            switch (option) {
                case MATCH_ALL -> {
                    matches = book.genres().containsAll(keywords);
                }
                case MATCH_ANY -> {
                    matches = book.genres()
                            .stream().anyMatch(book.genres()::contains);
                }
            }
            if (matches) {
                acceptedBooks.add(book);
            }
        }
        return acceptedBooks;
    }
}