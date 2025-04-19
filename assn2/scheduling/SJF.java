package assn2.scheduling;

/**
 * SJF scheduling algorithm.
 */
 
import java.util.*;

public class SJF implements Algorithm
{
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;

    public SJF(List<Task> queue) {
        this.queue = queue;

        tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("SJF Scheduling\n");

        while (!queue.isEmpty()) {
            currentTask = pickNextTask();
            
            CPU.run(currentTask, currentTask.getBurst());
        
            // now remove the task
            queue.remove(currentTask);
        }
    }

    public Task pickNextTask() {
        int sjfIndex = 0;
        Task sjfTask = queue.get(sjfIndex);
        int sjfBurst = sjfTask.getBurst();

        for (int i = 1; i < queue.size(); i++) {
            if (queue.get(i).getBurst() < sjfBurst) {
                // we have a new SJF
                sjfIndex = i;
                sjfBurst = queue.get(i).getBurst();
            }
        }

        // now select the task
        sjfTask = queue.get(sjfIndex);

        return sjfTask;
    }
}
