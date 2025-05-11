package controllers;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements  HistoryManager {
    private final List<Task> historyList = new ArrayList<>();
    private final int maxStorage = 10;

    @Override
    public void add(Task task) {
        if (historyList.size() == maxStorage) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
