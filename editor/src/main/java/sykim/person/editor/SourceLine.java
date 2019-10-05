package sykim.person.editor;

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
