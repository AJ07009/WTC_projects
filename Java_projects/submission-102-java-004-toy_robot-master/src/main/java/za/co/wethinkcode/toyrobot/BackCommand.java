package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class BackCommand extends Command {
    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        IWorld.UpdateResponse response = target.updatePosition(-nrSteps);

        switch (response) {
            case SUCCESS:
                target.setStatus("Moved back by "+nrSteps+" steps.");
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