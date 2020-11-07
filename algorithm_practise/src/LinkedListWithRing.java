/**
 * 判断单链表中是否有环
 */
public class LinkedListWithRing {

    public static void main(String[] args) {

    }

    public boolean haveRing(Node head) {
        // 头节点为空或只有一个节点且不是自身成环的情况，直接返回false
        if (head == null || head.next == null) {
            return false;
        }

        Node fasterNode = head; // 快指针
        Node slowerNode = head; // 慢指针
        while (fasterNode != null) {
            // 考虑快指针的next节点是空的情况，即链表无环，且快指针到达最后一个节点的情况
            fasterNode = fasterNode.next != null ? fasterNode.next.next : null;
            slowerNode = slowerNode.next;
            if (fasterNode == slowerNode) { // 如果快慢指针重合，则证明有环
                return true;
            }
        }

        // 跳出循环证明快指针为null，则链表无环
        return false;
    }
}

class Node {
    public int value;
    public Node next;
}
