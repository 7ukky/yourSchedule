package sample.database;

import java.sql.*;
import java.time.LocalDate;


public class DatabaseHandler extends Configs {

	public Connection connect() throws ClassNotFoundException{

		String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
		Class.forName("org.postgresql.Driver");

		Connection dbConnection = null;
		try {
			dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		if (dbConnection != null) {
			StaticSubject.error_code = 1000;
			System.out.println("Connected");
		}
		else {
			System.out.println("Failed");
			StaticSubject.error_code = 0;
		}
		return dbConnection;

	}

	public void signUpUser(User user) {
		DatabaseHandler sqlConnect = new DatabaseHandler();
		try {
			sqlConnect.connect();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
				Const.USER_NAME + "," +
				Const.USER_SURNAME + "," +
				Const.USER_GROUP + "," +
				Const.USER_PASSWORD + "," +
				Const.USER_EMAIL + ")" +
				"VALUES(?,?,?,?,?)";


		try {
			PreparedStatement prSt = connect().prepareStatement(insert);
			prSt.setString(1, user.getName());
			prSt.setString(2, user.getSurname());
			prSt.setInt(3, user.getGroup());
			prSt.setString(4, user.getPass());
			prSt.setString(5, user.getEmail());

			prSt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getSubjectWithoutDate(Subject subject) {
		ResultSet resultSet = null;

//		SELECT * FROM subjects LEFT JOIN subjects_with_dates ON subjects.sub_id = subjects_with_dates.sub_id
//		WHERE (sub_week = 'bot' OR sub_week = 'alw') AND sub_day = 'th' AND (sub_date = CURRENT_DATE OR sub_date IS NULL)
//		ORDER BY sub_serial_num;


		String select = "SELECT * FROM " + Const.SUBJECT_TABLE + " LEFT JOIN " + Const.SUBJECTS_WITH_DATES_TABLE +
				" ON " + Const.SUBJECT_TABLE + "." + Const.ID + " = " + Const.SUBJECTS_WITH_DATES_TABLE + "." +
				Const.SUBJECT_ID + " WHERE (" + Const.SUBJECT_WEEK + " =? OR " + Const.SUBJECT_WEEK + " = 'alw') AND " +
				Const.SUBJECT_DAY + " =? AND (" + Const.SUBJECT_DATE + " = CURRENT_DATE OR " + Const.SUBJECT_DATE +
				" IS NULL) AND " + Const.GROUP_ID + " =? ORDER BY " + Const.SUBJECT_SERIAL_NUMBER;
		try {
			PreparedStatement prSt = connect().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setString(1, subject.getWeek());
			prSt.setString(2, subject.getDay());
			prSt.setInt(3, subject.getGroup_id());
			resultSet = prSt.executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getSubjectWithDate(Subject subject, LocalDate date) {
		ResultSet resultSet = null;

		String select = "SELECT * FROM " + Const.SUBJECT_TABLE + " LEFT JOIN " + Const.SUBJECTS_WITH_DATES_TABLE +
				" ON " + Const.SUBJECT_TABLE + "." + Const.ID + " = " + Const.SUBJECTS_WITH_DATES_TABLE + "." +
				Const.SUBJECT_ID + " WHERE (" + Const.SUBJECT_WEEK + " =? OR " + Const.SUBJECT_WEEK + " = 'alw') AND " +
				Const.SUBJECT_DAY + " =? AND (" + Const.SUBJECT_DATE + " =? OR " + Const.SUBJECT_DATE +
				" IS NULL) AND " + Const.GROUP_ID + " =? ORDER BY " + Const.SUBJECT_SERIAL_NUMBER;
		try {
			PreparedStatement prSt = connect().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setString(1, subject.getWeek());
			prSt.setString(2, subject.getDay());
			prSt.setInt(4, subject.getGroup_id());
			prSt.setDate(3, Date.valueOf(date));
			resultSet = prSt.executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}


	public ResultSet getSubject(Subject subject) {
		ResultSet resultSet = null;

		String select = "SELECT " + Const.SUBJECT_NAME + " FROM " + Const.SUBJECT_TABLE + " WHERE " + Const.GROUP_ID + " =? AND " + Const.SUBJECT_DAY + " =? AND " + Const.SUBJECT_SERIAL_NUMBER + " =?";
		try {
			PreparedStatement prSt = connect().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setInt(1, subject.getGroup_id());
			prSt.setString(2, subject.getDay());
			prSt.setInt(3, subject.getSerial_num());

			resultSet = prSt.executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}


	public ResultSet getGroupId(String group_name) {
		ResultSet resultSet = null;
		String insert = "SELECT *  FROM " + Const.GROUPS_TABLE + " WHERE " + Const.GROUP_NAME + " =? ";
		try {
			PreparedStatement prSt = connect().prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setString(1, group_name);
			resultSet = prSt.executeQuery();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getAllGroups() {
		ResultSet resultSet = null;
		String insert = "SELECT * FROM " + Const.GROUPS_TABLE;
		try {
			PreparedStatement prSt = connect().prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = prSt.executeQuery();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getUser(User user) {
		ResultSet resultSet = null;

		String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
				Const.USER_EMAIL + "=? AND " + Const.USER_PASSWORD + "=?";
		try {
			PreparedStatement prSt = connect().prepareStatement(select);
			prSt.setString(1, user.getEmail());
			prSt.setString(2, user.getPass());
			resultSet = prSt.executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getUserGroup(String email) {
		ResultSet resultSet = null;

		String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
				Const.USER_EMAIL + "=?";
		try {
			PreparedStatement prSt = connect().prepareStatement(select);
			prSt.setString(1, email);
			resultSet = prSt.executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultSet;
	}


	public ResultSet getPossibleSerialNumber(Subject subject) {
		ResultSet result = null;
		String select = "SELECT " + Const.SUBJECT_SERIAL_NUMBER + " FROM " + Const.SUBJECT_TABLE + " WHERE " + Const.SUBJECT_DAY + " =? " +
				"AND (" + Const.SUBJECT_WEEK + " =? OR " + Const.SUBJECT_WEEK + " =? OR " + Const.SUBJECT_WEEK + " = 'alw') " +
				"AND " + Const.GROUP_ID + " =? ORDER BY " + Const.SUBJECT_SERIAL_NUMBER;
		try {
			PreparedStatement prSt = connect().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setString(1, subject.getDay());
			prSt.setString(2, subject.getWeek());
			prSt.setString(3, subject.getWeek2());
			prSt.setInt(4, subject.getGroup_id());
			result = prSt.executeQuery();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ResultSet getPossibleSerialNumberForDelete(Subject subject) {
		ResultSet result = null;
		String select = "SELECT " + Const.SUBJECT_SERIAL_NUMBER + " FROM " + Const.SUBJECT_TABLE + " WHERE " + Const.SUBJECT_DAY + " =? " +
				" AND " + Const.SUBJECT_WEEK + " =? AND " + Const.GROUP_ID + " =? ORDER BY " + Const.SUBJECT_SERIAL_NUMBER;
		try {
			PreparedStatement prSt = connect().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setString(1, subject.getDay());
			prSt.setString(2, subject.getWeek());
			prSt.setInt(3, subject.getGroup_id());
			result = prSt.executeQuery();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void AddSubject(Subject subject) {
		ResultSet result = null;
		String insert = "INSERT INTO " + Const.SUBJECT_TABLE + " (" + Const.SUBJECT_SERIAL_NUMBER + ", " + Const.SUBJECT_TYPE + ", " + Const.SUBJECT_NAME + ",\n" +
				"                      " + Const.SUBJECT_TEACHER + ", " + Const.SUBJECT_ROOM + ", " + Const.SUBJECT_DAY + ", " + Const.SUBJECT_WEEK + ",\n" +
				"                      " + Const.SUBJECT_TIME_START + ", " + Const.SUBJECT_TIME_END + ", " + Const.GROUP_ID + ")\n" +
				"                      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement prSt = connect().prepareStatement(insert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setInt(1, subject.getSerial_num());
			prSt.setString(2, subject.getType());
			prSt.setString(3, subject.getName());
			prSt.setString(4, subject.getTeacher());
			prSt.setString(5, subject.getRoom());
			prSt.setString(6, subject.getDay());
			prSt.setString(7, subject.getWeek());
			prSt.setTime(8, Time.valueOf(subject.getTime_start()));
			prSt.setTime(9, Time.valueOf(subject.getTime_end()));
			prSt.setInt(10, subject.getGroup_id());
			prSt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void AddSubject(Subject subject, LocalDate date) {
		ResultSet result = null;
		int id = 0;
		String insert1 = "INSERT INTO " + Const.SUBJECT_TABLE + " (" + Const.SUBJECT_SERIAL_NUMBER + ", " + Const.SUBJECT_TYPE + ", " + Const.SUBJECT_NAME + ",\n" +
				"                      " + Const.SUBJECT_TEACHER + ", " + Const.SUBJECT_ROOM + ", " + Const.SUBJECT_DAY + ", " + Const.SUBJECT_WEEK + ",\n" +
				"                      " + Const.SUBJECT_TIME_START + ", " + Const.SUBJECT_TIME_END + ", " + Const.GROUP_ID + ")\n" +
				"                      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING " + Const.ID;
		try {
			PreparedStatement prSt = connect().prepareStatement(insert1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setInt(1, subject.getSerial_num());
			prSt.setString(2, subject.getType());
			prSt.setString(3, subject.getName());
			prSt.setString(4, subject.getTeacher());
			prSt.setString(5, subject.getRoom());
			prSt.setString(6, subject.getDay());
			prSt.setString(7, subject.getWeek());
			prSt.setTime(8, Time.valueOf(subject.getTime_start()));
			prSt.setTime(9, Time.valueOf(subject.getTime_end()));
			prSt.setInt(10, subject.getGroup_id());
			result = prSt.executeQuery();
			result.absolute(1);
			id = result.getInt(Const.ID);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String insert2 = "INSERT INTO " + Const.SUBJECTS_WITH_DATES_TABLE + " VALUES (?, ?)";
		try {
			PreparedStatement prSt = connect().prepareStatement(insert2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			prSt.setDate(1, Date.valueOf(date));
			prSt.setInt(2, id);
			prSt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void DeleteSubject(Subject subject) {
		ResultSet result = null;
		String delete = "DELETE FROM " + Const.SUBJECT_TABLE + " WHERE " + Const.SUBJECT_DAY + " =? AND " + Const.SUBJECT_NAME + " =? AND " + Const.SUBJECT_SERIAL_NUMBER + " =? AND " + Const.GROUP_ID + " =?";
		try {
			PreparedStatement prSt = connect().prepareStatement(delete, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			prSt.setString(1, subject.getDay());
			prSt.setString(2, subject.getName());
			prSt.setInt(3, subject.getSerial_num());
			prSt.setInt(4, subject.getGroup_id());
			prSt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getSubjectForTable(String week) throws SQLException, ClassNotFoundException {
		ResultSet result = null;

		String select = "SELECT "+Const.SUBJECT_DAY+", "+Const.SUBJECT_SERIAL_NUMBER+", "+Const.SUBJECT_TIME_START+"," +
				" "+Const.SUBJECT_TIME_END+", "+Const.SUBJECT_TYPE+", "+Const.SUBJECT_NAME+", "+Const.SUBJECT_TEACHER+"," +
				" "+Const.SUBJECT_ROOM+
				" FROM "+Const.SUBJECT_TABLE+" WHERE "+Const.SUBJECT_WEEK+" = 'alw' OR "+Const.SUBJECT_WEEK+" =? " +
				"ORDER BY "+Const.SUBJECT_DAY+","+Const.SUBJECT_SERIAL_NUMBER;
		
		PreparedStatement prSt = connect().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		prSt.setString(1, week);
		result = prSt.executeQuery();
		return result;
	}
}


