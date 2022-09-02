package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(SimpleTask task) {
        customLinkedList.linkLast(task);
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(id);
    }

    @Override
    public List<SimpleTask> getHistory() {
        return customLinkedList.getTasks();
    }


    private class CustomLinkedList {
        public Node first;
        public Node last;
        private HashMap<Integer, Node> indexer = new HashMap<>();

        List<SimpleTask> getTasks() {
            List<SimpleTask> nodesItems = new ArrayList<>();
            Node currentNode = first;
            while (currentNode != null) {
                nodesItems.add(currentNode.getItem());
                currentNode = currentNode.getNext();
            }
            return nodesItems;
        }

        void linkLast(SimpleTask task) {
            removeNode(task.getId());
            indexer.remove(task.getId());
            Node prev = null;

            if (last != null)
                prev = last;

            Node newNode = new Node(task, prev, null);
            last = newNode;
            if (first == null)
                first = newNode;

            if (prev != null)
                prev.setNext(newNode);
            indexer.put(task.getId(), newNode);
        }

        void removeNode(int indx) {
            if (indexer.containsKey(indx)) {
                Node currentNode = indexer.get(indx);
                Node prev = currentNode.getPrev();
                Node next = currentNode.getNext();

                if (prev == null) {
                    currentNode.getNext().setPrev(null);
                    first = next;
                } else
                    currentNode.getPrev().setNext(currentNode.getNext());

                if (next == null) {
                    currentNode.getPrev().setNext(null);
                    last = prev;
                } else
                    currentNode.getNext().setPrev(currentNode.getPrev());
                indexer.remove(indx);
            }
        }
    }
}