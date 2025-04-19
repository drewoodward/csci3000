package assn2.scheduling;

/**
 * This algorithm will run the highest priority task. Tasks of equal priority
 * will run for a time quantum using a round-robin scheme.
 *
 * A higher numerical priority indicates a higher relative priority.
 */
 
import java.util.*;

public class PriorityRR implements Algorithm
{
    public static final int QUANTUM = 10;

    private List<Task> queue;
    private Task currentTask;

    public PriorityRR(List<Task> queue) {
        this.queue = queue;
    }

    public void schedule() {
        System.out.println("Priority with RR Scheduling \n");

        int burst = 0;
        boolean reschedule = true;

        while (!queue.isEmpty()) {
            if (reschedule) 
                currentTask = pickNextTask();

            // if there is another task in the queue
            // that has the same or equal priority, run
            // the current task for at most a time quantum
            if (needToReschedule()) {
                if (currentTask.getBurst() < QUANTUM)
                    burst = currentTask.getBurst();
                else
                    burst = QUANTUM;

                // execute the current task on the CPU
                CPU.run(currentTask, burst);

                /**
                 * Reduce the burst of the current task
                 * and remove it from the queue if it has completed.
                 */
                currentTask.setBurst(currentTask.getBurst() - burst);
            }
            else {
                CPU.run(currentTask, currentTask.getBurst());
                currentTask.setBurst(0);
            }

            if (currentTask.getBurst() == 0) {
                System.out.println("Task " + currentTask.getName() + " finished.\n");
                queue.remove(currentTask);

                reschedule = true;
            }
            else {
                // place the current task at the rear of the queue
                queue.remove(currentTask);
                queue.add(currentTask);

                // determine the need to reschedule
                reschedule = needToReschedule();
            }
        }
    }

    // determine if we need to select a new task
    // this is true if there is another task in the
    // queue with the same or equal priority as the 
    // current task.
    private boolean needToReschedule() {
        boolean rv = false;

        int currentPriority = currentTask.getPriority();

        for (Task task : queue) {
            if (!task.equals(currentTask)) {
                if (task.getPriority() >= currentPriority) {
                    rv = true;
                    
                    break;
                }
            }
        }
       
        return rv;
    }

    public Task pickNextTask() {
        int highestIndex = 0;
        Task highestTask = queue.get(highestIndex);
        int highestPriority = highestTask.getPriority();

        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i).getPriority() > highestPriority) {
                // we have a new high priority task
                highestIndex = i;
                highestPriority = queue.get(i).getPriority();
            }
        }

        highestTask = queue.get(highestIndex);

        return highestTask;
    }
}
