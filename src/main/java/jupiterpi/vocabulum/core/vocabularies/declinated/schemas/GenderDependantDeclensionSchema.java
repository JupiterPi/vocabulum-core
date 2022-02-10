package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;
import org.bson.Document;

public class GenderDependantDeclensionSchema extends DeclensionSchema {
    public static GenderDependantDeclensionSchema readFromDocument(Document document) {
        GenderDependantDeclensionSchema schema = new GenderDependantDeclensionSchema();

        // MASC

        Document masc = (Document) document.get("masc");

        Document sg_masc = (Document) masc.get("sg");
        schema.nom_sg_masc = sg_masc.getString("nom");
        schema.gen_sg_masc = sg_masc.getString("gen");
        schema.dat_sg_masc = sg_masc.getString("dat");
        schema.acc_sg_masc = sg_masc.getString("acc");
        schema.abl_sg_masc = sg_masc.getString("abl");

        Document pl_masc = (Document) masc.get("pl");
        schema.nom_pl_masc = pl_masc.getString("nom");
        schema.gen_pl_masc = pl_masc.getString("gen");
        schema.dat_pl_masc = pl_masc.getString("dat");
        schema.acc_pl_masc = pl_masc.getString("acc");
        schema.abl_pl_masc = pl_masc.getString("abl");

        // FEM

        Document fem = (Document) document.get("masc");

        Document sg_fem = (Document) fem.get("sg");
        schema.nom_sg_fem = sg_fem.getString("nom");
        schema.gen_sg_fem = sg_fem.getString("gen");
        schema.dat_sg_fem = sg_fem.getString("dat");
        schema.acc_sg_fem = sg_fem.getString("acc");
        schema.abl_sg_fem = sg_fem.getString("abl");

        Document pl_fem = (Document) fem.get("pl");
        schema.nom_pl_fem = pl_fem.getString("nom");
        schema.gen_pl_fem = pl_fem.getString("gen");
        schema.dat_pl_fem = pl_fem.getString("dat");
        schema.acc_pl_fem = pl_fem.getString("acc");
        schema.abl_pl_fem = pl_fem.getString("abl");

        // NEUT

        Document neut = (Document) document.get("masc");

        Document sg_neut = (Document) neut.get("sg");
        schema.nom_sg_neut = sg_neut.getString("nom");
        schema.gen_sg_neut = sg_neut.getString("gen");
        schema.dat_sg_neut = sg_neut.getString("dat");
        schema.acc_sg_neut = sg_neut.getString("acc");
        schema.abl_sg_neut = sg_neut.getString("abl");

        Document pl_neut = (Document) neut.get("pl");
        schema.nom_pl_neut = pl_neut.getString("nom");
        schema.gen_pl_neut = pl_neut.getString("gen");
        schema.dat_pl_neut = pl_neut.getString("dat");
        schema.acc_pl_neut = pl_neut.getString("acc");
        schema.abl_pl_neut = pl_neut.getString("abl");

        return schema;
    }

    private GenderDependantDeclensionSchema() {}

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
        Number number = form.getNumber();
        Gender gender = form.getGender();

        if (gender == Gender.MASC) {

            if (number == Number.SG) {
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

            if (number == Number.SG) {
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

            if (number == Number.SG) {
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
}