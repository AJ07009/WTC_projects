import unittest
from super_algos import*

class TestSum(unittest.TestCase):

    def test_step1_find_min_normal(self):
        result = find_min([3,5,6,1,10])
        self.assertEqual(1, result, "Should be 1")

    def test_step1_find_min_one_element(self):
        result = find_min([3])
        self.assertEqual(3, result, "Should be 3")

    def test_step1_find_min_negative(self):
        result = find_min([3,100,-101,4,-5])
        self.assertEqual(-101, result, "Should be -101")

    def test_step1_find_min_invalid_elements(self):
        result = find_min(['a',100,'b',4,-5])
        self.assertEqual(-1, result, "Should be -1")

    def test_step2_sum_all_normal(self):
        result = sum_all([1,2,3,4,5])
        self.assertEqual(15, result, "Should be 15")

    def test_step2_sum_all_one_element(self):
        result = sum_all([3])
        self.assertEqual(3, result, "Should be 3")

    def test_step2_sum_all_negative(self):
        result = sum_all([-1,-2,-3])
        self.assertEqual(-6, result, "Should be -6")

    def test_step2_sum_all_invalid_elements(self):
        result = sum_all(['a',100,'b',4,-5])
        self.assertEqual(-1, result, "Should be -1")


if __name__ == '__main__':
    unittest.main()
