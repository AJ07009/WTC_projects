import unittest
import sys
from test_base import run_unittests
import word_processor


class MyTestCase(unittest.TestCase):
    def test_step1_convert_word_list_1(self):
        words = word_processor.convert_to_word_list(
            'These are indeed interesting, an obvious understatement, times. What say you?')
        self.assertEqual(
            ['these', 'are', 'indeed', 'interesting', 'an', 'obvious', 'understatement', 'times', 'what', 'say', 'you'],
            words)

    def test_step1_convert_word_list_empty(self):
        words = word_processor.convert_to_word_list('')
        self.assertEqual([], words)

    def test_step1_convert_word_list_spaces(self):
        words = word_processor.convert_to_word_list('one  two     ')
        self.assertEqual(['one', 'two'], words)