package sctd.logic.command.queue;

import sctd.logic.command.CommandProcessor;
import sctd.logic.command.GameCommand;
import sctd.logic.command.PatrolCommand;
import sctd.model.GameUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Shabanov
 */
public final class GameCommandQueue {
  private final List<GameCommand> commands = new ArrayList<>();
  private GameCommand currentCommand = null;
  private final List<GameCommand> patrolCommands = new ArrayList<>();
  private int nextPatrolCommand = 0;
  private PatrolCommand moveToSourcePatrolCommand;

  public void newCommand(GameCommand command) {
    this.commands.clear();
    this.patrolCommands.clear();
    this.commands.add(command);
    this.currentCommand = null;
    this.nextPatrolCommand = 0;
    this.moveToSourcePatrolCommand = null;
  }

  public void addCommand(GameCommand command) {
    this.commands.add(command);
  }

  public void execute(GameUnit unit, CommandProcessor dispatcher) {
    if (this.currentCommand == null) {
      tryGetNextCommand(unit);
    }

    if (this.currentCommand != null) {
      this.currentCommand.execute(unit, dispatcher);
    } else {
      dispatcher.standStill(unit);
    }
  }

  /**
   * consider current command completed and get next one.
   */
  public void completeCommand() {
    this.currentCommand = null;
  }

  //
  // Private
  //

  private void tryGetNextCommand(GameUnit unit) {
    //System.out.println("Trying to get next command for unit #" + unit.getId());

    // get next command
    if (!this.commands.isEmpty()) {
      final GameCommand command = this.commands.remove(0);
      if (command instanceof PatrolCommand) {
        // special case - patrol command (repeatable)
        if (this.patrolCommands.isEmpty()) {
          // add special move-to-source command (so that unit will be able to return where it started
          this.moveToSourcePatrolCommand = new PatrolCommand((int) unit.getX(), (int) unit.getY());
        }
        this.patrolCommands.add(command);
      } else {
        // next command is non-patrol - cancel patrol
        this.nextPatrolCommand = 0;
        this.moveToSourcePatrolCommand = null;
        this.patrolCommands.clear();
      }

      this.currentCommand = command;
    } else if (!this.patrolCommands.isEmpty()) {
      // execute next patrol command
      if (this.nextPatrolCommand == this.patrolCommands.size()) {
        this.nextPatrolCommand = 0;
        this.currentCommand = this.moveToSourcePatrolCommand;
      } else {
        this.currentCommand = this.patrolCommands.get(this.nextPatrolCommand);
        this.nextPatrolCommand = this.nextPatrolCommand + 1;
      }
    }

    //System.out.println("next command for unit #" + unit.getId() + " is " + this.currentCommand);
  }
}
