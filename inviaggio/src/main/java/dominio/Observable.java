package dominio;

import java.util.ArrayList;
import java.util.List;


public class Observable {
    protected List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        if(!this.observers.contains(observer)){
            this.observers.add(observer);
        }
    }

    public void deleteObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }


}
