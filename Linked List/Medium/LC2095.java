/**
 * LC2095 - Delete the Middle Node of a Linked List
 *
 * PROBLEM TYPE: Linked List, Two Pointers
 * DIFFICULTY: Medium
 *
 * PROBLEM:
 * You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
 * The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start (0-indexed).
 *
 * EXAMPLE:
 * Input: head = [1,3,4,7,1,2,6]
 * Output: [1,3,4,1,2,6]
 * Explanation: The middle node with value 7 is deleted.
 *
 * Input: head = [1,2,3,4]
 * Output: [1,2,4]
 * Explanation: The middle node with value 3 is deleted.
 *
 * Input: head = [2,1]
 * Output: [2]
 * Explanation: The middle node with value 1 is deleted.
 *
 * INTUITION:
 * We need to find the middle node and delete it. The challenge is:
 * 1. Finding the middle node efficiently
 * 2. Keeping track of the node before the middle to update links
 * 3. Handling edge cases like single-node lists
 *
 * BRUTE FORCE APPROACH:
 * - First pass: Count the number of nodes (n)
 * - Second pass: Traverse to the (n/2 - 1)th node and delete the next node
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) + O(n/2) = O(n) - Two passes through the list
 * - Space: O(1) - Constant extra space
 *
 * OPTIMAL APPROACH (Fast & Slow Pointers with Prev Pointer):
 * - Use fast/slow pointers to find middle in one pass
 * - Maintain a pointer to the node before slow (prev)
 * - When fast reaches end, slow is at middle, prev is before middle
 * - Delete middle by updating prev.next
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(1) - Constant extra space
 *
 * KEY OBSERVATIONS:
 * 1. For even length: delete second middle (n=4 → delete node at index 2)
 * 2. Need to track node before middle to update links
 * 3. Single node list: return null (delete the only node)
 * 4. Two node list: delete second node (index 1)
 *
 * EDGE CASES:
 * - Empty list → return null
 * - Single node list → return null
 * - Two node list → delete second node
 * - Lists with odd vs even length
 */

class Solution {
    /**
     * OPTIMAL SOLUTION: Fast & Slow Pointers with Previous Pointer
     *
     * STRATEGY:
     * 1. Handle base cases (empty list, single node)
     * 2. Use fast/slow pointers to find middle
     * 3. Maintain 'prev' pointer to track node before slow
     * 4. When middle found, update prev.next to skip middle node
     *
     * WHY IT WORKS:
     * - Fast moves 2x speed, slow moves 1x speed
     * - When fast reaches end, slow is at middle
     * - 'prev' allows us to update links to skip middle node
     * - Handles both odd and even length lists correctly
     */
    public ListNode deleteMiddle(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null; // Pointer to track node before slow

        // Move fast 2 steps, slow 1 step until fast reaches end
        while (fast != null && fast.next != null) {
            prev = slow;          // Track node before slow
            slow = slow.next;     // Move slow one step
            fast = fast.next.next; // Move fast two steps
        }

        // Now slow is at middle node, prev is node before middle
        // Delete middle node by skipping it
        prev.next = slow.next;

        return head;
    }

    /**
     * ALTERNATIVE APPROACH: Dummy Node (Handles edge cases uniformly)
     *
     * STRATEGY:
     * - Use dummy node to simplify edge cases
     * - Fast/slow pointers start from dummy
     * - Always works without special casing single node
     *
     * COMPLEXITY: O(n) time, O(1) space
     */
    public ListNode deleteMiddleWithDummy(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode slow = dummy;
        ListNode fast = dummy;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Delete the node after slow (which is the middle)
        slow.next = slow.next.next;

        return dummy.next;
    }

    /**
     * BRUTE FORCE SOLUTION (For reference - not optimal)
     *
     * STRATEGY:
     * - First pass: count total nodes
     * - Second pass: traverse to node before middle and delete
     *
     * COMPLEXITY: O(n) time, O(1) space (but two passes)
     */
    public ListNode deleteMiddleBruteForce(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        // First pass: count nodes
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }

        // Find position to delete: middle = count/2 (0-indexed)
        int middleIndex = count / 2;

        // Second pass: go to node before middle
        current = head;
        for (int i = 0; i < middleIndex - 1; i++) {
            current = current.next;
        }

        // Delete middle node
        current.next = current.next.next;

        return head;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [1,3,4,7,1,2,6] → [1,3,4,1,2,6] (delete 7)
 * Example 2: [1,2,3,4] → [1,2,4] (delete 3 - second middle)
 * Example 3: [2,1] → [2] (delete second node)
 * Example 4: [1] → [] (delete only node)
 * Example 5: [1,2,3] → [1,3] (delete middle of three)
 * Example 6: [1,2,3,4,5] → [1,2,4,5] (delete middle of five)
 *
 * PATTERN RECOGNITION:
 * - Fast and slow pointers pattern (Tortoise and Hare)
 * - Similar to: Middle of linked list, but with deletion capability
 * - Core pattern: Fast/slow pointers with previous tracking
 *
 * VARIATIONS/FOLLOW-UPS:
 * - Delete kth node from end (LC19)
 * - Delete node with given value
 * - Delete all occurrences of a value
 * - Delete alternate nodes
 * - Delete nodes having greater value on right
 *
 * STEP-BY-STEP WALKTHROUGH:
 *
 * Example: [1,2,3,4] (even length - delete second middle)
 * Initial: slow=1, fast=1, prev=null
 *
 * Iteration 1:
 *   prev = slow (1)
 *   slow = slow.next (2)
 *   fast = fast.next.next (3)
 *
 * Iteration 2:
 *   prev = slow (2)
 *   slow = slow.next (3)
 *   fast = fast.next.next (null) → stop
 *
 * Result: prev=2, slow=3 (middle to delete)
 * Update: prev.next = slow.next → 2→4
 * Final: [1,2,4]
 *
 * Example: [1,2,3] (odd length - delete exact middle)
 * Initial: slow=1, fast=1, prev=null
 *
 * Iteration 1:
 *   prev = slow (1)
 *   slow = slow.next (2)
 *   fast = fast.next.next (3)
 *
 * Iteration 2: fast.next=null → stop
 * Result: prev=1, slow=2 (middle to delete)
 * Update: prev.next = slow.next → 1→3
 * Final: [1,3]
 *
 * TIPS:
 * 1. Always mention edge cases first (empty, single node)
 * 2. Explain why we need the 'prev' pointer
 * 3. Draw the pointer movements for visualization
 * 4. Test with both odd and even length examples
 * 5. Consider the dummy node approach for cleaner code
 *
 * COMMON MISTAKES:
 * - Forgetting to handle single node case
 * - Not updating links correctly (losing the list)
 * - Off-by-one errors in middle calculation
 * - Not checking both fast and fast.next in loop condition
 */