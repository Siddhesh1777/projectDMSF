public class AbstractObject {

    private int abstractId;
    private String abstractText;

    public int getAbstractId() {
        return abstractId;
    }

    public void setAbstractId(int abstractId) {
        this.abstractId = abstractId;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    @Override
    public String toString() {
        return "AbstractObject{" +
                "abstractId=" + abstractId +
                ", abstractText='" + abstractText + '\'' +
                '}';
    }
}
