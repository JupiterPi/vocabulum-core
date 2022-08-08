package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

public class VerbInfo {
    private String presentRoot;
    private String perfectRoot;

    private String ppp_sg;
    private String ppp_pl;
    private String ppa_sg;
    private String ppa_pl;
    private String pfa_sg;
    private String pfa_pl;

    public VerbInfo(String presentRoot, String perfectRoot, String ppp_sg, String ppp_pl, String ppa_sg, String ppa_pl, String pfa_sg, String pfa_pl) {
        this.presentRoot = presentRoot;
        this.perfectRoot = perfectRoot;
        this.ppp_sg = ppp_sg;
        this.ppp_pl = ppp_pl;
        this.ppa_sg = ppa_sg;
        this.ppa_pl = ppa_pl;
        this.pfa_sg = pfa_sg;
        this.pfa_pl = pfa_pl;
    }

    public String getPresentRoot() {
        return presentRoot;
    }

    public String getPerfectRoot() {
        return perfectRoot;
    }

    public String getPpp_sg() {
        return ppp_sg;
    }

    public String getPpp_pl() {
        return ppp_pl;
    }

    public String getPpa_sg() {
        return ppa_sg;
    }

    public String getPpa_pl() {
        return ppa_pl;
    }

    public String getPfa_sg() {
        return pfa_sg;
    }

    public String getPfa_pl() {
        return pfa_pl;
    }
}
