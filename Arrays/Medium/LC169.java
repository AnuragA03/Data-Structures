/**
 * LC169 - Majority Element
 *
 * PROBLEM TYPE: Array, Hash Table, Divide and Conquer, Sorting, Counting
 * DIFFICULTY: Easy
 *
 * PROBLEM:
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 *
 * EXAMPLE:
 * Input: nums = [3,2,3]
 * Output: 3
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 *
 * INTUITION:
 * We need to find the element that occurs more than n/2 times. Since it appears more than
 * half the time, it will always be the most frequent element and will survive cancellation
 * with other elements.
 *
 * BRUTE FORCE APPROACH:
 * - Count frequency of each element using nested loops
 * - For each element, count how many times it appears in the array
 * - Return the element with count > n/2
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n²) - For each element, scan the entire array
 * - Space: O(1) - Only using constant extra space
 *
 * BETTER APPROACH (Hash Map):
 * - Use HashMap to store element frequencies
 * - One pass to count, another pass to find majority
 * - Time: O(n), Space: O(n)
 *
 * BETTER APPROACH (Sorting):
 * - Sort the array, majority element will be at n/2 position
 * - Time: O(n log n), Space: O(1) or O(n) depending on sort
 *
 * OPTIMAL APPROACH (Boyer-Moore Voting Algorithm):
 * - Track a candidate and count
 * - Increment count when seeing candidate, decrement when seeing other
 * - Majority element will survive the cancellation process
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Single pass through the array
 * - Space: O(1) - Constant extra space
 *
 * KEY OBSERVATIONS:
 * 1. Majority element appears more than all other elements combined
 * 2. It will survive pairwise cancellation with other elements
 * 3. After cancellation, the majority element will remain
 *
 * EDGE CASES:
 * - Array with single element → return that element
 * - All elements same → return that element
 * - Minimum case: n=1, n=3 with majority
 */

import java.util.*;

class Solution {
    /**
     * OPTIMAL SOLUTION: Boyer-Moore Voting Algorithm
     *
     * STRATEGY:
     * 1. Initialize candidate and count = 0
     * 2. For each element in array:
     *    - If count == 0, set current element as candidate
     *    - If element == candidate, increment count
     *    - Else decrement count
     * 3. Return candidate (guaranteed to be majority element)
     *
     * WHY IT WORKS:
     * - Majority element appears more than n/2 times
     * - Each non-majority element can cancel at most one majority element
     * - Since majority > n/2, it will survive the cancellation process
     * - The algorithm implements this cancellation conceptually
     */
    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    /**
     * HASH MAP SOLUTION (Better approach - more intuitive)
     *
     * STRATEGY:
     * - Count frequency of each element using HashMap
     * - Return element with frequency > n/2
     *
     * COMPLEXITY: O(n) time, O(n) space
     */
    public int majorityElementHashMap(int[] nums) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int majorityThreshold = nums.length / 2;

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
            if (frequencyMap.get(num) > majorityThreshold) {
                return num;
            }
        }

        return -1; // Should never reach here due to problem assumption
    }

    /**
     * SORTING SOLUTION (Simple approach)
     *
     * STRATEGY:
     * - Sort the array
     * - Majority element will always be at n/2 index since it appears more than half the time
     *
     * COMPLEXITY: O(n log n) time, O(1) space (if sorting in-place)
     */
    public int majorityElementSorting(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * BRUTE FORCE SOLUTION (For reference - not optimal)
     *
     * STRATEGY:
     * - For each element, count its frequency by scanning entire array
     * - Return first element with frequency > n/2
     *
     * COMPLEXITY: O(n²) time, O(1) space
     */
    public int majorityElementBruteForce(int[] nums) {
        int majorityCount = nums.length / 2;

        for (int num : nums) {
            int count = 0;
            for (int elem : nums) {
                if (elem == num) {
                    count++;
                }
            }
            if (count > majorityCount) {
                return num;
            }
        }

        return -1; // Should never reach here
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [3,2,3] → 3
 * Example 2: [2,2,1,1,1,2,2] → 2
 * Example 3: [1] → 1 (single element)
 * Example 4: [5,5,5,5,5,1,2,3,4] → 5 (clear majority)
 * Example 5: [1,1,2,2,1,1,2] → 1 (exactly n/2 + 1 occurrences)
 * Example 6: [0,0,0,0,1,1,1] → 0 (majority at beginning)
 *
 * PATTERN RECOGNITION:
 * - Frequency counting problem with majority constraint
 * - Boyer-Moore algorithm pattern: cancellation and survival
 * - Similar problems: Majority Element II, Find the Dominant Index
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC229: Majority Element II (elements appearing more than n/3 times)
 * - LC1150: Check If a Number Is Majority Element in a Sorted Array
 * - LC2780: Minimum Index of a Valid Split (related to majority elements)
 * - When majority element may not exist (need verification step)
 *
 * ALGORITHM COMPARISON:
 *
 * Boyer-Moore Voting:
 * - Time: O(n), Space: O(1) - Optimal for this problem
 * - Best when majority element guaranteed to exist
 *
 * Hash Map:
 * - Time: O(n), Space: O(n) - More generalizable
 * - Works even when majority may not exist (with verification)
 * - Easy to understand and implement
 *
 * Sorting:
 * - Time: O(n log n), Space: O(1) or O(n)
 * - Simple one-liner but slower for large n
 *
 * Brute Force:
 * - Time: O(n²), Space: O(1) - Only for small inputs
 * - Demonstrates basic understanding but not practical
 *
 * INTERVIEW TIPS:
 * 1. Start with brute force to show understanding
 * 2. Mention Hash Map approach as improvement
 * 3. Introduce Boyer-Moore as optimal solution
 * 4. Explain why Boyer-Moore works (cancellation principle)
 * 5. Discuss follow-ups if majority element may not exist
 */