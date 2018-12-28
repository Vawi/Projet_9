package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class CompteComptableRMTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void mapRow() {

        CompteComptableRM compteComptableRM = new CompteComptableRM();
        CompteComptable cc = null;

        try {

            Mockito.when(resultSet.getInt("numero")).thenReturn(5);
            Mockito.when(resultSet.getString("libelle")).thenReturn("titi");

            cc = compteComptableRM.mapRow(resultSet, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(cc);
    }

}
