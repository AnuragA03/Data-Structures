/**
 * LC121 - Best Time to Buy and Sell Stock
 *
 * PROBLEM TYPE: Array, Sliding Window
 * DIFFICULTY: Easy
 *
 * PROBLEM:
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing
 * a different day in the future to sell that stock.
 *
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve
 * any profit, return 0.
 *
 * EXAMPLE:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 *
 * INTUITION:
 * The key insight is that we need to find the maximum difference between two prices where
 * the buying price comes before the selling price. This can be solved by tracking the
 * minimum price seen so far and calculating the potential profit at each step.
 *
 * BRUTE FORCE APPROACH:
 * - Try every possible pair of buy and sell days (where buy day < sell day)
 * - For each pair, calculate profit = prices[sell] - prices[buy]
 * - Track the maximum profit found
 *
 * BRUTE FORCE COMPLEXITY:
 * - Time: O(n²) - Nested loops checking all pairs
 * - Space: O(1) - Only using constant extra space
 *
 * OPTIMAL APPROACH (One Pass):
 * - Track the minimum price encountered so far
 * - At each day, calculate potential profit if we sell at current price
 * - Update maximum profit if current profit is better
 * - Update minimum price if current price is lower
 *
 * OPTIMAL COMPLEXITY:
 * - Time: O(n) - Single pass through the array
 * - Space: O(1) - Constant extra space
 *
 * KEY OBSERVATIONS:
 * 1. We only care about the minimum price that occurs BEFORE the current day
 * 2. The selling decision at each day depends on the lowest price seen so far
 * 3. This is essentially a sliding window problem where we maintain the minimum value
 *
 * EDGE CASES:
 * - Empty array or single element array → return 0
 * - Continuously decreasing prices → maximum profit will be 0
 * - All prices same → profit will be 0
 */

class Solution {
    /**
     * OPTIMAL SOLUTION: One Pass Approach
     *
     * STRATEGY:
     * 1. Initialize minPrice to the first price and maxProfit to 0
     * 2. Iterate through each price starting from the second element
     * 3. For each price:
     *    a. Calculate potential profit if we sell at current price
     *    b. Update maxProfit if current profit is better
     *    c. Update minPrice if current price is lower
     *
     * WHY IT WORKS:
     * - By tracking the minimum price so far, we ensure we always buy at the lowest possible price
     * - At each step, we check if selling at current price would yield better profit
     * - This guarantees we find the maximum difference where buy happens before sell
     */
    public int maxProfit(int[] prices) {
        // Handle edge case: if array has less than 2 elements, no transaction possible
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = prices[0];  // Track the minimum buying price seen so far
        int maxProfit = 0;         // Track the maximum profit found

        // Start from day 1 (index 1) since we need at least one day difference
        for (int i = 1; i < prices.length; i++) {
            int currentPrice = prices[i];

            // Calculate profit if we sell at current price (bought at minPrice)
            int currentProfit = currentPrice - minPrice;

            // Update maximum profit if current profit is better
            maxProfit = Math.max(maxProfit, currentProfit);

            // Update minimum price for future buying opportunities
            minPrice = Math.min(minPrice, currentPrice);
        }

        return maxProfit;
    }

    /**
     * BRUTE FORCE SOLUTION (For reference - not optimal)
     *
     * STRATEGY:
     * - Try every possible buy-sell combination where buy day < sell day
     * - Calculate profit for each combination and track maximum
     *
     * COMPLEXITY: O(n²) time, O(1) space
     */
    public int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {
            // Only consider selling days after buying day
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }
}

/**
 * TEST CASES:
 *
 * Example 1: [7,1,5,3,6,4] → 5
 * Example 2: [7,6,4,3,1] → 0 (prices decreasing)
 * Example 3: [1,2,3,4,5] → 4 (prices increasing)
 * Example 4: [2,2,2,2,2] → 0 (all prices same)
 * Example 5: [3,2,1,4,5] → 4 (minimum at middle)
 * Example 6: [1] → 0 (single element)
 * Example 7: [] → 0 (empty array)
 *
 * PATTERN RECOGNITION:
 * - This problem demonstrates the "maximum difference with ordering constraint" pattern
 * - Similar problems: Maximum subarray (Kadane's algorithm), Container with most water
 *
 * VARIATIONS/FOLLOW-UPS:
 * - LC122: Buy and Sell Stock II (multiple transactions)
 * - LC123: Buy and Sell Stock III (at most 2 transactions)
 * - LC188: Buy and Sell Stock IV (at most k transactions)
 * - LC309: Buy and Sell Stock with Cooldown
 * - LC714: Buy and Sell Stock with Transaction Fee
 */