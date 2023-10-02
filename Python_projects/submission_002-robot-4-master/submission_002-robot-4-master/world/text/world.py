def get_actual_orientation(robot, step):
    '''
    MOVED
    Function to get the actual move coordinates from the given
    step in the correct orientation
        Parameters: robot (the robot object), step (the step amount)
        Return: tuple (step-x, step-y)
    '''
    orient = robot.directions[robot.orientation]
    return tuple([step*x for x in orient])


def print_local(robot):
    '''
    function to display the position of the robot.
        Parameters: robot (the robot object)
        Return: None
    '''
    print(F" > {robot.name} now at position ({robot.x},{robot.y}).")


def valid_pos(robot, move):
    '''
    function to check whether the proposed move is valid.
        Parameters: robot (the robot object), move (tuple (x, y))
        Return: True (if valid), False (otherwise)
    '''
    move = tuple(map(sum, zip(robot.get_pos(), move)))
    if move[0] in robot.range_x and move[1] in robot.range_y:
        return True
    return False


def sprint(robot, cmd):
    '''
    Handler for recursive sprint function.
        Parameters: robot (the robot object), cmd ((list)the valid command)
        Returns: True (if move in valid range), False (otherwise)
    '''
    y = int(cmd[1])
    lim = sum(x for x in range(1, y+1))
    actual = robot.get_actual_orientation(lim)

    valid = robot.valid_pos(actual)
    if valid[0]:
        if valid[1]:
            print(F"{robot.name}: Sorry, there is an obstacle in the way.")
            return False
        step = robot.get_actual_orientation(y)
        sprint_rec(robot, step)
        robot.print_local()
        return True
    print(F"{robot.name}: Sorry, I cannot go outside my safe zone.")
    robot.print_local()
    return False


def sprint_rec(robot, step):
    '''
    Function to recursively step forward by step
        Parameters: robot (the robot object), step (the steo size)
        Returns: None
    '''
    if (step == (0, 0)):
        return
    y = [x for x in step if x != 0]
    y = y[0]
    delta = -1
    if not robot.silent:
        print(F" > {robot.name} moved forward by {abs(y)} steps.")
    robot.update_pos(*step)
    if y < 0:
        delta = 1
    step = tuple(x if x == 0 else x+delta for x in step)
    sprint_rec(robot, step)


def rotate(robot, cmd):
    '''
    Function to rotate the orientation in the given direction
        Parameters: robot (the robot object), cmd (the command - direction)
        Returns: True
    '''
    rot = list(robot.directions.keys())
    dist = robot.d
    if cmd[0] == 'right':
        print(F" > {robot.name} turned right.")
        dist += 1
    else:
        dist -= 1
        print(F" > {robot.name} turned left.")
    dist %= 4
    dir = rot[dist]
    robot.set_orientation(dir)
    robot.d = dist
    robot.print_local()
    return True


def forward(robot, cmd):
    '''
    Function to step forwards by given amount
        Parameters: robot (the robot object), cmd ((list)the valid command)
        Returns: True (if move in valid range), False (otherwise)
    '''
    y = int(cmd[1])
    actual =robot.get_actual_orientation(y)

    valid = robot.valid_pos(actual)
    if valid[0]:
        if valid[1]:
            print(F"{robot.name}: Sorry, there is an obstacle in the way.")
            return False
        robot.update_pos(*actual)
        # print update
        print(F" > {robot.name} moved forward by {y} steps.")
        robot.print_local()
        return True
    print(F"{robot.name}: Sorry, I cannot go outside my safe zone.")
    robot.print_local()
    return False


def back(robot, cmd):
    '''
    Function to step backwards by given amount
        Parameters: robot (the robot object), cmd ((list)the valid command)
        Returns: True (if move in valid range), False (otherwise)
    '''

    y = -1*int(cmd[1])
    actual = robot.get_actual_orientation(y)

    valid = robot.valid_pos(actual)
    if valid[0]:
        if valid[1]:
            print(F"{robot.name}: Sorry, there is an obstacle in the way.")
            return False
        robot.update_pos(*actual)
        # print update
        print(F" > {robot.name} moved back by {-y} steps.")
        robot.print_local()
        return True
    print(F"{robot.name}: Sorry, I cannot go outside my safe zone.")
    robot.print_local()
    return False


def off(robot=None, x=None):
    '''
        Function to 'exit' the game
        Parameters: robot (default None), x (default None)
        Returns: String
    '''
    return "EXIT"