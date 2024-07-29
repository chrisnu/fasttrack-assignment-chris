package com.airfranceklm.fasttrack.assignment.resources.generators;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class generate id for employee with ^klm[0-9]{6}$ format.
 * For the sake simplicity it's started with klm100000.
 */
public class EmployeeIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "klm";
        String query = "SELECT MAX(employee_id) FROM employee";

        try (PreparedStatement statement = session.getJdbcConnectionAccess().obtainConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString(1);
                if (lastId != null) {
                    int number = Integer.parseInt(lastId.substring(prefix.length())) + 1;
                    return prefix + number;
                }
            }
        } catch (SQLException e) {
            throw new HibernateException("Unable to generate ID", e);
        }

        return prefix + "100000"; // Initial value if no records are present
    }
}
