/**
 * LC2 - Add Two Numbers
 *
 * PROBLEM TYPE: Linked List, Math, Simulation
 * DIFFICULTY: Medium
 *
 * PROBLEM:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * EXAMPLE:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807
 *
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 *
 * INTUITION:
 * We need to simulate the process of adding two numbers digit by digit, handling carry-over.
 * Since the digits are stored in reverse order, we can add corresponding digits starting from
 * the head (least significant digit) and propagate the carry to more significant digits.
 *
 * BRUTE FORCE APPROACH (Not Recommended):
 * - Convert both linked lists to numbers
 * - Add the numbers
 * - Convert result back to reversed linked list
 * - Problem: Can overflow with large numbers
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n) - But risk of integer overflow
 * - Space: O(1) - But impractical for large numbers
 *
 * OPTIMAL APPROACH (Digit-by-Digit Addition):
 * - Traverse both lists simultaneously
 * - Add corresponding digits plus carry from previous step
 * - Create new node with sum % 10, update carry = sum / 10
 * - Continue until both lists exhausted and carry is 0
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(max(m,n)) - Where m,n are lengths of the two lists
 * - Space: O(max(m,n)) - For the result list (not counting input lists)
 *
 * KEY OBSERVATIONS:
 * 1. Digits are stored in reverse order (head = least significant digit)
 * 2. This makes addition straightforward from left to right
 * 3. Need to handle different length lists
 * 4. Final carry might require an extra node
 * 5. No leading zeros except for the number 0 itself
 *
 * EDGE CASES:
 * - One list longer than the other
 * - Final carry requires new node (e.g., 5 + 5 = 10)
 * - One or both lists are zero
 * - Lists of very different lengths
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
     * OPTIMAL SOLUTION: Digit-by-Digit Addition with Carry
     *
     * STRATEGY:
     * 1. Initialize dummy head and current pointer for result
     * 2. Traverse both lists while either has nodes or carry exists
     * 3. Sum = digit1 + digit2 + carry
     * 4. Create new node with sum % 10, update carry = sum / 10
     * 5. Move pointers forward for next digit
     *
     * WHY IT WORKS:
     * - Simulates manual addition from least to most significant digit
     * - Handles different length lists by treating missing digits as 0
     * - Properly propagates carry to more significant digits
     * - Handles final carry by creating extra node if needed
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0); // Dummy head to simplify logic
        ListNode current = dummyHead;
        int carry = 0;

        // Continue while either list has nodes or there's a carry
        while (l1 != null || l2 != null || carry != 0) {
            // Get current digits (0 if list exhausted)
            int digit1 = (l1 != null) ? l1.val : 0;
            int digit2 = (l2 != null) ? l2.val : 0;

            // Calculate sum and new carry
            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            int digit = sum % 10;

            // Create new node for current digit
            current.next = new ListNode(digit);
            current = current.next;

            // Move to next digits in input lists
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummyHead.next;
    }

    /**
     * ALTERNATIVE APPROACH: Without Dummy Node
     *
     * STRATEGY:
     * - Handle first node separately to avoid dummy
     * - More complex but saves one node
     *
     * COMPLEXITY: Same as optimal approach
     */
    public ListNode addTwoNumbersWithoutDummy(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;

        ListNode head = null;
        ListNode current = null;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int digit1 = (l1 != null) ? l1.val : 0;
            int digit2 = (l2 != null) ? l2.val : 0;

            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            int digit = sum % 10;

            ListNode newNode = new ListNode(digit);

            if (head == null) {
                head = newNode;
                current = head;
            } else {
                current.next = newNode;
                current = current.next;
            }

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return head;
    }

    /**
     * RECURSIVE APPROACH (Elegant but O(n) stack space)
     *
     * STRATEGY:
     * - Recursively add digits and propagate carry
     * - Base case: both lists null and carry 0
     *
     * COMPLEXITY: O(max(m,n)) time, O(max(m,n)) space (call stack)
     */
    public ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
        return addTwoNumbersHelper(l1, l2, 0);
    }

    private ListNode addTwoNumbersHelper(ListNode l1, ListNode l2, int carry) {
        // Base case: no more digits and no carry
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        // Calculate current digit and carry
        int digit1 = (l1 != null) ? l1.val : 0;
        int digit2 = (l2 != null) ? l2.val : 0;
        int sum = digit1 + digit2 + carry;

        // Create current node
        ListNode currentNode = new ListNode(sum % 10);
        int newCarry = sum / 10;

        // Recursively build the rest of the list
        ListNode next1 = (l1 != null) ? l1.next : null;
        ListNode next2 = (l2 != null) ? l2.next : null;
        currentNode.next = addTwoNumbersHelper(next1, next2, newCarry);

        return currentNode;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [2,4,3] + [5,6,4] = [7,0,8] (342 + 465 = 807)
 * Example 2: [0] + [0] = [0] (0 + 0 = 0)
 * Example 3: [9,9,9,9,9,9,9] + [9,9,9,9] = [8,9,9,9,0,0,0,1] (9999999 + 9999 = 10009998)
 * Example 4: [1] + [9,9,9] = [0,0,0,1] (1 + 999 = 1000)
 * Example 5: [5] + [5] = [0,1] (5 + 5 = 10)
 * Example 6: [1,0,0] + [1] = [2,0,0] (100 + 1 = 101? Wait, reverse: 001 + 1 = 002 → [2,0,0])
 *
 * PATTERN RECOGNITION:
 * - Digit-by-digit arithmetic simulation
 * - Two-pointer technique for multiple lists
 * - Carry propagation in number systems
 * - Similar to: Add Strings, Add Binary, Multiply Strings
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC445: Add Two Numbers II (digits stored in forward order)
 * - LC67: Add Binary
 * - LC415: Add Strings
 * - LC43: Multiply Strings
 * - Add multiple numbers (k linked lists)
 * - Subtract two numbers
 *
 * STEP-BY-STEP WALKTHROUGH:
 *
 * Example: [2,4,3] + [5,6,4]
 *
 * Iteration 1:
 *   digit1=2, digit2=5, carry=0
 *   sum=7, digit=7, carry=0
 *   Result: [7]
 *
 * Iteration 2:
 *   digit1=4, digit2=6, carry=0
 *   sum=10, digit=0, carry=1
 *   Result: [7,0]
 *
 * Iteration 3:
 *   digit1=3, digit2=4, carry=1
 *   sum=8, digit=8, carry=0
 *   Result: [7,0,8]
 *
 * Final: [7,0,8] (which represents 807)
 *
 * TIPS:
 * 1. Start by explaining the reverse order advantage
 * 2. Use dummy head to simplify edge cases
 * 3. Handle different length lists gracefully
 * 4. Don't forget the final carry
 * 5. Test with carry propagation cases (9+1, 99+1, etc.)
 *
 * COMMON MISTAKES:
 * - Forgetting to handle final carry
 * - Not handling different length lists
 * - Incorrect carry calculation
 * - Creating result list in wrong order
 * - Not using dummy head (makes code more complex)
 */