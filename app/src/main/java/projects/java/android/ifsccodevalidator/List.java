package projects.java.android.ifsccodevalidator;

public class List {
    private final String text;
    private final String resultText;

    public List(String text, String resultText) {
        this.text = text;
        this.resultText = resultText;
    }

    public String getText() {
        return text;
    }

    public String getResultText() {
        return resultText;
    }
}
