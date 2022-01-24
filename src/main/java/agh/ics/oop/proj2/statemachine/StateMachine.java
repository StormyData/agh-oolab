package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.HighlightData;
import agh.ics.oop.proj2.Side;
import agh.ics.oop.proj2.observers.ICellClickedObserver;
import agh.ics.oop.proj2.observers.IGameStateChangedObserver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StateMachine implements ICellClickedObserver {
    private final Set<IGameStateChangedObserver> observers = new HashSet<>();
    private AbstractMachineState currentState;
    private List<HighlightData> highlighted;
    private boolean running = true;
    public StateMachine(Board board)
    {
        currentState = new StartState(board);
        updateState();
    }

    private void updateState() {
        while (currentState.nextState() != currentState)
            currentState = currentState.nextState();

        highlighted = currentState.getCellsToHighlight();
        onHighlightChanged(highlighted);
        if (currentState instanceof GameEndedState endState) {
            onGameEnded(endState.sideWon);
        }
    }


    private void onHighlightChanged(List<HighlightData> highlighted) {
        observers.forEach(observer -> observer.highlightChanged(highlighted));
    }

    private void onGameEnded(Side sideWon) {
        observers.forEach(observer -> observer.gameEnded(sideWon));
        running = false;
    }

    public List<HighlightData> getHighlighted(){
        return highlighted;
    }

    public void addObserver(IGameStateChangedObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IGameStateChangedObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void cellClicked(Vector2d pos) {
        if(!running)
            return;
        currentState.cellClicked(pos);
        updateState();
    }
}
