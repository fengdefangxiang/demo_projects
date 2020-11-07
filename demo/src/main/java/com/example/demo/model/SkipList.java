package com.example.demo.model;

class Node<T extends Comparable<T>> {
    public T data;
    public int level; // 节点所在层数，从0开始计算
    public Node<T>[] nextList; // 当前节点在不同层的下一节点指针
}

public class SkipList<T extends Comparable<T>> {

    private static final int MAX_LEVEL = 16; // 最大层数

    private Node<T> head; // 头节点

    public SkipList() {
        this.head = new Node<>();
        head.level = MAX_LEVEL - 1; // 头节点直接置为顶层，因为后续遍历都是从头节点开始的
        head.nextList = new Node[MAX_LEVEL];
    }

    /**
     * 添加元素
     *
     * @param value
     */
    public void add(T value) {
        Node<T> node = new Node<>();
        node.data = value;
        int level = getLevel();
        node.level = level;
        node.nextList = new Node[level];

        Node<T> cur = head;
        Node<T>[] needUpdateNodes = new Node[level]; // 保存各层需要调整的节点
        // 从新节点所在层开始（因为所在层以上的层是肯定不需要调整的），找到新节点左侧的节点，就是需要调整的节点
        for (int i = level - 1; i >= 0; i--) {
            while (cur.nextList[i] != null && cur.nextList[i].data.compareTo(value) < 0) {
                cur = cur.nextList[i];
            }
            needUpdateNodes[i] = cur;
        }
        // 调整各层指定节点，实际上就是插入新节点，类似链表插入新节点
        for (int i = level - 1; i >= 0; i--) {
            node.nextList[i] = needUpdateNodes[i].nextList[i];
            needUpdateNodes[i].nextList[i] = node;
        }

    }

    /**
     * 删除指定元素
     *
     * @param value
     */
    public void remove(T value) {
        Node<T> p = head;
        int level = MAX_LEVEL - 1;
        while (level >= 0) {
            if (p.nextList[level] != null && p.nextList[level].data.compareTo(value) < 0) {
                // 该层的下一个节点值比要删除的值小，可以直接跳到下一个节点。
                p = p.nextList[level];
            } else if (p.nextList[level] != null && p.nextList[level].data.compareTo(value) > 0) {
                // 该层的下一个节点值比要删除的大，应该跳到下一层继续查找。
                level -= 1;
            } else if (p.nextList[level] != null) {
                // 该层下一个节点值等于要删除的值，在该层把下一个节点删掉，类似链表删除，然后继续跳到下一层处理。
                p.nextList[level] = p.nextList[level].nextList[level];
                level -= 1;
            } else {
                // 该节点是最后一个节点，或是个空条表，直接向下，最后跳出循环。
                level -= 1;
            }
        }
    }

    /**
     * 按值查找元素
     *
     * @param value
     * @return
     */
    public Node<T> find(T value) {
        Node<T> p = head;
        int level = MAX_LEVEL - 1;
        while (level >= 0) {
            if (p.nextList[level] != null && p.nextList[level].data.compareTo(value) < 0) {
                p = p.nextList[level];
            } else if (p.nextList[level] != null && p.nextList[level].data.compareTo(value) > 0) {
                level -= 1;
            } else if (p.nextList[level] != null) {
                return p.nextList[level];
            } else {
                level -= 1;
            }
        }

        return null;
    }

    /**
     * 获取层数，从1层开始，每次有1/2的概率向上一层，直到没有命中概率或到达顶层
     *
     * @return
     */
    private int getLevel() {
        int level = 1;
        while (true) {
            if (Math.random() < 0.5 && level < MAX_LEVEL) {
                level += 1;
            } else {
                break;
            }
        }

        return level;
    }

}
