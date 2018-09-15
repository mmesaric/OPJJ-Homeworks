package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents the model of a blog comment.
 * 
 * @author Marko Mesarić
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * blog comment id
	 */
	private Long id;
	/**
	 * Blog entry which contains this comment 
	 */
	private BlogEntry blogEntry;
	/**
	 * Comment author's email
	 */
	private String usersEMail;
	/**
	 * Comment message
	 */
	private String message;
	/**
	 * Date when comment was posted
	 */
	private Date postedOn;

	/**
	 * Getter for id
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * @param id id to bet
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for blog entry
	 * @return blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for blog entry
	 * @param blogEntry to be set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for email
	 * @return email of the author
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for email
	 * @param usersEMail email to be set
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for comment message
	 * @return comment message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for comment message
	 * @param message to be set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for time of posting
	 * @return time of posting
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for date of posting
	 * @param postedOn date of posting
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}