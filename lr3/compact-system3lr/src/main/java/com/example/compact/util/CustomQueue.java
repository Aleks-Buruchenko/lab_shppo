package com.example.compact.util;

import java.util.LinkedList;

public class CustomQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int capacity;

    public CustomQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait(); // Ждем, пока не освободится место
        }
        queue.add(item);
        notifyAll(); // Уведомляем потребителей
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Ждем, пока не появится элемент
        }
        T item = queue.removeFirst();
        notifyAll(); // Уведомляем производителей
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}