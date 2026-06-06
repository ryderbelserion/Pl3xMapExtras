package com.ryderbelserion.map.common.storage.impl.types;

import org.jspecify.annotations.NonNull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class ConnectionFactory {

    protected final String create_users_table = "create table if not exists pl3x_users(" +
            "uuid varchar(36) primary key, " +
            "name varchar(16) not null)";

    public abstract boolean tableExists(@NonNull final String table);

    public abstract Connection getConnection() throws SQLException;

    public abstract String getImplementation();

    public abstract boolean isRunning();

    public abstract void init();

    public abstract void stop();

    protected List<String> getTables(@NonNull final Connection connection) throws SQLException {
        final List<String> tables = new ArrayList<>();

        try (final ResultSet result = connection.getMetaData().getTables(connection.getCatalog(), null, "%", null)) {
            while (result.next()) {
                tables.add(result.getString(3).toLowerCase(Locale.ROOT));
            }
        }

        return tables;
    }
}