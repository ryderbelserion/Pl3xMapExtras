package com.ryderbelserion.map.common.storage.impl.types;

import com.ryderbelserion.map.api.storage.IStorageHolder;
import com.ryderbelserion.map.api.user.IUser;
import org.jspecify.annotations.NonNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ConnectionStorage extends IStorageHolder {

    private final ConnectionFactory factory;

    public ConnectionStorage(@NonNull final ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public @NonNull final ConnectionStorage init() {
        this.factory.init();

        return this;
    }

    @Override
    public void reload() {

    }

    @Override
    public void insertUser(@NonNull final IUser user) {
        CompletableFuture.runAsync(() -> {
            try (final Connection connection = this.factory.getConnection(); final PreparedStatement statement =
                    connection.prepareStatement("insert into pl3x_users(uuid, name) values(?, ?)")) {
                statement.setString(1, String.valueOf(user.getUniqueId()));
                statement.setString(2, user.getUsername());

                statement.executeUpdate();
            } catch (final SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public @NonNull final String getName(@NonNull final UUID uuid) {
        if (!hasUser(uuid)) return "N/A";

        return CompletableFuture.supplyAsync(() -> {
            String name = "N/A";

            try (final Connection connection = this.factory.getConnection(); final PreparedStatement statement =
                    connection.prepareStatement("select name from pl3x_users where uuid=?")) {
                statement.setString(1, uuid.toString());

                final ResultSet result = statement.executeQuery();

                while (result.next()) {
                    name = result.getString("name");
                }
            } catch (final SQLException exception) {
                name = "N/A";
            }

            return name;
        }).join();
    }

    @Override
    public boolean hasUser(@NonNull final UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            boolean hasUser = false;

            try (final Connection connection = this.factory.getConnection(); final PreparedStatement statement =
                    connection.prepareStatement("select 1 from pl3x_users where uuid=?")) {
                statement.setString(1, uuid.toString());

                final ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    hasUser = true;

                    return hasUser;
                }
            } catch (final SQLException exception) {
                exception.printStackTrace();
            }

            return hasUser;
        }).join();
    }

    @Override
    public void stop() {
        this.factory.stop();
    }
}