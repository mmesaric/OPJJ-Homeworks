package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class represents the implementation of a blog user model
 * 
 * @author Marko Mesarić
 *
 */
@Entity
@Table(name = "blog_users")
@NamedQueries({ @NamedQuery(name = "BlogUser.ByNick", query = "SELECT c FROM BlogUser as c where c.nick=:nick"),
		@NamedQuery(name = "BlogUser.AllAuthors", query = "select b from BlogUser b") })
public class BlogUser {

	/**
	 * id
	 */
	private Long id;
	/**
	 * blog entries belonging to this user
	 */
	private List<BlogEntry> blogEntries = new ArrayList<>();
	/**
	 * users first name
	 */
	private String firstName;
	/**
	 * users last name
	 */
	private String lastName;
	/**
	 * users nick
	 */
	private String nick;
	/**
	 * users email
	 */
	private String email;
	/**
	 * hashed password
	 */
	private String passwordHash;

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
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for list of blog entries
	 * 
	 * @return list of blog entries
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Setter for list of blog entries
	 * 
	 * @param blogEntries
	 *            list of blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * Getter for first name
	 * 
	 * @return first name
	 */
	@Column(length = 100, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for first name
	 * 
	 * @param firstName
	 *            first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for last name
	 * 
	 * @return last name
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	@Column(length = 100, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for hashed password
	 * 
	 * @return hashed password
	 */
	@Column(length = 100, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for hashed password
	 * 
	 * @param passwordHash
	 *            hashed password
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
