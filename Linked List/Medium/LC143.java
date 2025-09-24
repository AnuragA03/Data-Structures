/**
 * LC142 - Linked List Cycle II
 *
 * PROBLEM TYPE: Linked List, Two Pointers, Hash Table
 * DIFFICULTY: Medium
 *
 * PROBLEM:
 * Given the head of a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 *
 * EXAMPLE:
 * Input: head = [3,2,0,-4], pos = 1 (cycle from -4 to 2)
 * Output: Node with value 2
 *
 * Input: head = [1,2], pos = 0 (cycle from 2 to 1)
 * Output: Node with value 1
 *
 * Input: head = [1], pos = -1 (no cycle)
 * Output: null
 *
 * INTUITION:
 * This is an extension of LC141 (cycle detection). After detecting a cycle exists,
 * we need to find the exact node where the cycle begins. The mathematical insight is that
 * the distance from head to cycle start equals the distance from meeting point to cycle start.
 *
 * BRUTE FORCE APPROACH (Hash Table):
 * - Traverse the list, storing each node in a HashSet
 * - The first node we encounter that's already in the set is the cycle start
 * - If we reach null, no cycle exists
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) - Single pass through the list
 * - Space: O(n) - Store all nodes in HashSet
 *
 * OPTIMAL APPROACH (Floyd's Cycle Detection - Two Phase):
 * - Phase 1: Detect if cycle exists using fast/slow pointers
 * - Phase 2: Reset one pointer to head, move both at same speed until they meet
 * - The meeting point is the cycle start node
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Linear time complexity
 * - Space: O(1) - Constant extra space (only two pointers)
 *
 * KEY MATHEMATICAL INSIGHT:
 * Let:
 * - k = distance from head to cycle start
 * - m = distance from cycle start to meeting point
 * - n = cycle length
 *
 * When slow and fast meet:
 * - Slow distance = k + m
 * - Fast distance = k + m + p*n (where p is some integer ≥ 1)
 * - Since fast moves 2x speed: 2(k + m) = k + m + p*n
 * - Simplifies to: k + m = p*n → k = p*n - m
 * - This means: distance from head to start = multiple of cycle length minus m
 * - Therefore, moving from head and meeting point at same speed will meet at cycle start
 *
 * EDGE CASES:
 * - Empty list (head == null) → return null
 * - Single node with no cycle → return null
 * - Single node with cycle (pointing to itself) → return head
 * - Cycle starting at head vs cycle starting later
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
     * OPTIMAL SOLUTION: Floyd's Cycle Detection Algorithm (Two Phase)
     *
     * STRATEGY:
     * Phase 1: Detect cycle and find meeting point
     * Phase 2: Find cycle start by moving from head and meeting point at same speed
     *
     * WHY IT WORKS (MATHEMATICAL PROOF):
     * - Let k = distance from head to cycle start
     * - Let m = distance from cycle start to meeting point
     * - Let n = cycle length
     * - From phase 1: fast = 2 * slow → k + m + p*n = 2(k + m)
     * - Simplifies to: k = p*n - m
     * - This means pointer from head will meet pointer from meeting point at cycle start
     */
    public ListNode detectCycle(ListNode head) {
        // Empty list or single node with no cycle
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;

        // Phase 1: Detect cycle and find meeting point
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move slow one step
            fast = fast.next.next;     // Move fast two steps

            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }

        // No cycle detected
        if (!hasCycle) {
            return null;
        }

        // Phase 2: Find cycle start
        // Reset fast to head, keep slow at meeting point
        fast = head;

        // Move both pointers one step at a time until they meet
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // Meeting point is the cycle start
        return slow;
    }

    /**
     * HASH TABLE SOLUTION (Alternative approach - more intuitive)
     *
     * STRATEGY:
     * - Store visited nodes in HashSet
     * - The first duplicate node encountered is the cycle start
     *
     * COMPLEXITY: O(n) time, O(n) space
     */
    public ListNode detectCycleHashTable(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        ListNode current = head;

        while (current != null) {
            if (visited.contains(current)) {
                return current; // First duplicate is cycle start
            }
            visited.add(current);
            current = current.next;
        }

        return null; // No cycle
    }

    /**
     * VISUAL EXPLANATION WITH EXAMPLE:
     *
     * Example: [3,2,0,-4] with cycle at node 2
     *
     * Phase 1: Detect cycle and find meeting point
     * Step 0: slow=3, fast=3
     * Step 1: slow=2, fast=0
     * Step 2: slow=0, fast=2
     * Step 3: slow=-4, fast=-4 → MEET!
     *
     * Phase 2: Find cycle start
     * Reset: fast=3, slow=-4
     * Step 1: fast=2, slow=2 → MEET at cycle start!
     *
     * Mathematical validation:
     * k (head to start) = 1 (3→2)
     * m (start to meet) = 2 (2→0→-4)
     * n (cycle length) = 3 (2→0→-4→2)
     * k = p*n - m = 1*3 - 2 = 1 ✓
     */
}

/**
 * TEST CASES:
 *
 * Example 1: [3,2,0,-4] with pos=1 → Node with value 2
 * Example 2: [1,2] with pos=0 → Node with value 1
 * Example 3: [1] with pos=-1 → null
 * Example 4: [] → null
 * Example 5: [1,2,3] with cycle 3→1 → Node with value 1
 * Example 6: [1] with cycle 1→1 → Node with value 1 (self-loop)
 * Example 7: [1,2,3,4] with cycle 4→2 → Node with value 2
 *
 * PATTERN RECOGNITION:
 * - Extension of Floyd's Cycle Detection algorithm
 * - Two-phase approach: detection + localization
 * - Similar mathematical patterns in: Duplicate number detection, Circular array problems
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC141: Linked List Cycle (detection only)
 * - LC287: Find the Duplicate Number (cycle detection in array)
 * - Find cycle length (distance between meeting point and next meeting)
 * - Detect cycle in doubly linked list
 * - Find the node before cycle start
 *
 * ALGORITHM COMPARISON:
 *
 * Floyd's Algorithm (Optimal):
 * - Time: O(n), Space: O(1)
 * - Requires mathematical understanding
 * - More elegant and space-efficient
 *
 * Hash Table Approach:
 * - Time: O(n), Space: O(n)
 * - More intuitive and easier to implement
 * - Directly finds cycle start without extra logic
 *
 * TIPS:
 * 1. Start with hash table approach if stuck (shows problem understanding)
 * 2. Explain the mathematical intuition for Floyd's algorithm
 * 3. Draw the pointer movements for better visualization
 * 4. Handle edge cases: empty list, single node, no cycle
 * 5. Practice explaining why k = p*n - m works
 *
 * COMMON MISTAKES:
 * - Forgetting to check both fast != null AND fast.next != null
 * - Not handling the case where head is the cycle start
 * - Incorrect pointer reset in phase 2
 * - Assuming meeting point is the cycle start (it's not!)
 */