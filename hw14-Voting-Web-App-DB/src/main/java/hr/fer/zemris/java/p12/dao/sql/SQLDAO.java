package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */

/**
 * This class represents the implementation of a DAO interface using the SQL
 * technology. It expects an active connection from
 * {@link SQLConnectionProvider} in order for it to work.
 * 
 * @author Marko Mesarić
 *
 */
public class SQLDAO implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Poll> getAllPolls() {
		List<Poll> polls = new ArrayList<>();

		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select id, title, message from Polls");
			ResultSet rs = statement.executeQuery();

			while (rs != null && rs.next()) {
				Poll poll = new Poll();
				poll.setId(rs.getLong(1));
				poll.setTitle(rs.getString(2));
				poll.setMessage(rs.getString(3));
				polls.add(poll);
			}
		} catch (SQLException e) {
			throw new DAOException("Exception when retrieving poll entries.", e);
		}

		return polls;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollOption> getPollOptionsByID(long id) {

		List<PollOption> entries = new ArrayList<>();
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"select id, optionTitle, optionLink, votesCount, pollID from PollOptions where pollID=?");
			statement.setLong(1, id);

			ResultSet rs = statement.executeQuery();
			while (rs != null && rs.next()) {
				PollOption entry = new PollOption();
				entry.setId(rs.getLong(1));
				entry.setOptionTitle(rs.getString(2));
				entry.setOptionLink(rs.getString(3));
				entry.setVotesCount(rs.getLong(4));
				entry.setPollID(rs.getLong(5));
				entries.add(entry);
			}
		} catch (Exception ex) {
			throw new DAOException("Exception when retrieving entries for given poll.", ex);
		}
		return entries;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Poll getPollByID(long id) {
		Poll poll = null;
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select title, message from Polls where id=?");
			statement.setLong(1, id);

			ResultSet rs = statement.executeQuery();
			while (rs != null && rs.next()) {
				poll = new Poll();
				poll.setId(id);
				poll.setTitle(rs.getString(1));
				poll.setMessage(rs.getString(2));
				break;
			}
		} catch (Exception ex) {
			throw new DAOException("Exception when retrieving entries for given poll.", ex);
		}
		return poll;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateVotesCount(long id) {

		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("UPDATE pollOptions set votesCount=votesCount+1 WHERE id=?");
			statement.setLong(1, id);

			statement.executeUpdate();
		} catch (Exception ex) {
			throw new DAOException("Exception when retrieving entries for given poll.", ex);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getPollIDByOptionID(long pollOptionID) {
		long id = 0;
		Connection connection = SQLConnectionProvider.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select pollID from PollOptions where id=?");
			statement.setLong(1, pollOptionID);

			ResultSet rs = statement.executeQuery();
			while (rs != null && rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception ex) {
			throw new DAOException("Exception when retrieving entries for given poll.", ex);
		}
		return id;
	}
}
