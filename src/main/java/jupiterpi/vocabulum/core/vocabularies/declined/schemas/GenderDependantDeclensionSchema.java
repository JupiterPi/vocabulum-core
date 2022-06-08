package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

public class GenderDependantDeclensionSchema extends DeclensionSchema {
    public static GenderDependantDeclensionSchema readFromDocument(Document document) throws LoadingDataException {
        String name = document.getString("name");
        GenderDependantDeclensionSchema schema = new GenderDependantDeclensionSchema(name);

        boolean hasParent = false;
        if (document.containsKey("parent")) {
            hasParent = true;
            String parentName = document.getString("parent");
            Document parentDocument = DeclensionClasses.getRaw(parentName);
            DeclensionSchema parent = DeclensionClasses.makeSchema(parentDocument);

            try {

                // MASC

                schema.nom_sg_masc = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
                schema.gen_sg_masc = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC));
                schema.dat_sg_masc = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.SG, Gender.MASC));
                schema.acc_sg_masc = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.SG, Gender.MASC));
                schema.abl_sg_masc = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.SG, Gender.MASC));

                schema.nom_pl_masc = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC));
                schema.gen_pl_masc = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.MASC));
                schema.dat_pl_masc = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.PL, Gender.MASC));
                schema.acc_pl_masc = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC));
                schema.abl_pl_masc = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL, Gender.MASC));

                // FEM

                schema.nom_sg_fem = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM));
                schema.gen_sg_fem = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.FEM));
                schema.dat_sg_fem = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.SG, Gender.FEM));
                schema.acc_sg_fem = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.SG, Gender.FEM));
                schema.abl_sg_fem = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.SG, Gender.FEM));

                schema.nom_pl_fem = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.FEM));
                schema.gen_pl_fem = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.FEM));
                schema.dat_pl_fem = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM));
                schema.acc_pl_fem = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.FEM));
                schema.abl_pl_fem = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL, Gender.FEM));

                // NEUT

                schema.nom_sg_neut = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT));
                schema.gen_sg_neut = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.NEUT));
                schema.dat_sg_neut = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.SG, Gender.NEUT));
                schema.acc_sg_neut = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.SG, Gender.NEUT));
                schema.abl_sg_neut = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.SG, Gender.NEUT));

                schema.nom_pl_neut = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.NEUT));
                schema.gen_pl_neut = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.NEUT));
                schema.dat_pl_neut = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.PL, Gender.NEUT));
                schema.acc_pl_neut = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.NEUT));
                schema.abl_pl_neut = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL, Gender.NEUT));

            } catch (DeclinedFormDoesNotExistException e) {
                /*throw new LoadingDataException("Could not create GenderDependantDeclensionSchema " + name + ", as parent " + parentName + " does not accept form " + e.getForm());*/
                e.printStackTrace();
            }
        }

        // MASC

        Document masc = (Document) document.get("masc");

        Document sg_masc = (Document) masc.get("sg");
        String nom_sg_masc = sg_masc.getString("nom");
        schema.nom_sg_masc = nom_sg_masc.equals(".") ? schema.nom_sg_masc : nom_sg_masc;
        String gen_sg_masc = sg_masc.getString("gen");
        schema.gen_sg_masc = gen_sg_masc.equals(".") ? schema.gen_sg_masc : gen_sg_masc;
        String dat_sg_masc = sg_masc.getString("dat");
        schema.dat_sg_masc = dat_sg_masc.equals(".") ? schema.dat_sg_masc : dat_sg_masc;
        String acc_sg_masc = sg_masc.getString("acc");
        schema.acc_sg_masc = acc_sg_masc.equals(".") ? schema.acc_sg_masc : acc_sg_masc;
        String abl_sg_masc = sg_masc.getString("abl");
        schema.abl_sg_masc = abl_sg_masc.equals(".") ? schema.abl_sg_masc : abl_sg_masc;

        Document pl_masc = (Document) masc.get("pl");
        String nom_pl_masc = pl_masc.getString("nom");
        schema.nom_pl_masc = nom_pl_masc.equals(".") ? schema.nom_pl_masc : nom_pl_masc;
        String gen_pl_masc = pl_masc.getString("gen");
        schema.gen_pl_masc = gen_pl_masc.equals(".") ? schema.gen_pl_masc : gen_pl_masc;
        String dat_pl_masc = pl_masc.getString("dat");
        schema.dat_pl_masc = dat_pl_masc.equals(".") ? schema.dat_pl_masc : dat_pl_masc;
        String acc_pl_masc = pl_masc.getString("acc");
        schema.acc_pl_masc = acc_pl_masc.equals(".") ? schema.acc_pl_masc : acc_pl_masc;
        String abl_pl_masc = pl_masc.getString("abl");
        schema.abl_pl_masc = abl_pl_masc.equals(".") ? schema.abl_pl_masc : abl_pl_masc;

        // FEM

        Document fem = (Document) document.get("fem");

        Document sg_fem = (Document) fem.get("sg");
        String nom_sg_fem = sg_fem.getString("nom");
        schema.nom_sg_fem = nom_sg_fem.equals(".") ? schema.nom_sg_fem : nom_sg_fem;
        String gen_sg_fem = sg_fem.getString("gen");
        schema.gen_sg_fem = gen_sg_fem.equals(".") ? schema.gen_sg_fem : gen_sg_fem;
        String dat_sg_fem = sg_fem.getString("dat");
        schema.dat_sg_fem = dat_sg_fem.equals(".") ? schema.dat_sg_fem : dat_sg_fem;
        String acc_sg_fem = sg_fem.getString("acc");
        schema.acc_sg_fem = acc_sg_fem.equals(".") ? schema.acc_sg_fem : acc_sg_fem;
        String abl_sg_fem = sg_fem.getString("abl");
        schema.abl_sg_fem = abl_sg_fem.equals(".") ? schema.abl_sg_fem : abl_sg_fem;

        Document pl_fem = (Document) fem.get("pl");
        String nom_pl_fem = pl_fem.getString("nom");
        schema.nom_pl_fem = nom_pl_fem.equals(".") ? schema.nom_pl_fem : nom_pl_fem;
        String gen_pl_fem = pl_fem.getString("gen");
        schema.gen_pl_fem = gen_pl_fem.equals(".") ? schema.gen_pl_fem : gen_pl_fem;
        String dat_pl_fem = pl_fem.getString("dat");
        schema.dat_pl_fem = dat_pl_fem.equals(".") ? schema.dat_pl_fem : dat_pl_fem;
        String acc_pl_fem = pl_fem.getString("acc");
        schema.acc_pl_fem = acc_pl_fem.equals(".") ? schema.acc_pl_fem : acc_pl_fem;
        String abl_pl_fem = pl_fem.getString("abl");
        schema.abl_pl_fem = abl_pl_fem.equals(".") ? schema.abl_pl_fem : abl_pl_fem;

        // NEUT

        Document neut = (Document) document.get("neut");

        Document sg_neut = (Document) neut.get("sg");
        String nom_sg_neut = sg_neut.getString("nom");
        schema.nom_sg_neut = nom_sg_neut.equals(".") ? schema.nom_sg_neut : nom_sg_neut;
        String gen_sg_neut = sg_neut.getString("gen");
        schema.gen_sg_neut = gen_sg_neut.equals(".") ? schema.gen_sg_neut : gen_sg_neut;
        String dat_sg_neut = sg_neut.getString("dat");
        schema.dat_sg_neut = dat_sg_neut.equals(".") ? schema.dat_sg_neut : dat_sg_neut;
        String acc_sg_neut = sg_neut.getString("acc");
        schema.acc_sg_neut = acc_sg_neut.equals(".") ? schema.acc_sg_neut : acc_sg_neut;
        String abl_sg_neut = sg_neut.getString("abl");
        schema.abl_sg_neut = abl_sg_neut.equals(".") ? schema.abl_sg_neut : abl_sg_neut;

        Document pl_neut = (Document) neut.get("pl");
        String nom_pl_neut = pl_neut.getString("nom");
        schema.nom_pl_neut = nom_pl_neut.equals(".") ? schema.nom_pl_neut : nom_pl_neut;
        String gen_pl_neut = pl_neut.getString("gen");
        schema.gen_pl_neut = gen_pl_neut.equals(".") ? schema.gen_pl_neut : gen_pl_neut;
        String dat_pl_neut = pl_neut.getString("dat");
        schema.dat_pl_neut = dat_pl_neut.equals(".") ? schema.dat_pl_neut : dat_pl_neut;
        String acc_pl_neut = pl_neut.getString("acc");
        schema.acc_pl_neut = acc_pl_neut.equals(".") ? schema.acc_pl_neut : acc_pl_neut;
        String abl_pl_neut = pl_neut.getString("abl");
        schema.abl_pl_neut = abl_pl_neut.equals(".") ? schema.abl_pl_neut : abl_pl_neut;

        return schema;
    }

    private GenderDependantDeclensionSchema(String name) {
        super(name);
    }

    // MASC

    private String nom_sg_masc;
    private String gen_sg_masc;
    private String dat_sg_masc;
    private String acc_sg_masc;
    private String abl_sg_masc;

    private String nom_pl_masc;
    private String gen_pl_masc;
    private String dat_pl_masc;
    private String acc_pl_masc;
    private String abl_pl_masc;

    // FEM

    private String nom_sg_fem;
    private String gen_sg_fem;
    private String dat_sg_fem;
    private String acc_sg_fem;
    private String abl_sg_fem;

    private String nom_pl_fem;
    private String gen_pl_fem;
    private String dat_pl_fem;
    private String acc_pl_fem;
    private String abl_pl_fem;

    // NEUT

    private String nom_sg_neut;
    private String gen_sg_neut;
    private String dat_sg_neut;
    private String acc_sg_neut;
    private String abl_sg_neut;

    private String nom_pl_neut;
    private String gen_pl_neut;
    private String dat_pl_neut;
    private String acc_pl_neut;
    private String abl_pl_neut;

    @Override
    public String getSuffixRaw(DeclinedForm form) {
        Casus casus = form.getCasus();
        NNumber number = form.getNumber();
        Gender gender = form.getGender();

        if (gender == Gender.MASC) {

            if (number == NNumber.SG) {
                return switch (casus) {
                    case NOM -> nom_sg_masc;
                    case GEN -> gen_sg_masc;
                    case DAT -> dat_sg_masc;
                    case ACC -> acc_sg_masc;
                    case ABL -> abl_sg_masc;
                };
            } else {
                return switch (casus) {
                    case NOM -> nom_pl_masc;
                    case GEN -> gen_pl_masc;
                    case DAT -> dat_pl_masc;
                    case ACC -> acc_pl_masc;
                    case ABL -> abl_pl_masc;
                };
            }

        } else if (gender == Gender.FEM) {

            if (number == NNumber.SG) {
                return switch (casus) {
                    case NOM -> nom_sg_fem;
                    case GEN -> gen_sg_fem;
                    case DAT -> dat_sg_fem;
                    case ACC -> acc_sg_fem;
                    case ABL -> abl_sg_fem;
                };
            } else {
                return switch (casus) {
                    case NOM -> nom_pl_fem;
                    case GEN -> gen_pl_fem;
                    case DAT -> dat_pl_fem;
                    case ACC -> acc_pl_fem;
                    case ABL -> abl_pl_fem;
                };
            }

        } else {

            if (number == NNumber.SG) {
                return switch (casus) {
                    case NOM -> nom_sg_neut;
                    case GEN -> gen_sg_neut;
                    case DAT -> dat_sg_neut;
                    case ACC -> acc_sg_neut;
                    case ABL -> abl_sg_neut;
                };
            } else {
                return switch (casus) {
                    case NOM -> nom_pl_neut;
                    case GEN -> gen_pl_neut;
                    case DAT -> dat_pl_neut;
                    case ACC -> acc_pl_neut;
                    case ABL -> abl_pl_neut;
                };
            }

        }
    }

    @Override
    protected boolean isGenderDependant() {
        return true;
    }
}