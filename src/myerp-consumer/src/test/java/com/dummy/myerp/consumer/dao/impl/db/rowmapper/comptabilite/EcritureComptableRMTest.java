package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;


import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class EcritureComptableRMTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void mapRow() {

        EcritureComptableRM ecrm = new EcritureComptableRM();
        EcritureComptable ec = null;

        try {

            Mockito.when(resultSet.getInt("id")).thenReturn(5);
            Mockito.when(resultSet.getString("journal_code")).thenReturn("TT");
            Mockito.when(resultSet.getString("reference")).thenReturn("ref");
            Mockito.when(resultSet.getString("libelle")).thenReturn("lib");
            Mockito.when(resultSet.getString("journal_code")).thenReturn("TT");

            ec = ecrm.mapRow(resultSet, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(ec);

    }

}
