/**
 * LC19 - Remove Nth Node From End of List
 *
 * PROBLEM TYPE: Linked List, Two Pointers
 * DIFFICULTY: Medium
 *
 * PROBLEM:
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 *
 * EXAMPLE:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 *
 * Input: head = [1], n = 1
 * Output: []
 *
 * Input: head = [1,2], n = 1
 * Output: [1]
 *
 * INTUITION:
 * We need to remove the nth node from the end. The challenge is that we don't know the length
 * of the list, so we need to find the target node in one pass. The two-pointer technique allows
 * us to maintain a gap of n nodes between front and back pointers.
 *
 * BRUTE FORCE APPROACH:
 * - First pass: Count the number of nodes (L)
 * - Second pass: Traverse to the (L - n)th node and remove the next node
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) + O(n) = O(n) - Two passes through the list
 * - Space: O(1) - Constant extra space
 *
 * OPTIMAL APPROACH (Two Pointers - One Pass):
 * - Use two pointers: front and back with n nodes gap
 * - Move front pointer n steps ahead first
 * - Then move both pointers until front reaches end
 * - Back will be at the node before the target node
 * - Remove the target node by updating back.next
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(1) - Constant extra space
 *
 * KEY OBSERVATIONS:
 * 1. When front is n steps ahead, back will be at (L-n)th node when front reaches end
 * 2. Need special handling when removing the head node (n == length of list)
 * 3. Using a dummy node can simplify edge cases
 * 4. The gap between front and back should be n nodes
 *
 * EDGE CASES:
 * - Empty list → return null
 * - Single node with n=1 → return null
 * - Removing head node (n = length of list)
 * - n larger than list length (problem guarantees n <= size)
 */

class Solution {
    /**
     * OPTIMAL SOLUTION: Two Pointers with Gap of N
     *
     * STRATEGY:
     * 1. Handle base cases (empty list, single node with n=1)
     * 2. Move front pointer n steps ahead
     * 3. If front becomes null, we're removing the head
     * 4. Move both pointers until front reaches end
     * 5. Back will be at node before target, remove target node
     *
     * WHY IT WORKS:
     * - Maintaining n nodes gap ensures back reaches (L-n)th node
     * - When front reaches end, back is exactly before the node to remove
     * - Special case handling for head removal
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Base cases
        if (head == null) {
            return null;
        }
        if (head.next == null && n >= 1) {
            return null;
        }

        ListNode front = head;
        ListNode back = head;
        ListNode prevBack = new ListNode(-1); // Dummy node to handle head removal
        prevBack.next = head;

        // Move front pointer n steps ahead
        for (int i = 0; i < n; i++) {
            front = front.next;
        }

        // If front becomes null, we're removing the head element
        if (front == null) {
            return head.next;
        }

        // Move both pointers until front reaches end
        while (front != null) {
            prevBack = back;
            back = back.next;
            front = front.next;
        }

        // Remove the target node (back is the node to remove)
        prevBack.next = back.next;
        return head;
    }

    /**
     * CLEANER APPROACH: Using Dummy Node
     *
     * STRATEGY:
     * - Use dummy node to simplify head removal case
     * - Both pointers start from dummy
     * - No special case handling needed
     *
     * COMPLEXITY: O(n) time, O(1) space
     */
    public ListNode removeNthFromEndClean(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode front = dummy;
        ListNode back = dummy;

        // Move front n+1 steps to create gap of n nodes between front and back
        for (int i = 0; i <= n; i++) {
            front = front.next;
        }

        // Move both until front reaches end
        while (front != null) {
            front = front.next;
            back = back.next;
        }

        // Remove the nth node from end
        back.next = back.next.next;

        return dummy.next;
    }

    /**
     * BRUTE FORCE SOLUTION (For reference - not optimal)
     *
     * STRATEGY:
     * - First pass: count total nodes
     * - Second pass: traverse to node before target and remove
     *
     * COMPLEXITY: O(n) time, O(1) space (but two passes)
     */
    public ListNode removeNthFromEndBruteForce(ListNode head, int n) {
        if (head == null) return null;

        // First pass: count nodes
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }

        // Calculate position to remove (0-indexed)
        int positionFromStart = length - n;

        // If removing head
        if (positionFromStart == 0) {
            return head.next;
        }

        // Second pass: go to node before target
        current = head;
        for (int i = 0; i < positionFromStart - 1; i++) {
            current = current.next;
        }

        // Remove target node
        current.next = current.next.next;

        return head;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [1,2,3,4,5], n=2 → [1,2,3,5] (remove 4)
 * Example 2: [1], n=1 → [] (remove only node)
 * Example 3: [1,2], n=1 → [1] (remove last node)
 * Example 4: [1,2], n=2 → [2] (remove head)
 * Example 5: [1,2,3], n=3 → [2,3] (remove head)
 * Example 6: [1,2,3,4,5], n=5 → [2,3,4,5] (remove head of long list)
 *
 * PATTERN RECOGNITION:
 * - Two pointers with fixed gap pattern
 * - Similar to: Middle of linked list, but with variable gap
 * - Core pattern: Maintain n nodes distance between pointers
 *
 * VARIATIONS/FOLLOW-UPS:
 * - Remove nth node from beginning (trivial)
 * - Remove all occurrences of a value
 * - Remove duplicates from sorted list
 * - Remove every kth node
 *
 * STEP-BY-STEP WALKTHROUGH:
 *
 * Example: [1,2,3,4,5], n=2
 * Initial: front=1, back=1, prevBack→1
 *
 * Step 1: Move front 2 steps: front=3
 * Step 2: Since front≠null, move both until front=null
 *   - front=3, back=1, prevBack→1
 *   - front=4, back=2, prevBack=1
 *   - front=5, back=3, prevBack=2
 *   - front=null, back=4, prevBack=3 → stop
 *
 * Result: Remove back (4) by setting prevBack.next=back.next → 3→5
 * Final: [1,2,3,5]
 *
 * Example: [1,2], n=2 (remove head)
 * Initial: front=1, back=1, prevBack→1
 * Step 1: Move front 2 steps: front=null
 * Step 2: Since front=null, return head.next → [2]
 *
 * TIPS:
 * 1. Always mention the two-pass approach first (shows understanding)
 * 2. Explain why the one-pass approach is better
 * 3. Use dummy node to simplify edge cases
 * 4. **Test with head removal and tail removal cases**
 * 5. Draw the pointer movements for clarity
 *
 * COMMON MISTAKES:
 * - Not handling head removal case separately**
 * - Off-by-one errors in the gap calculation
 * - Not checking for null pointers after moving front
 * - Forgetting to update the prev pointer correctly
 */