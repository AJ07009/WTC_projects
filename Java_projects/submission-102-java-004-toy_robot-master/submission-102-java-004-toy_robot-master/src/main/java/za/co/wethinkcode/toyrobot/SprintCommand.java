package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class SprintCommand extends Command {
    public SprintCommand(String argument) {
        super("Sprint", argument);
    }

    private int totalSprintLength(int sprintNumber) {
        int count = 0;
        for (int i = 1; i <= sprintNumber; i++) {
            count += i;
        }
        return count;
    }

    @Override
    public boolean execute(Robot target) {
        
        int sprintNumber = Integer.parseInt(this.getArgument());
        int total = totalSprintLength(sprintNumber);
        IWorld.UpdateResponse response = target.updatePosition(total);

        switch (response) {
            case SUCCESS:
                Command reset = new BackCommand(Integer.toString(total));
                reset.execute(target);
                for (int i = sprintNumber; i > 0; i--) {
                    Command forward = new ForwardCommand(Integer.toString(i));

                    forward.execute(target);
                    if (i != 1) {
                        Play.printStatus(target.toString());
                    }
                }
                break;
            case FAILED_OUTSIDE_WORLD:
                target.setStatus("Sorry, I cannot go outside my safe zone.");
                target.setObstructed(true);
                break;
            default:
                target.setStatus("Sorry, there is an obstacle in the way.");
                target.setObstructed(true);
                break;
        }
        return true;
    }
}