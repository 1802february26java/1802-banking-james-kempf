package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.User;

public class UserRepositoryJdbc implements UserRepository {

	private static UserRepositoryJdbc repository = new UserRepositoryJdbc();

	private static Logger logger = Logger.getLogger(UserRepositoryJdbc.class);

	private UserRepositoryJdbc() {}

	public static UserRepositoryJdbc getInstance() {
		return repository;
	}

	@Override
	public boolean insertUser(User user) {
		logger.trace("Inserting new user");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			String sql = "INSERT INTO USERS(U_USERNAME, U_PASSWORD, U_BALANCE) VALUES(?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, user.getUsername());
			statement.setString(++parameterIndex, user.getPassword());
			statement.setDouble(++parameterIndex, user.getBalance());

			if (statement.executeUpdate() > 0) {
				logger.info("Insert success");
				return true;
			}
		} catch (SQLException e) {
			logger.error("Exception thrown while inserting new User", e);
		}
		return false;
	}

	@Override
	public Set<User> selectAllUsers() {
		logger.trace("Selecting all users");
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS";

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			Set<User> set = new HashSet<>();
			while (result.next()) {
				set.add(new User(
						result.getString("U_USERNAME"),
						result.getString("U_PASSWORD")
						));
				return set;
			}
		} catch (SQLException e) {
			logger.error("Exception thrown while selecting all Users", e);
		}
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		logger.trace("Selecting user by username: " + username);
		try (Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			String sql = "SELECT * FROM USERS WHERE U_USERNAME = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, username);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				User user = new User(
						result.getString("U_USERNAME"),
						result.getString("U_PASSWORD"),
						result.getDouble("U_BALANCE")
						);
				return new User(
						result.getString("U_USERNAME"),
						result.getString("U_PASSWORD"),
						result.getDouble("U_BALANCE")
						);
			}
		} catch (SQLException e) {
			logger.error("Exception thrown while selecting all Users", e);
		}
		return null;
	}

	@Override
	public boolean updateBalance(User user, double balance) {
		logger.trace("Updating balance");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			String sql = "UPDATE USERS SET U_BALANCE = ? WHERE U_USERNAME = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDouble(++parameterIndex, balance);
			statement.setString(++parameterIndex, user.getUsername());

			return (statement.executeUpdate() > 0);
		} catch (SQLException e) {
			logger.error("Exception thrown while updating balance", e);
		}
		return false;
	}
	
	@Override
	public double getBalance(User user) {
		logger.trace("Getting balance");
		try (Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			String sql = "SELECT U_BALANCE FROM USERS WHERE U_USERNAME = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, user.getUsername());
			
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				return result.getDouble("U_BALANCE");
			}
		} catch (SQLException e) {
			logger.error("Exception thrown while getting balance", e);
		}
		return 0;
	}

	@Override
	public void transactions() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		UserRepository repository = UserRepositoryJdbc.getInstance();

		User user = new User("AnthonyAardvark", "password1");
		repository.insertUser(user);
		System.out.println(repository.selectAllUsers());
		System.out.println(repository.getUserByUsername("AnthonyAarvark"));
		System.out.println(repository.updateBalance(user, 100d));
		System.out.println(repository.getBalance(user));
	}

}
