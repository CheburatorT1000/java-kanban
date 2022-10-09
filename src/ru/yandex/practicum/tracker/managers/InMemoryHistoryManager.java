package ru.yandex.practicum.tracker.managers;

import ru.yandex.practicum.tracker.tasks.SimpleTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(SimpleTask task) {
        customLinkedList.linkLast(task);
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(customLinkedList.indexer.get(id));
    }

    @Override
    public List<SimpleTask> getHistory() {
        return customLinkedList.getTasks();
    }


    private class CustomLinkedList {
        public Node first;
        public Node last;
        private final HashMap<Integer, Node> indexer = new HashMap<>();

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
            if (indexer.containsKey(task.getId())) {
                remove(task.getId());
            }
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

        void removeNode(Node currentNode) {
            if (currentNode != null) {
                Node prev = currentNode.getPrev();
                Node next = currentNode.getNext();

                if (prev == null) {
                    if (next != null) {
                        next.setPrev(null);
                    }
                    first = next;
                } else
                    if (prev != null) {
                        prev.setNext(next);
                    }

                if (next == null) {
                    if (prev != null) {
                        prev.setNext(null);
                    }
                    last = prev;
                } else {
                    if (next != null) {
                        next.setPrev(prev);
                    }
                }
                indexer.remove(currentNode.getItem().getId());
            }
        }
    }
}