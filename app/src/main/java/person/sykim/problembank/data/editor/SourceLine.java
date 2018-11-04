package person.sykim.problembank.data.editor;

public class SourceLine {
    private String line;

    public SourceLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return line;
    }
}
