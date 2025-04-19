package assn2.scheduling;

/**
 * Non-preemptive priority scheduling algorithm.
 *
 * A higher numerical priority indicates a higher relative priority.
 */
 
import java.util.*;

public class Priority implements Algorithm
{
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;

    public Priority(List<Task> queue) {
        this.queue = queue;

        tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("Priority Scheduling \n");

        while (!queue.isEmpty()) {
            currentTask = pickNextTask();
            
            CPU.run(currentTask, currentTask.getBurst());

            // now remove the task
            queue.remove(currentTask);
        }
    }

    public Task pickNextTask() {
        int highestIndex = 0;
        Task highestTask = queue.get(highestIndex);
        int highestPriority = highestTask.getPriority();

        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i).getPriority() > highestPriority) {
                // we have a new hig priority task
                highestIndex = i;
                highestPriority = queue.get(i).getPriority();
            }
        }

        // now get the task
        highestTask = queue.get(highestIndex);

        return highestTask;
    }
}
