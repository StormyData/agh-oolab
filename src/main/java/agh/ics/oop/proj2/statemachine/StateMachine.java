package agh.ics.oop.proj2.statemachine;

import agh.ics.oop.Vector2d;
import agh.ics.oop.observers.IObservable;
import agh.ics.oop.observers.IObserver;
import agh.ics.oop.observers.ObserverHolder;
import agh.ics.oop.proj2.Board;
import agh.ics.oop.proj2.Side;
import agh.ics.oop.proj2.observers.ICellClickedObserver;
import agh.ics.oop.proj2.observers.IGameStateChangedObserver;

public class StateMachine implements IObservable, ICellClickedObserver {
    private final ObserverHolder observers = new ObserverHolder(IGameStateChangedObserver.class);
    private AbstractMachineState currentState;
    private Vector2d[] highlighted;
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


    private void onHighlightChanged(Vector2d[] highlighted) {
        observers.notifyObservers(IGameStateChangedObserver.class,observer -> observer.highlightChanged(highlighted));
    }

    private void onGameEnded(Side sideWon) {
        observers.notifyObservers(IGameStateChangedObserver.class,observer -> observer.gameEnded(sideWon));
    }

    public Vector2d[] getHighlighted(){
        return highlighted;
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.removeObserver(observer);
    }

    @Override
    public void cellClicked(Vector2d pos) {
        currentState.cellClicked(pos);
        updateState();
    }

    public void revert() {
        currentState = currentState.revertState();
    }
}
