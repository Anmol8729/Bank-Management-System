package com.example.bank.dao;

import com.example.bank.model.TransactionRecord;
import java.sql.*;

public class TransactionDao {

    public long recordTransaction(TransactionRecord tr, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, txn_type, amount, description, related_account) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, tr.getAccountId());
            ps.setString(2, tr.getTxnType());
            ps.setBigDecimal(3, tr.getAmount());
            ps.setString(4, tr.getDescription());
            if (tr.getRelatedAccount() != null) ps.setLong(5, tr.getRelatedAccount());
            else ps.setNull(5, Types.BIGINT);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return -1;
    }
}
