package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.junit.Assert;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;


public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    private EcritureComptable vEcritureComptable = new EcritureComptable();

    public void setEcriture() {
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(123)));
    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        this.setEcriture();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        this.setEcriture();
        vEcritureComptable.setReference("AC////FFKLsiaofnjoea");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitRG2() throws FunctionalException {
        this.setEcriture();
        manager.checkIsEquilibre(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2Violation() throws Exception {

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));

        this.setEcriture();
        manager.checkIsEquilibre(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitRG3() throws Exception {
        this.setEcriture();
        manager.checkNumberLigneEcriture(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3Violation() throws Exception {
        EcritureComptable v = new EcritureComptable();
        v.setJournal(new JournalComptable("AC", "Achat"));
        v.setDate(new Date());
        v.setLibelle("Libelle");
        manager.checkNumberLigneEcriture(v);
    }

    @Test
    public void checkEcritureComptableContext() throws FunctionalException {
        this.setEcriture();
        vEcritureComptable.setReference("AC-2019/00001");
        manager.checkReference(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextViolation() throws FunctionalException {
        this.setEcriture();
        vEcritureComptable.setReference("AC-2010/00001");
        manager.checkReference(vEcritureComptable);
    }

    @Test
    public void getListCompteComptable() {
        List<CompteComptable> pList = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        Assert.assertTrue(pList.size() >= 1);
    }

    @Test
    public void getListJournalComptable() {
        List<CompteComptable> pList = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        Assert.assertTrue(pList.size() >= 1);
    }

    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> pList = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        Assert.assertTrue(pList.size() >= 1);
    }

    @Test
    public void addReference() throws NotFoundException {

        StringBuilder valSeq = new StringBuilder();
        String ref;

        this.setEcriture();
        manager.addReference(vEcritureComptable);
        Assert.assertNotNull(vEcritureComptable.getReference());
        Assert.assertEquals("AC-2019/00001", vEcritureComptable.getReference());

        valSeq.append(43);
        ref = "AC-2019/";
        while (valSeq.length() != 5) {
            valSeq.insert(0, '0');
        }
        ref = ref.concat(valSeq.toString());
        vEcritureComptable.setReference(ref);

        Assert.assertEquals("AC-2019/00043", vEcritureComptable.getReference());

    }

    @Test
    public void insertEcritureComptable() throws Exception {

        this.setEcriture();
        manager.insertEcritureComptable(vEcritureComptable);

        EcritureComptable eb =
                getDaoProxy().getComptabiliteDao()
                        .getEcritureComptableByRef("AC-2019/00001");

        Assert.assertEquals("AC-2019/00001", eb.getReference());

        manager.deleteEcritureComptable(eb.getId());

    }

    @Test
    public void updateEcritureComptable() throws Exception {

        this.setEcriture();
        manager.insertEcritureComptable(vEcritureComptable);

        EcritureComptable eb =
                getDaoProxy().getComptabiliteDao()
                        .getEcritureComptableByRef("AC-2019/00001");

        Assert.assertEquals("AC-2019/00001", eb.getReference());

        eb.setReference("AC-2022/00055");

        manager.updateEcritureComptable(eb);

        Assert.assertEquals("AC-2022/00055", eb.getReference());

        manager.deleteEcritureComptable(eb.getId());
    }

    @Test
    public void deleteEcritureComptable() throws Exception {

        this.setEcriture();
        manager.insertEcritureComptable(vEcritureComptable);

        EcritureComptable eb =
                getDaoProxy().getComptabiliteDao()
                        .getEcritureComptableByRef("AC-2019/00001");

        manager.deleteEcritureComptable(eb.getId());

    }
}
