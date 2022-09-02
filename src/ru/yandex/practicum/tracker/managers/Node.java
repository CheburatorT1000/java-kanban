package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.SimpleTask;

public class Node {
    private SimpleTask item;
    private Node next;
    private Node prev;

    public Node(SimpleTask item, Node prev, Node next) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }

    public SimpleTask getItem() {
        return item;
    }

    public void setItem(SimpleTask item) {
        this.item = item;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
