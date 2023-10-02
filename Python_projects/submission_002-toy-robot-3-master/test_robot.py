import unittest
from unittest.case import TestCase
from robot import*
from unittest.mock import patch
from io import StringIO


class TestCase(unittest.TestCase):
    @patch("sys.stdin", StringIO("Chloe\n"))
    def test_robot_input_name(self):
        self.assertEqual(get_name(),"Chloe","Lol poes")
     
    
    @patch("sys.stdin", StringIO("UP\nHelp\nPoes"))
    def test_robot_string(self):
        self.assertEqual(prompt("Chloe"), "Help","Lol poes")

  
    def test_help_output(self):
        help_string = "I can understand these commands:\n"
        help_string += "OFF  - Shut down robot\n"
        help_string += "HELP - provide information about commands\n"
        self.assertEqual(get_help(), help_string)


    # @patch("sys.stdin",StringIO("Chloe\noff\nBoiTahir"))
    # def test_shutdown(self):
    #     self.assertEqual(run_game(),)   


if __name__ == '__main__':
    unittest.main()



        





