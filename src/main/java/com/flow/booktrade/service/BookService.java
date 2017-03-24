package com.flow.booktrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flow.booktrade.domain.RBook;
import com.flow.booktrade.dto.Book;
import com.flow.booktrade.dto.User;
import com.flow.booktrade.dto.UserRole;
import com.flow.booktrade.exception.NoPermissionException;
import com.flow.booktrade.exception.ResourceNotFoundException;
import com.flow.booktrade.repository.BookRepository;
import com.flow.booktrade.service.mapper.BookMapper;
import com.flow.booktrade.service.util.CompareUtil;
import com.flow.booktrade.service.util.RestPreconditions;

/**
 * Service to handle Book objects
 * @author Dayna
 *
 */
@Service
public class BookService extends BaseService {
	
	@Autowired
	private BookRepository bookRepo;
	
	private BookMapper bookMapper = new BookMapper();

	/**
	 * Create a book to sell/trade
	 * @param owner
	 * @param book
	 * @return
	 */
	public Book createBook(User owner, Book book){
		RestPreconditions.checkNotNull(owner);
		RestPreconditions.checkNotNull(book);
		
		book.setOwner(owner);
		RBook rb = bookMapper.toRBook(book);
		RBook saved = bookRepo.save(rb);
		return bookMapper.toBook(saved);
	}
	
	/**
	 * Delete a book if user is owner or admin
	 * @param owner
	 * @param bookId
	 * @return
	 * @throws NoPermissionException
	 */
	public Long removeBook(User owner, Long bookId) throws NoPermissionException{
		RestPreconditions.checkNotNull(owner);
		RestPreconditions.checkNotNull(bookId);
		RBook toDelete = bookRepo.findOne(bookId);
		if(toDelete.getOwner().getId() != owner.getId() && !owner.getRole().equals(UserRole.ADMIN)){
			throw new NoPermissionException("You must be the owner to remove this book.");
		}
		
		bookRepo.delete(bookId);
		return bookId;
	}
	
	/**
	 * Update a book posting
	 * @param user
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public Book updateBook(User user, Book book) throws Exception{
		RestPreconditions.checkNotNull(user);
		RestPreconditions.checkNotNull(book);
		RBook rb = loadBook(book.getId());
		if(rb.getOwner().getId() != user.getId() && !user.getRole().equals(UserRole.ADMIN)){
			throw new NoPermissionException("You must be the owner to update this book.");
		}
		
		boolean dirty = false;
		
		if(!CompareUtil.compare(rb.getCondition(), book.getCondition())){
			rb.setCondition(book.getCondition());
			if(!dirty){
				dirty = true;
			}
		}
		
		if(!CompareUtil.compare(rb.getDescription(), book.getDescription())){
			rb.setDescription(book.getDescription());
			if(!dirty){
				dirty = true;
			}
		}
		
		if(!CompareUtil.compare(rb.getStatus(), book.getStatus())){
			rb.setStatus(book.getStatus());
			if(!dirty){
				dirty = true;
			}
		}
		
		if(dirty){
			RBook updated = bookRepo.save(rb);
			return bookMapper.toBook(updated);
		}		
		
		return bookMapper.toBook(rb);
	}
	
	/**
	 * Find book by it's id
	 * @param bookId
	 * @return
	 * @throws ResourceNotFoundException 
	 */
	public Book findBookById(Long bookId) throws ResourceNotFoundException{
		RestPreconditions.checkNotNull(bookId);
		RBook rb = loadBook(bookId);
		return bookMapper.toBook(rb);
	}

}