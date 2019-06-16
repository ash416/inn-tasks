package ru.inno.stc14.dao;

import java.sql.Connection;

public interface ConnectionManager {

    Connection getConnection();
}
