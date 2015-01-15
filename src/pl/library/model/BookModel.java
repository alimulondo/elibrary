package pl.library.model;

import java.util.*;

public class BookModel {
	private List<Book> _books;
	
	public BookModel() {
		_books = new LinkedList<Book>();
	}
	
	public void addBook(String title, String author) {
		_books.add(new Book(title, author));
	}
	
	public List<Book> getBooks() {
		return _books;
	}
	
	public static void main(String[] args) {
		BookModel bM = new BookModel();
		bM.addBook("Lesio", "Chmielewska");
		List<Book> books = bM.getBooks();
		for(Book b: books) {
			System.out.println(b);
		}
		System.out.println("it works...");
	}
}
