package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

public class SimpleDeclensionSchema extends DeclensionSchema {
    public static SimpleDeclensionSchema readFromDocument(Document document) throws LoadingDataException {
        String name = document.getString("name");
        SimpleDeclensionSchema schema = new SimpleDeclensionSchema(name);

        boolean hasParent = false;
        if (document.containsKey("parent")) {
            hasParent = true;
            String parentName = document.getString("parent");
            Document parentDocument = Database.get().getDeclensionClasses().getRaw(parentName);
            DeclensionSchema parent = Database.get().getDeclensionClasses().makeSchema(parentDocument);

            try {
                schema.nom_sg = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG));
                schema.gen_sg = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.SG));
                schema.dat_sg = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.SG));
                schema.acc_sg = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.SG));
                schema.abl_sg = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.SG));

                schema.nom_pl = parent.getSuffix(new DeclinedForm(Casus.NOM, NNumber.PL));
                schema.gen_pl = parent.getSuffix(new DeclinedForm(Casus.GEN, NNumber.PL));
                schema.dat_pl = parent.getSuffix(new DeclinedForm(Casus.DAT, NNumber.PL));
                schema.acc_pl = parent.getSuffix(new DeclinedForm(Casus.ACC, NNumber.PL));
                schema.abl_pl = parent.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL));
            } catch (DeclinedFormDoesNotExistException e) {
                throw new LoadingDataException("Could not create SimpleDeclensionSchema " + name + ", as parent " + parentName + " does not accept form " + e.getForm());
            }
        }

        Document sg = (Document) document.get("sg");
        String nom_sg = sg.getString("nom");
        schema.nom_sg = nom_sg.equals(".") ? schema.nom_sg : nom_sg;
        String gen_sg = sg.getString("gen");
        schema.gen_sg = gen_sg.equals(".") ? schema.gen_sg : gen_sg;
        String dat_sg = sg.getString("dat");
        schema.dat_sg = dat_sg.equals(".") ? schema.dat_sg : dat_sg;
        String acc_sg = sg.getString("acc");
        schema.acc_sg = acc_sg.equals(".") ? schema.acc_sg : acc_sg;
        String abl_sg = sg.getString("abl");
        schema.abl_sg = abl_sg.equals(".") ? schema.abl_sg : abl_sg;

        Document pl = (Document) document.get("pl");
        String nom_pl = pl.getString("nom");
        schema.nom_pl = nom_pl.equals(".") ? schema.nom_pl : nom_pl;
        String gen_pl = pl.getString("gen");
        schema.gen_pl = gen_pl.equals(".") ? schema.gen_pl : gen_pl;
        String dat_pl = pl.getString("dat");
        schema.dat_pl = dat_pl.equals(".") ? schema.dat_pl : dat_pl;
        String acc_pl = pl.getString("acc");
        schema.acc_pl = acc_pl.equals(".") ? schema.acc_pl : acc_pl;
        String abl_pl = pl.getString("abl");
        schema.abl_pl = abl_pl.equals(".") ? schema.abl_pl : abl_pl;

        return schema;
    }

    private SimpleDeclensionSchema(String name) {
        super(name);
    }

    private String nom_sg;
    private String gen_sg;
    private String dat_sg;
    private String acc_sg;
    private String abl_sg;

    private String nom_pl;
    private String gen_pl;
    private String dat_pl;
    private String acc_pl;
    private String abl_pl;

    @Override
    public String getSuffixRaw(DeclinedForm form) {
        Casus casus = form.getCasus();
        NNumber number = form.getNumber();

        if (number == NNumber.SG) {
            return switch (casus) {
                case NOM -> nom_sg;
                case GEN -> gen_sg;
                case DAT -> dat_sg;
                case ACC -> acc_sg;
                case ABL -> abl_sg;
            };
        } else {
            return switch (casus) {
                case NOM -> nom_pl;
                case GEN -> gen_pl;
                case DAT -> dat_pl;
                case ACC -> acc_pl;
                case ABL -> abl_pl;
            };
        }
    }

    @Override
    protected boolean isGenderDependant() {
        return false;
    }
}