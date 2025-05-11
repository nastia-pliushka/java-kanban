package controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import model.Task;
import model.SubTask;
import model.Epic;
import model.Status;

import java.util.ArrayList;
import java.util.List;

class ManagersTest {
//убедимся, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void getDefaultShouldInitializeInMemoryTaskManager() {
        assertInstanceOf(InMemoryTaskManager.class, Managers.getDefault());
    }

    @Test
    void getDefaultHistoryShouldInitializeInMemoryHistoryManager() {
        assertInstanceOf(InMemoryHistoryManager.class, Managers.getDefaultHistory());
    }
}