package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class JournalComptableRMTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void mapRow() {

        JournalComptableRM jcrm = new JournalComptableRM();
        JournalComptable jc = null;

        try {

            Mockito.when(resultSet.getString("code")).thenReturn("007");
            Mockito.when(resultSet.getString("libelle")).thenReturn("titi");

            jc = jcrm.mapRow(resultSet, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(jc);
    }
}
