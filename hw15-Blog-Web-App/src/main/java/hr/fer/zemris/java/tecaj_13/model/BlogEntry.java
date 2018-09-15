package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents the implementation of a blog entry model
 * 
 * @author Marko Mesarić
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"),
		@NamedQuery(name = "BlogEntry.ByEntryID", query = "select b from BlogEntry as b where b.id=:entryID") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * blog entry id
	 */
	private Long id;
	/**
	 * user which created the blog entry
	 */
	private BlogUser creator;
	/**
	 * list of blog comments
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * date of creation
	 */
	private Date createdAt;
	/**
	 * date of modification
	 */
	private Date lastModifiedAt;
	/**
	 * Blog entry title
	 */
	private String title;
	/**
	 * Blog entry text
	 */
	private String text;

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id
	 *            to be set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for creator of blog entry
	 * 
	 * @return creator
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter for creator
	 * 
	 * @param creator
	 *            to be set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Getter for list of comments
	 * 
	 * @return list of comments
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for comments
	 * 
	 * @param comments
	 *            comments to be set
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for date of creation
	 * 
	 * @return date of creation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for date of creation
	 * 
	 * @param createdAt
	 *            date of creation
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for date of modification
	 * @return date of modification
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for date of modification
	 * @param lastModifiedAt date of last modification
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for entry title
	 * @return
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for entry title
	 * @param title entry title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for entry text
	 * @return entry text
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Setter for entry text
	 * @param text to be set
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}