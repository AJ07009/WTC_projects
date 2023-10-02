import unittest
from unittest.mock import patch
from io import StringIO
from mastermind import *

class TestFunctions(unittest.TestCase):
    def test_create_code_inbounds(self):
        code = create_code()
        code = list(map(str, code))
        c = "".join(code)   
        pattern= "^[1-8]{4}$"
        self.assertRegex(c, pattern)


    def test_create_code_unique(self):
        code = create_code()
        code = list(map(str, code))
        code_prev = "".join(code)
        for i in range(100):
            code = create_code()
            code = list(map(str, code))
            code = "".join(code)
            self.assertNotEqual(code_prev, code)
            code_prev = code


    def test_create_code_no_rep(self):
        for i in range(100):
            code = create_code()
            for i in range(4):
                c = set(code)
                self.assertEqual(4, len(c))


    def test_correctness_true(self):
        val = check_correctness(11, 4)
        self.assertTrue(val)


    def test_correctness_false(self):
        val = check_correctness(11, 2)
        self.assertFalse(val)


    @patch("sys.stdin", StringIO("12h5d\n1234\n"))
    def test_get_answer_input(self):
        answer = get_answer_input()
        self.assertEqual("1234", answer)


    @patch("sys.stdin", StringIO("12h5d\n1234\n"))
    def test_turn_valid(self):
        i, o = take_turn([1,2,3,4])
        self.assertEqual(4, i)


    @patch("sys.stdin", StringIO("12h5d\n4321\n"))
    def test_turn_invalid(self):
        i, o = take_turn([1,2,3,4])
        self.assertEqual(4, o)


if __name__ == '__main__':
    unittest.main()