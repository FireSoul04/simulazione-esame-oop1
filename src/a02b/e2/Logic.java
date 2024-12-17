package a02b.e2;

import java.util.List;

public interface Logic {

    public boolean click(Position position);

    public boolean check();

    public void restart();

    public List<Position> getDisabledDiagonal();
}
