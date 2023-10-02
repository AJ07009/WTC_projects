import unittest
from robot import *
from collections import Counter
import re


class Tests(unittest.TestCase):
    def test_command_list_normal(self):
        l = ['first', 'second']
        c = get_command_list(None, l)
        self.assertEqual(Counter(l), Counter(c))


    def test_command_list_reverse(self):
        l = ['first', 'second']
        l.reverse()
        c = get_command_list(None, l)
        self.assertEqual(Counter(l), Counter(c))


    def test_command_list_normal_range(self):
        l = ['first', 'second']
        c = get_command_list("1", l)
        self.assertEqual(Counter(l[1:]), Counter(c))


    def test_command_list_reverse_range(self):
        l = ['first', 'second']
        l.reverse()
        c = get_command_list("1", l)
        self.assertEqual(Counter(l[1:]), Counter(c))


    def test_command_list_normal_range_two(self):
        l = ['first', 'second', 'third', 'fourth']
        c = get_command_list("4-2", l)
        self.assertEqual(Counter(l[2:0:-1]), Counter(c))


    def test_command_list_reverse_range_two(self):
        l = ['first', 'second', 'third', 'fourth']
        l.reverse()
        c = get_command_list("4-2", l)
        self.assertEqual(Counter(l[2:0:-1]), Counter(c))

    def test_regex_normal(self):
        pattern = "^(replay)( \d+(-\d+)?)?$"
        txt = "replay"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_normal_range(self):
        pattern = "^(replay)( \d+(-\d+)?)?$"
        txt = "replay 2"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_silent(self):
        pattern = "^(replay( \d+(-\d+)?)? silent)$"
        txt = "replay silent"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_silent_range(self):
        pattern = "^(replay( \d+(-\d+)?)? silent)$"
        txt = "replay 3 silent"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_reverse(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed)$"
        txt = "replay reversed"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_reverse_range(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed)$"
        txt = "replay 4 reversed"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_reverse_silent(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed silent)$"
        txt = "replay reversed silent"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_reverse_silent_range(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed silent)$"
        txt = "replay 5 reversed silent"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_normal_range(self):
        pattern = "^(replay)( \d+(-\d+)?)?$"
        txt = "replay 22-16"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_silent_range(self):
        pattern = "^(replay( \d+(-\d+)?)? silent)$"
        txt = "replay 3-1 silent"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_reverse_range(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed)$"
        txt = "replay 4-1 reversed"
        self.assertTrue(re.match(pattern, txt))


    def test_regex_reverse_silent_range(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed silent)$"
        txt = "replay 5-2 reversed silent"
        self.assertTrue(re.match(pattern, txt))
    

    def test_regex_normal_invalid(self):
        pattern = "^(replay)( \d+(-\d+)?)?$"
        txt = "replay 2khndfdb"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_normal_range_invalid(self):
        pattern = "^(replay)( \d+(-\d+)?)?$"
        txt = "replay f"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_silent_invalid(self):
        pattern = "^(replay( \d+(-\d+)?)? silent)$"
        txt = "replay silent reversed 3"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_silent_range_invalid(self):
        pattern = "^(replay( \d+(-\d+)?)? silent)$"
        txt = "replay 3 reverse"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_reverse_invalid(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed)$"
        txt = "repla"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_reverse_range_invalid(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed)$"
        txt = "replay 4fhg reversed"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_reverse_silent_invalid(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed silent)$"
        txt = "replay reversed jake 3"
        self.assertFalse(re.match(pattern, txt))


    def test_regex_reverse_silent_range_invalid(self):
        pattern = "^(replay( \d+(-\d+)?)? reversed silent)$"
        txt = "replay 5 reversed  -3 silent"
        self.assertFalse(re.match(pattern, txt))


if __name__ == '__main__':
    unittest.main()