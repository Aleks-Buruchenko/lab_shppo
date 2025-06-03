package com.example.compact.util;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int capacity;
    private final AtomicBoolean isProducerDone = new AtomicBoolean(false);

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
        while (queue.isEmpty() && !isProducerDone.get()) {
            wait(); // Ждем, пока не появится элемент или производитель не завершит
        }
        if (queue.isEmpty() && isProducerDone.get()) {
            return null; // Очередь пуста и производитель завершил
        }
        T item = queue.removeFirst();
        notifyAll(); // Уведомляем производителей
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public void setProducerDone() {
        isProducerDone.set(true);
        synchronized (this) {
            notifyAll(); // Уведомляем ожидающих потребителей
        }
    }
}