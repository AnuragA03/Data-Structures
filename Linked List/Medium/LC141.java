/**
 * LC141 - Linked List Cycle
 *
 * PROBLEM TYPE: Linked List, Two Pointers, Hash Table
 * DIFFICULTY: Easy
 *
 * PROBLEM:
 * Given head, the head of a linked list, determine if the linked list has a cycle.
 * A cycle exists if a node's next pointer points to a node that has been visited before.
 *
 * EXAMPLE:
 * Input: head = [3,2,0,-4], pos = 1 (cycle from -4 to 2)
 * Output: true
 *
 * Input: head = [1,2], pos = 0 (cycle from 2 to 1)
 * Output: true
 *
 * Input: head = [1], pos = -1 (no cycle)
 * Output: false
 *
 * INTUITION:
 * We need to detect if a linked list contains a cycle. This can be solved using:
 * 1. Hash Table: Store visited nodes, check if we encounter a node twice
 * 2. Fast & Slow Pointers: If there's a cycle, fast pointer will eventually meet slow pointer
 *
 * BRUTE FORCE APPROACH (Hash Table):
 * - Traverse the list, storing each node in a HashSet
 * - If we encounter a node already in the set, cycle exists
 * - If we reach null, no cycle exists
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(n) - Store all nodes in HashSet
 *
 * OPTIMAL APPROACH (Floyd's Cycle Detection - Fast & Slow Pointers):
 * - Use two pointers: slow moves 1 step, fast moves 2 steps
 * - If there's a cycle, fast will eventually meet slow (like runners on track)
 * - If no cycle, fast will reach null
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Linear time complexity
 * - Space: O(1) - Constant extra space (only two pointers)
 *
 * KEY OBSERVATIONS:
 * 1. If no cycle: fast reaches null in O(n) time
 * 2. If cycle exists: fast will lap slow and they'll meet
 * 3. Meeting point is not necessarily the cycle start point
 * 4. Algorithm works for any cycle length and position
 *
 * EDGE CASES:
 * - Empty list (head == null) → return false
 * - Single node with no cycle → return false
 * - Single node with cycle (pointing to itself) → return true
 * - Cycle at beginning vs cycle at end
 */

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    /**
     * OPTIMAL SOLUTION: Floyd's Cycle Detection Algorithm (Tortoise and Hare)
     *
     * STRATEGY:
     * 1. Initialize both pointers at head
     * 2. Move slow one step, fast two steps in each iteration
     * 3. If fast meets slow, cycle exists
     * 4. If fast reaches null, no cycle exists
     *
     * WHY IT WORKS (MATHEMATICAL PROOF):
     * - Let k be distance from head to cycle start
     * - Let m be distance from cycle start to meeting point
     * - Let n be cycle length
     * - When slow enters cycle, fast is already inside
     * - Fast catches up to slow at relative speed of 1 step per iteration
     * - They will meet after at most n iterations within the cycle
     */
    public boolean hasCycle(ListNode head) {
        // Empty list or single node with no cycle
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Fast moves twice as fast as slow
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move slow one step
            fast = fast.next.next;     // Move fast two steps

            // If they meet, cycle exists
            if (fast == slow) {
                return true;
            }
        }

        // Fast reached null - no cycle
        return false;
    }

    /**
     * HASH TABLE SOLUTION (Alternative approach)
     *
     * STRATEGY:
     * - Store visited nodes in HashSet
     * - If node already visited, cycle exists
     *
     * COMPLEXITY: O(n) time, O(n) space
     */
    public boolean hasCycleHashTable(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        ListNode current = head;

        while (current != null) {
            if (visited.contains(current)) {
                return true; // Cycle detected
            }
            visited.add(current);
            current = current.next;
        }

        return false; // No cycle
    }

    /**
     * DESTRUCTIVE APPROACH (For reference - not recommended)
     *
     * STRATEGY:
     * - Mark visited nodes by setting a special value or breaking links
     * - If we encounter a marked node, cycle exists
     *
     * COMPLEXITY: O(n) time, O(1) space but destroys the list
     */
    public boolean hasCycleDestructive(ListNode head) {
        ListNode current = head;

        while (current != null) {
            if (current.val == Integer.MIN_VALUE) { // Special marker
                return true;
            }
            current.val = Integer.MIN_VALUE; // Mark as visited
            current = current.next;
        }

        return false;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [3,2,0,-4] with pos=1 → true (cycle from -4 to 2)
 * Example 2: [1,2] with pos=0 → true (cycle from 2 to 1)
 * Example 3: [1] with pos=-1 → false (no cycle)
 * Example 4: [] → false (empty list)
 * Example 5: [1,2,3] with cycle 3→1 → true
 * Example 6: [1] with cycle 1→1 → true (self-loop)
 *
 * PATTERN RECOGNITION:
 * - Fast and slow pointers pattern (Floyd's Cycle Detection)
 * - Similar to: Middle of linked list, Find cycle start, Duplicate number detection
 * - Core idea: Different pointer speeds to detect cycles
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC142: Linked List Cycle II (find the cycle start node)
 * - LC287: Find the Duplicate Number (array as linked list cycle)
 * - LC202: Happy Number (cycle detection in number sequence)
 * - Detect cycle in doubly linked list
 * - Find cycle length
 *
 * CYCLE DETECTION SCENARIOS:
 *
 * Scenario 1: No cycle
 * [1→2→3→null]
 * Fast reaches null, returns false
 *
 * Scenario 2: Cycle at end
 * [1→2→3→2] (cycle back to node 2)
 * Slow: 1→2→3→2→3→2...
 * Fast: 1→3→2→3→2→3...
 * They meet at node 2 or 3
 *
 * Scenario 3: Self-loop
 * [1→1] (node points to itself)
 * Slow: 1→1→1...
 * Fast: 1→1→1...
 * They meet immediately at node 1
 *
 * MATHEMATICAL INSIGHTS:
 *
 * Time Complexity Analysis:
 * - Worst case (no cycle): O(n) - fast reaches null in n/2 steps
 * - Worst case (with cycle): O(n) - fast meets slow within 2 cycles
 *
 * Why they must meet in cycle:
 * - When both enter cycle, fast is some distance behind slow
 * - Fast gains 1 step per iteration (2 steps vs 1 step)
 * - Since cycle is finite, fast will eventually catch slow
 *
 * TIPS:
 * 1. Always check for null head first
 * 2. Remember to check both fast != null AND fast.next != null
 * 3. Understand why the algorithm terminates (either null or meeting point)
 * 4. Be prepared to find cycle start (LC142 follow-up)
 * 5. Practice explaining the mathematical intuition
 */