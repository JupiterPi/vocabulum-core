package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;
import org.bson.Document;

public class SimpleDeclensionSchema extends DeclensionSchema {
    public static SimpleDeclensionSchema readFromDocument(Document document) {
        String name = document.getString("name");
        SimpleDeclensionSchema schema = new SimpleDeclensionSchema(name);

        Document sg = (Document) document.get("sg");
        schema.nom_sg = sg.getString("nom");
        schema.gen_sg = sg.getString("gen");
        schema.dat_sg = sg.getString("dat");
        schema.acc_sg = sg.getString("acc");
        schema.abl_sg = sg.getString("abl");

        Document pl = (Document) document.get("pl");
        schema.nom_pl = pl.getString("nom");
        schema.gen_pl = pl.getString("gen");
        schema.dat_pl = pl.getString("dat");
        schema.acc_pl = pl.getString("acc");
        schema.abl_pl = pl.getString("abl");

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
        Number number = form.getNumber();

        if (number == Number.SG) {
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
}