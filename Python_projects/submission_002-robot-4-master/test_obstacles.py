import unittest
from world.obstacles import *
import sys

class Test(unittest.TestCase):
    def test_one(self):
        o  = Obstacle()
        o.list = [(3, 5), (-50, 0)]
        self.assertTrue(o.is_position_blocked(4, 5))

    def test_two(self):
        o  = Obstacle()
        o.list = [(3, 5), (-50, 0)]
        self.assertFalse(o.is_path_blocked(3, 0, 3, 4))


    def test_three(self):
        o  = Obstacle()
        o.list = [(3, 5), (-50, 0)]
        self.assertFalse(o.is_path_blocked(0, 0, -90, 0))


    def test_four(self):
        o  = Obstacle()
        o.list = [(3, 5), (-50, 0)]
        self.assertTrue(o.is_position_blocked(-48, 0))


if __name__ == '__main__':
    unittest.main()
