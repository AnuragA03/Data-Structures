/**
 * LC328 - Odd Even Linked List
 *
 * PROBLEM TYPE: Linked List, Two Pointers, In-place Rearrangement
 * DIFFICULTY: Medium
 *
 * PROBLEM:
 * Given the head of a singly linked list, group all odd-indexed nodes together
 * followed by the even-indexed nodes. The first node is considered odd, second even.
 *
 * Note: Indexing starts from 1 (not 0). Return the reordered list.
 *
 * EXAMPLE:
 * Input: 1 → 2 → 3 → 4 → 5
 * Output: 1 → 3 → 5 → 2 → 4
 *
 * Input: 2 → 1 → 3 → 5 → 6 → 4 → 7
 * Output: 2 → 3 → 6 → 7 → 1 → 5 → 4
 *
 * INTUITION:
 * We need to separate the list into two groups: odd-indexed nodes and even-indexed nodes,
 * then concatenate the odd list with the even list. The challenge is doing this in-place
 * with O(1) extra space and O(n) time.
 *
 * BRUTE FORCE APPROACH:
 * - Create two new lists: one for odd nodes, one for even nodes
 * - Traverse original list, adding nodes to appropriate new list
 * - Concatenate the two new lists
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(n) - Creating new nodes or storing references
 *
 * OPTIMAL APPROACH (In-place Rearrangement):
 * - Use two pointers: one for odd list, one for even list
 * - Traverse and rearrange links in-place
 * - Connect odd list tail to even list head
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(1) - Constant extra space (only pointers)
 *
 * KEY OBSERVATIONS:
 * 1. Odd and even nodes are alternating in the original list
 * 2. We can rearrange links without creating new nodes
 * 3. Need to preserve the head of the even list to connect later
 * 4. Careful handling of null pointers is crucial
 *
 * EDGE CASES:
 * - Empty list → return null
 * - Single node → return head (no even nodes)
 * - Two nodes → simple rearrangement
 * - Odd number of nodes vs even number of nodes
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
     * OPTIMAL SOLUTION: In-place Separation and Concatenation
     *
     * STRATEGY:
     * 1. Handle base cases (empty list, single node)
     * 2. Initialize pointers: oddHead, evenHead, evenStart
     * 3. Traverse and rearrange links to separate odd and even nodes
     * 4. Connect odd list tail to even list head
     *
     * WHY IT WORKS:
     * - Odd and even nodes are naturally alternating
     * - By updating next pointers carefully, we can separate them in one pass
     * - The even list head needs to be preserved for final connection
     */
    public ListNode oddEvenList(ListNode head) {
        // Base cases: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }

        ListNode oddHead = head;
        ListNode evenHead = head.next;
        ListNode evenStart = head.next; // Preserve start of even list

        // Traverse and rearrange links
        while (evenHead != null && evenHead.next != null) {
            // Connect odd node to next odd node (skip even)
            oddHead.next = evenHead.next;
            oddHead = oddHead.next;

            // Connect even node to next even node (skip odd)
            evenHead.next = oddHead.next;
            evenHead = evenHead.next;
        }

        // Connect end of odd list to start of even list
        oddHead.next = evenStart;

        return head;
    }

    /**
     * BRUTE FORCE SOLUTION (For reference - not optimal)
     *
     * STRATEGY:
     * - Create two new lists using dummy nodes
     * - Traverse original list, appending to appropriate list
     * - Concatenate the two lists
     *
     * COMPLEXITY: O(n) time, O(1) space (but creates dummy nodes)
     */
    public ListNode oddEvenListBruteForce(ListNode head) {
        if (head == null) return null;

        ListNode oddDummy = new ListNode(0);
        ListNode evenDummy = new ListNode(0);
        ListNode oddTail = oddDummy;
        ListNode evenTail = evenDummy;

        ListNode current = head;
        int index = 1;

        while (current != null) {
            if (index % 2 == 1) { // Odd index
                oddTail.next = current;
                oddTail = oddTail.next;
            } else { // Even index
                evenTail.next = current;
                evenTail = evenTail.next;
            }
            current = current.next;
            index++;
        }

        // Terminate both lists
        oddTail.next = null;
        evenTail.next = null;

        // Connect odd list to even list
        oddTail.next = evenDummy.next;

        return oddDummy.next;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [1,2,3,4,5] → [1,3,5,2,4]
 * Example 2: [2,1,3,5,6,4,7] → [2,3,6,7,1,5,4]
 * Example 3: [1] → [1] (single node)
 * Example 4: [1,2] → [1,2] (two nodes - already correct)
 * Example 5: [1,2,3] → [1,3,2] (three nodes)
 * Example 6: [] → [] (empty list)
 * Example 7: [1,2,3,4] → [1,3,2,4] (even number of nodes)
 *
 * PATTERN RECOGNITION:
 * - Linked list separation and concatenation
 * - Two-pointer technique for alternating patterns
 * - Similar to: Partition List, Split Linked List in Parts
 * - Core pattern: Maintain two separate lists in-place
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC86: Partition List (separate by value instead of index)
 * - Split linked list into k parts
 * - Rearrange list in different patterns (zig-zag, etc.)
 * - Group nodes by index modulo k
 *
 * STEP-BY-STEP WALKTHROUGH:
 *
 * Example: [1,2,3,4,5]
 * Initial: odd=1, even=2, evenStart=2
 *
 * Iteration 1:
 *   odd.next = even.next (3) → 1→3
 *   odd = 3
 *   even.next = odd.next (4) → 2→4
 *   even = 4
 *
 * Iteration 2:
 *   odd.next = even.next (5) → 3→5
 *   odd = 5
 *   even.next = odd.next (null) → 4→null
 *   even = null → stop
 *
 * Final: Connect 5→2 → [1,3,5,2,4]
 *
 * TIPS:
 * 1. Always handle base cases first (empty, single node)
 * 2. Draw the pointer movements for better visualization
 * 3. Explain why we need to preserve evenStart
 * 4. Mention the time/space complexity advantages
 * 5. Test with both odd and even length lists
 *
 * COMMON MISTAKES:
 * - Forgetting to terminate the even list properly
 * - Losing the reference to even list head
 * - Not handling the last node correctly
 * - Infinite loops due to incorrect pointer updates
 */