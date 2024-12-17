package a02b.e2;

import java.util.*;

public class LogicImpl implements Logic {

    private final int size;
    private final Map<Position, Boolean> cells;
    private final List<Position> disabledDiagonal;

    public LogicImpl(final int columns, final int rows) {
        final int totalSize = columns * rows;
        this.size = columns;
        this.cells = new HashMap<>(totalSize);
        this.disabledDiagonal = new ArrayList<>(totalSize);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                this.cells.put(new Position(x, y), false);
            }
        }
    }

    @Override
    public boolean click(final Position position) {
        final boolean newValue = !this.cells.get(position);
        this.cells.put(position, newValue);
        return newValue;
    }

    @Override
    public boolean check() {
        for (int x = 0; x < this.size; x++) {
            if (this.pointsOnDiagonal(x, 0) == 3) {
                this.disableCurrentDiagonal(x, 0);
                return true;
            }
        }
        for (int y = 0; y < this.size; y++) {
            if (this.pointsOnDiagonal(0, y) == 3) {
                this.disableCurrentDiagonal(0, y);
                return true;
            }
        }
        return false;
    }

    @Override
    public void restart() {
        this.disabledDiagonal.clear();
        for (final var key : this.cells.keySet()) {
            this.cells.put(key, false);
        }
    }

    private int pointsOnDiagonal(final int offsetX, final int offsetY) {
        int pointCount = 0;
        for (int d = 0; d < this.size; d++) {
            final int x = d + offsetX;
            final int y = d + offsetY;
            if (x < this.size && y < this.size && this.cells.get(new Position(x, y))) {
                pointCount++;
            }
        }
        return pointCount;
    }
    
    private void disableCurrentDiagonal(final int offsetX, final int offsetY) {
        this.disabledDiagonal.clear();
        for (int d = 0; d < this.size; d++) {
            final int x = d + offsetX;
            final int y = d + offsetY;
            if (x < this.size && y < this.size) {
                this.disabledDiagonal.add(new Position(x, y));
            }
        }
    }

    @Override
    public List<Position> getDisabledDiagonal() {
        return List.copyOf(this.disabledDiagonal);
    }
}