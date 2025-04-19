package assn2.scheduling;

/**
 * Non-preemptive priority scheduling algorithm using RR.
 *
 * This algorithm will run tasks according to round-robin scheduling.
 */
 
import java.util.*;

public class RR implements Algorithm
{
    public static final int QUANTUM = 10;

    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;

    public RR(List<Task> queue) {
        this.queue = queue;

        tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("RR Scheduling \n");

        int burst = 0;
        boolean reschedule = true;

        while (!queue.isEmpty()) {
            if (reschedule)
                currentTask = pickNextTask();

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

            if (currentTask.getBurst() == 0) {
                System.out.println("Task " + currentTask.getName() + " finished.");
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
    private boolean needToReschedule() {
        return true;
    }

    public Task pickNextTask() {
        Task nextTask = queue.get(0);

        return nextTask;
    }
}
