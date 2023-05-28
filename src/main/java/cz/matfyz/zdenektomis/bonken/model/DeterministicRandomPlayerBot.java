package cz.matfyz.zdenektomis.bonken.model;

public class DeterministicRandomPlayerBot extends RandomPlayerBot {
    public DeterministicRandomPlayerBot(Position position) {
        super(position);
    }

    @Override
    protected int getChoice(int max) {
        return 0;
    }
}
