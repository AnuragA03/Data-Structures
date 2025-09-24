/**
 * LC876 - Middle of the Linked List
 *
 * PROBLEM TYPE: Linked List, Two Pointers
 * DIFFICULTY: Easy
 *
 * PROBLEM:
 * Given the head of a singly linked list, return the middle node of the linked list.
 * If there are two middle nodes (even length), return the second middle node.
 *
 * EXAMPLE:
 * Input: head = [1,2,3,4,5]
 * Output: [3,4,5] (Node with value 3)
 * Explanation: The middle node is node 3.
 *
 * Input: head = [1,2,3,4,5,6]
 * Output: [4,5,6] (Node with value 4)
 * Explanation: Since the list has two middle nodes [3,4], return the second one.
 *
 * INTUITION:
 * We need to find the middle node without knowing the length of the list.
 * The fast and slow pointer technique allows us to find the middle in one pass.
 * Fast pointer moves twice as fast as slow pointer, so when fast reaches the end,
 * slow will be at the middle.
 *
 * BRUTE FORCE APPROACH:
 * - First pass: Count the number of nodes (n)
 * - Second pass: Traverse to the n/2-th node
 * - Return the middle node
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) + O(n/2) = O(n) - Two passes through the list
 * - Space: O(1) - Constant extra space
 *
 * OPTIMAL APPROACH (Fast & Slow Pointers):
 * - Use two pointers: slow moves one step, fast moves two steps
 * - When fast reaches the end, slow will be at the middle
 * - Handles both even and odd length cases correctly
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(1) - Constant extra space
 *
 * KEY OBSERVATIONS:
 * 1. Fast pointer moves at twice the speed of slow pointer
 * 2. When fast reaches end, slow is at middle
 * 3. For even length: fast becomes null, slow at second middle
 * 4. For odd length: fast->next becomes null, slow at exact middle
 *
 * EDGE CASES:
 * - Single node list → return head
 * - Two node list → return second node
 * - Empty list (but constraints say at least 1 node)
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    /**
     * OPTIMAL SOLUTION: Fast & Slow Pointers (Tortoise and Hare)
     *
     * STRATEGY:
     * 1. Initialize both pointers at head
     * 2. Move slow one step, fast two steps in each iteration
     * 3. Stop when fast reaches end (null) or last node (next is null)
     * 4. Return slow pointer which will be at middle
     *
     * WHY IT WORKS:
     * - Fast pointer covers twice the distance as slow pointer
     * - When fast completes the journey, slow is exactly halfway
     * - Mathematical proof: distance = speed × time
     * - If fast speed = 2× slow speed, then fast time = n/2 when slow time = n/2
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // Move fast twice as fast as slow
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move slow one step
            fast = fast.next.next;     // Move fast two steps
        }

        return slow;
    }

    /**
     * BRUTE FORCE SOLUTION (For reference - not optimal)
     *
     * STRATEGY:
     * 1. First pass: Count total nodes
     * 2. Second pass: Traverse to middle node (n/2)
     *
     * COMPLEXITY: O(n) time, O(1) space (but two passes)
     */
    public ListNode middleNodeBruteForce(ListNode head) {
        int count = 0;
        ListNode current = head;

        // First pass: count nodes
        while (current != null) {
            count++;
            current = current.next;
        }

        // Second pass: go to middle node
        current = head;
        for (int i = 0; i < count / 2; i++) {
            current = current.next;
        }

        return current;
    }

    /**
     * ALTERNATIVE APPROACH: Fast stops one step earlier
     *
     * STRATEGY:
     * - Different stopping condition for different middle node preference
     * - This version returns first middle for even length lists
     */
    public ListNode middleNodeFirstMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // Stop when fast.next is null (for even length, slow at first middle)
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [1,2,3,4,5] → Node with value 3 (odd length)
 * Example 2: [1,2,3,4,5,6] → Node with value 4 (even length, second middle)
 * Example 3: [1] → Node with value 1 (single node)
 * Example 4: [1,2] → Node with value 2 (two nodes, second middle)
 * Example 5: [1,2,3] → Node with value 2 (three nodes)
 *
 * PATTERN RECOGNITION:
 * - Fast and slow pointers pattern (Tortoise and Hare)
 * - Similar to: Cycle detection, Find kth from end, Palindrome linked list
 * - Core idea: Two pointers moving at different speeds
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC141: Linked List Cycle Detection
 * - LC142: Linked List Cycle II (find cycle start)
 * - LC19: Remove Nth Node From End of List
 * - LC234: Palindrome Linked List (middle + reverse)
 * - Find first middle node instead of second
 * - Find kth node from middle
 *
 * POINTER MOVEMENT ANALYSIS:
 *
 * Odd length (5 nodes): [1,2,3,4,5]
 * Step 0: slow=1, fast=1
 * Step 1: slow=2, fast=3
 * Step 2: slow=3, fast=5
 * Stop: fast.next=null → return slow=3
 *
 * Even length (6 nodes): [1,2,3,4,5,6]
 * Step 0: slow=1, fast=1
 * Step 1: slow=2, fast=3
 * Step 2: slow=3, fast=5
 * Step 3: slow=4, fast=null
 * Stop: fast=null → return slow=4
 *
 */