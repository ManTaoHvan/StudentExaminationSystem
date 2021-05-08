package com.itstar.exam.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler {
	void handle(ResultSet res) throws SQLException;
}
