package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;
import agh.ics.oop.proj2.observers.ICellClickedObserver;
import agh.ics.oop.proj2.observers.IGameStateChangedObserver;

import java.util.HashSet;
import java.util.Set;

public class StateMachine implements ICellClickedObserver {
    private final Set<IGameStateChangedObserver> observers = new HashSet<>();
    private AbstractMachineState currentState;
    private Vector2d[] highlighted;
    public StateMachine(Board board)
    {
        currentState = new StartState(board);
        updateState();
    }

    private void updateState() {
        while (currentState.nextState() != currentState)
        {
            currentState = currentState.nextState();
            System.out.printf("changing state to : %s\n",currentState);
        }
        highlighted = currentState.getCellsToHighlight();
        onHighlightChanged(highlighted);
        if (currentState instanceof GameEndedState endState) {
            onGameEnded(endState.sideWon);
        }
    }


    private void onHighlightChanged(Vector2d[] highlighted) {
        observers.forEach(observer -> observer.highlightChanged(highlighted));
    }

    private void onGameEnded(Side sideWon) {
        observers.forEach(observer -> observer.gameEnded(sideWon));
    }

    public Vector2d[] getHighlighted(){
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
        currentState.cellClicked(pos);
        updateState();
    }
}
